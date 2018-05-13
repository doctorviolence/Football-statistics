package dal;

import model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Set;

@Repository
public class TeamDao implements ITeamDao {

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

    public Set<Team> getTeams() {
        throw new NotImplementedException();
    }

    public boolean createTeam(Team team) {
        throw new NotImplementedException();
    }

    public boolean updateTeam(Team team) {
        throw new NotImplementedException();
    }

    public boolean deleteTeam(Team team) {
        throw new NotImplementedException();
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

}
