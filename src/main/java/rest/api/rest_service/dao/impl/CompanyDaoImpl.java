package rest.api.rest_service.dao.impl;

import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.db.ConnectionManager;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDaoImpl implements ICompanyDao {

    private static ICompanyDao INSTANCE;

    public static ICompanyDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CompanyDaoImpl();
        }
        return INSTANCE;
    }

    private CompanyDaoImpl() {
    }

    private final static String SAVE_SQL = """
            INSERT INTO company (name, city)
            VALUES (?, ?)
            """;

    private final static String FIND_BY_ID_SQL = """
            SELECT id, name, city FROM company
            WHERE id = ?
            """;
    private final static String FIND_ALL_SQL = """
            SELECT id, name, city FROM company
            """;

    private final static String UPDATE_SQL = """
            UPDATE company
            SET name = ?, city = ? WHERE id = ?
            """;

    private final static String DELETE_SQL = """
            DELETE FROM company
            WHERE id = ?
            """;

    @Override
    public CompanyEntity save(CompanyEntity companyEntity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, companyEntity.getName());
            statement.setString(2, companyEntity.getCity());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                companyEntity.setId(keys.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DaoException("Сохранить не удалось проверьте верны ли параметры");
        }
        return companyEntity;
    }

    @Override
    public Optional<CompanyEntity> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException("Найти не удалось проверьте верны ли параметры");
        }
    }

    public Optional<CompanyEntity> findById(Long id, Connection connection) {
        CompanyEntity company = null;
        // Здесь используем try with resources
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next()) {
                company = builderCompanyEntity(result);
            }
            return Optional.ofNullable(company);
        } catch (SQLException e) {
            throw new DaoException("Найти не удалось проверьте верны ли параметры");
        }
    }


    @Override
    public List<CompanyEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<CompanyEntity> companyEntityList = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                companyEntityList.add(builderCompanyEntity(result)
                );
            }
            return companyEntityList;
        } catch (SQLException e) {
            throw new DaoException("Данные отсутствуют");
        }
    }

    @Override
    public boolean update(CompanyEntity company) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, company.getName());
            statement.setString(2, company.getCity());
            statement.setLong(3, company.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Обновить не удалось проверьте верны ли параметры");
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Удалить не удалось проверьте верны ли параметры");
        }
    }

    private CompanyEntity builderCompanyEntity(ResultSet resultSet) throws SQLException {
        return new CompanyEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("city"));
    }
}
