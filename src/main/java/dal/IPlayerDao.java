package dal;

import model.Player;

import javax.sql.DataSource;
import java.util.Set;

public interface IPlayerDao {

    void setDataSource(DataSource ds);

    Set<Player> getPlayers();

    Player getPlayer();

    boolean createPlayer(Player player);

    boolean updatePlayer(Player player);

    boolean deletePlayer(Player player);

}
