package rest.api.rest_service.dao.impl;

import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.dao.IStaffDao;
import rest.api.rest_service.db.ConnectionManager;
import rest.api.rest_service.entity.StaffEntity;
import rest.api.rest_service.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class StaffDaoImpl implements IStaffDao {

    private static IStaffDao INSTANCE;

    public static IStaffDao getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StaffDaoImpl();
        }
        return INSTANCE;
    }

    public ICompanyDao companyDao = CompanyDaoImpl.getInstance();
    public IPostDao postDao = PostDaoImpl.getInstance();

    private StaffDaoImpl() {
    }

    private final static String SAVE_SQL = """
            INSERT INTO staff (first_name, last_name, company_id, post_id)
            VALUES (?, ?, ?, ?)
            """;

    private final static String FIND_BY_ID_SQL = """
            SELECT public.staff.id, first_name, last_name, staff.company_id, name, staff.post_id, title
            FROM staff
                     LEFT JOIN company ON staff.company_id = company.id
                     LEFT JOIN post on staff.post_id = post.id
                     WHERE staff.id = ?
            """;
    private final static String FIND_ALL_SQL = """
            SELECT public.staff.id, first_name, last_name, staff.company_id, name, staff.post_id, title
            FROM staff
                     LEFT JOIN company ON staff.company_id = company.id
                     LEFT JOIN post on staff.post_id = post.id
            """;

    private final static String UPDATE_SQL = """
            UPDATE staff
            SET first_name = ?, last_name = ?, company_id = ?, post_id = ? WHERE id = ?
            """;

    private final static String DELETE_SQL = """
            DELETE FROM staff
            WHERE id = ?
            """;


    @Override
    public StaffEntity save(StaffEntity staffEntity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, staffEntity.getFirstName());
            preparedStatement.setString(2, staffEntity.getLastName());
            preparedStatement.setLong(3, staffEntity.getCompany().getId());
            preparedStatement.setLong(4, staffEntity.getPost().getId());
            preparedStatement.executeUpdate();
            var keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                staffEntity.setId(keys.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DaoException("Сохранить не удалось проверьте верны ли параметры");
        }
        return staffEntity;
    }

    @Override
    public Optional<StaffEntity> findById(Long id) {
        StaffEntity staffEntity = null;
        try (Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                staffEntity = builderStaff(resultSet);
            }

        } catch (SQLException e) {
            throw new DaoException("Найти не удалось проверьте верны ли параметры");
        }
        return Optional.ofNullable(staffEntity);
    }

    @Override
    public List<StaffEntity> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<StaffEntity> staffEntities = new ArrayList<>();
            while (resultSet.next()) {
                staffEntities.add(builderStaff(resultSet));
            }
            return staffEntities;
        } catch (SQLException e) {
            throw new DaoException("Данные отсутствуют");
        }
    }

    @Override
    public boolean update(StaffEntity staffEntity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, staffEntity.getFirstName());
            preparedStatement.setString(2, staffEntity.getLastName());
            preparedStatement.setLong(3, staffEntity.getCompany().getId());
            preparedStatement.setLong(4, staffEntity.getPost().getId());
            preparedStatement.setLong(5, staffEntity.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Обновить не удалось проверьте верны ли параметры");
        }
    }


    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Удалить не удалось проверьте верны ли параметры");
        }
    }



    private StaffEntity builderStaff(ResultSet resultSet) throws SQLException {
        return new StaffEntity(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                postDao.findById(resultSet.getLong("post_id")).orElse(null),
                companyDao.findById(resultSet.getLong("company_id")).orElse(null));
    }
}