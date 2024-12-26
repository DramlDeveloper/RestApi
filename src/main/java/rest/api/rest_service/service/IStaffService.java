package rest.api.rest_service.service;

import rest.api.rest_service.service.dto.StaffDtoIn;
import rest.api.rest_service.service.dto.StaffDtoOut;

import java.util.List;

public interface IStaffService {
    List<StaffDtoOut> findAll();

    StaffDtoOut findById(Long id);

    boolean save(StaffDtoIn staffDtoIn);

    boolean deleteById(Long id);

    boolean update(StaffDtoIn staffDtoIn);
}
