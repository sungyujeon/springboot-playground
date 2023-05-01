package playground.springboot;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class CustomSpringbootApplication {

    public static void main(String[] args) {
        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();

        WebServer webServer = serverFactory.getWebServer(new CustomServletContextInitializer());
        webServer.start();
    }

    private static class CustomServletContextInitializer implements ServletContextInitializer {
        @Override
        public void onStartup(ServletContext servletContext) {
            servletContext.addServlet("hello", new CustomHttpServlet())
                          .addMapping("/hello");
        }
    }

    private static class CustomHttpServlet extends HttpServlet {
        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setStatus(HttpStatus.OK.value());
            resp.setHeader("Content-Type", "text/plain");
            resp.getWriter().println("Hello Servlet");
        }
    }
}
