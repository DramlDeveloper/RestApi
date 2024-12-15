package dao;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.dao.impl.StaffDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.entity.StaffEntity;
import rest.api.rest_service.util.PropertiesUtil;

import java.util.Optional;

@Testcontainers
@Tag("DockerRequired")
class StaffDaoImplTest {
    public static StaffDaoImpl staffDao;
    public static CompanyDaoImpl companyDao;
    public static PostDaoImpl postDao;
    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1-alpine")
            .withDatabaseName("postgres")
            .withUsername(PropertiesUtil.getProperty("db.username"))
            .withPassword(PropertiesUtil.getProperty("db.password"));

    @BeforeAll
    static void beforeAll() {
        container.start();
        staffDao = StaffDaoImpl.getInstance();
        companyDao = CompanyDaoImpl.getInstance();
        postDao = PostDaoImpl.getInstance();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @Test
    void checkSave() {
        String expectedFirstName = "Fredi";
        String expectedLastName = "Colin";
        PostEntity post = postDao.findById(1L).orElse(null);
        CompanyEntity company = companyDao.findById(1L).orElse(null);
        StaffEntity staffEntity = new StaffEntity(
                expectedFirstName,
                expectedLastName,
                post,
                company
        );
        staffEntity = staffDao.save(staffEntity);
        Optional<StaffEntity> result = staffDao.findById(staffEntity.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedFirstName, result.get().getFirstName());
        Assertions.assertEquals(expectedLastName, result.get().getLastName());
        Assertions.assertEquals(post, result.get().getPost());
        Assertions.assertEquals(company, result.get().getCompany());
    }

    @DisplayName("findBy_ID")
    @ParameterizedTest
    @CsvSource(value = {
            "1, true",
            "30, false"
    })
    void findById(Long expectedId, Boolean expectedValue) {
        Optional<StaffEntity> staffEntity = staffDao.findById(expectedId);

        Assertions.assertEquals(expectedValue, staffEntity.isPresent());
        if (staffEntity.isPresent()) {
            Assertions.assertEquals(expectedId, staffEntity.get().getId());
        }
    }

    @Test
    void checkUpdate() {
        String expectedFirstName = "Viktor";
        String expectedLastName = "Svarowski";
        PostEntity post = postDao.findById(1L).orElse(null);
        CompanyEntity company = companyDao.findById(1L).orElse(null);

        StaffEntity staffEntity = staffDao.findById(1L).get();
        String oldFirstName = staffEntity.getFirstName();
        String oldLastName = staffEntity.getFirstName();
        PostEntity oldPost = staffEntity.getPost();
        CompanyEntity oldCompany = staffEntity.getCompany();

        staffEntity.setFirstName(expectedFirstName);
        staffEntity.setLastName(expectedLastName);
        staffDao.update(staffEntity);

        StaffEntity result = staffDao.findById(1L).get();

        Assertions.assertNotEquals(expectedFirstName, oldFirstName);
        Assertions.assertNotEquals(expectedLastName, oldLastName);
        Assertions.assertEquals(post, oldPost);
        Assertions.assertEquals(company, oldCompany);
        Assertions.assertEquals(post, result.getPost());
        Assertions.assertEquals(company, result.getCompany());
        Assertions.assertEquals(expectedFirstName, result.getFirstName());
        Assertions.assertEquals(expectedLastName, result.getLastName());
    }

    @Test
    void deleteById() {
        Boolean expectedValue = true;
        int expectedSize = companyDao.findAll().size();

        StaffEntity tempStaff = new StaffEntity(
                "Jon",
                "Bond",
                postDao.findById(1L).orElse(null),
                companyDao.findById(1L).orElse(null));
        tempStaff = staffDao.save(tempStaff);

        int resultSizeBefore = staffDao.findAll().size();
        Assertions.assertNotEquals(expectedSize, resultSizeBefore);

        boolean resultDelete = staffDao.deleteById(tempStaff.getId());
        int resultSizeAfter = staffDao.findAll().size();

        Assertions.assertEquals(expectedValue, resultDelete);
        Assertions.assertEquals(expectedSize, resultSizeAfter);

    }

    @Test
    void findAll() {
        int expectedSize = 1;
        int resultSize = staffDao.findAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }
}