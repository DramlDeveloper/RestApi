package rest.api.rest_service.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import rest.api.rest_service.dao.impl.CompanyDaoImpl;
import rest.api.rest_service.service.dto.CompanyDtoIn;
import rest.api.rest_service.service.impl.CompanyService;

import java.io.IOException;

import static rest.api.rest_service.util.JsonHeaderUtil.jsonHeader;


@WebServlet("/company")
public class CompanyServlet extends HttpServlet {
    private final CompanyService service = new CompanyService(CompanyDaoImpl.getInstance());
    private final ObjectMapper mapper = new ObjectMapper();

    public void writeJson(Object value, HttpServletResponse resp) throws IOException {
        resp.getWriter().write(mapper.writeValueAsString(value));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsonHeader(resp);
        if (req.getParameter("id") != null) {
            writeJson(service.findById(Long.parseLong(req.getParameter("id"))), resp);
        } else {
            writeJson(service.findAll(), resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsonHeader(resp);
        if (req.getParameter("name") != null && req.getParameter("city") != null) {
            service.save(new CompanyDtoIn(req.getParameter("name"), req.getParameter("city")));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            service.deleteById(Long.parseLong(req.getParameter("id")));
            resp.getWriter().write("Deleted id N " + req.getParameter("id"));
        }
        if (req.getParameter("id") == null) {
            service.deleteAll();
            resp.getWriter().write("Deleted All");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsonHeader(resp);
        service.update(new CompanyDtoIn(Long.parseLong(
                req.getParameter("id")),
                req.getParameter("name"),
                req.getParameter("city")));
    }
}