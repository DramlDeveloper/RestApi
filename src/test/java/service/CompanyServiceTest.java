package service;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import rest.api.rest_service.dao.Dao;
import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.service.impl.CompanyService;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.dto.CompanyDtoOut;


public class CompanyServiceTest {
    private CompanyService companyService;
    private ICompanyDao mockCompanyDao;

    @BeforeEach
    void beforeEach() {
        mockCompanyDao = Mockito.mock(ICompanyDao.class);
        companyService = new CompanyService();
        companyService.setCompanyDao(mockCompanyDao);
    }

    @Test
    void save_companyService() {
        CompanyEntity company = new CompanyEntity(1L, "Google", "USA");
        CompanyDtoIn dtoIn = new CompanyDtoIn(1L, "Google", "USA");

        Mockito.doReturn(company).when(mockCompanyDao).save(company);
        companyService.setCompanyDao(mockCompanyDao);
        CompanyDtoOut saveCompany = companyService.save(dtoIn);

        Assertions.assertNotNull(saveCompany);
        //  Assertions.assertEquals(1L, saveCompany.getId());
        Assertions.assertEquals("name: Google city: USA", saveCompany.getDescription());

    }

    @Test
    void update_companyService() {
        CompanyDtoIn dto = new CompanyDtoIn(1L, "Yandex", "Russia");
        companyService.save(dto);

        boolean result = companyService.update(new CompanyDtoIn(1L, "Google", "Russia"));
        Assertions.assertTrue(result);
    }

    @Test
    void findById_companyService() {

        CompanyDtoOut result = companyService.findById(1L);

        Assertions.assertEquals("name: Google city: Russia", result.getDescription());
    }

    @Test
    void deleteById_companyService() {
        Long expectedId = 1L;


        Assertions.assertTrue(companyService.deleteById(expectedId));
    }
/*
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
     //   Mockito.when(mockCompanyService.findAll()).thenReturn(companyDtoOuts);
    }*/
}