package cosyfish.pro.feign.service;




import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;




@FeignClient(name = "user-service",contextId = "user")
public interface TestService {

    @GetMapping(value = "/test")
    String queryUser();
}
