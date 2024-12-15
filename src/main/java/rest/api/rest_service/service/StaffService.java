package rest.api.rest_service.service;

import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.dao.impl.StaffDaoImpl;
import rest.api.rest_service.entity.StaffEntity;
import rest.api.rest_service.exception.DaoException;
import rest.api.rest_service.service.dto.StaffDtoIn;
import rest.api.rest_service.service.dto.StaffDtoOut;

import java.util.List;

public class StaffService {

    private final static StaffService INSTANCE = new StaffService();
    private final StaffDaoImpl staffDao = StaffDaoImpl.getInstance();
    private final CompanyDaoImpl companyDao = CompanyDaoImpl.getInstance();
    private final PostDaoImpl postDao = PostDaoImpl.getInstance();

    public static StaffService getInstance() {
        return INSTANCE;
    }

    private StaffService() {
    }

    public List<StaffDtoOut> findAll() {
        return staffDao.findAll().stream()
                .map(
                        entity -> new StaffDtoOut(
                                entity.getId(),
                                entity.getFirstName(),
                                entity.getLastName(),
                                entity.getCompany().getName(),
                                entity.getPost().getTitle())
                ).toList();
    }

    public StaffDtoOut findById(Long id) {
        StaffDtoOut staffDtoOut = null;
        try {
             staffDtoOut = staffDao.findById(id).stream()
                    .map(entity -> new StaffDtoOut(
                            entity.getId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getCompany().getName(),
                            entity.getPost().getTitle()))
                    .findAny()
                    .orElseThrow();
        } catch (Exception e) {
            throw new DaoException("the ID does not exist");
        }
        return staffDtoOut;
    }

    public StaffDtoOut save(StaffDtoIn staffDtoIn) {
                staffDao.save(new StaffEntity(
                staffDtoIn.getFirstName(),
                staffDtoIn.getLastName(),
                postDao.findById(staffDtoIn.getPostId()).orElse(null),
                companyDao.findById(staffDtoIn.getCompanyId()).orElse(null)
        ));
                return new StaffDtoOut(
                        staffDtoIn.getId(),
                        staffDtoIn.getFirstName(),
                        staffDtoIn.getLastName(),
                        companyDao.findById(staffDtoIn.getCompanyId()).get().getName(),
                        postDao.findById(staffDtoIn.getPostId()).get().getTitle());
    }

    public boolean deleteById(Long id) {
        if(staffDao.findById(id).isPresent()) {
            staffDao.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAll() {
        for (StaffEntity staffEntity : staffDao.findAll()) {
            staffDao.deleteById(staffEntity.getId());
        }
    }

    public boolean update(StaffDtoIn staffDtoIn) {
        if (staffDao.findById(staffDtoIn.getId()).isPresent()) {
            staffDao.update(new StaffEntity(
                    staffDtoIn.getId(),
                    staffDtoIn.getFirstName(),
                    staffDtoIn.getLastName(),
                    postDao.findById(staffDtoIn.getPostId()).orElse(null),
                    companyDao.findById(staffDtoIn.getCompanyId()).orElse(null)));
        return true;
        }
        return false;
    }
}