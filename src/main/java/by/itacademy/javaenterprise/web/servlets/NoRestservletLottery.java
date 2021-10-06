package by.itacademy.javaenterprise.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Random;

@WebServlet("/norest/*")
public class NoRestservletLottery extends HttpServlet {
    private int random;
    private final static int LUCKYNUMBER = 20;
    private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NoRestservletLottery.class);


    @Override
    public void init() throws ServletException {
        random = new Random().nextInt(100 + 1);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println(LocalDateTime.now());
        writer.println("Luckynumber" + LUCKYNUMBER);
        writer.println("Randomnumber" + random);
        if (random == LUCKYNUMBER) {
            writer.println("YOU ARE LUCKY ONE");
        } else {
            writer.println("LOOSER");
        }
    }
}
