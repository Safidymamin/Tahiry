package io.tahiry.iombonana.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.tahiry.iombonana.domain")
@EnableJpaRepositories("io.tahiry.iombonana.repos")
@EnableTransactionManagement
public class DomainConfig {
}
