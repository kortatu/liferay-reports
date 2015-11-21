package com.cgi.ecm.reports.rest_service.multipost;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.support.PeriodicTrigger;



@Configuration
@IntegrationComponentScan("com.cgi.ecm.reports.rest_service.multipost")
public class MultiPostConfiguration {

// tag::contains[]
    @Bean
    @Description("Channel for splitting multiPosts in individual pageViews")
    public MessageChannel multiPostChannel() {
        return new QueueChannel(100);
    }

    @Bean
    @Description("Channel for processing each individual post of a pageView")
    public MessageChannel singlePostChannel() {
        return new ExecutorChannel(singleMessageExecutor());
    }

    @Bean
    @Description("Channel for processing each individual post response of a pageView saved")
    public MessageChannel singlePostResponseChannel() {
        return new DirectChannel();
    }
// end::contains[]
// tag::executor[]
    @Bean
    public Executor singleMessageExecutor() {
	    return Executors.newCachedThreadPool();
    }
// end::executor[]
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(1, TimeUnit.SECONDS));
            return pollerMetadata;
    }


}

