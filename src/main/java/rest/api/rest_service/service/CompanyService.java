package rest.api.rest_service.service;

import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.service.dto.CompanyDtoOut;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyService {
    private final static CompanyService INSTANCE = new CompanyService();
    private final CompanyDaoImpl companyDao = CompanyDaoImpl.getInstance();

    public static CompanyService getInstance() {
        return INSTANCE;
    }


    private CompanyService() {
    }

    public List<CompanyDtoOut> findAll() {
        return companyDao.findAll().stream()
                .map(
                        companyEntity -> new CompanyDtoOut(companyEntity.getId(),
                                "name: %s address: %s".formatted(
                                        companyEntity.getName(),
                                        companyEntity.getAddress())
                        )
                ).collect(Collectors.toList());
    }

    public CompanyDtoOut findById(Long id) {
                   return  companyDao.findById(id).stream()
                            .map(entry -> new CompanyDtoOut(entry.getId(),
                                    "name: %s address: %s".formatted(
                                            entry.getName(),
                                            entry.getAddress()))).findAny().orElse(new CompanyDtoOut(0L, "not found"));


    }


}
