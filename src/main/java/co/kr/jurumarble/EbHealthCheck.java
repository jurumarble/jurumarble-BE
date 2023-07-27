package co.kr.jurumarble;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@Controller
public class    EbHealthCheck {

    @Value("${swagger.url}")
    private String swaggerUrl;

    @GetMapping("/")
    public RedirectView redirectToSwagger() {
        return new RedirectView(swaggerUrl);
    }
    @GetMapping("/api/swagger-check3")
    public String swagger() {
        return "<h1>되는지 확인용</h1>";
    }

}