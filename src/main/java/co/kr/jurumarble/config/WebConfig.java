package co.kr.jurumarble.config;

import co.kr.jurumarble.comment.controller.converter.StringToEnumConverter;
import co.kr.jurumarble.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
    private final TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .order(1)
                .addPathPatterns("/api/users/additional-info")
                .addPathPatterns("/api/users")
                .addPathPatterns("/api/votes/normal")
                .addPathPatterns("/api/votes/drink")
                .addPathPatterns("/api/votes/participated")
                .addPathPatterns("/api/votes/my-vote")
                .addPathPatterns("/api/votes/bookmarked")
                .addPathPatterns("/api/votes/{voteId}/")
                .addPathPatterns("/api/votes/{voteId}/normal")
                .addPathPatterns("/api/votes/{voteId}/drink")
                .addPathPatterns("/api/votes/{voteId}/vote")
                .addPathPatterns("/api/votes/{voteId}/voted")
                .addPathPatterns("/api/votes/{voteId}/bookmark")
                .addPathPatterns("/api/drinks/enjoys")
                .addPathPatterns("/api/{conmmentType}/{typeId}/comments/create")
                .addPathPatterns("/api/{conmmentType}/{typeId}/comments/{commentId}")
                .addPathPatterns("/api/{conmmentType}/{typeId}/comments/{commentId}/likers")
                .addPathPatterns("/api/{conmmentType}/{typeId}/comments/{commentId}/haters")
                .addPathPatterns("/api/{conmmentType}/{typeId}/comments/{commentId}/restaurant")
                .addPathPatterns("/api/{conmmentType}/{typeId}/comments/{commentId}/restaurant/{contentId}");
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
    }
}