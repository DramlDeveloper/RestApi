package dao;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.util.PropertiesUtil;

import java.util.Optional;

@Testcontainers
@Tag("DockerRequired")
class CompanyDaoImplTest {
    public static CompanyDaoImpl companyDao;
    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1-alpine")
            .withDatabaseName("postgres")
            .withUsername(PropertiesUtil.getProperty("db.username"))
            .withPassword(PropertiesUtil.getProperty("db.password"));

    @BeforeAll
    static void beforeAll() {
        container.start();
        companyDao = CompanyDaoImpl.getInstance();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @Test
    void checkSave() {
        String expectedName = "Lada";
        String expectedCity = "Tolyatti";
        CompanyEntity companyEntity = new CompanyEntity(
                expectedName,
                expectedCity
        );
        companyEntity = companyDao.save(companyEntity);
        Optional<CompanyEntity> result = companyDao.findById(companyEntity.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedName, result.get().getName());
    }

    @DisplayName("findBy_ID")
    @ParameterizedTest
    @CsvSource(value = {
            "1, true",
            "30, false"
    })
    void findById(Long expectedId, Boolean expectedValue) {
        Optional<CompanyEntity> company = companyDao.findById(expectedId);

        Assertions.assertEquals(expectedValue, company.isPresent());
        if (company.isPresent()) {
            Assertions.assertEquals(expectedId, company.get().getId());
        }
    }

    @Test
    void checkUpdate() {
        String expectedName = "Toyota";
        String expectedCity = "Tokio";

        CompanyEntity companyEntity = companyDao.findById(11L).get();
        String oldName = companyEntity.getName();
        String oldCity = companyEntity.getCity();
        companyEntity.setName(expectedName);
        companyEntity.setCity(expectedCity);
        companyDao.update(companyEntity);

        CompanyEntity result = companyDao.findById(11L).get();

        Assertions.assertNotEquals(expectedName, oldName);
        Assertions.assertNotEquals(expectedCity, oldCity);
        Assertions.assertEquals(expectedName, result.getName());
        Assertions.assertEquals(expectedCity, result.getCity());
    }

    @Test
    void deleteById() {
        Boolean expectedValue = true;
        int expectedSize = companyDao.findAll().size();

        CompanyEntity tempCompany = new CompanyEntity("Geely", "China");
        tempCompany = companyDao.save(tempCompany);

        int resultSizeBefore = companyDao.findAll().size();
        Assertions.assertNotEquals(expectedSize, resultSizeBefore);

        boolean resultDelete = companyDao.deleteById(tempCompany.getId());
        int resultSizeAfter = companyDao.findAll().size();

        Assertions.assertEquals(expectedValue, resultDelete);
        Assertions.assertEquals(expectedSize, resultSizeAfter);

    }

    @Test
    void findAll() {
        int expectedSize = 1;
        int resultSize = companyDao.findAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }
}