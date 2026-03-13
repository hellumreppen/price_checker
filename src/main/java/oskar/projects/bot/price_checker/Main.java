package oskar.projects.bot.price_checker;

import oskar.projects.bot.price_checker.model.SkiBootApiScraper;

public class Main {
  static void main() throws Exception{
    SkiBootApiScraper apiScraper = new SkiBootApiScraper("atomic hawx ultra 130 xtd boa", "27,5");
    apiScraper.getPrices();
  }

}
