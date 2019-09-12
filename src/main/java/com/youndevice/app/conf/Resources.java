package com.youndevice.app.conf;

import com.youndevice.app.bootstrap.ApplicationReadyEventHandlerService;
import com.youndevice.app.service.repo.RepoServiceMarker;
import com.youndevice.app.util.Holders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackageClasses = {ApplicationReadyEventHandlerService.class, RepoServiceMarker.class, Holders.class})
public class Resources {

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:message");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }
}
