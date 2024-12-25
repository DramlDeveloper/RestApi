package dao;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.util.PropertiesUtil;

import java.util.Optional;

@Testcontainers
@Tag("DockerRequired")
class CompanyDaoImplTest {
    private static final String INIT_SQL = "script.sql";
    private static int containerPort = 5432;
    private static int localPort = 5432;
    public static ICompanyDao companyDao;
    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1-alpine")
            .withDatabaseName("postgres")
            .withUsername(PropertiesUtil.getProperty("db.username"))
            .withPassword(PropertiesUtil.getProperty("db.password"))
            .withExposedPorts(containerPort)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
            ))
            .withInitScript(INIT_SQL);

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


    @Test
    void findById() {
        checkSave();
        Optional<CompanyEntity> company = companyDao.findById(1L);

        Assertions.assertTrue(company.isPresent());
        Assertions.assertEquals("Lada", company.get().getName());
        Assertions.assertEquals(1L, company.get().getId());
    }

    @Test
    void checkUpdate() {
        checkSave();

        String expectedName = "Toyota";
        String expectedCity = "Tokio";

        CompanyEntity companyEntity = companyDao.findById(1L).get();
        String oldName = companyEntity.getName();
        String oldCity = companyEntity.getCity();
        companyEntity.setName(expectedName);
        companyEntity.setCity(expectedCity);
        companyDao.update(companyEntity);

        CompanyEntity result = companyDao.findById(1L).get();

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
        int resultSize = companyDao.findAll().size();

        Assertions.assertEquals(0, resultSize);
    }
}