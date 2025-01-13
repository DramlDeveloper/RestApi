package rest.api.rest_service.service.impl;

import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.service.ICompanyService;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.dto.CompanyDtoOut;
import rest.api.rest_service.service.mapper.ICompanyDtoMapper;
import rest.api.rest_service.service.mapper.impl.ICompanyDtoMapperIml;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyService implements ICompanyService {
    private final static CompanyService INSTANCE = new CompanyService();
    private final ICompanyDao companyDao = CompanyDaoImpl.getInstance();
    private final ICompanyDtoMapper ICompanyDtoMapper = ICompanyDtoMapperIml.getInstance();

    public static CompanyService getInstance() {
        return INSTANCE;
    }


    private CompanyService() {
    }

    public List<CompanyDtoOut> findAll() {
        return companyDao.findAll().stream()
                .map(ICompanyDtoMapper::map).collect(Collectors.toList());
    }

    public CompanyDtoOut findById(Long id) {
        return companyDao.findById(id).stream()
                .map(ICompanyDtoMapper::map)
                .findAny()
                .orElse(new CompanyDtoOut(0L, "not found"));
    }

    public CompanyDtoOut save(CompanyDtoIn companyDtoIn) {
        var id = companyDao.save(ICompanyDtoMapper.map(companyDtoIn)).getId();
        return findById(id);
    }

    public boolean deleteById(Long id) {
        return companyDao.deleteById(id);
    }

    public void deleteAll() {
        if (!companyDao.findAll().isEmpty()) {
            for (CompanyEntity companyEntity : companyDao.findAll()) {
                companyDao.deleteById(companyEntity.getId());
            }
        }
    }

    public boolean update(CompanyDtoIn companyDtoIn) {
        if (companyDtoIn != null) {
            companyDao.update(ICompanyDtoMapper.map(companyDtoIn));
            return true;
        }
        return false;
    }
}