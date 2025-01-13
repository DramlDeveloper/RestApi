package rest.api.rest_service.service.mapper.impl;

import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.dto.CompanyDtoOut;
import rest.api.rest_service.service.mapper.ICompanyDtoMapper;

public class ICompanyDtoMapperIml implements ICompanyDtoMapper {

    private static ICompanyDtoMapperIml INSTANCE = new ICompanyDtoMapperIml();

    public static ICompanyDtoMapper getInstance() {
        return INSTANCE;
    }

    private ICompanyDtoMapperIml() {
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
        if (companyEntity != null) {
            new CompanyDtoOut(companyEntity.getId(),
                    "name: %s city: %s".formatted(
                            companyEntity.getName(),
                            companyEntity.getCity()));
        }
        return null;
    }
}
