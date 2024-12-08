package rest.api.rest_service.dao.impl;

import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.dao.Dao;

import java.util.List;
import java.util.Optional;


public class StaffDaoImpl implements Dao<CompanyEntity, Long> {

    private final static StaffDaoImpl INSTANCE = new StaffDaoImpl();

    public static StaffDaoImpl getInstance() {
        return INSTANCE;
    }

    private StaffDaoImpl() {
    }

    @Override
    public Optional<CompanyEntity> findById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public List<CompanyEntity> findAll() {
        return null;
    }

    @Override
    public boolean update(CompanyEntity companyEntity) {
        return false;
    }

    @Override
    public CompanyEntity save(CompanyEntity companyEntity) {
        return null;
    }
}
