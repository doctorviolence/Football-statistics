import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FootballCrawler {

    private String[] teamAcronyms = {
            "atl", "buf", "car", "crd", "chi", "cin", "cle", "clt", "dal", "den", "det", "gnb", "htx", "jax", "kan", "mia",
            "min", "nwe", "nor", "nyg", "nyj", "oti", "phi", "pit", "rai", "ram", "rav", "sdg", "sea", "sfo", "tam", "was"};

    private String[] newTeamNames = {
            "Arizona Cardinals", "Atlanta Falcons", "Baltimore Ravens", "Buffalo Bills",
            "Carolina Panthers", "Chicago Bears", "Cincinnati Bengals", "Cleveland Browns",
            "Dallas Cowboys", "Denver Broncos", "Detroit Lions", "Green Bay Packers",
            "Houston Texans", "Indianapolis Colts", "Jacksonville Jaguars", "Kansas City Chiefs",
            "Miami Dolphins", "Minnesota Vikings", "New England Patriots", "New Orleans Saints",
            "New York Giants", "New York Jets", "Oakland Raiders", "Philadelphia Eagles",
            "Pittsburgh Steelers", "Los Angeles Rams", "Los Angeles Chargers", "San Francisco 49ers",
            "Seattle Seahawks", "Tampa Bay Buccaneers", "Tennessee Titans", "Washington Redskins"
    };

    private String[] sixteenTeamNames = {
            "Arizona Cardinals", "Atlanta Falcons", "Baltimore Ravens", "Buffalo Bills",
            "Carolina Panthers", "Chicago Bears", "Cincinnati Bengals", "Cleveland Browns",
            "Dallas Cowboys", "Denver Broncos", "Detroit Lions", "Green Bay Packers",
            "Houston Texans", "Indianapolis Colts", "Jacksonville Jaguars", "Kansas City Chiefs",
            "Los Angeles Rams", "Miami Dolphins", "Minnesota Vikings", "New England Patriots",
            "New Orleans Saints", "New York Giants", "New York Jets", "Oakland Raiders",
            "Philadelphia Eagles", "Pittsburgh Steelers", "San Diego Chargers", "San Francisco 49ers",
            "Seattle Seahawks", "Tampa Bay Buccaneers", "Tennessee Titans", "Washington Redskins"
    };

    private String[] oldTeamNames = {
            "Arizona Cardinals", "Atlanta Falcons", "Baltimore Ravens", "Buffalo Bills",
            "Carolina Panthers", "Chicago Bears", "Cincinnati Bengals", "Cleveland Browns",
            "Dallas Cowboys", "Denver Broncos", "Detroit Lions", "Green Bay Packers",
            "Houston Texans", "Indianapolis Colts", "Jacksonville Jaguars", "Kansas City Chiefs",
            "St. Louis Rams", "Miami Dolphins", "Minnesota Vikings", "New England Patriots",
            "New Orleans Saints", "New York Giants", "New York Jets", "Oakland Raiders",
            "Philadelphia Eagles", "Pittsburgh Steelers", "San Diego Chargers", "San Francisco 49ers",
            "Seattle Seahawks", "Tampa Bay Buccaneers", "Tennessee Titans", "Washington Redskins"
    };

    public Team[] crawlForTeamStats(int season) {
        String requestUrl = String.format("https://www.pro-football-reference.com/years/%s", season);
        Team[] teams = new Team[32];
        String[] teamNames = null;

        if (season >= 2017) {
            teamNames = newTeamNames;
        } else if (season == 2016) {
            teamNames = sixteenTeamNames;
        } else {
            teamNames = oldTeamNames;
        }

        try {
            Document doc = Jsoup.connect(requestUrl).userAgent("Safari").timeout(3000).get();

            Elements stats = doc.select("table");

            if (stats != null) {
                for (int i = 0; i < 32; i++) {
                    Elements e = stats.select(String.format("tr:contains(%s)", teamNames[i]));

                    String name = teamNames[i];
                    int w = Integer.parseInt(e.select("[data-stat='wins']").text());
                    int l = Integer.parseInt(e.select("[data-stat='losses']").text());
                    int pf = Integer.parseInt(e.select("[data-stat='points']").text());
                    int pa = Integer.parseInt(e.select("[data-stat='points_opp']").text());

                    Team team = new Team(name, season, w, l, pf, pa);
                    teams[i] = team;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return teams;
    }

    public Set<Player> crawlForPlayerUrls(int season) {
        Document[] docs = new Document[32];
        Elements[] players = new Elements[32];
        Set<Player> playersCrawled = new HashSet<Player>();

        try {
            for (int i = 0; i < teamAcronyms.length; i++) {
                String requestUrl = String.format("https://www.pro-football-reference.com/teams/%s/%s_roster.htm", teamAcronyms[i], season);
                docs[i] = Jsoup.connect(requestUrl).timeout(5000).get();
                String teamName = docs[i].select(String.format("a[href=/teams/%s/]:first-child", teamAcronyms[i])).first().text();
                players[i] = docs[i].select("#starters td:nth-child(2) a");

                if (players[i] != null) {
                    for (Element e : players[i]) {
                        String name = e.text();
                        String playerUrl = e.attr("abs:href");

                        Team t = new Team(teamName, season);
                        Player p = new Player(name, playerUrl, t);
                        playersCrawled.add(p);
                    }
                    System.out.println("Crawling players from " + teamName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playersCrawled;
    }

    /*
    IN PROGRESS: send player url, season as parameter... crawl page for stats in given season... return player stats
     */
    public Player crawlForPlayerStats(String url, int season) {
        Player p = new Player();
        try {
            Document doc = Jsoup.connect(url).timeout(5000).get();
            String position = doc.select("td[data-stat='pos']").first().text();

            if (doc != null) {
                p.setPosition(position);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NotImplementedException();
    }

}
