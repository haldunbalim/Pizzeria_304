package DataSource;

import database.DatabaseConnectionHandler;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static database.DataBaseCredentials.*;

// Done for convenience define common functions here if needed
abstract class AbstractDataSource {
    static DatabaseConnectionHandler dbHandler = DatabaseConnectionHandler.getInstance();
    static Connection connection = dbHandler.getConnection();
    String primaryTable; // define the primary table of each data source for convenience

    /**
     * @param tableName
     * @param values    formatted string of insertion values separated by comma, eg for pizza: String.format("'%s', '%s', %d", crust, type, did)
     * @return
     */
    protected OperationResult insertIntoDb(String tableName, String values) {
        try {
            //int newDid = getNextId("DELIVERABLE", "did");
            String statement = String.format(Locale.CANADA, "INSERT INTO %s VALUES (%s)", tableName, values);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(statement);
            stmt.close();
            connection.commit();

            return OperationResult.inserted;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(EXCEPTION_TAG + e.getMessage());
            return OperationResult.integrityConstraintError;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + e.getMessage());
            return OperationResult.insertionFailed;
        }
    }

    /**
     * @param tableName
     * @param primaryKeys pass in the format primaryKeyColumn=primaryKeyValue
     * @return
     */
    protected OperationResult removeFromDb(String tableName, String primaryKeys) {
        try {
            String statement = String.format("DELETE FROM %s WHERE %s", tableName, primaryKeys);
            // remove the unnecessary comma at the end
            Statement stmt = connection.createStatement();
            int rowCount = stmt.executeUpdate(statement);

            if (rowCount == 0) {
                System.out.println(WARNING_TAG + notFound);
            }
            connection.commit();
            stmt.close();

            return OperationResult.deleted;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + deleteError);
            return OperationResult.deletionFailed;
        }

    }

    /**
     * use for iny typed ids
     *
     * @param tableName
     * @param primaryKey columnName of the PrimaryKey in db
     * @return a unique id in int format
     */
    protected int getNextIdInt(String tableName, String primaryKey) {
        int nextId = 1;
        ArrayList<Integer> takenIds = new ArrayList<Integer>();
        try {
            Statement stmt = connection.createStatement();
            // Oracle DB uses FETCH NEXT row_number ROWS ONLY
            String query = String.format("SELECT %s FROM %s ORDER BY %s", primaryKey, tableName, primaryKey);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                takenIds.add(rs.getInt(primaryKey));
            }
            nextId = takenIds.get(0);
            int high = (int) Math.pow(10, Math.ceil(Math.log10(takenIds.get(0)))) - 1;
            int low = (int) Math.pow(10, Math.floor(Math.log10(takenIds.get(0))));
            while (takenIds.contains(nextId)) {
                nextId = new Random().nextInt(high - low) + low;
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nextId;
    }

    /**
     * use for long typed ids
     *
     * @param tableName
     * @param primaryKey columnName of the PrimaryKey in db
     * @return a unique id in long format
     */
    protected long getNextIdLong(String tableName, String primaryKey) {
        long nextId = 1;
        ArrayList<Long> takenIds = new ArrayList<Long>();
        try {
            Statement stmt = connection.createStatement();
            // Oracle DB uses FETCH NEXT row_number ROWS ONLY
            String query = String.format("SELECT %s FROM %s ORDER BY %s", primaryKey, tableName, primaryKey);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                takenIds.add(rs.getLong(primaryKey));
            }
            nextId = takenIds.get(0);
            long high = (long) Math.pow(10, Math.ceil(Math.log10(takenIds.get(0)))) - 1;
            long low = (long) Math.pow(10, Math.floor(Math.log10(takenIds.get(0))));
            while (takenIds.contains(nextId)) {
                nextId = (long) (Math.random() * (high - low)) + low;
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nextId;
    }

    public String getPrimaryTable() {
        return primaryTable;
    }

    /**
     * @param pc postalCode String
     * @return
     */
    public String getCityFromPostalCode(String pc) {
        String city = "";
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT city FROM CITYPOSTALCODE WHERE postalcode='%s'", pc);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                city = rs.getString("city");
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {

        }
        return city;
    }

    /**
     * @param tableName String
     * @param columns   specify the sql statement after SET eg. col1=val1, col2=val2 where colx=valx
     * @return
     */
    protected OperationResult updateColumnValues(String tableName, String columns) {
        try {
            Statement stmt = connection.createStatement();
            String statement = String.format("UPDATE %s SET %s", tableName, columns);

            int rowCount = stmt.executeUpdate(statement);

            connection.commit();
            stmt.close();

            if (rowCount == 0) {
                System.out.println(WARNING_TAG + notFound);
                return OperationResult.noElementUpdated;
            }
            return OperationResult.updated;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + e.getMessage());
            return OperationResult.updateFailed;
        }
    }

    /**
     * this method is necessary to overcome locale issue in db insertions
     *
     * @param date
     * @return
     */
    protected String parseDate(LocalDate date) {
        String d = date.toString();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
            d = formatter.format(date);
        } catch (Exception e) {
            System.out.println("Time formatting error.");
        }
        return d;
    }
}
