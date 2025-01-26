package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.dto.CompanyDtoOut;
import rest.api.rest_service.service.impl.CompanyService;

import java.util.Arrays;
import java.util.Optional;

public class CompanyServiceTest {

    @Mock
    private ICompanyDao companyDao;
    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save_companyService() {
        CompanyDtoIn dtoIn = new CompanyDtoIn(1L, "Google", "USA");
        CompanyEntity companyEntity = new CompanyEntity(1L, "Google", "USA");

        Mockito.when(companyDao.save(companyEntity)).thenReturn(companyEntity);

        Assertions.assertEquals("name: Google city: USA", companyService.save(dtoIn).getDescription());
        Assertions.assertEquals(1L, companyService.save(dtoIn).getId());
    }

    @Test
    void findById_companyService() {
        CompanyDtoOut company = new CompanyDtoOut(1L, "name: Google, city: USA");
        Optional<CompanyEntity> companyEntity = Optional.of(new CompanyEntity(1L, "Google", "USA"));

        Mockito.when(companyDao.findById(Mockito.anyLong())).thenReturn(companyEntity);

        Assertions.assertEquals("name: Google city: USA", companyService.findById(company.getId()).getDescription());
        Assertions.assertEquals(1L, companyService.findById(company.getId()).getId());
    }

    @Test
    void update_companyService() {
        CompanyEntity companyEntity = new CompanyEntity(1L, "Google", "USA");
        CompanyDtoIn dtoIn = new CompanyDtoIn(1L, "Google", "USA");

        Mockito.doReturn(true).when(companyDao).update(companyEntity);

        Assertions.assertTrue(companyService.update(dtoIn));
    }

    @Test
    void deleteById_companyService() {
        Long expectedId = 1L;

        Mockito.when(companyDao.deleteById(expectedId)).thenReturn(true);

        Assertions.assertTrue(companyService.deleteById(expectedId));
    }

    @Test
    void findAll_companyService() {
        Mockito.when(companyDao.findAll()).thenReturn(Arrays.asList(
                new CompanyEntity(1L, "Toyota", "Tokio"),
                new CompanyEntity(2L, "Lada", "Tolyatti")
        ));
        Assertions.assertEquals(2, companyService.findAll().size());
        Assertions.assertEquals("name: Toyota city: Tokio", companyService.findAll().get(0).getDescription());
        Assertions.assertEquals("name: Lada city: Tolyatti", companyService.findAll().get(1).getDescription());

    }
}