package config;

import dal.TeamDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    private DriverManagerDataSource ds;
    private String driverClass = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/football_statistics";
    private String user = "root";
    private String pw = "eIhuJk-dq2Jd";

    public ApplicationConfig() {
        ds = new DriverManagerDataSource();
    }

    @Bean
    public TeamDao teamDao() {
        return new TeamDao();
    }

    @Bean
    public DataSource getDataSource() {
        ds.setDriverClassName(driverClass);
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(pw);
        return ds;
    }

}
