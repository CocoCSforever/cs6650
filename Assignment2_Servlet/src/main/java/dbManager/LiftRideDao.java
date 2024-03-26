package dbManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.SeasonVertical;
import org.apache.commons.dbcp2.*;
import db.LiftRide;

public class LiftRideDao {
    private static BasicDataSource dataSource;

    public LiftRideDao() {
        dataSource = DBCPDataSource.getDataSource();
    }

    public void createLiftRide(LiftRide newLiftRide) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String insertQueryStatement = "INSERT INTO LiftRides (skierId, resortId, seasonId, dayId, time, liftId) " +
                "VALUES (?,?,?,?,?,?)";
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(insertQueryStatement);
            preparedStatement.setInt(1, newLiftRide.getSkierID());
            preparedStatement.setInt(2, newLiftRide.getResortID());
            preparedStatement.setInt(3, newLiftRide.getSeasonID());
            preparedStatement.setInt(4, newLiftRide.getDayID());
            preparedStatement.setInt(5, newLiftRide.getTime());
            preparedStatement.setInt(6, newLiftRide.getLiftID());

            // execute insert SQL statement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    // GET /resorts/{resortID}/seasons/{seasonID}/day/{dayID}/skiers
    public List<Integer> getSkiersForResortDay(int resortID, int seasonID, int dayID) throws SQLException {
        String sql = "SELECT DISTINCT skierID FROM liftRides WHERE resortID = ? AND seasonID = ? AND dayID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, resortID);
            stmt.setInt(2, seasonID);
            stmt.setInt(3, dayID);
            ResultSet rs = stmt.executeQuery();
            List<Integer> skierIds = new ArrayList<>();
            while (rs.next()) {
                skierIds.add(rs.getInt("skierID"));
            }
            return skierIds;
        }
    }

    // GET /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}
    // get the total vertical for the skier for the specified ski day
    // return total vertical for the skier+day+resort+season
    public int getVerticalsForSkier(int resortID, int seasonID, int dayID, int skierID) throws SQLException {
        String sql = "SELECT SUM(liftID * 10) AS totalVertical FROM liftRides WHERE resortID = ? AND seasonID = ? AND dayID = ? AND skierID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, resortID);
            stmt.setInt(2, seasonID);
            stmt.setInt(3, dayID);
            stmt.setInt(4, skierID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("totalVertical");
            }
            return 0;
        }
    }

    // GET/skiers/{skierID}/vertical
    // get the total vertical for the skier the specified resort. If no season is specified, return all seasons
    /*
    {
        "resorts": [
            {
                "seasonID": "string1",
                "totalVert": 0
            }
            {
                "seasonID": "string2",
                "totalVert": 0
            }
        ]
    }
     */
    public List<SeasonVertical> getTotalVerticalForSkier(int skierID, int resortID, Integer seasonID) throws SQLException {
        List<SeasonVertical> seasonVerticals = new ArrayList<>();
        String sql = "SELECT SUM(liftID * 10) AS totalVertical FROM liftRides WHERE skierID = ? AND resortID = ?";
        if (seasonID != null) {
            sql += " AND seasonID = ?";
        }
        sql += " GROUP BY seasonID";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, skierID);
            stmt.setInt(2, resortID);
            if(seasonID != null){
                stmt.setInt(3, seasonID);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String season = rs.getString("seasonID");
                int totalVertical = rs.getInt("totalVertical");
                seasonVerticals.add(new SeasonVertical(season, totalVertical));
            }
            return seasonVerticals; // Default to empty list if no records found
        }
    }
}
