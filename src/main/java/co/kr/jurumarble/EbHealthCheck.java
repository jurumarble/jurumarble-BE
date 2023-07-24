package co.kr.jurumarble;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class EbHealthCheck {

    @GetMapping("/")
    public ResponseEntity<?> redirectToSwagger() {
        String swaggerUrl = "http://jurumarble-env.eba-tv8jafay.ap-northeast-2.elasticbeanstalk.com/swagger-ui/index.html";
        // 200(OK) 상태 코드로 리다이렉트 응답을 반환
        return ResponseEntity.status(200).header("Location", swaggerUrl).build();
    }

    @GetMapping("/api/swagger-check3")
    public String swagger() {
        return "<h1>되는지 확인용</h1>";
    }

}