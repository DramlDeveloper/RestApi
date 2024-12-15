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
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.dao.impl.StaffDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.entity.StaffEntity;
import rest.api.rest_service.exception.DaoException;
import rest.api.rest_service.service.PostService;
import rest.api.rest_service.service.StaffService;
import rest.api.rest_service.service.dto.PostDtoIn;
import rest.api.rest_service.service.dto.PostDtoOut;
import rest.api.rest_service.service.dto.StaffDtoIn;
import rest.api.rest_service.service.dto.StaffDtoOut;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StaffServiceTest {

    @InjectMocks
    private static StaffService staffService;

    @Mock
    private static StaffDaoImpl staffDao;

    @Test
    void save_staffService() {
        StaffDtoIn dto = new StaffDtoIn("Jon", "Forest", 2L, 6L);
        StaffEntity staffEntity = new StaffEntity("Jon", "Forest", new PostEntity(), new CompanyEntity());

        Mockito.when(staffDao.save(Mockito.any(StaffEntity.class))).thenReturn(staffEntity);

        StaffDtoOut saveStaff = staffService.save(dto);
        Assertions.assertNotNull(saveStaff);
        Assertions.assertEquals("Jon", saveStaff.getFirstName());
        Assertions.assertEquals("Forest", saveStaff.getLastName());
        Assertions.assertEquals("Yandex", saveStaff.getNameCompany());
        Assertions.assertEquals("Архитектор", saveStaff.getTitlePost());
    }

    @Test
    void update_staffService() {
        Long expectedId = 1L;
        StaffDtoIn dto = new StaffDtoIn(expectedId, "Kely", "Moren", 2L, 6L);

        Mockito.when(staffDao.update(Mockito.any(StaffEntity.class))).thenReturn(true);

        Assertions.assertTrue(staffService.update(dto));
        Assertions.assertEquals("Kely", staffService.findById(expectedId).getFirstName());
        Assertions.assertEquals("Moren", staffService.findById(expectedId).getLastName());
        Assertions.assertEquals("Yandex", staffService.findById(expectedId).getNameCompany());
        Assertions.assertEquals("Архитектор", staffService.findById(expectedId).getTitlePost());
    }

    @Test
    void findById_staffService() {
        Long expectedId = 1L;
        Optional<StaffEntity> staffEntity = Optional.of(new StaffEntity());

        Mockito.when(staffDao.findById(Mockito.anyLong())).thenReturn(staffEntity);

        Assertions.assertEquals("Архитектор", staffService.findById(expectedId).getTitlePost());
        Assertions.assertEquals("Kely", staffService.findById(expectedId).getFirstName());
        Assertions.assertEquals("Moren", staffService.findById(expectedId).getLastName());
        Assertions.assertEquals("Yandex", staffService.findById(expectedId).getNameCompany());
    }

    @Test
    void deleteById_staffService() {
        Long expectedId = 1L;

        Mockito.when(staffDao.deleteById(Mockito.anyLong())).thenReturn(true);

        Assertions.assertTrue(staffService.deleteById(expectedId));
    }

        @Test
        void findByIdNotFound() {
            Optional<StaffEntity> staffEntity = Optional.empty();

            Mockito.when(staffDao.findById(Mockito.anyLong())).thenReturn(staffEntity);

            var exception = Assertions.assertThrows(DaoException.class, () -> {
                staffService.findById(0L);
            });

            Assertions.assertEquals("the ID does not exist", exception.getMessage());
        }

    @Test
    void findAll_postService() {
        List<StaffEntity> staffEntities = Arrays.asList(new StaffEntity());

        Mockito.when(staffDao.findAll()).thenReturn(staffEntities);
    }
}