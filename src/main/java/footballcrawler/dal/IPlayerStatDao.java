package footballcrawler.dal;

import footballcrawler.model.PlayerStat;

import javax.sql.DataSource;

public interface IPlayerStatDao {

    void setDataSource(DataSource ds);

    boolean findPlayerStatsInDb(String playerUrl, int season);

    boolean createPlayerStats(PlayerStat stat, int season);

    boolean updatePlayerStats(PlayerStat stat);

    boolean deletePlayerStats(PlayerStat stat);

}
