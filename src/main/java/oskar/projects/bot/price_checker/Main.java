package oskar.projects.bot.price_checker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  static void main() throws Exception {
    String requestBody = """
            {
                "query": "tecnica zero g tour pro",
                "relatedResultsOptions": {"skip": 0, "take": 5},
                "resultsOptions": {
                    "sortBy": [{"type": "relevance", "order": "desc"}],
                    "take": 40,
                    "skip": 0
                }
            }
            """;

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://braasport-no.54proxy.com/search"))
            .header("Content-Type", "application/json;charset=UTF-8")
            .header("Accept", "application/json, text/plain, */*")
            .header("Accept-Language", "nb-NO,nb;q=0.9")
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
    JsonNode produkter = root.get("results").get("items");

    for (JsonNode produkt : produkter) {
      String navn = finnVerdi(produkt, "Name");
      String pris = finnVerdi(produkt, "RetailPrice");
      String medlemspris = finnVerdi(produkt, "MembershipPrice");

      System.out.println("Produkt:      " + navn);
      System.out.println("Ordinær pris: " + pris + " kr");
      System.out.println("Medlemspris:  " + medlemspris + " kr");
      System.out.println("---");
    }
  }
  // Hjelpemetode som leter gjennom attributes-listen etter riktig navn
  static String finnVerdi(JsonNode produkt, String attributtNavn) {
    for (JsonNode attr : produkt.get("attributes")) {
      if (attr.get("name").asText().equals(attributtNavn)) {
        return attr.get("values").get(0).asText();
      }
    }
    return "ikke funnet";
  }
}
