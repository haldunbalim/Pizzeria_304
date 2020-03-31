package DataSource;

import database.DatabaseConnectionHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import static database.DataBaseCredentials.*;

// Done for convenience define common functions here if needed
// If none can be removed
abstract class AbstractDataSource {
    static DatabaseConnectionHandler dbHandler = DatabaseConnectionHandler.getInstance();
    static Connection connection = dbHandler.getConnection();
    String primaryTable;

    // doesn't work because it needs preparedStatement
    protected OperationResult insertIntoDb (String tableName, String values) {
        try {
            //int newDid = getNextId("DELIVERABLE", "did");
            String statement = String.format("INSERT INTO %s VALUES (%s)", tableName, values);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(statement);
            stmt.close();
            connection.commit();

            return OperationResult.inserted;

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + insertionError);
            return OperationResult.insertionFailed;
        }
    }
    // pass the primaryKeys as a formatted String
    protected OperationResult removeFromDb (String tableName, String primaryKeys) {
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

    protected int getNextId(String tableName, String primaryKey) {
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

    public String getPrimaryTable() {
        return primaryTable;
    }

}
