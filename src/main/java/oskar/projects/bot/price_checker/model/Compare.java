package oskar.projects.bot.price_checker.model;

import oskar.projects.bot.price_checker.model.scrapers.Braasport;
import oskar.projects.bot.price_checker.model.scrapers.Fjellsport;
import oskar.projects.bot.price_checker.model.scrapers.Skistar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Compare {

  List<Result> results = new ArrayList<>();

  public Compare() throws Exception {
    results.add(new Braasport().checkPrice("tecnica zero g tour pro", "27,5"));
    results.add(new Skistar().checkPrice("tecnica zero g tour pro", "27,5"));
    results.add((new Fjellsport()).checkPrice("tecnica zero g pro", "27,5"));
  }

  public Result getLowestPrice() throws Exception {
    return results.stream()
            .filter(Objects::nonNull)
            .filter(Result::hasSize)
            .min((r1,r2) -> Double.compare(r1.getPrice(), r2.getPrice()))
            .orElse(null);
  }
}
