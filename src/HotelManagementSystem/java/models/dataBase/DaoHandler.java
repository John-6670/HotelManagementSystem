package models.dataBase;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import models.interfaces.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class is used to handle the database operations.
 *
 * @param <T> the type of the object that the class will handle
 *
 * @author John
 */
public class DaoHandler<T> {
    private final String url = "jdbc:sqlite:identifier.sqlite";
    private static ConnectionSource connection;
    protected Dao<T, Integer> dao;

    /**
     * This constructor is used to create an instance of this class.
     * It initializes the connection to the database and creates the table if it does not exist.
     *
     * @param type the type of the object that the class will handle
     */
    public DaoHandler(Class<T> type) {
        try {
            connection = createConnection();
            dao = DaoManager.createDao(connection, type);
            TableUtils.createTableIfNotExists(connection, type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to create a connection to the database.
     *
     * @return the connection to the database
     * @throws SQLException if an error occurs while creating the connection
     */
    private ConnectionSource createConnection() throws SQLException {
        return new JdbcConnectionSource(url);
    }

    /**
     * This method is used to close the connection to the database.
     *
     * @throws Exception if an error occurs while closing the connection
     */
    public void closeConnection() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * This method is used to create an object in the database.
     *
     * @param obj the object to be created
     * @throws SQLException if an error occurs while creating the object
     */
    public void create(T obj) throws SQLException {
        dao.create(obj);
    }

    /**
     * This method is used to get an object by its ID.
     *
     * @param id the ID of the object
     * @return the object with the specified ID
     * @throws SQLException if an error occurs while getting the object
     */
    public T getById(int id) throws SQLException {
        return dao.queryForId(id);
    }

    /**
     * This method is used to search for objects in the database.
     *
     * @param fieldValues the fields and their values to search for
     * @return the objects that match the search criteria
     * @throws SQLException if an error occurs while searching for the objects
     */
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

                boolean matchesStartDate = startDate == null || (itemStartDate != null && !itemStartDate.after((Date) startDate));
                boolean matchesEndDate = endDate == null || (itemEndDate != null && !itemEndDate.before((Date) endDate));

                if (matchesStartDate && matchesEndDate) {
                    filteredResults.add(item);
                }
            }
        }
        // Step 3: Return the filtered list
        return filteredResults;
    }

    /**
     * This method is used to get all the objects in the database.
     *
     * @return all the objects in the database
     * @throws SQLException if an error occurs while getting all the objects
     */
    public List<T> getAll() throws SQLException {
        return dao.queryForAll();
    }

    /**
     * This method is used to update an object in the database.
     *
     * @param obj the object to be updated
     * @throws SQLException if an error occurs while updating the object
     */
    public void update(T obj) throws SQLException {
        dao.update(obj);
    }

    /**
     * This method is used to delete an object from the database.
     *
     * @param obj the object to be deleted
     * @throws SQLException if an error occurs while deleting the object
     */
    public void delete(T obj) throws SQLException {
        dao.delete(obj);
    }

    /**
     * This method is used to delete an object from the database by its ID.
     *
     * @param id the ID of the object to be deleted
     * @throws SQLException if an error occurs while deleting the object
     */
    public void delete(int id) throws SQLException {
        dao.deleteById(id);
    }
}