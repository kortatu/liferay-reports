package com.cgi.ecm.reports.jaskula;

import java.util.Collection;

import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.SimpleMessageStore;
import org.springframework.messaging.Message;

public class InfiniteGroupMessageStore extends SimpleMessageStore {

	@Override
	public MessageGroup getMessageGroup(Object groupId) {
		return new InfiniteMessageGroup(super.getMessageGroup(groupId));
	}

	private class InfiniteMessageGroup implements MessageGroup {
		private MessageGroup messageGroup;

		public InfiniteMessageGroup(MessageGroup messageGroup2) {
			this.messageGroup = messageGroup2;
		}

		public boolean canAdd(Message<?> message) {
			return messageGroup.canAdd(message);
		}

		public Collection<Message<?>> getMessages() {
			return messageGroup.getMessages();
		}

		public Object getGroupId() {
			return messageGroup.getGroupId();
		}

		public int getLastReleasedMessageSequenceNumber() {
			return messageGroup.getLastReleasedMessageSequenceNumber();
		}

		public boolean isComplete() {
			return false;
		}

		public void complete() {
		}

		public int getSequenceSize() {
			return messageGroup.getSequenceSize();
		}

		public int size() {
			return messageGroup.size();
		}

		public Message<?> getOne() {
			return messageGroup.getOne();
		}

		public long getTimestamp() {
			return messageGroup.getTimestamp();
		}

		public long getLastModified() {
			return messageGroup.getLastModified();
		}
	}

}
