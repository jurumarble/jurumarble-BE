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
                .addPathPatterns("/api/votes/myActivities")
                .addPathPatterns("/api/votes/{voteId}")
                .addPathPatterns("/api/votes/{voteId}/normal")
                .addPathPatterns("/api/votes/{voteId}/drink")
                .addPathPatterns("/api/votes/{voteId}/vote")
                .addPathPatterns("/api/votes/{voteId}/voted")
                .addPathPatterns("/api/votes/{voteId}/bookmark")
                .addPathPatterns("/api/drinks/enjoys")
                .addPathPatterns("/api/drinks/{drinkId}/enjoy")
                .addPathPatterns("/api/{commentType}/{typeId}/comments/create")
                .addPathPatterns("/api/{commentType}/{typeId}/comments/{commentId}")
                .addPathPatterns("/api/{commentType}/{typeId}/comments/{commentId}/likers")
                .addPathPatterns("/api/{commentType}/{typeId}/comments/{commentId}/haters")
                .addPathPatterns("/api/{commentType}/{typeId}/comments/{commentId}/restaurant")
                .addPathPatterns("/api/{commentType}/{typeId}/comments/{commentId}/restaurant/{contentId}")
                .addPathPatterns("/api/notifications")
                .addPathPatterns("/api/notifications/subscribe")
                .addPathPatterns("/api/reports/votes")
                .addPathPatterns("/api/reports/comments");

    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .allowedOrigins("http://localhost:3000","https://jurumarble-git-develop-chooz.vercel.app/","https://jurumarble.site","https://jurumarble.vercel.app/")
                .maxAge(3600);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
    }
}