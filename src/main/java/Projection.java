import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;
import static java.lang.Math.pow;

public class Projection {

    /*
        WORK IN PROGRESS
     */

    public double getPythagoreanWins(Team team) {
        DecimalFormat value = new DecimalFormat("#.#");
        double pf = team.getPointsFor();
        double pa = team.getPointsAgainst();

        double expectedWins = pow(pf, 2.37) / (pow(pf, 2.37) + pow(pa, 2.37)) * 16;

        return parseDouble(value.format(expectedWins));
    }

    public Team[] getFootballTeams(Team[] teams) {
        Team[] t = new Team[32];

        for (Team team : teams) {
            System.out.println(team.getName() + " " + getPythagoreanWins(team));

        }

        return t;
    }

}
