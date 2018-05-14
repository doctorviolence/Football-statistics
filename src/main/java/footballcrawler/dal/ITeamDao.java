package footballcrawler.dal;

import footballcrawler.model.Team;

import javax.sql.DataSource;

public interface ITeamDao {

    void setDataSource(DataSource ds);

    boolean findTeamInDb(String acronym, int season);

    boolean createTeam(Team team);

    boolean updateTeam(Team team);

    boolean deleteTeam(Team team);

}
