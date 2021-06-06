package xyz.zerxoi.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author zouxin <zouxin@kuaishou.com>
 * Created on 2021-06-06
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    // @Bean
    // public WebMvcConfigurer webMvcConfigurer() {
    //     return new WebMvcConfigurer() {
    //         @Override
    //         public void configurePathMatch(PathMatchConfigurer configurer) {
    //             UrlPathHelper urlPathHelper = new UrlPathHelper();
    //             urlPathHelper.setRemoveSemicolonContent(false);
    //             configurer.setUrlPathHelper(urlPathHelper);
    //         }
    //     };
    // }


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}
