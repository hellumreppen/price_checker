package oskar.projects.bot.price_checker.model.scrapers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import oskar.projects.bot.price_checker.model.Result;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Skistar implements MainScraper{

  public Result checkPrice(String searchWord, String preferredSize) throws Exception{
    preferredSize = preferredSize.replace(",", ".");

    String url = "https://wc1a73d47.api.esales.apptus.cloud/api/storefront/v3/queries/search-page"
            + "?market=NO"
            + "&customerKey=e82450a9-1895-46e1-9076-be961a7dd8f6"
            + "&sessionKey=7302c58-04e0-4127-8b73-3deb7cc9d480"
            + "&locale=nb-NO"
            + "&touchpoint=desktop"
            + "&limit=24&skip=0"
            + "&q=" + searchWord.replace(" ", "+")
            + "&presentPrices=member_bas";

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Accept", "application/json")
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(response.body());

    JsonNode productGroups = root.get("primaryList").get("productGroups");
    boolean exact = true;

    if (productGroups.isEmpty()) {
      productGroups = root.get("secondaryList").get("productGroups");
      exact = false;
    }

    // Returner tomt resultat hvis ingen treff
    if (productGroups.isEmpty()) {
      return new Result("Skistar Shop", "Ikke funnet", null, false);
    }

    JsonNode product0 = productGroups.get(0).get("products").get(0);

    String name = product0.get("title").asText();
    Double price = product0.get("sellingPrice").get("min").asDouble();

    boolean hasSize = false;
    for (JsonNode variant : product0.get("variants")) {
      if (variant.get("size").asText().equals(preferredSize)
              && variant.get("inStock").asBoolean()) {
        hasSize = true;
        break;
      }
    }

    return new Result("Skistar Shop", name, price, hasSize);
  }
}
