package rest.api.rest_service.service.mapper;

import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.dto.CompanyDtoOut;

public interface ICompanyDtoMapper {

    CompanyEntity map(CompanyDtoIn companyDtoIn);

    CompanyDtoOut map(CompanyEntity companyEntity);
}
