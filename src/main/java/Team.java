public class Team {

    private String name;
    private int season;
    private int wins;
    private int losses;
    private int pointsFor;
    private int pointsAgainst;

    public Team() {
    }

    public Team(String name, int season) {
        this.name = name;
        this.season = season;
    }

    public Team(String name, int season, int wins, int losses, int pointsFor, int pointsAgainst) {
        this.name = name;
        this.season = season;
        this.wins = wins;
        this.losses = losses;
        this.pointsFor = pointsFor;
        this.pointsAgainst = pointsAgainst;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getPointsFor() {
        return pointsFor;
    }

    public void setPointsFor(int pointsFor) {
        this.pointsFor = pointsFor;
    }

    public int getPointsAgainst() {
        return pointsAgainst;
    }

    public void setPointsAgainst(int pointsAgainst) {
        this.pointsAgainst = pointsAgainst;
    }

    @Override
    public String toString() {
        return getName() + " W: " + getWins() + " L: " + getLosses() + " PF: " + getPointsFor() + " PA: " + getPointsAgainst();
    }

}
