package rest.api.rest_service.dao.impl;

import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.db.ConnectionManager;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PostDaoImpl implements IPostDao {

    private static IPostDao INSTANCE;

    public static IPostDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostDaoImpl();
        }
        return INSTANCE;
    }

    private PostDaoImpl() {
    }

    private final static String SAVE_SQL = """
            INSERT INTO post (title)
            VALUES (?)
            """;

    private final static String FIND_BY_ID_SQL = """
            SELECT id, title FROM post
            WHERE id = ?
            """;
    private final static String FIND_ALL_SQL = """
            SELECT id, title FROM post
            """;

    private final static String UPDATE_SQL = """
            UPDATE post
            SET title = ? WHERE id = ?
            """;

    private final static String DELETE_SQL = """
            DELETE FROM post
            WHERE id = ?
            """;

    @Override
    public PostEntity save(PostEntity postEntity) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, postEntity.getTitle());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                postEntity.setId(keys.getLong("id"));
            }
            return postEntity;
        } catch (SQLException e) {
            throw new DaoException("Сохранить не удалось проверьте верны ли параметры");
        }
    }

    @Override
    public Optional<PostEntity> findById(Long id) {
        try (Connection connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException("Найти не удалось проверьте верны ли параметры");
        }
    }

    public Optional<PostEntity> findById(Long id, Connection connection) throws  DaoException {
        PostEntity postEntity = null;
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                postEntity = builderPostEntity(resultSet);
            }
            return Optional.ofNullable(postEntity);
        } catch (SQLException e) {
            throw new DaoException("Найти не удалось проверьте верны ли параметры");
        }
    }

    @Override
    public List<PostEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<PostEntity> postEntityArrayList = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                postEntityArrayList.add(builderPostEntity(resultSet));
            }
            return postEntityArrayList;
        } catch (SQLException e) {
            throw new DaoException("Данные отсутствуют");
        }
    }

    @Override
    public boolean update(PostEntity postEntity) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, postEntity.getTitle());
            statement.setLong(2, postEntity.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Обновить не удалось проверьте верны ли параметры");
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Удалить не удалось проверьте верны ли параметры");
        }
    }

    private PostEntity builderPostEntity(ResultSet resultSet) throws SQLException {
        return new PostEntity(
                resultSet.getLong("id"),
                resultSet.getString("title"));
    }
}