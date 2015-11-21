package com.cgi.ecm.reports.jaskula;

import org.springframework.context.annotation.*;
import org.springframework.integration.jdbc.store.channel.ChannelMessageStoreQueryProvider;
import org.springframework.integration.jdbc.store.channel.HsqlChannelMessageStoreQueryProvider;

/**
 * Created by kortatu on 6/08/15.
 */
@Configuration
@PropertySource("classpath:/test.ecm_reports.properties")
@ImportResource("classpath:/test_database.xml")
public class TestConfig {


    @Bean
    public ChannelMessageStoreQueryProvider queryProvider() {
        return new HsqlChannelMessageStoreQueryProvider();
    }
}
