package com.isslab.se_form_backend.config;

import com.isslab.se_form_backend.audit.AuditInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuditInterceptor auditInterceptor;

    public WebMvcConfig(AuditInterceptor auditInterceptor){
        this.auditInterceptor = auditInterceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 靜態資源配置
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600)
                .resourceChain(true);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/login").setViewName("forward:/index.html");
        registry.addViewController("/dashboard").setViewName("forward:/index.html");
        registry.addViewController("/users").setViewName("forward:/index.html");
        registry.addViewController("/profile").setViewName("forward:/index.html");
        registry.addViewController("/forms").setViewName("forward:/index.html");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditInterceptor)
                .addPathPatterns("/**") // 只監控 API 路徑
                .excludePathPatterns(
                        "/api/health",     // 排除健康檢查
                        "/api/metrics",    // 排除監控端點
                        "/api/actuator/**" // 排除 actuator 端點
                );
    }
}
