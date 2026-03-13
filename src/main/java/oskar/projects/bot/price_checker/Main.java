package oskar.projects.bot.price_checker;

import oskar.projects.bot.price_checker.model.Compare;
import oskar.projects.bot.price_checker.model.Result;
import oskar.projects.bot.price_checker.model.scrapers.Braasport;
import oskar.projects.bot.price_checker.model.scrapers.Skistar;

public class Main {
  static void main() throws Exception{
    try {
      String output = "";
      Compare compare = new Compare();
      if (compare.getLowestPrice() != null) {
        output = compare.getLowestPrice().toString();
      } else {
        output = "Ingen varer i din størrelse:(";
      }

      java.nio.file.Files.writeString(java.nio.file.Path.of("src/main/resources/resultat.txt"), output);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
