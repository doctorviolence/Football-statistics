import exceptions.CrawlerException;

public class Main {

    public static void main(String[] args) throws CrawlerException {
        crawler.FootballCrawler crawler = new crawler.FootballCrawler();
        crawler.crawlForTeamStats(2017);
        crawler.crawlForPlayerUrls(2017);
        crawler.crawlForPlayerStats("https://www.pro-football-reference.com/players/G/GronRo00.htm", 2017);
    }

}
