package footballcrawler.crawler;

import footballcrawler.exception.CrawlerException;
import footballcrawler.model.Player;
import footballcrawler.model.PlayerStat;
import footballcrawler.model.Team;
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
            "atl", "buf", "car",
            "crd", "chi", "cin",
            "cle", "clt", "dal",
            "den", "det", "gnb",
            "htx", "jax", "kan",
            "mia", "min", "nwe",
            "nor", "nyg", "nyj",
            "oti", "phi", "pit",
            "rai", "ram", "rav",
            "sdg", "sea", "sfo",
            "tam", "was"};

    private String[] newTeamNames = {
            "Arizona Cardinals", "Atlanta Falcons",
            "Baltimore Ravens", "Buffalo Bills",
            "Carolina Panthers", "Chicago Bears",
            "Cincinnati Bengals", "Cleveland Browns",
            "Dallas Cowboys", "Denver Broncos",
            "Detroit Lions", "Green Bay Packers",
            "Houston Texans", "Indianapolis Colts",
            "Jacksonville Jaguars", "Kansas City Chiefs",
            "Miami Dolphins", "Minnesota Vikings",
            "New England Patriots", "New Orleans Saints",
            "New York Giants", "New York Jets",
            "Oakland Raiders", "Philadelphia Eagles",
            "Pittsburgh Steelers", "Los Angeles Rams",
            "Los Angeles Chargers", "San Francisco 49ers",
            "Seattle Seahawks", "Tampa Bay Buccaneers",
            "Tennessee Titans", "Washington Redskins"
    };

    private String[] sixteenTeamNames = {
            "Arizona Cardinals", "Atlanta Falcons",
            "Baltimore Ravens", "Buffalo Bills",
            "Carolina Panthers", "Chicago Bears",
            "Cincinnati Bengals", "Cleveland Browns",
            "Dallas Cowboys", "Denver Broncos",
            "Detroit Lions", "Green Bay Packers",
            "Houston Texans", "Indianapolis Colts",
            "Jacksonville Jaguars", "Kansas City Chiefs",
            "Los Angeles Rams", "Miami Dolphins",
            "Minnesota Vikings", "New England Patriots",
            "New Orleans Saints", "New York Giants",
            "New York Jets", "Oakland Raiders",
            "Philadelphia Eagles", "Pittsburgh Steelers",
            "San Diego Chargers", "San Francisco 49ers",
            "Seattle Seahawks", "Tampa Bay Buccaneers",
            "Tennessee Titans", "Washington Redskins"
    };

    private String[] oldTeamNames = {
            "Arizona Cardinals", "Atlanta Falcons",
            "Baltimore Ravens", "Buffalo Bills",
            "Carolina Panthers", "Chicago Bears",
            "Cincinnati Bengals", "Cleveland Browns",
            "Dallas Cowboys", "Denver Broncos",
            "Detroit Lions", "Green Bay Packers",
            "Houston Texans", "Indianapolis Colts",
            "Jacksonville Jaguars", "Kansas City Chiefs",
            "St. Louis Rams", "Miami Dolphins",
            "Minnesota Vikings", "New England Patriots",
            "New Orleans Saints", "New York Giants",
            "New York Jets", "Oakland Raiders",
            "Philadelphia Eagles", "Pittsburgh Steelers",
            "San Diego Chargers", "San Francisco 49ers",
            "Seattle Seahawks", "Tampa Bay Buccaneers",
            "Tennessee Titans", "Washington Redskins"
    };

    /*
        Scrapes for team stats pertaining to season (year) parameter. Loops through arrays of team names/acronyms and returns array of teams
     */
    public Team[] scrapeForTeamStats(int season) throws CrawlerException {
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
                    String teamAcronym = teamAcronyms[i];
                    String teamUrl = String.format("https://www.pro-football-reference.com/teams/%s/%s.htm", teamAcronyms[i], season);
                    int w = NumberUtils.toInt(e.select("[data-stat='wins']").text(), 0);
                    int l = NumberUtils.toInt(e.select("[data-stat='losses']").text(), 0);
                    int pf = NumberUtils.toInt(e.select("[data-stat='points']").text(), 0);
                    int pa = NumberUtils.toInt(e.select("[data-stat='points_opp']").text(), 0);

                    Team team = new Team(name, teamAcronym, teamUrl, season, w, l, pf, pa);
                    teams[i] = team;
                }
            }

        } catch (IOException e) {
            throw new CrawlerException("There was an error whilst attempting to scrape team stats ", e);
        }
        return teams;
    }

    /*
        Season sent as parameter, loops through team acronyms and returns set of players (w/ player url)
     */
    public Set<Player> scrapeForPlayerUrls(int season) throws CrawlerException {
        Document[] docs = new Document[32];
        Elements[] players = new Elements[32];
        Set<Player> playersCrawled = new HashSet<>();

        try {
            for (int i = 0; i < teamAcronyms.length; i++) {
                String requestUrl = String.format("https://www.pro-football-reference.com/teams/%s/%s_roster.htm", teamAcronyms[i], season);
                docs[i] = Jsoup.connect(requestUrl).timeout(5000).get();
                //String teamAcronym = docs[i].select(String.format("a[href=/teams/%s/]:first-child", teamAcronyms[i])).first().text();
                players[i] = docs[i].select("#starters td:nth-child(2) a");

                if (players[i] != null) {
                    for (Element e : players[i]) {
                        String name = e.text();
                        //String teamUrl = String.format("/teams/%s/%s.htm", teamAcronyms[i], season);
                        String playerUrl = e.attr("abs:href");

                        Player p = new Player(name, playerUrl);
                        playersCrawled.add(p);
                    }
                }
            }
        } catch (IOException e) {
            throw new CrawlerException("There was an error whilst attempting to scrape player urls ", e);
        }
        return playersCrawled;
    }

    /*
        Player and season (year) sent as paramater, scrapes page for stats based on player url/season and returns player stats
     */
    public PlayerStat scrapeForPlayerStats(Player player, int season) throws CrawlerException {
        PlayerStat playerStat = null;
        String url = player.getPlayerUrl();

        try {
            Document doc = Jsoup.connect(url).timeout(5000).get();
            Elements stats = doc.select(String.format("tr:contains(%s)", season));

            if (stats != null) {
                //System.out.println(stats.toString());

                String position = stats.select("td[data-stat='pos']").first().text();
                int games = NumberUtils.toInt(stats.select("[data-stat='g']").text(), 0);

                playerStat = new PlayerStat(position, games, player);

                // Offensive stats
                if (playerStat.playsOffense() && !playerStat.playsDefense() && !playerStat.playsSpecialTeams()) {
                    playerStat.setPassingTds(NumberUtils.toInt(stats.select("[data-stat='pass_td']").text(), 0));
                    playerStat.setPassingYds(NumberUtils.toInt(stats.select("[data-stat='pass_yds']").text(), 0));
                    playerStat.setPassingInts(NumberUtils.toInt(stats.select("[data-stat='pass_int']").text(), 0));
                    playerStat.setReceptions(NumberUtils.toInt(stats.select("[data-stat='rec']").text(), 0));
                    playerStat.setReceivingYds(NumberUtils.toInt(stats.select("[data-stat='rec_yds']").text(), 0));
                    playerStat.setReceivingTds(NumberUtils.toInt(stats.select("[data-stat='rec_td']").text(), 0));
                    playerStat.setRushingTds(NumberUtils.toInt(stats.select("[data-stat='rush_td']").text(), 0));
                    playerStat.setRushingYds(NumberUtils.toInt(stats.select("[data-stat='rush_yds']").text(), 0));
                    playerStat.setFumbles(NumberUtils.toInt(stats.select("[data-stat='fumbles']").text(), 0));
                    playerStat.setRushes(NumberUtils.toInt(stats.select("[data-stat='fumbles']").text(), 0));
                }
                // Defensive stats
                else if (playerStat.playsDefense() && !playerStat.playsOffense() && !playerStat.playsSpecialTeams()) {
                    playerStat.setTackles(NumberUtils.toDouble(stats.select("[data-stat='tackles_solo']").text(), 0));
                    playerStat.setAssistedTackles(NumberUtils.toDouble(stats.select("[data-stat='tackles_assists']").text(), 0));
                    playerStat.setSacks(NumberUtils.toDouble(stats.select("[data-stat='sacks']").text(), 0));
                    playerStat.setInts(NumberUtils.toInt(stats.select("[data-stat='def_int']").text(), 0));
                    playerStat.setPassesDefended(NumberUtils.toInt(stats.select("[data-stat='pass_defended']").text(), 0));
                    playerStat.setForcedFumbles(NumberUtils.toInt(stats.select("[data-stat='fumbles_forced']").text(), 0));
                    playerStat.setFumbleRecoveries(NumberUtils.toInt(stats.select("[data-stat='fumbles_rec']").text(), 0));
                    int fumbleTds = NumberUtils.toInt(stats.select("[data-stat='fumbles_rec_td']").text(), 0);
                    int intTds = NumberUtils.toInt(stats.select("[data-stat='def_int_td']").text(), 0);
                    playerStat.setDefensiveTd(intTds + fumbleTds);
                }
                // Special teams stats
                else if (playerStat.playsSpecialTeams() && !playerStat.playsOffense() && !playerStat.playsDefense()) {
                    int a = NumberUtils.toInt(stats.select("[data-stat='fgm1']").text(), 0);
                    int b = NumberUtils.toInt(stats.select("[data-stat='fgm2']").text(), 0);
                    int c = NumberUtils.toInt(stats.select("[data-stat='fgm3']").text(), 0);
                    int d = NumberUtils.toInt(stats.select("[data-stat='fgm4']").text(), 0);
                    playerStat.setFieldGoalsBelowFiftyYds(a + b + c + d);
                    playerStat.setFieldGoalsAboveFiftyYds(NumberUtils.toInt(stats.select("[data-stat='fgm5']").text(), 0));
                    playerStat.setExtraPoints(NumberUtils.toInt(stats.select("[data-stat='xpm']").text(), 0));
                }
            }
        } catch (Exception e) {
            throw new CrawlerException("There was an error whilst attempting to scrape player stats ", e);
        }
        return playerStat;
    }

}
