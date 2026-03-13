package oskar.projects.bot.price_checker;

import oskar.projects.bot.price_checker.model.Compare;
import oskar.projects.bot.price_checker.model.Result;
import oskar.projects.bot.price_checker.model.scrapers.Braasport;
import oskar.projects.bot.price_checker.model.scrapers.Skistar;

public class Main {
  static void main() throws Exception{
    try {
      //Compare compare = new Compare();
      String output = new Skistar().checkPrice("atomic hawx ultra xtd 130 ", "27,5").toString();

      java.nio.file.Files.writeString(java.nio.file.Path.of("target/resultat.txt"), output);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
