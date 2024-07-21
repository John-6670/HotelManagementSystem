package models.dataBase;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.interfaces.Temporal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DaoHandler<T> {
    private final String url = "jdbc:sqlite:identifier.sqlite";
    private static ConnectionSource connection;
    protected Dao<T, Integer> dao;

    public DaoHandler(Class<T> type) {
        try {
            connection = createConnection();
            dao = DaoManager.createDao(connection, type);
            TableUtils.createTableIfNotExists(connection, type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ConnectionSource createConnection() throws SQLException {
        return new JdbcConnectionSource(url);
    }

    public void closeConnection() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public void create(T obj) throws SQLException {
        dao.create(obj);
    }

    public T getById(int id) throws SQLException {
        return dao.queryForId(id);
    }

    public List<T> search(Map<String, Object> fieldValues) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
        Where<T, Integer> where = queryBuilder.where();
        boolean first = true;
        for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
            if (first) {
                if (entry.getValue() == null) {
                    where.isNull(entry.getKey());
                } else {
                    where.eq(entry.getKey(), entry.getValue());
                }
                first = false;
            } else {
                if (entry.getValue() == null) {
                    where.and().isNull(entry.getKey());
                } else {
                    where.and().eq(entry.getKey(), entry.getValue());
                }
            }
        }
        return queryBuilder.query();
    }

    public List<T> search(Map<String, Object> fieldValues, Object startDate, Object endDate) throws SQLException {
        // Step 1: Use the existing search method to get initial results
        List<T> initialResults = search(fieldValues);

        // Step 2: Manually filter the results based on the period
        List<T> filteredResults = new ArrayList<>();
        for (T item : initialResults) {
            if (item instanceof Temporal temporalItem) { // Ensure item is an instance of Temporal
                Date itemStartDate = temporalItem.getStartDate();
                Date itemEndDate = temporalItem.getEndDate();

                boolean matchesStartDate = startDate == null || (itemStartDate != null && !itemStartDate.before((Date) startDate));
                boolean matchesEndDate = endDate == null || (itemEndDate != null && !itemEndDate.after((Date) endDate));

                if (matchesStartDate && matchesEndDate) {
                    filteredResults.add(item);
                }
            }
        }
        // Step 3: Return the filtered list
        return filteredResults;
    }

    public List<T> getAll() throws SQLException {
        return dao.queryForAll();
    }

    public void update(T obj) throws SQLException {
        dao.update(obj);
    }

    public void delete(T obj) throws SQLException {
        dao.delete(obj);
    }

    public void delete(int id) throws SQLException {
        dao.deleteById(id);
    }
}