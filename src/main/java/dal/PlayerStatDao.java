package dal;

import model.PlayerStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class PlayerStatDao {

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

    public PlayerStat getPlayerStats() {
        throw new NotImplementedException();
    }

    public boolean createPlayerStats(PlayerStat stat) {
        throw new NotImplementedException();
    }

    public boolean updatePlayerStats(PlayerStat stat) {
        throw new NotImplementedException();
    }

    public boolean deletePlayeStats(PlayerStat stat) {
        throw new NotImplementedException();
    }

}
