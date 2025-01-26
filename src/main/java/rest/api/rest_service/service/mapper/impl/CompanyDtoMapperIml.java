package rest.api.rest_service.service.mapper.impl;

import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.dto.CompanyDtoOut;
import rest.api.rest_service.service.mapper.ICompanyDtoMapper;

public class CompanyDtoMapperIml implements ICompanyDtoMapper {

    private static CompanyDtoMapperIml INSTANCE = new CompanyDtoMapperIml();

    public static ICompanyDtoMapper getInstance() {
        return INSTANCE;
    }

    private CompanyDtoMapperIml() {
    }

    @Override
    public CompanyEntity map(CompanyDtoIn companyDtoIn) {
        return new CompanyEntity(
                companyDtoIn.getId(),
                companyDtoIn.getName(),
                companyDtoIn.getCity()
        );
    }

    @Override
    public CompanyDtoOut map(CompanyEntity companyEntity) {
        CompanyDtoOut companyDtoOut = new CompanyDtoOut();
        if (companyEntity != null) {
        companyDtoOut = new CompanyDtoOut(
                companyEntity.getId(),
                "name: %s city: %s".formatted(
                        companyEntity.getName(),
                        companyEntity.getCity()));
        }
        return companyDtoOut;
    }
}