package by.itacademy.javaenterprise.web.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/restfirst")
public class FilterContent extends HttpFilter {
    private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FilterContent.class);
    private static final String ALLOWED_TYPE = "text/plain";
    private static final String REQUESTED_HEADER = "content-type";

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String contentType = req.getHeader(REQUESTED_HEADER);
        if (ALLOWED_TYPE.equals(contentType)) {
            chain.doFilter(req, res);
        } else {
            res.getWriter().write("Text plain only!");
            res.setStatus(404);
        }
    }
}
