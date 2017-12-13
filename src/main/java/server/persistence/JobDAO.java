package server.persistence;

import common.formatter.DateFormatter;
import common.model.Job;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class JobDAO implements DAO<Job> {

    private final DataSource dataSource;
    private final StationDAO stationDAO;
    private DateTimeFormatter dateTimeFormatter;
    private final AtomicInteger primaryKey = new AtomicInteger(0);


    public JobDAO(DataSource dataSource, StationDAO stationDAO) {
        this.dataSource = dataSource;
        this.stationDAO = stationDAO;
        this.dateTimeFormatter = new DateFormatter().getFormatter();
    }

    // Setup
    @Override
    public void setup() {
        createJobTableIfNotExisted();
    }

    private void createJobTableIfNotExisted() {
        try (Connection con = dataSource.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Job" +
                    "(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "detailName TEXT NOT NULL," +
                    "requester TEXT NOT NULL," +
                    "typeOfMedia TEXT NOT NULL," +
                    "quantity TEXT NOT NULL," +
                    "fromDate TEXT NOT NULL," +
                    "toDate TEXT NOT NULL," +
                    "status TEXT NOT NULL);";
            con.prepareStatement(createTableSQL).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is a designated method for JobDAO
     * The method loads an event from a source according to the Job ID.
     */
    // Implementations


    // Common
    @Override
    public void insert(Job job) {
        adjustPrimaryKey();
        job.setId(primaryKey.incrementAndGet());
        String insertSQL = "INSERT INTO Job (detailName, requester, typeOfMedia, quantity, fromDate, toDate, status) VALUES(?,?,?,?,?,?,?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pStmt = con.prepareStatement(insertSQL)) {
            pStmt.setString(1, job.getJobDetail());
            pStmt.setString(2, job.getRequester());
            pStmt.setString(3, job.getTypeOfMedia());
            pStmt.setInt(4, job.getQuantity());
            pStmt.setString(5, dateTimeFormatter.format(job.getFromDate()));
            pStmt.setString(6, dateTimeFormatter.format(job.getToDate()));
            pStmt.setString(7, job.getStatus());
            pStmt.executeUpdate();
            stationDAO.insert(job);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Job job) {
        try (Connection con = dataSource.getConnection()) {
            String deleteSQL = String.format("DELETE FROM Job WHERE jobID = %d", job.getId());
            con.prepareStatement(deleteSQL).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Job job) {
        String updateSQL = "UPDATE Job SET  detailName = ?, requester = ?, typeOfMedia = ?, quantity = ?, fromDate = ?, toDate = ?, status = ? WHERE ID = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pStmt = con.prepareStatement(updateSQL)) {
            pStmt.setString(1, job.getJobDetail());
            pStmt.setString(2, job.getRequester());
            pStmt.setString(3, job.getTypeOfMedia());
            pStmt.setInt(4, job.getQuantity());
            pStmt.setString(5, dateTimeFormatter.format(job.getFromDate()));
            pStmt.setString(6, dateTimeFormatter.format(job.getToDate()));
            pStmt.setString(7, job.getStatus());
            pStmt.setInt(8, job.getId());
            pStmt.executeUpdate();
            stationDAO.update(job);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Client - Requester
    public Job load(int jobID) {
        Job job = new Job();
        try(Connection con = dataSource.getConnection()) {
            String loadSQL = String.format("SELECT FROM Job WHERE jobID = %d", jobID);
            ResultSet resultSet = con.prepareStatement(loadSQL).executeQuery();
            if(resultSet.next()){
                String detailName = resultSet.getString("detailName");
                String requester = resultSet.getString("requester");
                String typeOfMedia = resultSet.getString("typeOfMedia");
                int quantity = resultSet.getInt("quantity");
                String fromDate = resultSet.getString("fromDate");
                String toDate = resultSet.getString("toDate");
                String status = resultSet.getString("status");
                job.setId(jobID);
                job.setJobDetail(detailName);
                job.setRequester(requester);
                job.setTypeOfMedia(typeOfMedia);
                job.setQuantity(quantity);
                job.setFromDate(LocalDate.parse(fromDate, new DateFormatter().getFormatter()));
                job.setToDate(LocalDate.parse(toDate, new DateFormatter().getFormatter()));
                job.setStatus(status);
                stationDAO.loadStationsInJob(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return job;
    }

    // Client - Manager
    @Override
    public List<Job> loadAll() {
        List<Job> jobs = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            String loadSQL = "SELECT * FROM Job";
            ResultSet resultSet = con.prepareStatement(loadSQL).executeQuery();
            pullDataToJobs(resultSet, jobs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }

    private void pullDataToJobs(ResultSet resultSet, List<Job> jobs) throws SQLException {
        while (resultSet.next()) {
            int jobID = resultSet.getInt("ID");
            String detailName = resultSet.getString("detailName");
            String requester = resultSet.getString("requester");
            String typeOfMedia = resultSet.getString("typeOfMedia");
            int quantity = resultSet.getInt("quantity");
            String fromDate = resultSet.getString("fromDate");
            String toDate = resultSet.getString("toDate");
            String status = resultSet.getString("status");
            Job job = new Job();
            job.setId(jobID);
            job.setJobDetail(detailName);
            job.setRequester(requester);
            job.setTypeOfMedia(typeOfMedia);
            job.setQuantity(quantity);
            job.setFromDate(LocalDate.parse(fromDate, new DateFormatter().getFormatter()));
            job.setToDate(LocalDate.parse(toDate, new DateFormatter().getFormatter()));
            job.setStatus(status);
            stationDAO.loadStationsInJob(job);
            jobs.add(job);
        }
    }

    private void adjustPrimaryKey(){
        try(Connection con = dataSource.getConnection()){
            String pkSQL = "SELECT FROM sqlite_sequence where name = Job";
            ResultSet resultSet = con.prepareStatement(pkSQL).executeQuery();
            if(resultSet.next()){
                primaryKey.set(resultSet.getInt("seq"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tear down
    @Override
    public void close() {

    }
}
