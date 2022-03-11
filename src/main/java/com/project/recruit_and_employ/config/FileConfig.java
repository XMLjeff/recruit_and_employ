package com.project.recruit_and_employ.config;

import com.project.recruit_and_employ.constant.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/11 20:58
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/recruitAndEmploy/**").addResourceLocations("file:/" + Constant.BASE_DIR + "/");
    }

}
