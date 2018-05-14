package footballcrawler.dal;

import footballcrawler.model.Player;
import footballcrawler.model.PlayerStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class PlayerStatDao implements IPlayerStatDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource ds;

    @Autowired
    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }

    @PostConstruct
    public void postConstruct() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public boolean findPlayerStatsInDb(String playerUrl, int season) {
        String sql = "SELECT COUNT(*) FROM player_stats p WHERE p.player_url = ? AND p.season = ?";

        int i = getJdbcTemplate().queryForObject(sql, Integer.class, playerUrl, season);

        return i != 1;
    }

    public boolean createPlayerStats(PlayerStat stat, int season) {
        String sql = "INSERT INTO player_stats(player_url, season, g, pos, pass_yds, pass_td, pass_int, " +
                "rush_yds, rush_td, rec, rec_yds, rec_td, fumbles, rushes, " +
                "tackles_solo, tackles_assists, sacks, pass_defended, def_int, fumbles_forced, fumbles_rec, def_tds, " +
                "fgs_below_fifty, fgs_above_fifty) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean created = false;
        try {
            Player p = stat.getPlayer();
            String playerUrl = p.getPlayerUrl();
            int g = stat.getGames();
            String pos = stat.getPosition();
            int passYds = stat.getPassingYds();
            int passTds = stat.getPassingTds();
            int passInts = stat.getPassingInts();
            int rushYds = stat.getRushingYds();
            int rushTds = stat.getRushingTds();
            int rec = stat.getReceptions();
            int recYds = stat.getReceivingYds();
            int recTds = stat.getReceivingTds();
            int fumbles = stat.getFumbles();
            int rushes = stat.getRushes();
            double tacklesSolo = stat.getTackles();
            double tacklesAssists = stat.getAssistedTackles();
            double sacks = stat.getSacks();
            int passDefended = stat.getPassesDefended();
            int defInts = stat.getInts();
            int fumblesForced = stat.getForcedFumbles();
            int fumblesRec = stat.getFumbleRecoveries();
            int defTds = stat.getDefensiveTd();
            int fgsBelowFifty = stat.getFieldGoalsBelowFiftyYds();
            int fgsAboveFifty = stat.getFieldGoalsAboveFiftyYds();

            if (findPlayerStatsInDb(playerUrl, season)) {
                getJdbcTemplate().update(sql, playerUrl, season, g, pos, passYds, passTds, passInts,
                        rushYds, rushTds, rec, recYds, recTds, fumbles, rushes, tacklesSolo, tacklesAssists, sacks,
                        passDefended, defInts, fumblesForced, fumblesRec, defTds, fgsBelowFifty, fgsAboveFifty);
                created = true;
            } else {
                created = false;
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Error adding player stats to DB ", e);
        }
        return created;
    }

    // To-do: create a find/read method to get player id
    public boolean updatePlayerStats(PlayerStat stat) {
        String sql = "UPDATE player_stats p SET p.g = ?, p.pos = ?, p.pass_yds = ?, p.pass_td = ?, p.pass_int = ?, " +
                "p.rush_yds = ?, p.rush_td = ?, p.rec = ?, p.rec_yds = ?, p.rec_td = ?, p.fumbles = ?, p.rushes = ?, " +
                "p.tackles_solo = ?, p.tackles_assists = ?, p.sacks = ?, p.pass_defended = ?, p.def_int = ?, " +
                "p.fumbles_forced = ?, p.fumbles_rec = ?, p.def_tds = ?, p.fgs_below_fifty = ?, p.fgs_above_fifty = ? " +
                "WHERE p.player_stat_id = ? AND p.player_url = ?";
        boolean updated = false;
        try {
            Player p = stat.getPlayer();
            String playerUrl = p.getPlayerUrl();
            long id = stat.getPlayerStatId();
            int g = stat.getGames();
            String pos = stat.getPosition();
            int passYds = stat.getPassingYds();
            int passTds = stat.getPassingTds();
            int passInts = stat.getPassingInts();
            int rushYds = stat.getRushingYds();
            int rushTds = stat.getRushingTds();
            int rec = stat.getReceptions();
            int recYds = stat.getReceivingYds();
            int recTds = stat.getReceivingTds();
            int fumbles = stat.getFumbles();
            int rushes = stat.getRushes();
            double tacklesSolo = stat.getTackles();
            double tacklesAssists = stat.getAssistedTackles();
            double sacks = stat.getSacks();
            int passDefended = stat.getPassesDefended();
            int defInts = stat.getInts();
            int fumblesForced = stat.getForcedFumbles();
            int fumblesRec = stat.getFumbleRecoveries();
            int defTds = stat.getDefensiveTd();
            int fgsBelowFifty = stat.getFieldGoalsBelowFiftyYds();
            int fgsAboveFifty = stat.getFieldGoalsAboveFiftyYds();

            getJdbcTemplate().update(sql, playerUrl, g, pos, passYds, passTds, passInts,
                    rushYds, rushTds, rec, recYds, recTds, fumbles, rushes, tacklesSolo, tacklesAssists, sacks,
                    passDefended, defInts, fumblesForced, fumblesRec, defTds, fgsBelowFifty, fgsAboveFifty, id, playerUrl);
            updated = true;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error updating player stats in DB ", e);
        }
        return updated;
    }

    public boolean deletePlayerStats(PlayerStat stat) {
        String sql = "DELETE FROM player_stats p WHERE p.player_stat_id = ?";
        boolean deleted = false;
        try {
            long id = stat.getPlayerStatId();

            getJdbcTemplate().update(sql, id);
            deleted = true;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error deleting player stats from DB ", e);
        }
        return deleted;
    }

}
