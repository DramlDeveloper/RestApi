package rest.api.rest_service.service.impl;

import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.service.ICompanyService;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.dto.CompanyDtoOut;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyService implements ICompanyService {
    private static CompanyService INSTANCE = new CompanyService();
    private final ICompanyDao companyDao = CompanyDaoImpl.getInstance();

    public static CompanyService getInstance() {
        return INSTANCE;
    }


    private CompanyService() {
    }

    public List<CompanyDtoOut> findAll() {
        return companyDao.findAll().stream()
                .map(
                        companyEntity -> new CompanyDtoOut(companyEntity.getId(),
                                "name: %s city: %s".formatted(
                                        companyEntity.getName(),
                                        companyEntity.getCity())
                        )
                ).collect(Collectors.toList());
    }

    public CompanyDtoOut findById(Long id) {
        return companyDao.findById(id).stream()
                .map(entry -> new CompanyDtoOut(entry.getId(),
                        "name: %s city: %s".formatted(
                                entry.getName(),
                                entry.getCity())))
                .findAny()
                .orElse(new CompanyDtoOut(0L, "not found"));
    }

    public CompanyDtoOut save(CompanyDtoIn companyDtoIn) {
        var id = companyDao.save(new CompanyEntity(
                companyDtoIn.getName(),
                companyDtoIn.getCity())
        ).getId();
        return new CompanyDtoOut(
                id, "name: %s city: %s".formatted(companyDtoIn.getName(),
                companyDtoIn.getCity()));
    }

    public boolean deleteById(Long id)  {
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
            companyDao.update(new CompanyEntity(
                    companyDtoIn.getId(),
                    companyDtoIn.getName(),
                    companyDtoIn.getCity()
            ));
            return true;
        } else {
            return false;
        }
    }
}