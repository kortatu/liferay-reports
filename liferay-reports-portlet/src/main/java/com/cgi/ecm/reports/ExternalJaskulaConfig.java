package com.cgi.ecm.reports;

import com.cgi.ecm.reports.spring.NullDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.integration.jdbc.store.channel.ChannelMessageStoreQueryProvider;
import org.springframework.integration.jdbc.store.channel.MySqlChannelMessageStoreQueryProvider;
import org.springframework.integration.store.ChannelMessageStore;
import org.springframework.integration.store.MessageGroupQueue;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.messaging.MessageChannel;

import javax.sql.DataSource;

/**
 * Configuration for supporting Jaskula externally. It will create a channel with an external persistence.
 * Created by kortatu on 7/08/15.
 */
@Configuration
@Lazy
public class ExternalJaskulaConfig {

    @Lazy @Bean
    public MessageChannel externalOutputChannel(ChannelMessageStore externalChannelStore) {
        return new QueueChannel(new MessageGroupQueue(externalChannelStore, "jaskula"));
    }

    @Lazy @Bean
    public ChannelMessageStore externalChannelStore(DataSource externalJaskulaDataSource, ChannelMessageStoreQueryProvider queryProvider) {
        JdbcChannelMessageStore jdbcChannelMessageStore = new JdbcChannelMessageStore(externalJaskulaDataSource);
        jdbcChannelMessageStore.setChannelMessageStoreQueryProvider(queryProvider);
        jdbcChannelMessageStore.setRegion("JASKULA");
        return jdbcChannelMessageStore;
    }

    @Lazy @Bean
    public DataSource externalJaskulaDataSource(@Value("${ecm.reports.jaskula.externalStore.url}") String url,
                                 @Value("${ecm.reports.jaskula.externalStore.user}") String user,
                                 @Value("${ecm.reports.jaskula.externalStore.password}") String password) {
        if (url!=null && !url.isEmpty())
            return new DriverManagerDataSource(url, user, password);
        else
            return new NullDataSource("External Jaskula");
    }

    @Lazy @Bean
    public ChannelMessageStoreQueryProvider queryProvider() {
        return new MySqlChannelMessageStoreQueryProvider();
    }

}
