package models.dataBase;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
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