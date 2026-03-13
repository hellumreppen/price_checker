package oskar.projects.bot.price_checker;

import oskar.projects.bot.price_checker.model.SkiBootApiScraper;

public class Main {
  static void main() throws Exception{
    SkiBootApiScraper apiScraper = new SkiBootApiScraper("tecnica zero g tour pro", "27,5");
    System.out.println(apiScraper.checkSkistar("atomic hawx ultra xtd 130", "27,5"));
  }

}
