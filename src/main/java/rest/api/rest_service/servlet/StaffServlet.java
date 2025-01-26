package rest.api.rest_service.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import rest.api.rest_service.service.dto.StaffDtoIn;
import rest.api.rest_service.service.impl.StaffService;

import java.io.IOException;

import static rest.api.rest_service.util.JsonHeaderUtil.jsonHeader;

@WebServlet("/staff")
public class StaffServlet extends HttpServlet {
    private final StaffService service = StaffService.getInstance();
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
        if(req.getParameter("id") != null){
            resp.getWriter().write("the id: "+ req.getParameter("id") +"does not exist");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            service.deleteById(Long.parseLong(req.getParameter("id")));
            resp.getWriter().write("Deleted id N " + req.getParameter("id"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsonHeader(resp);
        if (req.getParameter("first_name") != null && req.getParameter("last_name") != null) {
            service.save(new StaffDtoIn(
                    req.getParameter("first_name"),
                    req.getParameter("last_name"),
                    Long.parseLong(req.getParameter("post_id")),
                    Long.parseLong(req.getParameter("company_id"))
            ));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsonHeader(resp);
        service.update(new StaffDtoIn(
                Long.parseLong(req.getParameter("id")),
                req.getParameter("first_name"),
                req.getParameter("last_name"),
                Long.parseLong(req.getParameter("post_id")),
                    Long.parseLong(req.getParameter("company_id"))
        ));
    }
}
