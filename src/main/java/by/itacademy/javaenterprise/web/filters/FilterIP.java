package by.itacademy.javaenterprise.web.filters;

import by.itacademy.javaenterprise.web.servlets.FirstRestServlet;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static by.itacademy.javaenterprise.utils.IPUtils.getIP;

@WebFilter(urlPatterns = "/filterip/*")
public class FilterIP extends HttpFilter {
    private static final String NOT_ALLOWED_IP = "Данному IP адресу не разрешено подключаться!\n";
    List<String> whiteListIP = new ArrayList<>();
    private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FirstRestServlet.class);

    @Override
    public void init(FilterConfig config) throws ServletException {
        String whitelistIPConfig = getServletContext().getInitParameter("whiteListIP");
        if (whitelistIPConfig != null) {
            String[] split = whitelistIPConfig.split(",");
            whiteListIP.addAll(Arrays.asList(split));
        }
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        String ipAddress=getIP(req);

        if (whiteListIP.contains(ipAddress)){
            chain.doFilter(req, res);
            res.setStatus(200);
        }
        else {
            PrintWriter out = res.getWriter();
            res.setContentType("text/html");
            out.println(NOT_ALLOWED_IP);
            res.setStatus(400);
        }

    }
}
