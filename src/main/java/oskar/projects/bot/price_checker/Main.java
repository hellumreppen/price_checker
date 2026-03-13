package oskar.projects.bot.price_checker;

import oskar.projects.bot.price_checker.model.SkiBootApiScraper;

public class Main {
  static void main() throws Exception{
    SkiBootApiScraper apiScraper = new SkiBootApiScraper("atomic hawx ultra xtd 130", "27,5");
    apiScraper.getPrices();
  }

}
