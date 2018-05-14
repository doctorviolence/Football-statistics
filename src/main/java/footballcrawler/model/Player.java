package footballcrawler.model;

import java.util.Set;

public class Player {

    private String name;
    private String playerUrl;
    private Set<PlayerStat> playerStats;

    public Player() {

    }

    public Player(String name, String playerUrl) {
        this.name = name;
        this.playerUrl = playerUrl;
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

    public Set<PlayerStat> getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(Set<PlayerStat> playerStats) {
        this.playerStats = playerStats;
    }

    public void addPlayerStats(PlayerStat playerStat) {
        this.playerStats.add(playerStat);
    }

    @Override
    public int hashCode() {
        return (int) playerUrl.hashCode();
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
        return (!playerUrl.equals(p.playerUrl));
    }

    @Override
    public String toString() {
        return "Name: " + getName() + " " + getPlayerUrl();
    }

}
