package rest.api.rest_service.dao.impl;


import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.exception.RepositoryException;
import rest.api.rest_service.db.ConnectionManager;
import rest.api.rest_service.dao.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class CompanyDaoImpl implements Dao<CompanyEntity, Long> {

    private static final CompanyDaoImpl INSTANCE = new CompanyDaoImpl();

    public static CompanyDaoImpl getInstance() {
        return INSTANCE;
    }

    private CompanyDaoImpl() {
    }

    private final static String SAVE_SQL = """
            INSERT INTO company (name, address)
            VALUES (?, ?)
            """;

    private final static String FIND_BY_ID_SQL = """
            SELECT id, name, address FROM company
            WHERE id = ?
            """;
    private final static String FIND_ALL_SQL = """
            SELECT id, name, address FROM company
            """;

    private final static String UPDATE_SQL = """
            UPDATE company
            SET name = ?, address = ? WHERE id = ?
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
            statement.setString(2, companyEntity.getAddress());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                companyEntity.setId(keys.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return companyEntity;
    }

    @Override
    public Optional<CompanyEntity> findById(Long id) {
        CompanyEntity company = null;
        // Здесь используем try with resources
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();

            if (result.next()) {
                company = builderCompanyEntity(result);
            }
            return Optional.ofNullable(company);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CompanyEntity> findAll(CompanyEntity companyEntity, Connection connection) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if(companyEntity.getName() != null){
            parameters.add(companyEntity.getName());
            whereSql.add("name = ?");
        }
        if (companyEntity.getAddress() != null) {
            parameters.add(companyEntity.getAddress());
            whereSql.add("address = ?");
        }
        String wSql = whereSql.stream().collect(Collectors.joining(
                " AMD ",
                " WHERE ",
                " limit ? offset ?"
        ));


        String sql = FIND_ALL_SQL + wSql;
        try (var connectionM = connection;
             var statement = connection.prepareStatement(sql)) {
            List<CompanyEntity> companyEntityList = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                companyEntityList.add(builderCompanyEntity(result)
                );
            }
            return companyEntityList;
        } catch (SQLException e) {
            throw new RepositoryException(e);
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
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean update(CompanyEntity company) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, company.getName());
            statement.setString(2, company.getAddress());
            statement.setLong(3, company.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private CompanyEntity builderCompanyEntity(ResultSet resultSet) throws SQLException {
        return new CompanyEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("address"));
    }
}
