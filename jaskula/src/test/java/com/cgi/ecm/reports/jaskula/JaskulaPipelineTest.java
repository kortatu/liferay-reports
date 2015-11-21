package com.cgi.ecm.reports.jaskula;

import com.cgi.ecm.reports.jaskula.test.PageViewerTestClient;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JaskulaPipeline.class, TestActivators.class})
public class JaskulaPipelineTest {

    @Resource
    private JaskulaPipeline jaskulaPipeline;
	@Resource
    private DirectChannel multipostInputChannel;
    @Resource
    private MessageGroupStore messageStore;
    @Resource
    private PageViewerTestClient jsonPageViewerClient;

	@Test
	public void testExpireGroup() {
        String messagePayload = "WTF!";
        boolean sent = multipostInputChannel.send(new GenericMessage<Object>(messagePayload));
        assertThat(sent, is(true));
        List<Object> releasedBatches = jsonPageViewerClient.getMessagePayloads();
        //Still not sent to the service activator as it doesn't match the release strategy
        assertThat(releasedBatches, is(empty()));
        //But we can release all the group
        messageStore.expireMessageGroups(0);
        releasedBatches = jsonPageViewerClient.getMessagePayloads();
        //This time the message has been released to the service activator
        assertThat(releasedBatches, hasSize(1));
        Object actual = releasedBatches.get(0);
        assertThat(actual, instanceOf(List.class));
        List<String> batch = (List)actual;
        assertThat(batch.get(0), is(messagePayload));
    }

    @Test
    public void testReduceBatchSize() {
        jaskulaPipeline.setBatchSize(20);
        String messagePayload = "WTF!";
        boolean sent = multipostInputChannel.send(new GenericMessage<Object>(messagePayload));
        assertThat(sent, is(true));
        List<Object> releasedBatches = jsonPageViewerClient.getMessagePayloads();
        //Still not sent to the service activator as it doesn't match the release strategy
        assertThat(releasedBatches, is(empty()));
        MessageGroup messageGroup = messageStore.getMessageGroup(1);
        assertThat(messageGroup.size(), is(1));
        //But we can change the batch size and send a second message
        jaskulaPipeline.setBatchSize(1);
        String messagePayload2 = "WTF2!";
        sent = multipostInputChannel.send(new GenericMessage<Object>(messagePayload2));
        assertThat(sent, is(true));
        releasedBatches = jsonPageViewerClient.getMessagePayloads();
        //This time the message has been released to the service activator
        assertThat(releasedBatches, hasSize(1));
        Object batchObject = releasedBatches.get(0);
        assertThat(batchObject, instanceOf(List.class));
        List<String> batch = (List)batchObject;
        assertThat(batch, hasSize(2));
        assertThat(batch, containsInAnyOrder(messagePayload, messagePayload2));
    }

    @After
    public void cleanUp() {
        messageStore.expireMessageGroups(0);
        jsonPageViewerClient.clean();
    }

}
