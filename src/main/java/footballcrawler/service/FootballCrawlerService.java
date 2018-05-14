package footballcrawler.service;

import footballcrawler.crawler.FootballCrawler;
import footballcrawler.dal.IPlayerDao;
import footballcrawler.dal.IPlayerStatDao;
import footballcrawler.dal.ITeamDao;
import footballcrawler.exception.CrawlerException;
import footballcrawler.model.Player;
import footballcrawler.model.PlayerStat;
import footballcrawler.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("footballCrawlerService")
@ComponentScan({"footballcrawler"})
public class FootballCrawlerService {

    ITeamDao teamDao;
    IPlayerDao playerDao;
    IPlayerStatDao playerStatDao;
    private FootballCrawler crawler = new FootballCrawler();
    private int[] years = {2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017};

    @Autowired
    public FootballCrawlerService(ITeamDao teamDao, IPlayerDao playerDao, IPlayerStatDao playerStatDao) {
        this.teamDao = teamDao;
        this.playerDao = playerDao;
        this.playerStatDao = playerStatDao;

        try {
            runCrawler();
        } catch (CrawlerException e) {
            e.printStackTrace();
        }
    }

    public void runCrawler() throws CrawlerException {

        for (int i : years) {
            // Step 1. Scrape for teams
            System.out.println("Scraping teams from " + i + "...");
            scrapeForTeamsAndInsertToDb(i);

            // Step 2: Scrape for players' urls
            Set<Player> players = scrapeForPlayersAndInsertToDb(i);

            // Step 3: Scrape for players' stats per season
            System.out.println("Scraping player stats from " + i + ". This will take a looooong time (please be patient)..");

            for (Player p : players) {
                scrapeForPlayerStatsAndInsertToDb(p, i);
            }

            System.out.println("Player stats from " + i + " inserted to DB...");
        }
        System.out.println("All done...");
    }

    /*
        Scrapes for teams in a particular season
     */
    public void scrapeForTeamsAndInsertToDb(int season) throws CrawlerException {
        Team[] teams = crawler.scrapeForTeamStats(season);

        for (Team t : teams) {
            teamDao.createTeam(t);
        }


        System.out.println("Teams from " + season + " inserted to DB...");
    }

    /*
        Scrapes for player urls of particular team/season
     */
    public Set<Player> scrapeForPlayersAndInsertToDb(int season) throws CrawlerException {
        Set<Player> players = new HashSet<>();
        System.out.println("Scraping players from " + season + ". This may take a while...");

        players.addAll(crawler.scrapeForPlayerUrls(season));

        for (Player p : players) {
            playerDao.createPlayer(p);
        }

        System.out.println("Players from " + season + " inserted to DB...");

        return players;
    }

    /*
        Scrapes page (based on player's url and (int) season) and inserts player stats to DB
     */
    public void scrapeForPlayerStatsAndInsertToDb(Player p, int season) throws CrawlerException {
        PlayerStat stats = crawler.scrapeForPlayerStats(p, season);

        playerStatDao.createPlayerStats(stats, season);
    }

}
