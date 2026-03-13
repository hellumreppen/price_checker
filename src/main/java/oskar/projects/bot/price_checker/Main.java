package oskar.projects.bot.price_checker;

import oskar.projects.bot.price_checker.model.Compare;
import oskar.projects.bot.price_checker.model.scrapers.Braasport;
import oskar.projects.bot.price_checker.model.scrapers.Skistar;

public class Main {
  static void main() throws Exception{
    try {
      Compare compare = new Compare();
      System.out.println("Billigste pris som har størrelse 27,5 er : \n -------------------\n");
      System.out.println(compare.getLowestPrice());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
