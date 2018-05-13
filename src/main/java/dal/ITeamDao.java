package dal;

import model.Team;

import javax.sql.DataSource;
import java.util.Set;

public interface ITeamDao {

    void setDataSource(DataSource ds);

    Set<Team> getTeams();

    boolean createTeam(Team team);

    boolean updateTeam(Team team);

    boolean deleteTeam(Team team);

}
