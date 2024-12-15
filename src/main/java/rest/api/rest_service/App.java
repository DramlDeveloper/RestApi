package rest.api.rest_service;

import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.dao.impl.PostDaoImpl;
import rest.api.rest_service.dao.impl.StaffDaoImpl;
import rest.api.rest_service.entity.CompanyEntity;
import rest.api.rest_service.entity.PostEntity;
import rest.api.rest_service.entity.StaffEntity;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.util.ExecuteSQLUtil;

public class App {
    public static void main(String[] args) {
       // ExecuteSQLUtil.executeScriptSQL();
/*        PostDaoImpl postDao = PostDaoImpl.getInstance();
    var res =  postDao.save(new PostEntity("HOr"));
        System.out.println(res);*/
/*        CompanyDaoImpl companyDao = CompanyDaoImpl.getInstance();
        var res = companyDao.save(new CompanyEntity("Dog", "Word"));
        System.out.println(res);*/

        StaffDaoImpl staffDao = StaffDaoImpl.getInstance();
        CompanyDaoImpl companyDao = CompanyDaoImpl.getInstance();
        PostDaoImpl postDao = PostDaoImpl.getInstance();

        staffDao.save(new StaffEntity("Honor", "Kolin", postDao.findById(1L).orElse(null), companyDao.findById(1L).orElse(null)));
    }
}
