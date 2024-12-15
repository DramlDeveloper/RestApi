package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.service.CompanyService;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.dto.CompanyDtoOut;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CompanyServiceTest {

    @InjectMocks
    private static CompanyService companyService;

    @Mock
    private static CompanyDaoImpl companyDao;

    @Test
    void save_companyService() {
        CompanyDtoIn dto = new CompanyDtoIn( "Google", "USA");
        CompanyEntity companyEntity = new CompanyEntity( "Google", "USA");

        Mockito.when(companyDao.save(Mockito.any(CompanyEntity.class))).thenReturn(companyEntity);

        CompanyDtoOut saveCompany = companyService.save(dto);
        Assertions.assertNotNull(saveCompany);
        Assertions.assertEquals( "name: Google city: USA", saveCompany.getDescription());
    }

    @Test
    void update_companyService() {
        Long expectedId = 5L;

        CompanyDtoIn dto = new CompanyDtoIn(expectedId, "Yandex", "Russia");

        Mockito.when(companyDao.update(Mockito.any(CompanyEntity.class))).thenReturn(true);

        Assertions.assertTrue(companyService.update(dto));
        Assertions.assertEquals("name: Yandex city: Russian", companyService.findById(5L).getDescription());
    }

    @Test
    void findById_companyService() {

        Optional<CompanyEntity> companyEntity =  Optional.of(new CompanyEntity( "Google", "USA"));

        Mockito.when(companyDao.findById(Mockito.anyLong())).thenReturn(companyEntity);

        Assertions.assertEquals( "name: Yandex city: Russia", companyService.findById(5L).getDescription());
    }

    @Test
    void deleteById_companyService()  {
        Long expectedId = 5L;

        Mockito.when(companyDao.deleteById(Mockito.anyLong())).thenReturn(true);

        Assertions.assertTrue(companyService.deleteById(expectedId));
    }

    @Test
    void findByIdNotFound() {
        Optional<CompanyEntity> companyEmpty = Optional.empty();

        Mockito.when(companyDao.findById(Mockito.anyLong())).thenReturn(companyEmpty);

        Assertions.assertEquals("not found", companyService.findById(5L).getDescription());
    }

    @Test
    void findAll_companyService() {
        List<CompanyEntity> companyEntities = Arrays.asList(new CompanyEntity());

        Mockito.when(companyDao.findAll()).thenReturn(companyEntities);
    }
}