package playground.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    /*
    요청을 보낼 때
    - web browser
    - curl
    - HTTPie
    - Intellij IDEA http request
    - Postman
    - JUnit
    - 다른 api 테스트 도구들
     */
    @GetMapping("/healthCheck")
    public String hello() {
        return "ok";
    }

}
