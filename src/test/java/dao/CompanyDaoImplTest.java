package dao;

import org.junit.jupiter.api.*;
import rest.api.rest_service.dao.ICompanyDao;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.exception.DaoException;

import java.sql.SQLException;
import java.util.Optional;

import static dao.PostgresContainerTest.container;


class CompanyDaoImplTest {

    public static ICompanyDao companyDao;

    @BeforeEach
     void beforeAll()  {
        container.start();
        companyDao = CompanyDaoImpl.getInstance();

    }

    @Test
    void checkSave() {
        String expectedName = "Lada";
        String expectedCity = "Tolyatti";
        CompanyEntity companyEntity = new CompanyEntity(
                1L,
                expectedName,
                expectedCity
        );
        Assertions.assertEquals("Lada", companyEntity.getName());
        companyEntity = companyDao.save(companyEntity);
        Optional<CompanyEntity> result = companyDao.findById(companyEntity.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedName, result.get().getName());
    }


    @Test
    void findById() {
        companyDao.save(new CompanyEntity(1L, "Google", "Moscow"));
        Optional<CompanyEntity> company = companyDao.findById(1L);

        Assertions.assertTrue(company.isPresent());
        Assertions.assertEquals("Google", company.get().getName());
        Assertions.assertEquals("Moscow", company.get().getCity());
        Assertions.assertEquals(1L, company.get().getId());

        Assertions.assertThrows(DaoException.class, () -> companyDao.findById(11L));

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
    void findAll() throws SQLException {
        companyDao.save(new CompanyEntity(1L, "Google", "Moscow"));
        companyDao.save(new CompanyEntity(2L, "Toyota", "Tolyatti"));
        Assertions.assertEquals(2, companyDao.findAll().size());
    }
}