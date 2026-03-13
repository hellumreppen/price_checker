package oskar.projects.bot.price_checker;

import oskar.projects.bot.price_checker.model.Compare;
import oskar.projects.bot.price_checker.model.scrapers.Braasport;
import oskar.projects.bot.price_checker.model.scrapers.Skistar;

public class Main {
  static void main() throws Exception{
    Compare compare = new Compare();
    System.out.println(new Braasport().checkPrice("atomic hawx ultra xtd 130", "27,5"));
    System.out.println(new Skistar().checkPrice("atomic hawx ultra xtd 130", "27,5"));
    System.out.println("-------------------\nBilligste pris er : \n");
    System.out.println(compare.getLowestPrice());
  }

}
