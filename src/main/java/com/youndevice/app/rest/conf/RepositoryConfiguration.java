package com.youndevice.app.rest.conf;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
//@EntityScan(basePackageClasses = {DomainMarker.class})
//@EnableJpaRepositories(basePackages = {"com.youndevice.repository"})
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
public class RepositoryConfiguration {


}
