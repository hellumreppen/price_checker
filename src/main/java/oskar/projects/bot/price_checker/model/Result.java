package oskar.projects.bot.price_checker.model;

import java.util.Objects;

public class Result {
  String store;
  String name;
  String price;
  boolean hasSize;

  Result(String store, String name, String price, boolean hasSize) {
    this.store = Objects.requireNonNull(store);
    this.name = Objects.requireNonNull(name);
    this.price = Objects.requireNonNull(price);
    this.hasSize = Objects.requireNonNull(hasSize);
  }

  public String getStore() {
    return store;
  }

  public String getName() {
    return name;
  }

  public String getPrice() {
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
