package oskar.projects.bot.price_checker.model.scrapers;

import oskar.projects.bot.price_checker.model.Result;

public interface MainScraper {

  Result checkPrice(String searchWord, String preferredSize) throws Exception;
}
