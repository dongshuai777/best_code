package com.lccx.manager.frame.interceptor;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Resource
//    private LoginInterceptor loginInterceptor;

//    @Resource
//    private JwtFilter jwtFilter;


    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/WEB-INF/");
    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // addPathPatterns("/**") 表示拦截所有的请求，
//        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
//        registry.addInterceptor(jwtFilter).addPathPatterns("/**").excludePathPatterns("/Login","/Login/**", "/js/**","/css/**","/common/**","/image/**","/login/**");
////        super.addInterceptors(registry);
//    }
//
}
