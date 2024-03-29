package db;

public class SeasonVertical {
    private String seasonID;
    private int totalVert;

    public SeasonVertical(String seasonID, int totalVert) {
        this.seasonID = seasonID;
        this.totalVert = totalVert;
    }

    // Getters and Setters
    public String getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(String seasonID) {
        this.seasonID = seasonID;
    }

    public int getTotalVert() {
        return totalVert;
    }

    public void setTotalVert(int totalVert) {
        this.totalVert = totalVert;
    }
}

