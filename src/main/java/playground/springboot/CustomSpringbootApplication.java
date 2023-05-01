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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import playground.springboot.controller.HelloController;

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
            servletContext.addServlet("frontcontroller", new CustomHttpServlet())
                          .addMapping("/*");
        }
    }

    private static class CustomHttpServlet extends HttpServlet {

        private final HelloController helloController = new HelloController();

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            // 인증, 보안, 다국어, 공통 기능 등
            if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {  // binding
                String name = req.getParameter("name");

                String ret = helloController.hello(name);  // HelloController에 처리를 위임(mapping)

                resp.setStatus(HttpStatus.OK.value());
                resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                resp.getWriter().println(ret);
            } else if (req.getRequestURI().equals("/user")){
                // user request...
            } else {
                resp.setStatus(HttpStatus.NOT_FOUND.value());
            }
        }
    }
}
