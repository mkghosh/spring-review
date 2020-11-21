package com.mithun.config;

import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:dbconfig.properties")
public class DataSourceConfig implements WebMvcConfigurer {

	@Autowired
	private Environment env;

	// set up a logger
	private Logger logger = Logger.getLogger(getClass().getName());

	@Bean(name = "mysqlDataSource")
	@Primary
//	@ConfigurationProperties(prefix = "jdbc")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create()
				.driverClassName(env.getProperty("jdbc.driver"))
				.username(env.getProperty("jdbc.user"))
				.password(env.getProperty("jdbc.password"))
				.url(env.getProperty("jdbc.url"))
				.build();
	}

	@Bean(name = "postgreDataSource")
//	@ConfigurationProperties(prefix = "postgre")
	public DataSource postgreDataSource() {
		return DataSourceBuilder.create()
				.driverClassName(env.getProperty("postgre.driver"))
				.username(env.getProperty("postgre.user"))
				.password(env.getProperty("postgre.password"))
				.url(env.getProperty("postgre.url"))
				.build();
	}

	@Bean(name = "mysqlTransactionManager")
	@Autowired
	@Primary
	DataSourceTransactionManager mysqlTransactionManager(@Qualifier("mysqlDataSource") DataSource datasource) {
		DataSourceTransactionManager mysqlTransactionManager = new DataSourceTransactionManager(datasource);
		return mysqlTransactionManager;
	}

	@Bean(name = "postgreTransactionManager")
	@Autowired
	DataSourceTransactionManager postgreTransactionManager(@Qualifier("postgreDataSource") DataSource datasource) {
		DataSourceTransactionManager postgreTransactionManager = new DataSourceTransactionManager(datasource);
		return postgreTransactionManager;
	}

	// need a helper method
	// read environment property and convert to int
	private int getIntProperty(String propName) {
		String propVal = env.getProperty(propName);
		int intPropVal = Integer.parseInt(propVal);
		return intPropVal;
	}

	@Bean(name = "mysqlJdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("mysqlDataSource") DataSource mysqlDataSource) {
		return new JdbcTemplate(mysqlDataSource);
	}

	@Bean(name = "postgresJdbcTemplate")
	public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgreDataSource") DataSource postgreDataSource) {
		return new JdbcTemplate(postgreDataSource);
	}

	@Bean(name="entityManager")
	public LocalContainerEntityManagerFactoryBean entityManager(@Qualifier("mysqlDataSource") DataSource dataSource,
			Environment env) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.mithun.db.mysql.*");

		Properties jpaProperties = new Properties();

		// Configures the used database dialect. This allows Hibernate to create SQL
		// that is optimized for the used database.
		jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.mysql.dialect"));

		// If the value of this property is true, Hibernate writes all SQL
		// statements to the console.
		jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));

		// If the value of this property is true, Hibernate will format the SQL
		// that is written to the console.
		jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

}
