package rest.api.rest_service.repository.impl;

import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.repository.Repository;

import java.util.List;
import java.util.Optional;


public class PostRepositoryImpl implements Repository<CompanyEntity, Long> {

    private final static PostRepositoryImpl INSTANCE = new PostRepositoryImpl();

    public static PostRepositoryImpl getInstance() {
        return INSTANCE;
    }

    private PostRepositoryImpl() {
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
