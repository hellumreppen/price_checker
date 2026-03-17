package oskar.projects.bot.price_checker.model.scrapers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import oskar.projects.bot.price_checker.model.Result;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Antonsport implements MainScraper {
  private final ObjectMapper mapper = new ObjectMapper();

  public Result checkPrice(String searchWord, String preferredSize) throws Exception {
    // Konverter "27,5" eller "27.5" → "275"
    String sizeFormatted = preferredSize.replace(",", "").replace(".", "");

    String requestBody = """
                {
                    "query": "%s",
                    "relatedResultsOptions": {"skip": 0, "take": 5},
                    "resultsOptions": {
                        "sortBy": [{"type": "relevance", "order": "desc"}],
                        "take": 40,
                        "skip": 0
                    }
                }
                """.formatted(searchWord);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://antonsport-no-prod.54proxy.com/search"))
            .header("User-Id", "a1119274-dbe6-4e11-8f07-95c8f7a5e3a4")
            .header("Content-Type", "application/json;charset=UTF-8")
            .header("Accept", "application/json, text/plain, */*")
            .header("Api-Version", "V3")
            .header("Lib-Version", "JS:1.9.128")
            .header("Origin", "https://www.antonsport.no")
            .header("Referer", "https://www.antonsport.no/")
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println("Body: " + response.body().substring(0, 500));

    JsonNode root = mapper.readTree(response.body());
    JsonNode items = root.get("results").get("items");

    if (items == null || items.isEmpty()) {
      return new Result("Antonsport", "Ikke funnet", 0.0, false);
    }

    JsonNode product = items.get(0);

    // Hent navn og pris
    String name = findValue(product, "name");
    double price = Double.parseDouble(findValue(product, "price.current"));

    // Parse FullDataObject for varianter
    String fullDataJson = findValue(product, "FullDataObject");
    JsonNode fullData = mapper.readTree(fullDataJson);

    boolean hasSize = false;
    for (JsonNode variant : fullData.get("variants")) {
      String size = variant.get("size").asText();
      int available = variant.get("stock").get("available").asInt();
      if (size.equals(sizeFormatted) && available > 0) {
        hasSize = true;
        break;
      }
    }

    return new Result("Antonsport", name, price, hasSize);
  }

  static String findValue(JsonNode product, String attributeName) {
    for (JsonNode attr : product.get("attributes")) {
      if (attr.get("name").asText().equals(attributeName)) {
        return attr.get("values").get(0).asText();
      }
    }
    return "ikke funnet";
  }
}
