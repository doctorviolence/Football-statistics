package dal;

import model.PlayerStat;

import javax.sql.DataSource;

public interface IPlayerStatDao {

    void setDataSource(DataSource ds);

    PlayerStat getPlayerStats();

    boolean createPlayerStats(PlayerStat stat);

    boolean updatePlayerStats(PlayerStat stat);

    boolean deletePlayeStats(PlayerStat stat);

}
