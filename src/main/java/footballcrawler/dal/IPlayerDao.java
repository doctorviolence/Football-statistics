package footballcrawler.dal;

import footballcrawler.model.Player;

import javax.sql.DataSource;

public interface IPlayerDao {

    void setDataSource(DataSource ds);

    boolean findPlayerInDb(String playerUrl);

    boolean createPlayer(Player player);

    boolean updatePlayer(Player player);

    boolean deletePlayer(Player player);

}
