package dao;

import org.junit.jupiter.api.*;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;
import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.dao.IStaffDao;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.dao.impl.StaffDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.entity.StaffEntity;

import java.util.Optional;

import static dao.PostgresContainerTest.container;

class StaffDaoImplTest {

    public static IStaffDao staffDao;
    public static ICompanyDao companyDao;
    public static IPostDao postDao;

    @BeforeEach
     void beforeAll() {
        container.start();
        staffDao = StaffDaoImpl.getInstance();
        companyDao = CompanyDaoImpl.getInstance();
        postDao = PostDaoImpl.getInstance();
    }

    @Test
    void checkSave() {
        String expectedFirstName = "Fredi";
        String expectedLastName = "Colin";
        PostEntity post = postDao.save(new PostEntity("Архитектор"));
        CompanyEntity company = companyDao.save(new CompanyEntity("Lada", "Tolyatti"));
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

    @Test
    void findById() {
        checkSave();
        Optional<StaffEntity> staffEntity = staffDao.findById(1L);

        Assertions.assertTrue(staffEntity.isPresent());
        Assertions.assertEquals(1L, staffEntity.get().getId());
        Assertions.assertEquals("Fredi", staffEntity.get().getFirstName());
        Assertions.assertEquals("Colin", staffEntity.get().getLastName());
        Assertions.assertEquals("Lada", staffEntity.get().getCompany().getName());
        Assertions.assertEquals("Tolyatti", staffEntity.get().getCompany().getCity());
        Assertions.assertEquals("Архитектор", staffEntity.get().getPost().getTitle());
    }

    @Test
    void checkUpdate() {
        checkSave();

        String oldFirstName = staffDao.findById(1L).get().getFirstName(); // "Fredi"
        String oldLastName = staffDao.findById(1L).get().getLastName(); // "Colin"
        Long oldPostId = staffDao.findById(1L).get().getPost().getId(); // 1L
        Long oldCompanyId = staffDao.findById(1L).get().getCompany().getId(); // 1L

        String expectedFirstName = "Viktor";
        String expectedLastName = "Svarowski";

        StaffEntity staffEntity = new StaffEntity(
                1L,
                expectedFirstName,
                expectedLastName,
                postDao.findById(1L).get(),
                companyDao.findById(1L).get()
        );

        Assertions.assertEquals(oldFirstName, staffDao.findById(1L).get().getFirstName());
        Assertions.assertEquals(oldLastName, staffDao.findById(1L).get().getLastName());

        boolean resultUpdate = staffDao.update(staffEntity);

        Assertions.assertTrue(resultUpdate);
        Assertions.assertEquals(1L, staffDao.findById(1L).get().getId());
        Assertions.assertEquals(expectedFirstName, staffDao.findById(1L).get().getFirstName());
        Assertions.assertEquals(expectedLastName, staffDao.findById(1L).get().getLastName());
//        Assertions.assertEquals(oldPostId, staffDao.findById(1L).get().getPost().getId());
//        Assertions.assertEquals(oldCompanyId, staffDao.findById(1L).get().getCompany().getId());

    }

    @Test
    void deleteById() {
        checkSave();
        boolean resultDelete = staffDao.deleteById(1L);

        Assertions.assertTrue(resultDelete);
    }

    @Test
    void findAll() {
        Assertions.assertEquals(0, staffDao.findAll().size());

        checkSave();
        Assertions.assertEquals(1, staffDao.findAll().size());
    }
}