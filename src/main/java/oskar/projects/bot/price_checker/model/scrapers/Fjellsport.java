package oskar.projects.bot.price_checker.model.scrapers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import oskar.projects.bot.price_checker.model.Result;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Fjellsport implements MainScraper {
  private final ObjectMapper mapper = new ObjectMapper();

  public Result checkPrice(String searchWord, String preferredSize) throws Exception {
    preferredSize = preferredSize.replace(".", ",");

    String productUrl = "https://www.fjellsport.no/api/content/turutstyr/ski-utstyr/skisko-stovler/toppturstovel/tecnica-zero-g-pro-dusk-orange-552559";

    HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(productUrl))
            .header("Accept", "application/json")
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36")
            .header("X-Requested-With", "XMLHttpRequest")
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    JsonNode root = mapper.readTree(response.body());

    String name = root.get("displayName").asText();
    double price = root.get("price").get("current").get("inclVat").asDouble();

    boolean hasSize = false;
    for (JsonNode variant : root.get("variants")) {
      String selectorLabel = variant.get("selectorLabel").asText();
      boolean isBuyable = variant.get("isBuyable").asBoolean();
      if (selectorLabel.equals(preferredSize) && isBuyable) {
        hasSize = true;
        break;
      }
    }

    return new Result("Fjellsport", name, price, hasSize);
  }
}
