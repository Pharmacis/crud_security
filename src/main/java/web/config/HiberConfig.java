package web.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "web")
public class HiberConfig {


   // нужен чтобы получить свойства из проперти.
   private  Environment env;

   @Autowired
   public HiberConfig(Environment environment){
      this.env= environment;
   }

   @Bean
   public  DataSource getDataSource() {
      //BasicDataSource dataSource = new BasicDataSource ();
      DriverManagerDataSource dataSource = new DriverManagerDataSource ();
      dataSource.setDriverClassName(env.getProperty("db.driver"));
      dataSource.setUrl(env.getProperty("db.url"));
      dataSource.setUsername(env.getProperty("db.username"));
      dataSource.setPassword(env.getProperty("db.password"));
      return dataSource;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean getSessionFactory() {
      LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean ();
      factoryBean.setDataSource(getDataSource());
      
      Properties props=new Properties();
      props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
      props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
     // props.put("hibernate.dialect",env.getProperty ("hibernate.dialect"));

      factoryBean.setPackagesToScan("web/model");

      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter ();
      factoryBean.setJpaVendorAdapter(vendorAdapter);
      factoryBean.setJpaProperties (props);
      return factoryBean;
   }

   @Bean
   public PlatformTransactionManager getTransactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager ();
      transactionManager.setEntityManagerFactory (getSessionFactory ().getObject ());
      return transactionManager;
   }
}
