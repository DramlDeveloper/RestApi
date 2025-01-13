package rest.api.rest_service.service.mapper;

import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.entity.StaffEntity;
import rest.api.rest_service.service.dto.StaffDtoIn;
import rest.api.rest_service.service.dto.StaffDtoOut;

public interface IStaffDtoMapper {

    StaffDtoOut map(StaffEntity staffEntity);

    StaffEntity map(StaffDtoIn staffDtoIn, IPostDao postDao, ICompanyDao companyDao);
}
