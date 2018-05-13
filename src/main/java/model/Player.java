package model;

public class Player {

    private String name;
    private String playerUrl;

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
