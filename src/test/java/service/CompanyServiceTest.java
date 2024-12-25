package service;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.service.impl.CompanyService;
import rest.api.rest_service.service.ICompanyService;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.dto.CompanyDtoOut;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class CompanyServiceTest {
    private static ICompanyService  companyService;
    private static ICompanyDao mockCompanyDao;
    private static CompanyDaoImpl oldCompanyDao;

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

    @BeforeAll
    static void beforeAll() {
        mockCompanyDao = Mockito.mock(ICompanyDao.class);
        setMock(mockCompanyDao);
        companyService = CompanyService.getInstance();
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = CompanyService.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);
        instance.set(instance, oldCompanyDao);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(mockCompanyDao);
    }

    @Test
    void save_companyService() {
        CompanyDtoIn dtoIn = new CompanyDtoIn(1L, "Google", "USA");
        CompanyEntity companyEntity = new CompanyEntity(1L, "Google", "USA");

        Mockito.when(mockCompanyDao.save(Mockito.any(CompanyEntity.class))).thenReturn(companyEntity);

        CompanyDtoOut saveCompany = companyService.save(dtoIn);
        Assertions.assertNotNull(saveCompany);
        Assertions.assertEquals( "name: Google city: USA", saveCompany.getDescription());
    }

    @Test
    void update_companyService() {
        CompanyDtoIn dto = new CompanyDtoIn(1L, "Yandex", "Russia");

        Mockito.when(mockCompanyDao.update(Mockito.any(CompanyEntity.class))).thenReturn(true);

        Assertions.assertTrue(companyService.update(dto));
    }

    @Test
    void findById_companyService() {
        Optional<CompanyEntity> companyEntity = Optional.of(new CompanyEntity("Google", "USA"));

        Mockito.when(mockCompanyDao.findById(Mockito.anyLong())).thenReturn(companyEntity);

        Assertions.assertEquals("name: Google city: USA", companyService.findById(1L).getDescription());
    }

    @Test
    void deleteById_companyService() {
        Long expectedId = 1L;

        Mockito.when(mockCompanyDao.deleteById(Mockito.anyLong())).thenReturn(true);

        Assertions.assertTrue(companyService.deleteById(expectedId));
    }

    @Test
    void findByIdNotFound() {
        Optional<CompanyEntity> companyEmpty = Optional.empty();

        Mockito.when(mockCompanyDao.findById(Mockito.anyLong())).thenReturn(companyEmpty);

        Assertions.assertEquals("not found", companyService.findById(5L).getDescription());
    }

    @Test
    void findAll_companyService() {
        List<CompanyEntity> companyEntities = Arrays.asList(new CompanyEntity());
        List<CompanyDtoOut> companyDtoOuts = Arrays.asList(new CompanyDtoOut("name: null city: null"));

        Mockito.when(mockCompanyDao.findAll()).thenReturn(companyEntities);

        Assertions.assertEquals(companyDtoOuts.get(0).getDescription(), companyService.findAll().get(0).getDescription());
    }
}