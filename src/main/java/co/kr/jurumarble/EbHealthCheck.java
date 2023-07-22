package co.kr.jurumarble;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbHealthCheck {

    @GetMapping("/")
    public String check() {
        return "<h1>스웨거 주소: http://jurumarble-env.eba-tv8jafay.ap-northeast-2.elasticbeanstalk.com/swagger-ui/index.html </h1>";
    }

    @GetMapping("/api/swagger-check3")
    public String swagger() {
        return "<h1>되는지 확인용</h1>";
    }

}