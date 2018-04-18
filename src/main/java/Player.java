public class Player {

    private String[] positions = {"QB", "RB", "WR", "TE", "DE", "DT", "LB", "CB", "S", "K", "P"};
    private long playerId;
    private String name;
    private String playerUrl;
    private String position;
    private Team team;
    private int touchdowns;
    private int passingYards;
    private int catches;
    private int receivingYards;
    private int rushes;
    private int rushingYards;
    private int tackles;
    private int sacks;
    private int interceptions;
    private int fumbles;
    private int fieldGoals;
    private int points;

    public Player() {

    }

    public Player(String name, String playerUrl, Team team) {
        this.name = name;
        this.playerUrl = playerUrl;
        this.team = team;
    }

    public long getId() {
        return playerId;
    }

    public void setId(long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayerUrl() {
        return playerUrl;
    }

    public void setPlayerUrl(String playerUrl) {
        this.playerUrl = playerUrl;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        for (String s : this.positions) {
            if (position.equals(s)) {
                this.position = s;
            }
        }
    }

    public int getTouchdowns() {
        return touchdowns;
    }

    public void setTouchdowns(int touchdowns) {
        this.touchdowns = touchdowns;
    }

    public int getPassingYards() {
        return passingYards;
    }

    public void setPassingYards(int passingYards) {
        this.passingYards = passingYards;
    }

    public int getInterceptions() {
        return interceptions;
    }

    public void setInterceptions(int interceptions) {
        this.interceptions = interceptions;
    }

    public int getCatches() {
        return catches;
    }

    public void setCatches(int catches) {
        this.catches = catches;
    }

    public int getReceivingYards() {
        return receivingYards;
    }

    public void setReceivingYards(int receivingYards) {
        this.receivingYards = receivingYards;
    }

    public int getRushes() {
        return rushes;
    }

    public void setRushes(int rushes) {
        this.rushes = rushes;
    }

    public int getRushingYards() {
        return rushingYards;
    }

    public void setRushingYards(int rushingYards) {
        this.rushingYards = rushingYards;
    }

    public int getTackles() {
        return tackles;
    }

    public void setTackles(int tackles) {
        this.tackles = tackles;
    }

    public int getSacks() {
        return sacks;
    }

    public void setSacks(int sacks) {
        this.sacks = sacks;
    }

    public int getFumbles() {
        return fumbles;
    }

    public void setFumbles(int fumbles) {
        this.fumbles = fumbles;
    }

    public int getFieldGoals() {
        return fieldGoals;
    }

    public void setFieldGoals(int fieldGoals) {
        this.fieldGoals = fieldGoals;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public int hashCode() {
        return (int) playerId * playerUrl.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o && this.getClass() == o.getClass()) {
            return true;
        }

        if (o == null && this.getClass() != o.getClass()) {
            return false;
        }

        Player p = (Player) o;
        return playerId != p.playerId && (!playerUrl.equals(p.playerUrl));
    }

    @Override
    public String toString() {
        return "Name: " + getName() + " " + getPlayerUrl() + " Team: " + getTeam().getName();
    }

}
