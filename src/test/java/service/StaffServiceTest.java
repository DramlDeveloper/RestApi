package service;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.dao.IStaffDao;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.dao.impl.StaffDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.entity.StaffEntity;
import rest.api.rest_service.exception.DaoException;
import rest.api.rest_service.service.IStaffService;
import rest.api.rest_service.service.dto.StaffDtoIn;
import rest.api.rest_service.service.dto.StaffDtoOut;
import rest.api.rest_service.service.impl.CompanyService;
import rest.api.rest_service.service.impl.PostService;
import rest.api.rest_service.service.impl.StaffService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


class StaffServiceTest {
    private static IStaffService staffService;
    private static IStaffDao mockStaffDao;
    private static StaffDaoImpl oldStaffDao;
    private static ICompanyDao mockCompanyDao;
    private static CompanyDaoImpl oldCompanyDao;
    private static IPostDao mockPostDao;
    private static PostDaoImpl oldPostDao;
    private static IStaffService mockStaffService;

/*    private static void setMock(IStaffDao mock) {
        try {
            Field instance = StaffDaoImpl.class.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            oldStaffDao = (StaffDaoImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setMock(ICompanyDao mock) {
        try {
            Field instance = CompanyDaoImpl.class.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            oldCompanyDao = (CompanyDaoImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setMock(IPostDao mock) {
        try {
            Field instance = PostDaoImpl.class.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            oldPostDao = (PostDaoImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        mockStaffDao = Mockito.mock(IStaffDao.class);
        setMock(mockStaffDao);

        mockCompanyDao = Mockito.mock(ICompanyDao.class);
        setMock(mockCompanyDao);

        mockPostDao = Mockito.mock(IPostDao.class);
        setMock(mockPostDao);

        staffService = StaffService.getInstance();
        mockStaffService = Mockito.mock(IStaffService.class);
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = StaffService.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);

        instance = CompanyService.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);

        instance = PostService.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(mockStaffDao);
    }


    @Test
    void save_staffService() {
        PostEntity postEntity = new PostEntity(1L, "Дизайнер");
        CompanyEntity companyEntity = new CompanyEntity(1L, "Google", "USA");
        StaffEntity staffEntity = new StaffEntity("Jon", "Forest", postEntity, companyEntity);
        StaffDtoIn dto = new StaffDtoIn(1L, "Jon", "Forest", 1L, 1L);

        Mockito.doReturn(staffEntity).when(mockStaffDao).save(Mockito.any(StaffEntity.class));
        Mockito.when(mockCompanyDao.save(Mockito.any(CompanyEntity.class))).thenReturn(companyEntity);
        Mockito.when(mockPostDao.save(Mockito.any(PostEntity.class))).thenReturn(postEntity);

        Assertions.assertTrue(staffService.save(dto));

    }

    @Test
    void update_staffService() {
        StaffDtoIn dto = new StaffDtoIn(1L, "Kely", "Moren", 1L, 1L);
        StaffService staffService = Mockito.mock(StaffService.class);

        Mockito.when(mockStaffDao.update(Mockito.any(StaffEntity.class))).thenReturn(true);
        Mockito.when(staffService.update(dto)).thenReturn(Mockito.mock(StaffDtoOut.class));

    }

    @Test
    void findById_staffServiceNotFound() {
        Long expectedId = 1L;
        Optional<StaffEntity> staffEntity = Optional.of(new StaffEntity());

        Mockito.when(mockStaffDao.findById(Mockito.anyLong())).thenReturn(staffEntity);

        Assertions.assertThrows(DaoException.class, () -> staffService.findById(expectedId));
    }

    @Test
    void deleteById_staffService() {
        Long expectedId = 1L;

        Mockito.when(mockStaffDao.deleteById(Mockito.anyLong())).thenReturn(true);

        Assertions.assertTrue(staffService.deleteById(expectedId));
    }

    @Test
    void findAll_postService() {
        List<StaffEntity> staffEntities = Arrays.asList(new StaffEntity());

        Mockito.when(mockStaffDao.findAll()).thenReturn(staffEntities);
        Mockito.when(mockStaffService.findAll()).thenReturn(new ArrayList<>());
    }*/
}