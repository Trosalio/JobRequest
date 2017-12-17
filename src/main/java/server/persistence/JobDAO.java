package server.persistence;

import common.formatter.DateFormatter;
import common.model.Job;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
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

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

@Repository
public class JobDAO implements DAO<Job> {

    private final DataSource dataSource;
    private DateTimeFormatter dateTimeFormatter;
    private final AtomicInteger primaryKey = new AtomicInteger(-1);


    public JobDAO(DataSource dataSource) {
        this.dataSource = dataSource;
        this.dateTimeFormatter = new DateFormatter().getFormatter();
        createReport();
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
                    "status TEXT NOT NULL," +
                    "adsRefNumber TEXT," +
                    "FOREIGN KEY(adsRefNumber) REFERENCES Advertise(refNumber) ON DELETE CASCADE);";
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
        String insertSQL = "INSERT INTO Job (detailName, requester, typeOfMedia, quantity, fromDate, toDate, status, adsRefNumber) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pStmt = con.prepareStatement(insertSQL)) {
            pStmt.setString(1, job.getJobDetail());
            pStmt.setString(2, job.getRequester());
            pStmt.setString(3, job.getTypeOfMedia());
            pStmt.setInt(4, job.getQuantity());
            pStmt.setString(5, dateTimeFormatter.format(job.getFromDate()));
            pStmt.setString(6, dateTimeFormatter.format(job.getToDate()));
            pStmt.setString(7, job.getStatus());
            pStmt.setString(8, job.getRefNumber());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Job job) {
        try (Connection con = dataSource.getConnection()) {
            String deleteSQL = String.format("DELETE FROM Job WHERE ID = %d", job.getId());
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Client - Requester
    public Job load(String refNo) {
        Job job = null;
        try (Connection con = dataSource.getConnection()) {
            String loadSQL = "SELECT * FROM Job WHERE adsRefNumber = ?";
            PreparedStatement statement = con.prepareStatement(loadSQL);
            statement.setString(1, refNo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                job = new Job();
                job.setId(resultSet.getInt("ID"));
                job.setJobDetail(resultSet.getString("detailName"));
                job.setRequester(resultSet.getString("requester"));
                job.setTypeOfMedia(resultSet.getString("typeOfMedia"));
                job.setQuantity(resultSet.getInt("quantity"));
                job.setFromDate(LocalDate.parse(resultSet.getString("fromDate"), dateTimeFormatter));
                job.setToDate(LocalDate.parse(resultSet.getString("toDate"), dateTimeFormatter));
                job.setStatus(resultSet.getString("status"));
                job.setRefNumber(resultSet.getString("adsRefNumber"));
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
            String loadSQL = "SELECT * FROM Job WHERE NOT status = 'READY'";
            ResultSet resultSet = con.prepareStatement(loadSQL).executeQuery();
            pullDataToJobs(resultSet, jobs);
            adjustPrimaryKey();
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
            String refNo = resultSet.getString("adsRefNumber");
            Job job = new Job();
            job.setId(jobID);
            job.setJobDetail(detailName);
            job.setRequester(requester);
            job.setTypeOfMedia(typeOfMedia);
            job.setQuantity(quantity);
            job.setFromDate(LocalDate.parse(fromDate, dateTimeFormatter));
            job.setToDate(LocalDate.parse(toDate, dateTimeFormatter));
            job.setStatus(status);
            job.setRefNumber(refNo);
            jobs.add(job);
        }
    }

    private void adjustPrimaryKey() {
        try (Connection con = dataSource.getConnection()) {
            String pkSQL = "SELECT * FROM sqlite_sequence WHERE name = 'Job'";
            ResultSet resultSet = con.prepareStatement(pkSQL).executeQuery();
            if (resultSet.next()) {
                primaryKey.set(resultSet.getInt("seq"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createReport() {
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT status, ID, createDate, detailName  " +
                    "FROM Job " +
                    "INNER JOIN Advertise ON Job.adsRefNumber = Advertise.refNumber";
            TextColumnBuilder<Integer> idColumn = col.column("ID", "ID", type.integerType());
            TextColumnBuilder<String> issueDateColumn = col.column("Issue Date", "createDate", type.stringType());
            TextColumnBuilder<String> nameColumn = col.column("Name", "detailName", type.stringType());
            TextColumnBuilder<String> statusColumn = col.column("Status", "status", type.stringType());
            StyleBuilder columnTitleStyle = stl.style(stl.style().bold()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

            try {
                report().setColumnTitleStyle(columnTitleStyle)
                        .highlightDetailEvenRows()
                        .columns(statusColumn, idColumn, issueDateColumn, nameColumn)
                        .subtotalsAtFirstGroupFooter(sbt.count(statusColumn).setLabel("count"))
                        .title(cmp.text("Simple Report"))
                        .pageFooter(cmp.pageXofY())
                        .setDataSource(con.prepareStatement(sql).executeQuery())
                        .show();
            } catch (DRException e) {
                e.printStackTrace();
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
