package db;

import java.util.LinkedList;
import java.util.List;

public class LiftRide {
    private static List<LiftRide> liftRides= new LinkedList<>();
    private int skierID;  // between 1 and 100000
    private int resortID;  // between 1 and 10
    private int liftID;  // between 1 and 40
    private int seasonID;  // 2024
    private int dayID;  // 1
    private int time;  // between 1 and 360

    public LiftRide(String skierID, String resortID, String liftID, String seasonID, String dayID, String time) {
        this.skierID = Integer.parseInt(skierID);
        this.resortID = Integer.parseInt(resortID);
        this.liftID = Integer.parseInt(liftID);
        this.seasonID = Integer.parseInt(seasonID);
        this.dayID = Integer.parseInt(dayID);
        this.time = Integer.parseInt(time);
    }

    public LiftRide(int skierID, int resortID, int liftID, int seasonID, int dayID, int time) {
        this.skierID = skierID;
        this.resortID = resortID;
        this.liftID = liftID;
        this.seasonID = seasonID;
        this.dayID = dayID;
        this.time = time;
    }

    public static List<LiftRide> getLiftRides() {
        return liftRides;
    }

    public void setSkierID(String skierID) {
        this.skierID = Integer.parseInt(skierID);
    }

    public void setResortID(String resortID) {
        this.resortID = Integer.parseInt(resortID);
    }

    public void setLiftID(String liftID) {
        this.liftID = Integer.parseInt(liftID);
    }

    public void setSeasonID(String seasonID) {
        this.seasonID = Integer.parseInt(seasonID);
    }

    public void setDayID(String dayID) {
        this.dayID = Integer.parseInt(dayID);
    }

    public void setTime(String time) {
        this.time = Integer.parseInt(time);
    }

    public int getSkierID() {
        return skierID;
    }

    public int getResortID() {
        return resortID;
    }

    public int getLiftID() {
        return liftID;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public int getDayID() {
        return dayID;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "LiftRide{" +
                "skierID='" + skierID + '\'' +
                ", resortID='" + resortID + '\'' +
                ", liftID='" + liftID + '\'' +
                ", seasonID='" + seasonID + '\'' +
                ", dayID='" + dayID + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
