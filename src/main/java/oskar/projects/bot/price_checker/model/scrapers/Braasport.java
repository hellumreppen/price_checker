package oskar.projects.bot.price_checker.model.scrapers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import oskar.projects.bot.price_checker.model.Result;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Braasport implements MainScraper {

  private Double price;

  public Result checkPrice(String searchWord, String preferredSize) throws Exception{
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
            .uri(URI.create("https://braasport-no.54proxy.com/search"))
            .header("Content-Type", "application/json;charset=UTF-8")
            .header("Accept", "application/json, text/plain, */*")
            .header("Api-Version", "V3")
            .header("Lib-Version", "JS:1.9.128")
            .header("User-Id", "5hLga0MXGD")
            .header("Origin", "https://braasport.no")
            .header("Referer", "https://braasport.no/")
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(response.body());
    JsonNode products = root.get("results").get("items");

    JsonNode product = products.get(0);
    String name = findPrice(product, "Name");
    String ordinaryPrice = findPrice(product, "RetailPrice");
    String membershipPrice = findPrice(product, "MembershipPrice");
    boolean hasSize = hasSize(product, "Attributes.sizeInStock", preferredSize);

    if (membershipPrice != null) {
      price = Double.parseDouble(membershipPrice);
    } else {
      price = Double.parseDouble(ordinaryPrice);
    }

    return new Result("Braasport.no", name, price, hasSize);
  }

  static String findPrice(JsonNode product, String attributeName) {
    for (JsonNode attr : product.get("attributes")) {
      if (attr.get("name").asText().equals(attributeName)) {
        return attr.get("values").get(0).asText();
      }
    }
    return "ikke funnet";
  }

  static boolean hasSize(JsonNode product, String attributeName, String size) {
    for (JsonNode attr : product.get("attributes")) {
      if (attr.get("name").asText().equals(attributeName)) {
        for (JsonNode val : attr.get("values")) {
          if (val.asText().equals(size)) {
            return true;
          }
        }
      }
    }
    return false;
  }
}
