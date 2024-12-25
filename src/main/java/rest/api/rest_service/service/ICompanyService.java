package rest.api.rest_service.service;

import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.dto.CompanyDtoOut;

import java.util.List;

public interface ICompanyService {
    CompanyDtoOut save(CompanyDtoIn companyDtoIn);

    List<CompanyDtoOut> findAll();

    CompanyDtoOut findById(Long id);

    boolean update(CompanyDtoIn companyDtoIn);

    boolean deleteById(Long id);
}
