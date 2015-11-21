package com.cgi.ecm.reports.jaskula;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.support.PeriodicTrigger;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by kortatu on 28/07/15.
 */
@Configuration
@EnableIntegration
@ImportResource("classpath:/jaskula-min.xml")
@PropertySource("classpath:/default.ecm_reports.properties")
public class JaskulaPipeline {

    private int batchSize = 20;

    void setBatchSize(int newBatchSize) {
        this.batchSize = newBatchSize;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer pspc() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(1, TimeUnit.SECONDS));
        return pollerMetadata;
    }
//tag::beans[]
    @Bean
    public DirectChannel multipostInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel singlepostInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageGroupStore messageStore() {
        return new InfiniteGroupMessageStore();
    }

    @Bean
    public Object batchReleaseStrategy() {
        return new Object() {
            @Resource
            JaskulaPipeline jaskulaPipeline;
            @ReleaseStrategy
            public boolean batchReleaseStrategy(List<Message<?>> messages) {
                int currentBatchSize = jaskulaPipeline.batchSize;
                return messages.size() >= currentBatchSize;
            }
        };
    }

    @Bean
    public DirectChannel joinedChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel logReplyChannel() {
        return new DirectChannel();
    }

//end::beans[]
}
