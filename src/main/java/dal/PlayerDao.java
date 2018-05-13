package dal;

import model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Set;

@Repository
public class PlayerDao implements IPlayerDao {

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

    public Set<Player> getPlayers() {
        throw new NotImplementedException();
    }

    public Player getPlayer() {
        throw new NotImplementedException();
    }

    public boolean createPlayer(Player player) {
        throw new NotImplementedException();
    }

    public boolean updatePlayer(Player player) {
        throw new NotImplementedException();
    }

    public boolean deletePlayer(Player player) {
        throw new NotImplementedException();
    }

}
