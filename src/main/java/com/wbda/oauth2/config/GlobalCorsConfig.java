package com.wbda.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /* 全局跨域支持 */
    @Bean
    public FilterRegistrationBean corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(urlBasedCorsConfigurationSource));
        //bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        //要设置的比springsecurityfilter级别高(值越小级别越高)
        //否则跨域的options请求会被springsecurityfilter直接deny
        //从源码看springsecurityfilter的级别是-100
        //这里把cors级别设置为比springsecurityfilter高100
        bean.setOrder(securityProperties.getFilterOrder() - 100);
        return bean;
        //return new CorsFilter(urlBasedCorsConfigurationSource);
    }

 }





