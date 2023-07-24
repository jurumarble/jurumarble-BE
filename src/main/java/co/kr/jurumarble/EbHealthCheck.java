package co.kr.jurumarble;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class EbHealthCheck {

    @GetMapping("/")
    public RedirectView redirectToSwagger() {
        String swaggerUrl = "http://jurumarble-env.eba-tv8jafay.ap-northeast-2.elasticbeanstalk.com/swagger-ui/index.html";
        return new RedirectView(swaggerUrl);
    }

    @GetMapping("/api/swagger-check3")
    public String swagger() {
        return "<h1>되는지 확인용</h1>";
    }

}