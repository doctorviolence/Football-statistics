package footballcrawler.dal;

import footballcrawler.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

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

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public boolean findTeamInDb(String acronym, int season) {
        String sql = "SELECT COUNT(*) FROM teams t WHERE t.team_acronym = ? AND t.season = ?";

        int i = getJdbcTemplate().queryForObject(sql, Integer.class, acronym, season);

        return i != 1;
    }

    public boolean createTeam(Team team) {
        String sql = "INSERT INTO teams(team_url, team_acronym, team_name, season, w, l, pf, pa) VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";
        boolean created;
        try {
            String url = team.getTeamUrl();
            String acronym = team.getTeamAcronym();
            String name = team.getName();
            int season = team.getSeason();
            int w = team.getWins();
            int l = team.getLosses();
            int pf = team.getPointsFor();
            int pa = team.getPointsAgainst();

            if (findTeamInDb(acronym, season)) {
                getJdbcTemplate().update(sql, url, acronym, name, season, w, l, pf, pa);
                created = true;
            } else {
                created = false;
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Error adding team to DB ", e);
        }
        return created;
    }

    public boolean updateTeam(Team team) {
        String sql = "UPDATE teams t SET t.w = ?, t.l = ?, t.pf = ?, t.pa = ? WHERE t.team_url = ? AND t.season = ?";
        boolean updated = false;
        try {
            String url = team.getTeamUrl();
            int season = team.getSeason();
            int w = team.getWins();
            int l = team.getLosses();
            int pf = team.getPointsFor();
            int pa = team.getPointsAgainst();

            getJdbcTemplate().update(sql, w, l, pf, pa, url, season);
            updated = true;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error deleting team from DB ", e);
        }
        return updated;
    }

    public boolean deleteTeam(Team team) {
        String sql = "DELETE FROM teams t WHERE t.team_url = ? AND t.season = ?";
        boolean deleted = false;
        try {
            String url = team.getTeamUrl();
            int season = team.getSeason();
            getJdbcTemplate().update(sql, url, season);
            deleted = true;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error deleting team from DB ", e);
        }
        return deleted;
    }

}
