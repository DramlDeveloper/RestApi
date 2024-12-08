package rest.api.rest_service;

import rest.api.rest_service.db.ConnectionManager;
import rest.api.rest_service.service.CompanyService;

import java.sql.Connection;
import java.sql.SQLException;

public class App {

    public static void checkMetaData() {
        try (Connection connection = ConnectionManager.get()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs().getMetaData();
            var value = metaData.getClientInfoProperties();

            while (value.next()) {
                System.out.println(value.getString(4));
            }

            System.out.println(catalogs.getColumnClassName(1));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        /*        checkMetaData();*/
     //   ExecuteSQLUtil.executeScriptSQL();
     //   CompanyDaoImpl companyRepository = CompanyDaoImpl.getInstance();



//        System.out.println(companyRepository.findAll());
//        CompanyEntity companyEntity = new CompanyEntity();
//        companyEntity.setName("Google");
//        companyEntity.setAddress("Moscow");
        //    companyRepository.save(companyEntity);
        //companyRepository.deleteById(3L);
       // System.out.println(companyRepository.findById(4L));
      /*  companyEntity = companyRepository.findById(7L).get();
        companyEntity.setName("Mail");
        companyEntity.setAddress("Podolsk");
        companyRepository.update(companyEntity);*/
        //    System.out.println(companyRepository.findAll());

    }
}
