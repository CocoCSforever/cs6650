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

    // GET /resorts/{resortID}/seasons/{seasonID}/day/{dayID}/skierslist
    public int getSkiersForResortDay(int resortID, int seasonID, int dayID) throws SQLException {
        // Construct the unique identifier for the visit date from the provided resortID, seasonID, and dayID
        String visitDate = resortID + "/" + seasonID + "/" + dayID;
        // SQL command to retrieve the number of skiers from the AggregatedSkierVisits table where the VisitDate matches the constructed identifier
        String sql = "SELECT NumberOfSkiers FROM SkierVisits WHERE VisitDate = ?";

        // Establishing a connection to the database
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Setting the parameter for the prepared statement
            stmt.setString(1, visitDate);

            // Executing the query and processing the result set
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Returning the count of distinct skiers if found
                    return rs.getInt("NumberOfSkiers");
                } else {
                    // Returning 0 if no matching record is found, indicating no skiers for the specified day
                    return 0;
                }
            }
        }
//        String sql = "SELECT COUNT(DISTINCT skierID) AS totalSkiers FROM LiftRides WHERE resortID = ? AND seasonID = ? AND dayID = ?";
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, resortID);
//            stmt.setInt(2, seasonID);
//            stmt.setInt(3, dayID);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt("totalSkiers");
//            } else {
//                // Assuming 0 if no skiers found; adjust based on your requirements
//                return 0;
//            }
//        }
    }

    // GET /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}
    // get the total vertical for the skier for the specified ski day
    // return total vertical for the skier+day+resort+season
    public int getVerticalsForSkier(int resortID, int seasonID, int dayID, int skierID) throws SQLException {
        String sql = "SELECT SUM(liftID * 10) AS totalVertical FROM LiftRides WHERE skierID = ? AND resortID = ? AND seasonID = ? AND dayID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, skierID);
            stmt.setInt(2, resortID);
            stmt.setInt(3, seasonID);
            stmt.setInt(4, dayID);
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
    public List<SeasonVertical> getTotalVerticalForSkier(int skierID, int resortID, int seasonID) throws SQLException {
        List<SeasonVertical> seasonVerticals = new ArrayList<>();

        String sql = "SELECT seasonID, SUM(liftID * 10) AS totalVertical FROM LiftRides WHERE skierID = ? AND resortID = ?";
        if (seasonID != -1) {
            sql += " AND seasonID = ?";
//            sID = Integer.valueOf(seasonID);
        }
        sql += " GROUP BY seasonID";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, skierID);
            stmt.setInt(2, resortID);
            if(seasonID != -1){
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

    // Future Use: GET /resorts/{resortID}/seasons/{seasonID}/day/{dayID}/skierslist
    public List<Integer> getSkiersListForResortDay(int resortID, int seasonID, int dayID) throws SQLException {
        String sql = "SELECT DISTINCT skierID FROM LiftRides WHERE resortID = ? AND seasonID = ? AND dayID = ?";
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
}
