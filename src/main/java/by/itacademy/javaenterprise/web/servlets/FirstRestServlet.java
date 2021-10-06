package by.itacademy.javaenterprise.web.servlets;

import by.itacademy.javaenterprise.web.entity.Woman;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

import static by.itacademy.javaenterprise.web.entity.Woman.getList;
import static by.itacademy.javaenterprise.utils.IPUtils.getIP;
import static by.itacademy.javaenterprise.web.entity.MACAdress.getMacClient;


@WebServlet("/restfirst")
public class FirstRestServlet extends HttpServlet {
    AtomicLong requestCount = new AtomicLong(0);
    private static final String TASK_ADD_ERROR = "Произошла ошибка, задача не добавлена !\n";
    private static final String TASK_UPDATE_ERROR = "Произошла ошибка, задача не обновлена\n";
    private static final String TASK_DELETE_ERROR = "Произошла ошибка, задача не удалена\n";
    private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FirstRestServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/HTML; charset=UTF-8");

        PrintWriter out = resp.getWriter();
        requestCount.incrementAndGet();


        String id = req.getParameter("id");
        String requestedWoman = null;
        if (id != null) {
            for (Woman wom : getList()) {
                requestedWoman = wom.getName(Integer.parseInt(id));
            }
            if (requestedWoman == null) {
                out.println("Искомой женщины не найдено !!!");
            } else {
                out.println("Предлагамая спутница найдена:" + requestedWoman);
            }
        }

        out.println("Client IP: " + getIP(req));
        out.println("Server IP: " + req.getLocalAddr());
        out.println("MAC client: " + getMacClient(getIP(req)));
        out.println(LocalDateTime.now());

        out.println("Content-type: " + req.getContentType());
        out.println("Request URL: " + req.getRequestURL());
        out.println("Count of request: " + requestCount);
        out.println("Number of thread: " + Thread.currentThread().getName());
        out.println("LocalHost: " + NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
        out.println("PathInfo: " + req.getPathInfo());
        out.println("Cookies: " + Arrays.toString(req.getCookies()));

        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        String name = req.getParameter("name");
        Integer id = Integer.parseInt(req.getParameter("id"));
        String age = req.getParameter("age");
        String gender = req.getParameter("gender");
        String girl = req.getParameter("girl");
        if (id != null && name != null & age != null) {
            getList().add(new Woman(id, name, age));
            writer.println("Woman added to Base");
            resp.setStatus(200);
        } else {
            writer.println("Введите id,name,age to add woman to database");
            resp.getWriter().write(TASK_ADD_ERROR);
            resp.setStatus(404);

        }

        writer.println("<p>id: " + id + "</p>");
        writer.println("<p>Name: " + name + "</p>");
        writer.println("<p>Age: " + age + "</p>");
        writer.println("<p>Gender: " + gender + "</p>");
        writer.println("<p>Country: " + girl + "</p>");
        resp.setStatus(200);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        String name = req.getParameter("name");
        Integer id = Integer.parseInt(req.getParameter("id"));
        String age = req.getParameter("age");
        Woman updatedWoman = null;
        if (id != null&& name != null & age != null) {
            for (Woman wom : getList()) {
                if (wom.getName(id) != null) {
                    wom.getName(id);
                }
            }
            if (updatedWoman == null) {
                writer.println("Искомой женщины не найдено !!!");
                resp.getWriter().write(TASK_UPDATE_ERROR);
                resp.setStatus(404);
            } else {
                updatedWoman.setName(name);
                updatedWoman.setAge(age);
                resp.setStatus(200);
            }
        }
        writer.println("<p>id: " + id + "</p>");
        writer.println("<p>Name: " + name + "</p>");
        writer.println("<p>Age: " + age + "</p>");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        resp.getWriter().write(TASK_DELETE_ERROR);
        resp.setStatus(404);
    }
}
