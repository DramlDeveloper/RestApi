package servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import rest.api.rest_service.service.impl.CompanyService;
import rest.api.rest_service.servlet.CompanyServlet;

import java.io.BufferedReader;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CompanyServletTest {
    @InjectMocks
    private static CompanyServlet companyServlet;
    private static CompanyService companyService;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private BufferedReader mockBufferedReader;

    @Test
    public void testDoGet() throws ServletException, IOException {

    }
}