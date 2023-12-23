package moe.icegame.coreutils;

import com.zaxxer.hikari.HikariDataSource;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MySqlUtil {
    public static void AsyncQuery(HikariDataSource dataSource, String sql, BiConsumer<Boolean, String[][]> callback) {// Define a CompletableFuture to execute the database query
        CompletableFuture.runAsync(() -> {
            try {
                String[][] resultSet = Query(dataSource, sql);
                callback.accept(true, resultSet);
            } catch (RuntimeException e) {
                e.printStackTrace();
                callback.accept(false, null);
            }
        });
    }

    public static String[][] Query(HikariDataSource dataSource, String sql) {// Define a CompletableFuture to execute the database query
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement.executeQuery(sql)) {

            // row count
            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            String[][] ret = new String[rowCount][resultSet.getMetaData().getColumnCount()];

            while (resultSet.next()) {
                for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                    Object columnValue = resultSet.getObject(i + 1);
                    String stringValue = null;
                    if (columnValue != null) {
                        stringValue = columnValue.toString();
                    }

                    ret[resultSet.getRow() - 1][i] = stringValue;
                }
            }

            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static String[][] QueryWithParams(HikariDataSource dataSource, String sql, String... params) {// Define a CompletableFuture to execute the database query
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

            for (int i = 0; i < params.length; i++) statement.setString(i + 1, params[i]);
            ResultSet resultSet = statement.executeQuery();

            // row count
            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            String[][] ret = new String[rowCount][resultSet.getMetaData().getColumnCount()];

            while (resultSet.next()) {
                for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                    Object columnValue = resultSet.getObject(i + 1);
                    String stringValue = null;
                    if (columnValue != null) {
                        stringValue = columnValue.toString();
                    }

                    ret[resultSet.getRow() - 1][i] = stringValue;
                }
            }

            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void AsyncExecute(HikariDataSource dataSource, String sql){
        AsyncExecute(dataSource, sql, (a, b) -> {});
    }

    public static void AsyncExecute(HikariDataSource dataSource, String sql, BiConsumer<Boolean, Integer> callback) {// Define a CompletableFuture to execute the database query
        CompletableFuture.runAsync(() -> {
            Integer res = ExecuteMany(dataSource, new String[]{sql});
            callback.accept(res != null, res);
        });
    }

    public static Integer ExecuteWithParams(HikariDataSource dataSource, String sql, String... params) {// Define a CompletableFuture to execute the database query
        try (Connection connection = dataSource.getConnection();){
            PreparedStatement statement = connection.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) statement.setString(i + 1, params[i]);

            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Executes many sql queries at once.
     * Returns the total amount of documents changed in the callback,
     * or fails if one of the queries fails to execute.
     * @param dataSource HikariDataSource
     * @param queries A String[] of all the queries to be executed in order
     */
    public static void AsyncExecuteMany(HikariDataSource dataSource, String[] queries, BiConsumer<Boolean, Integer> callback) {
        CompletableFuture.runAsync(() -> {
            Integer res = ExecuteMany(dataSource, queries);
            callback.accept(res != null, res);
        });
    }

    /**
     * BLOCKING. Executes many sql queries at once.
     * Returns the total amount of documents changed in the callback,
     * or fails if one of the queries fails to execute.
     * @param dataSource HikariDataSource
     * @param queries A String[] of all the queries to be executed in order
     */
    public static Integer ExecuteMany(HikariDataSource dataSource, String[] queries) {
        try (Connection connection = dataSource.getConnection();){
            Statement statement = connection.createStatement();
            int ret = 0;

            for (String cmd : queries) {
                ret += statement.executeUpdate(cmd);
            }

            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
