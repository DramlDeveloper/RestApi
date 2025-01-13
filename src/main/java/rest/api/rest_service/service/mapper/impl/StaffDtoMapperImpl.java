package rest.api.rest_service.service.mapper.impl;

import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.entity.StaffEntity;
import rest.api.rest_service.service.dto.StaffDtoIn;
import rest.api.rest_service.service.dto.StaffDtoOut;
import rest.api.rest_service.service.mapper.IStaffDtoMapper;

public class StaffDtoMapperImpl implements IStaffDtoMapper {

    private static final IStaffDtoMapper INSTANCE = new StaffDtoMapperImpl();

    public static IStaffDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public StaffDtoOut map(StaffEntity staffEntity) {
        if (staffEntity == null) {
            return new StaffDtoOut(
                    staffEntity.getId(),
                    staffEntity.getFirstName(),
                    staffEntity.getLastName(),
                    staffEntity.getCompany().getName(),
                    staffEntity.getPost().getTitle());
        }
        return null;
    }

    @Override
    public StaffEntity map(StaffDtoIn staffDtoIn, IPostDao postDao, ICompanyDao companyDao) {
        if (staffDtoIn != null) {
            return new StaffEntity(
                    staffDtoIn.getPostId(),
                    staffDtoIn.getFirstName(),
                    staffDtoIn.getLastName(),
                    postDao.findById(staffDtoIn.getPostId()).orElse(null),
                    companyDao.findById(staffDtoIn.getCompanyId()).orElse(null));
        }
        return null;
    }
}
