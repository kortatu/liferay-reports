package com.cgi.ecm.reports.jaskula;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.integration.jdbc.store.channel.ChannelMessageStoreQueryProvider;
import org.springframework.integration.jdbc.store.channel.MySqlChannelMessageStoreQueryProvider;
import org.springframework.integration.store.ChannelMessageStore;
import org.springframework.integration.store.MessageGroupQueue;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by kortatu on 4/08/15.
 */

@EnableIntegration
@SpringBootApplication
@Import(JaskulaPipeline.class)
public class JaskulaApplication implements CommandLineRunner {

    private static final Log log = LogFactory.getLog(JaskulaApplication.class);

    @Resource
    private MessageChannel externalInputChannel;

    @Resource
    private DataSource dataSource;

    @Resource
    private ChannelMessageStore externalChannelStore;

    //tag::bridge[]
    @Bean
    @BridgeTo(value = "multipostInputChannel" )
    public MessageChannel externalInputChannel(ChannelMessageStore externalChannelStore) {
        return new QueueChannel(new MessageGroupQueue(externalChannelStore, "jaskula"));
    }
    //end::bridge[]
    //tag::messageStore[]
    @Bean
    public ChannelMessageStore externalChannelStore(DataSource dataSource, ChannelMessageStoreQueryProvider queryProvider) {
        JdbcChannelMessageStore jdbcChannelMessageStore = new JdbcChannelMessageStore(dataSource);
        jdbcChannelMessageStore.setChannelMessageStoreQueryProvider(queryProvider);
        jdbcChannelMessageStore.setRegion("JASKULA");
        return jdbcChannelMessageStore;
    }

    @Lazy @Bean
    public DataSource dataSource(@Value("${ecm.reports.jaskula.externalStore.url}") String url,
                                 @Value("${ecm.reports.jaskula.externalStore.user}") String user,
                                 @Value("${ecm.reports.jaskula.externalStore.password}") String password) {
        return new DriverManagerDataSource(url, user, password);
    }

    @Bean
    public ChannelMessageStoreQueryProvider queryProvider() {
        return new MySqlChannelMessageStoreQueryProvider();
    }

    //end::messageStore[]

    public static void main(String[] args) {
        SpringApplication.run(JaskulaApplication.class, args);
    }

    //tag::init[]
    @Override
    public void run(String... args) throws Exception {
        try {
            externalChannelStore.messageGroupSize(1);
        } catch (Exception e) {
            log.info("We need to init the database");
            try {
                initializeDatabase(dataSource);
                externalChannelStore.messageGroupSize(1);
            } catch (Exception e1) {
                log.error("Error initializing the database or persisting error after initializing",e);
            }
        }
    }

    public void initializeDatabase(DataSource dataSource) {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new DefaultResourceLoader(JaskulaApplication.class.getClassLoader()).getResource("classpath:org/springframework/integration/jdbc/store/channel/schema-mysql.sql"));
        DatabasePopulatorUtils.execute(databasePopulator,dataSource);
    }
    //end::init[]
}
