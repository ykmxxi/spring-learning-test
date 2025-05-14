package cholog.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cholog.ui.CheckLoginInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("hello");
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        CheckLoginInterceptor loginInterceptor = new CheckLoginInterceptor();

        registry.addInterceptor(loginInterceptor).addPathPatterns("/admin/**");
    }

    // TODO: AuthenticationPrincipalArgumentResolver 등록하기
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    }
}
