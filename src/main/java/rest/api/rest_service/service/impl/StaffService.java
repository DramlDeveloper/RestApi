package rest.api.rest_service.service.impl;

import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.dao.IStaffDao;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.dao.impl.StaffDaoImpl;
import rest.api.rest_service.entity.StaffEntity;
import rest.api.rest_service.exception.DaoException;
import rest.api.rest_service.service.IStaffService;
import rest.api.rest_service.service.dto.StaffDtoIn;
import rest.api.rest_service.service.dto.StaffDtoOut;
import rest.api.rest_service.service.mapper.IStaffDtoMapper;
import rest.api.rest_service.service.mapper.impl.StaffDtoMapperImpl;

import java.util.List;

public class StaffService implements IStaffService {

    private final static StaffService INSTANCE = new StaffService();
    private  IStaffDao staffDao = StaffDaoImpl.getInstance();
    private  ICompanyDao companyDao = CompanyDaoImpl.getInstance();
    private  IPostDao postDao = PostDaoImpl.getInstance();
    private  final IStaffDtoMapper staffDtoMapper = StaffDtoMapperImpl.getInstance();

    public static StaffService getInstance() {
        return INSTANCE;
    }

    public StaffService() {
    }

    public List<StaffDtoOut> findAll() {
            return staffDao.findAll().stream()
                    .map(staffDtoMapper::map).toList();
        }

    public StaffDtoOut findById(Long id) {
            return staffDao.findById(id).stream()
                    .map(staffDtoMapper::map)
                    .findAny()
                    .orElse(null);

    }

    public boolean save(StaffDtoIn staffDtoIn) {
        StaffEntity staffEntity;
        if(staffDtoIn != null) {
            staffEntity = staffDtoMapper.map(staffDtoIn, postDao, companyDao);
            staffDao.save(staffEntity);
            return true;
        }
        return false;
    }

    public boolean deleteById(Long id) {
        if (id != null){
            return staffDao.deleteById(id);
        }
        return false;
    }

    public StaffDtoOut update(StaffDtoIn staffDtoIn) {
            staffDao.update(staffDtoMapper.map(staffDtoIn, postDao, companyDao));
            return findById(staffDtoIn.getId());
    }
}