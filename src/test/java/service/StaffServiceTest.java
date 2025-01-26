package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.dao.IStaffDao;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.entity.StaffEntity;
import rest.api.rest_service.service.dto.StaffDtoIn;
import rest.api.rest_service.service.dto.StaffDtoOut;
import rest.api.rest_service.service.impl.CompanyService;
import rest.api.rest_service.service.impl.PostService;
import rest.api.rest_service.service.impl.StaffService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StaffServiceTest {
    StaffDtoIn staffDtoIn = new StaffDtoIn(1L, "Jon", "Forest", 1L, 1L);
    StaffDtoOut staffDtoOut = new StaffDtoOut(1L, "Jon", "Forest", "Google", "Дизайнер");
    PostEntity postEntity = new PostEntity(1L, "Дизайнер");
    CompanyEntity companyEntity = new CompanyEntity(1L, "Google", "USA");
    StaffEntity staffEntity = new StaffEntity("Jon", "Forest", postEntity, companyEntity);

    @Mock
    private IStaffDao staffDao;
    @Mock
    private ICompanyDao companyDao;
    @Mock
    private IPostDao postDao;

    @InjectMocks
    private StaffService staffService;
    @InjectMocks
    private CompanyService companyService;
    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {MockitoAnnotations.initMocks(this);}

    @Test
    void save_staffService() {
        Mockito.when(staffDao.save(staffEntity)).thenReturn(staffEntity);

        Assertions.assertTrue(staffService.save(staffDtoIn));
        Assertions.assertFalse(staffService.save(null));
    }

    @Test
    void update_staffService() {
        Mockito.when(staffDao.findById(Mockito.anyLong())).thenReturn(Optional.of(staffEntity));

        Assertions.assertEquals(staffDtoOut.getFirstName(), staffService.update(staffDtoIn).getFirstName());
        Assertions.assertEquals(staffDtoOut.getLastName(), staffService.update(staffDtoIn).getLastName());
        Assertions.assertEquals(staffDtoOut.getNameCompany(), staffService.update(staffDtoIn).getNameCompany());
        Assertions.assertEquals(staffDtoOut.getTitlePost(), staffService.update(staffDtoIn).getTitlePost());
    }

    @Test
    void findById_staffService() {
        Long expectedId = 1L;

        Mockito.when(staffDao.findById(Mockito.anyLong())).thenReturn(Optional.of(staffEntity));

        Assertions.assertEquals(staffDtoOut.getFirstName(), staffService.findById(expectedId).getFirstName());
        Assertions.assertEquals(staffDtoOut.getLastName(), staffService.findById(expectedId).getLastName());
        Assertions.assertEquals(staffDtoOut.getNameCompany(), staffService.findById(expectedId).getNameCompany());
        Assertions.assertEquals(staffDtoOut.getTitlePost(), staffService.findById(expectedId).getTitlePost());
    }

    @Test
    void deleteById_staffService() {
        Long expectedId = 1L;

        Mockito.when(staffDao.deleteById(Mockito.anyLong())).thenReturn(true);

        Assertions.assertTrue(staffService.deleteById(expectedId));
        Assertions.assertFalse(staffService.deleteById(null));
    }

    @Test
    void findAll_postService() {
        List<StaffEntity> staffEntities = Arrays.asList(
                staffEntity,
                staffEntity
        );

        Mockito.when(staffDao.findAll()).thenReturn(staffEntities);
        Assertions.assertEquals(2, staffService.findAll().size());
        Assertions.assertEquals(staffEntity.getFirstName(), staffService.findAll().get(0).getFirstName());
        Assertions.assertEquals(staffEntity.getLastName(), staffService.findAll().get(1).getLastName());
        Assertions.assertEquals(staffEntity.getId(), staffService.findAll().get(0).getId());
    }
}