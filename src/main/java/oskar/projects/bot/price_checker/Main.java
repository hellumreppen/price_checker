package oskar.projects.bot.price_checker;

import oskar.projects.bot.price_checker.model.ApiScraper;

public class Main {
  static void main() throws Exception{
    ApiScraper apiScraper = new ApiScraper();
    apiScraper.getPrices();
  }

}
