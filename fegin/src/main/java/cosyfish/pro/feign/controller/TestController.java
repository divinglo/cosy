package cosyfish.pro.feign.controller;

import cosyfish.pro.feign.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/feign/test")
    public String queryUser() {
        return testService.queryUser();
    }

}
