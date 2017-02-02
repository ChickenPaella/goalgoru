package com.gorugoru.api.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories("com.gorugoru.api.domain.repository")
public class JpaRepositoryConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource defaultDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	/**
	 * sessionFactory 미사용시, 없어도되나, 있어야 @Transactional 정상동작
	 * @param builder
	 * @return
	 */
	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(defaultDataSource()).packages("com.gorugoru.api.domain.model").build();
	}
	
	/*
	@Bean(name = "sessionFactory")
	public HibernateJpaSessionFactoryBean sessionFactory() {
		return new HibernateJpaSessionFactoryBean();
	}
	*/

}