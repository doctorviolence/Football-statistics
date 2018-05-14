package footballcrawler.dal;

import footballcrawler.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

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

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public boolean findPlayerInDb(String playerUrl) {
        String sql = "SELECT COUNT(*) FROM players p WHERE p.player_url = ?";

        int i = getJdbcTemplate().queryForObject(sql, Integer.class, playerUrl);

        return i != 1;
    }

    public boolean createPlayer(Player player) {
        String sql = "INSERT INTO players(player_url, player_name) VALUES(?, ?)";
        boolean created;
        try {
            String url = player.getPlayerUrl();
            String name = player.getName();

            if (findPlayerInDb(url)) {
                getJdbcTemplate().update(sql, url, name);
                created = true;
            } else {
                created = false;
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Error adding player to DB ", e);
        }
        return created;
    }

    public boolean updatePlayer(Player player) {
        String sql = "UPDATE players p SET p.player_url = ?, p.player_name = ? WHERE p.player_url = ?";
        boolean updated = false;
        try {
            String url = player.getPlayerUrl();
            String name = player.getName();

            getJdbcTemplate().update(sql, url, name, url);
            updated = true;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error updating player in DB ", e);
        }
        return updated;
    }

    public boolean deletePlayer(Player player) {
        String sql = "DELETE FROM players p WHERE p.player_url = ?";
        boolean deleted = false;
        try {
            String url = player.getPlayerUrl();

            getJdbcTemplate().update(sql, url);
            deleted = true;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error deleting player from DB ", e);
        }
        return deleted;
    }

}
