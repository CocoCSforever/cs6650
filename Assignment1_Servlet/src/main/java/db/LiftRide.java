package db;

import java.util.LinkedList;
import java.util.List;

public class LiftRide {
    private static List<LiftRide> liftRides= new LinkedList<>();
    private String skierID;  // between 1 and 100000
    private String resortID;  // between 1 and 10
    private String liftID;  // between 1 and 40
    private String seasonID;  // 2024
    private String dayID;  // 1
    private String time;  // between 1 and 360

    public LiftRide(String skierID, String resortID, String liftID, String seasonID, String dayID, String time) {
        this.skierID = skierID;
        this.resortID = resortID;
        this.liftID = liftID;
        this.seasonID = seasonID;
        this.dayID = dayID;
        this.time = time;
        liftRides.add(this);
        System.out.println(this);
    }

    public static List<LiftRide> getLiftRides() {
        return liftRides;
    }

    public void setSkierID(String skierID) {
        this.skierID = skierID;
    }

    public void setResortID(String resortID) {
        this.resortID = resortID;
    }

    public void setLiftID(String liftID) {
        this.liftID = liftID;
    }

    public void setSeasonID(String seasonID) {
        this.seasonID = seasonID;
    }

    public void setDayID(String dayID) {
        this.dayID = dayID;
    }

    public void setTime(String time) {
        this.time = time;
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
