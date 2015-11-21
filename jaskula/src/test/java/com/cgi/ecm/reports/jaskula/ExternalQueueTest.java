package com.cgi.ecm.reports.jaskula;

import com.cgi.ecm.reports.jaskula.test.PageViewerTestClient;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class,JaskulaPipeline.class, JaskulaApplication.class, ExternalQueueTest.class})
@EnableIntegration
@Ignore
public class ExternalQueueTest {

    @Bean
    public PageViewerTestClient jsonPageViewerClient() {
        return new PageViewerTestClient();
    }

    @Bean(name="joinedServiceActivator")
    @ServiceActivator(inputChannel = "joinedChannel",outputChannel = "logReplyChannel")
    public MessageHandler joinedServiceActivator(PageViewerTestClient pageViewerTestClient) {
        return new ServiceActivatingHandler(pageViewerTestClient);
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(1, TimeUnit.SECONDS));
        return pollerMetadata;
    }

    @Resource
    private QueueChannel externalInputChannel;


    @Test
    @Ignore
    public void testWriteToReactorQueue() {
        assertThat(externalInputChannel, is(notNullValue()));
        String payloadMessage = "Hello " + Math.random();
        System.out.println(externalInputChannel.getQueueSize());
        externalInputChannel.send(new GenericMessage<>(payloadMessage));
        try {
            Message<?> received = externalInputChannel.receive();
            assertThat(received, is(notNullValue()));
            Object payload = received.getPayload();
            assertThat(payload, instanceOf(String.class));
            String payloadString = (String) payload;
            assertThat(payloadString, is(equalTo(payloadMessage)));
            assertThat(externalInputChannel.getQueueSize(), is(0));
        } finally {
            System.out.println(externalInputChannel.getQueueSize());
            externalInputChannel.clear();
        }
    }

}
