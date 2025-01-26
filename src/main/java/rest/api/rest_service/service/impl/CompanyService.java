package rest.api.rest_service.service.impl;

import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.service.ICompanyService;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.dto.CompanyDtoOut;
import rest.api.rest_service.service.mapper.ICompanyDtoMapper;
import rest.api.rest_service.service.mapper.impl.CompanyDtoMapperIml;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyService implements ICompanyService {
    private final static CompanyService INSTANCE = new CompanyService();
    private  ICompanyDao companyDao = CompanyDaoImpl.getInstance();
    private final ICompanyDtoMapper iCompanyDtoMapper = CompanyDtoMapperIml.getInstance();

    public static CompanyService getInstance() {
        return INSTANCE;
    }

    public CompanyService() {
    }

    public List<CompanyDtoOut> findAll() {
        return companyDao.findAll().stream()
                .map(iCompanyDtoMapper::map).toList();
    }

    public CompanyDtoOut findById(Long id) {
        return companyDao.findById(id).stream()
                .map(iCompanyDtoMapper::map)
                .findAny()
                .orElse(new CompanyDtoOut(0L, "not found"));
    }

    public CompanyDtoOut save(CompanyDtoIn companyDtoIn) {
      return iCompanyDtoMapper.map(companyDao.save(iCompanyDtoMapper.map(companyDtoIn)));
    }

    public boolean deleteById(Long id) {
        return companyDao.deleteById(id);
    }

    public boolean update(CompanyDtoIn companyDtoIn) {
        return companyDao.update(iCompanyDtoMapper.map(companyDtoIn));
    }
}