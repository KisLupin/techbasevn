//package com.techbasevn.backend.configure.db;
//
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.PersistenceContext;
//import javax.sql.DataSource;
//import java.util.Objects;
//
//@Configuration
//@PropertySource({"classpath:application.properties"})
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "dbEntityManagerFactory",
//        transactionManagerRef = "dbTransactionManager",
//        basePackages = {"com.techbasevn.backend.repositories"}
//)
//@AllArgsConstructor
//public class DbConfigure {
//    private final Environment env;
//
//    @Bean(name = "dbDataSource")
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.app.datasource.driverClassName")));
//        dataSource.setUrl(env.getProperty("spring.app.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.app.datasource.username"));
//        dataSource.setPassword(env.getProperty("spring.app.datasource.password"));
//        return dataSource;
//    }
//
//    @PersistenceContext(name = "db")
//    @Bean(name = "dbEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("dbDataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.techbasevn.backend.model.entities")
//                .persistenceUnit("db")
//                .build();
//    }
//
//    @Bean(name = "dbTransactionManager")
//    public PlatformTransactionManager transactionManager(
//            @Qualifier("dbEntityManagerFactory")
//                    EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}
