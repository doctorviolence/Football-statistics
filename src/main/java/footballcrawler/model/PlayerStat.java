package footballcrawler.model;

import java.util.Arrays;

public class PlayerStat {

    private String[] offense = {"QB", "qb", "RB", "rb", "WR", "wr", "TE", "te"};
    private String[] defense = {"DE", "de", "de/dt", "DT", "dt,", "LB", "lb", "de/lb", "CB", "cb", "RCB", "cb/RCB", "LCB", "cb/LCB", "S", "S/ss", "s"};
    private String[] specialTeams = {"K", "P"};

    private long playerStatId;
    private String position;
    private String playerUrl;
    private int games;
    private Player player;

    // Offense
    private int passingYds;
    private int passingTds;
    private int passingInts;
    private int rushingYds;
    private int rushingTds;
    private int receptions;
    private int receivingYds;
    private int receivingTds;
    private int fumbles;
    private int rushes;

    // Defense
    private double tackles;
    private double assistedTackles;
    private double sacks;
    private int passesDefended;
    private int ints;
    private int forcedFumbles;
    private int fumbleRecoveries;
    private int defensiveTd;

    // Special Teams
    private int fieldGoalsBelowFiftyYds;
    private int fieldGoalsAboveFiftyYds;
    private int extraPoints;

    public PlayerStat() {

    }

    public PlayerStat(String position, int games, Player player) {
        this.position = position;
        this.games = games;
        this.player = player;
    }

    public PlayerStat(long playerStatId, String position,  int games, Player player) {
        this.playerStatId = playerStatId;
        this.position = position;
        this.games = games;
        this.player = player;
    }

    public long getPlayerStatId() {
        return playerStatId;
    }

    public void setPlayerStatId(long playerStatId) {
        this.playerStatId = playerStatId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if (Arrays.stream(this.offense).parallel().anyMatch(position::contains)) {
            this.position = position;
        } else if (Arrays.stream(this.defense).parallel().anyMatch(position::contains)) {
            this.position = position;
        } else if (Arrays.stream(this.specialTeams).parallel().anyMatch(position::contains)) {
            this.position = position;
        }
    }

    public boolean playsOffense() {
        return Arrays.stream(offense).parallel().anyMatch(this.position::contains);
    }

    public boolean playsDefense() {
        return Arrays.stream(defense).parallel().anyMatch(this.position::contains);
    }

    public boolean playsSpecialTeams() {
        return Arrays.stream(specialTeams).parallel().anyMatch(this.position::contains);
    }

    public String getPlayerUrl() {
        return playerUrl;
    }

    public void setPlayerUrl(String playerUrl) {
        this.playerUrl = playerUrl;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPassingYds() {
        return passingYds;
    }

    public void setPassingYds(int passingYds) {
        this.passingYds = passingYds;
    }

    public int getPassingTds() {
        return passingTds;
    }

    public void setPassingTds(int passingTds) {
        this.passingTds = passingTds;
    }

    public int getPassingInts() {
        return passingInts;
    }

    public void setPassingInts(int passingInts) {
        this.passingInts = passingInts;
    }

    public int getRushingYds() {
        return rushingYds;
    }

    public void setRushingYds(int rushingYds) {
        this.rushingYds = rushingYds;
    }

    public int getRushingTds() {
        return rushingTds;
    }

    public void setRushingTds(int rushingTds) {
        this.rushingTds = rushingTds;
    }

    public int getReceptions() {
        return receptions;
    }

    public void setReceptions(int receptions) {
        this.receptions = receptions;
    }

    public int getReceivingYds() {
        return receivingYds;
    }

    public void setReceivingYds(int receivingYds) {
        this.receivingYds = receivingYds;
    }

    public int getReceivingTds() {
        return receivingTds;
    }

    public void setReceivingTds(int receivingTds) {
        this.receivingTds = receivingTds;
    }

    public int getFumbles() {
        return fumbles;
    }

    public void setFumbles(int fumbles) {
        this.fumbles = fumbles;
    }

    public int getRushes() {
        return rushes;
    }

    public void setRushes(int rushes) {
        this.rushes = rushes;
    }

    public double getTackles() {
        return tackles;
    }

    public void setTackles(double tackles) {
        this.tackles = tackles;
    }

    public double getAssistedTackles() {
        return assistedTackles;
    }

    public void setAssistedTackles(double assistedTackles) {
        this.assistedTackles = assistedTackles;
    }

    public double getSacks() {
        return sacks;
    }

    public void setSacks(double sacks) {
        this.sacks = sacks;
    }

    public int getPassesDefended() {
        return passesDefended;
    }

    public void setPassesDefended(int passesDefended) {
        this.passesDefended = passesDefended;
    }

    public int getInts() {
        return ints;
    }

    public void setInts(int ints) {
        this.ints = ints;
    }

    public int getForcedFumbles() {
        return forcedFumbles;
    }

    public void setForcedFumbles(int forcedFumbles) {
        this.forcedFumbles = forcedFumbles;
    }

    public int getFumbleRecoveries() {
        return fumbleRecoveries;
    }

    public void setFumbleRecoveries(int fumbleRecoveries) {
        this.fumbleRecoveries = fumbleRecoveries;
    }

    public int getDefensiveTd() {
        return defensiveTd;
    }

    public void setDefensiveTd(int defensiveTd) {
        this.defensiveTd = defensiveTd;
    }

    public int getFieldGoalsBelowFiftyYds() {
        return fieldGoalsBelowFiftyYds;
    }

    public void setFieldGoalsBelowFiftyYds(int fieldGoalsBelowFiftyYds) {
        this.fieldGoalsBelowFiftyYds = fieldGoalsBelowFiftyYds;
    }

    public int getFieldGoalsAboveFiftyYds() {
        return fieldGoalsAboveFiftyYds;
    }

    public void setFieldGoalsAboveFiftyYds(int fieldGoalsAboveFiftyYds) {
        this.fieldGoalsAboveFiftyYds = fieldGoalsAboveFiftyYds;
    }

    public int getExtraPoints() {
        return extraPoints;
    }

    public void setExtraPoints(int extraPoints) {
        this.extraPoints = extraPoints;
    }

    @Override
    public int hashCode() {
        return (int) Long.hashCode(playerStatId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o && this.getClass() == o.getClass()) {
            return true;
        }

        if (o == null && this.getClass() != o.getClass()) {
            return false;
        }

        PlayerStat p = (PlayerStat) o;
        return (playerStatId == p.playerStatId);
    }

}
