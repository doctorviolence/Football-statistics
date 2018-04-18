import java.util.Set;

public class Main {

    public static void main(String[] args) {
        FootballCrawler crawler = new FootballCrawler();
        Projection projection = new Projection();

        Team[] test = crawler.crawlForTeamStats(2017);

        for (Team t : test) {
            System.out.println(t);
        }

        crawler.crawlForPlayerUrls(2017);

        Set<Player> players = crawler.crawlForPlayerUrls(2017);

        for (Player p : players){
            System.out.println(p.toString());
        }

        // TEST
        crawler.crawlForPlayerStats("https://www.pro-football-reference.com/players/G/GronRo00.htm", 2);

        projection.getFootballTeams(test);

    }

}
