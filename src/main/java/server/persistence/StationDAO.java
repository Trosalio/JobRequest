package server.persistence;

import common.model.Job;
import common.model.Station;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StationDAO implements DAO<Station> {

    private final DataSource dataSource;

    public StationDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Setup
    @Override
    public void setup() {
        createStationTableIfNotExisted();
        createStationInJobTableIfNotExisted();
    }

    private void createStationTableIfNotExisted() {
        try (Connection con = dataSource.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Station" +
                    "(code TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "PRIMARY KEY(code));";
            con.prepareStatement(createTableSQL).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createStationInJobTableIfNotExisted() {
        try (Connection con = dataSource.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Stations_In_Job " +
                    "(jobID INTEGER NOT NULL," +
                    "stationCode TEXT NOT NULL," +
                    "FOREIGN KEY(jobID) REFERENCES Job(ID) ON DELETE CASCADE," +
                    "FOREIGN KEY(stationCode) REFERENCES Station(code)," +
                    "PRIMARY KEY(jobID,stationCode));";
            con.prepareStatement(createTableSQL).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is a designated method for StationDAO
     * The method loads an event from a source according to the Station Code.
     */

    // Implementations

    // Common
    @Override
    public List<Station> loadAll() {
        List<Station> stations = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            String loadSQL = "SELECT * FROM Station";
            ResultSet resultSet = con.prepareStatement(loadSQL).executeQuery();
            pullDataToStations(resultSet, stations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stations;
    }

    private void pullDataToStations(ResultSet resultSet, List<Station> stations) throws SQLException {
        while (resultSet.next()) {
            String code = resultSet.getString("code");
            String name = resultSet.getString("name");
            Station station = new Station(code, name);
            stations.add(station);
        }
    }

    public void loadStationsInJob(Job job) {
        List<Station> stations = job.getStations();
        try (Connection con = dataSource.getConnection()) {
            String loadStationsSQL = String.format("SELECT * FROM Stations_In_Job WHERE jobID = %d", job.getId());
            ResultSet resultSet = con.prepareStatement(loadStationsSQL).executeQuery();
            while (resultSet.next()) {
                String code = resultSet.getString("stationCode");
                String loadStationSQL = String.format("SELECT name FROM Station WHERE code = %s", code);
                ResultSet rs = con.prepareStatement(loadStationSQL).executeQuery();
                String name;
                if (rs.next()) {
                    name = rs.getString("name");
                    Station station = new Station(code, name);
                    stations.add(station);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Client - Requester

    public void insert(Job job) {
        if (!job.getStations().isEmpty()) {
            try (Connection con = dataSource.getConnection()) {
                for (Station station : job.getStations()) {
                    String insertSQL = "INSERT INTO Stations_In_Job (jobID, stationCode) VALUES(?,?)";
                    PreparedStatement pStmt = con.prepareStatement(insertSQL);
                    pStmt.setInt(1, job.getId());
                    pStmt.setString(2, station.getCode());
                    pStmt.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Job job) {
        try (Connection con = dataSource.getConnection()) {
            String deleteSQL = String.format("DELETE FROM Stations_In_Job WHERE jobID = %d", job.getId());
            con.prepareStatement(deleteSQL).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Job job) {
        delete(job);
        insert(job);
    }

    // Client - Manager
    @Override
    public void insert(Station station) {
        try (Connection con = dataSource.getConnection()) {
            String insertSQL = String.format("INSERT INTO Station (code, name) VALUE('%s','%s')", station.getCode(), station.getName());
            con.prepareStatement(insertSQL).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Station station) {
        try (Connection con = dataSource.getConnection()) {
            String deleteSQL = String.format("DELETE FROM Station WHERE code = %s", station.getCode());
            con.prepareStatement(deleteSQL).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Station station) {
        try (Connection con = dataSource.getConnection()) {
            String deleteSQL = String.format("UPDATE Station SET name = %s WHERE code = %s", station.getCode(), station.getName());
            con.prepareStatement(deleteSQL).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tear down

    @Override
    public void close() {

    }
}
