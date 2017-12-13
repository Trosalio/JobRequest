package server.persistence;

import common.formatter.DateFormatter;
import common.model.Advertise;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdvertiseDAO implements DAO<Advertise> {

    private final DataSource dataSource;
    private final JobDAO jobDAO;
    private DateTimeFormatter dateTimeFormatter;

    public AdvertiseDAO(DataSource dataSource, JobDAO jobDAO) {
        this.dataSource = dataSource;
        this.jobDAO = jobDAO;
        this.dateTimeFormatter = new DateFormatter().getFormatter();
    }

    // Setup
    @Override
    public void setup() {
        createFileIfNotExisted();
    }

    private void createFileIfNotExisted() {
        File dbFile = new File("database.db");
        if (!dbFile.exists()) try {
            boolean success = dbFile.createNewFile();
            if (success) System.out.println("Successfully create new DB file");
            createAdvertiseTableIfNotExisted();
        } catch (IOException e) {
            e.printStackTrace();
        }
        createAdvertiseTableIfNotExisted();
    }

    private void createAdvertiseTableIfNotExisted() {
        try (Connection con = dataSource.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Advertise" +
                    "(refNumber TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "createDate TEXT NOT NULL," +
                    "jobID INTEGER NOT NULL," +
                    "FOREIGN KEY(jobID) REFERENCES Job(ID) ON DELETE CASCADE," +
                    "PRIMARY KEY(refNumber));";
            con.prepareStatement(createTableSQL).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Implementations
    @Override
    public List<Advertise> loadAll() {
        List<Advertise> advertises = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            String loadSQL = "SELECT * FROM Advertise";
            ResultSet resultSet = con.prepareStatement(loadSQL).executeQuery();
            pullDataToAdvertises(resultSet, advertises);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return advertises;
    }

    private void pullDataToAdvertises(ResultSet resultSet, List<Advertise> advertises) throws SQLException {
        while (resultSet.next()) {
            Advertise advertise = new Advertise();
            String refNumber = resultSet.getString("refNumber");
            String name = resultSet.getString("name");
            String date = resultSet.getString("createDate");
            int jobID = resultSet.getInt("jobID");

            advertise.setRefNumber(refNumber);
            advertise.setAdsName(name);
            advertise.setJob(jobID < 0 ? null : jobDAO.load(jobID));
            advertise.setCreateDate(LocalDate.parse(date, new DateFormatter().getFormatter()));
            advertises.add(advertise);
        }
    }

    @Override
    public void insert(Advertise advertise) {
        String insertSQL = "INSERT INTO Advertise (refNumber, name, createDate, jobID) VALUES(?,?,?,?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pStmt = con.prepareStatement(insertSQL)) {
            pStmt.setString(1, advertise.getRefNumber());
            pStmt.setString(2, advertise.getAdsName());
            pStmt.setString(3, dateTimeFormatter.format(advertise.getCreateDate()));
            int jobID;
            if(advertise.getJob() == null){
                jobID = -1;
            } else {
                jobID = advertise.getJob().getId();
                jobDAO.insert(advertise.getJob());
            }
            pStmt.setInt(4, jobID);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Advertise advertise) {
        try (Connection con = dataSource.getConnection()) {
            String deleteSQL = String.format("DELETE FROM Advertise WHERE refNumber = %s", advertise.getRefNumber());
            con.prepareStatement(deleteSQL).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Advertise advertise) {
        String updateSQL = "UPDATE Advertise SET name = ?, createDate = ?, jobID = ? WHERE refNumber = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pStmt = con.prepareStatement(updateSQL)) {
            pStmt.setString(1, advertise.getAdsName());
            pStmt.setString(2, dateTimeFormatter.format(advertise.getCreateDate()));
            int jobID;
            if(advertise.getJob() == null) {
                jobID = -1;
            } else {
                jobID = advertise.getJob().getId();
                jobDAO.update(advertise.getJob());
            }
            pStmt.setInt(3, jobID);
            pStmt.setString(4, advertise.getRefNumber());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tear down
    @Override
    public void close() {

    }
}
