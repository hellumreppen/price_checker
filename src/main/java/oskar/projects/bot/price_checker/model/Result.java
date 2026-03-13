package oskar.projects.bot.price_checker.model;

import java.util.Objects;

import static java.lang.String.valueOf;

public class Result {
  String store;
  String name;
  Double price;
  boolean hasSize;

  public Result(String store, String name, Double price, boolean hasSize) {
    this.store = Objects.requireNonNull(store);
    this.name = Objects.requireNonNull(name);
    this.price = price;
    this.hasSize = Objects.requireNonNull(hasSize);
  }

  public String getStore() {
    return store;
  }

  public String getName() {
    return name;
  }

  public Double getPrice() {
    return price;
  }

  public boolean hasSize() {
    return hasSize;
  }

  @Override
  public String toString() {
    return "Produkt: " + name + "\n"
            + "Laveste pris: " + price + " kr" + "\n"
            + "Størrelse 27,5 på lager: " + (hasSize ? "JA" : "Nei");
  }
}
