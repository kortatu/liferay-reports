package com.cgi.ecm.reports;

import com.cgi.ecm.reports.client.PageView;
import com.cgi.ecm.reports.client.PageViewerClientI;
import com.cgi.ecm.reports.client.PortletsSetupTransformer;
import com.cgi.ecm.reports.jaskula.JaskulaPipeline;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portlet.PortletPreferencesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestJaskulaIntegration.class)
@ImportResource("/reportsAgentContext.xml")
public class TestJaskulaIntegration {

	@Resource
	private PageViewerClientI pageViewerClient;
	
	@Resource
	private PortletsSetupTransformer portletsSetupTransformer;

	@Resource
	private JaskulaPipeline jaskulaPipeline;

	@Resource
	private MessageGroupStore messageStore;

	@Resource
	private PortalPropertiesStub portalPropertiesStub;

	@Bean
	PortalPropertiesStub portalProperties() {
		return new PortalPropertiesStub();
	}

	private static class PortalPropertiesStub implements PortalPropertiesI {
		private boolean enabled = true;
		@Override
		public boolean isReportsEnabled() {
			return enabled;
		}

		@Override
		public void setSendingEnabled(boolean newEnabled) {
			enabled = newEnabled;
		}

		@Override
		public boolean isJaskulaLocal() {
			return true;
		}

		@Override
		public void setJaskulaLocal(boolean local) {

		}
	}
	
	@Test
	public void test() {
        this.portletsSetupTransformer.setLayoutLocalService(mock(LayoutLocalService.class));
        this.portletsSetupTransformer.setPortletPreferencesFactory(mock(PortletPreferencesFactory.class));
		pageViewerClient.savePageView(testPageView());
		pageViewerClient.savePageView(testPageView());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertThat(messageStore.getMessageGroup(1).size(), is(2));
		portalPropertiesStub.enabled = false;
		pageViewerClient.savePageView(testPageView());
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        assertThat(messageStore.getMessageGroup(1).size(), is(2));
	}

	private PageView testPageView() {
		return new PageView("", "", 0l, 0, null, null, 0, null, new ArrayList<String>(), null, 0, null, null);
	}

}
