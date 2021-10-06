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
import static by.itacademy.javaenterprise.web.entity.MACAdress.getMacClient;

@WebFilter(urlPatterns = "/restfirst")
public class MACFilter extends HttpFilter {
    private static final String NOT_ALLOWED_MAC = "Данному МАС адресу не разрешено подключаться!\n";
    List<String> whiteListMAC = new ArrayList<>();
    private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FirstRestServlet.class);

    @Override
    public void init(FilterConfig config) throws ServletException {
        String whitelist = getServletContext().getInitParameter("whitelist");
        if (whitelist != null) {
            String[] split = whitelist.split(",");
            whiteListMAC.addAll(Arrays.asList(split));
        }
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());

        String clientMAC = getMacClient(getIP(req));
        if (whiteListMAC.contains(clientMAC)) {
            chain.doFilter(req, res);
            res.setStatus(200);
        } else {
            PrintWriter out = res.getWriter();
            res.setContentType("text/html");
            out.println(NOT_ALLOWED_MAC);
            res.setStatus(400);
        }

    }
}
