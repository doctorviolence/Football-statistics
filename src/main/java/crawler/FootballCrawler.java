package crawler;

import exceptions.CrawlerException;
import model.Player;
import model.PlayerStat;
import model.Team;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    /*
        https://www.pro-football-reference.com/teams/ + teamURL
     */
    public Team[] crawlForTeamStats(int season) throws CrawlerException {
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
                    String teamUrl = String.format("/teams/%s/%s.htm", teamAcronyms[i], season);
                    int w = NumberUtils.toInt(e.select("[data-stat='wins']").text(), 0);
                    int l = NumberUtils.toInt(e.select("[data-stat='losses']").text(), 0);
                    int pf = NumberUtils.toInt(e.select("[data-stat='points']").text(), 0);
                    int pa = NumberUtils.toInt(e.select("[data-stat='points_opp']").text(), 0);

                    Team team = new Team(name, teamUrl, season, w, l, pf, pa);
                    teams[i] = team;
                }
            }

        } catch (IOException e) {
            throw new CrawlerException("There was an error crawling for team stats: ", e);
        }
        return teams;
    }

    /*
        https://www.pro-football-reference.com/teams/ + teamURL
     */
    public Set<Player> crawlForPlayerUrls(int season) throws CrawlerException {
        Document[] docs = new Document[32];
        Elements[] players = new Elements[32];
        Set<Player> playersCrawled = new HashSet<>();

        try {
            for (int i = 0; i < teamAcronyms.length; i++) {
                String requestUrl = String.format("https://www.pro-football-reference.com/teams/%s/%s_roster.htm", teamAcronyms[i], season);
                docs[i] = Jsoup.connect(requestUrl).timeout(5000).get();
                //String teamName = docs[i].select(String.format("a[href=/teams/%s/]:first-child", teamAcronyms[i])).first().text();
                players[i] = docs[i].select("#starters td:nth-child(2) a");

                if (players[i] != null) {
                    for (Element e : players[i]) {
                        String name = e.text();
                        //String teamUrl = String.format("/teams/%s/%s.htm", teamAcronyms[i], season);
                        String playerUrl = e.attr("abs:href");

                        Player p = new Player(name, playerUrl);
                        playersCrawled.add(p);
                    }
                    //System.out.println("Crawling players from " + teamName);
                }
            }
        } catch (IOException e) {
            throw new CrawlerException("There was an error crawling for player urls: ", e);
        }
        return playersCrawled;
    }

    /*
        IN PROGRESS: Send player url, crawl page for stats in given season. Returns player stats
     */
    public Player crawlForPlayerStats(String url, int season) throws CrawlerException {
        PlayerStat p = new PlayerStat();

        try {
            Document doc = Jsoup.connect(url).timeout(5000).get();
            Elements stats = doc.select(String.format("tr:contains(%s)", season));

            if (stats != null) {
                System.out.println(stats.toString());

                String position = stats.select("td[data-stat='pos']").first().text();
                String teamUrl = stats.select("a").get(1).attr("href");
                int games = NumberUtils.toInt(stats.select("[data-stat='g']").text(), 0);

                p.setPosition(position);
                p.setTeamUrl(teamUrl);
                p.setGames(games);

                // Offensive stats
                if (p.playsOffense() && !p.playsDefense() && !p.playsSpecialTeams()) {
                    p.setPassingTds(NumberUtils.toInt(stats.select("[data-stat='pass_td']").text(), 0));
                    p.setPassingYds(NumberUtils.toInt(stats.select("[data-stat='pass_yds']").text(), 0));
                    p.setPassingInts(NumberUtils.toInt(stats.select("[data-stat='pass_int']").text(), 0));
                    p.setReceptions(NumberUtils.toInt(stats.select("[data-stat='rec']").text(), 0));
                    p.setReceivingYds(NumberUtils.toInt(stats.select("[data-stat='rec_yds']").text(), 0));
                    p.setReceivingTds(NumberUtils.toInt(stats.select("[data-stat='rec_td']").text(), 0));
                    p.setRushingTds(NumberUtils.toInt(stats.select("[data-stat='rush_td']").text(), 0));
                    p.setRushingYds(NumberUtils.toInt(stats.select("[data-stat='rush_yds']").text(), 0));
                    p.setFumbles(NumberUtils.toInt(stats.select("[data-stat='fumbles']").text(), 0));
                    p.setRushes(NumberUtils.toInt(stats.select("[data-stat='fumbles']").text(), 0));
                }
                // Defensive stats
                else if (p.playsDefense() && !p.playsOffense() && !p.playsSpecialTeams()) {
                    p.setTackles(NumberUtils.toDouble(stats.select("[data-stat='tackles_solo']").text(), 0));
                    p.setAssistedTackles(NumberUtils.toDouble(stats.select("[data-stat='tackles_assists']").text(), 0));
                    p.setSacks(NumberUtils.toDouble(stats.select("[data-stat='sacks']").text(), 0));
                    p.setInts(NumberUtils.toInt(stats.select("[data-stat='def_int']").text(), 0));
                    p.setPassesDefended(NumberUtils.toInt(stats.select("[data-stat='pass_defended']").text(), 0));
                    p.setForcedFumbles(NumberUtils.toInt(stats.select("[data-stat='fumbles_forced']").text(), 0));
                    p.setFumbleRecoveries(NumberUtils.toInt(stats.select("[data-stat='fumbles_rec']").text(), 0));
                    int fumbleTds = NumberUtils.toInt(stats.select("[data-stat='fumbles_rec_td']").text(), 0);
                    int intTds = NumberUtils.toInt(stats.select("[data-stat='def_int_td']").text(), 0);
                    p.setDefensiveTd(intTds + fumbleTds);
                }
                // Special teams stats
                else if (p.playsSpecialTeams() && !p.playsOffense() && !p.playsDefense()) {
                    int a = NumberUtils.toInt(stats.select("[data-stat='fgm1']").text(), 0);
                    int b = NumberUtils.toInt(stats.select("[data-stat='fgm2']").text(), 0);
                    int c = NumberUtils.toInt(stats.select("[data-stat='fgm3']").text(), 0);
                    int d = NumberUtils.toInt(stats.select("[data-stat='fgm4']").text(), 0);
                    p.setFieldGoalsBelowFiftyYds(a + b + c + d);
                    p.setFieldGoalsAboveFiftyYds(NumberUtils.toInt(stats.select("[data-stat='fgm5']").text(), 0));
                    p.setExtraPoints(NumberUtils.toInt(stats.select("[data-stat='xpm']").text(), 0));
                }
            }
        } catch (Exception e) {
            throw new CrawlerException("There was an error crawling player stats: ", e);
        }
        return p;
    }

}
