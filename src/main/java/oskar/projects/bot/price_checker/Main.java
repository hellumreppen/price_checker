package oskar.projects.bot.price_checker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  static void main() throws Exception {
    String url = "https://braasport.no/sok?q=tecnica%20zero%20g%20tour%20pro";

    Document doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0")
            .get();

    Elements prices = doc.select(".price");
    for  (Element p : prices) {
      System.out.println(p.text());
    }

  }
}
