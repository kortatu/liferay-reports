package com.cgi.ecm.reports;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.liferay.portlet.PortletPreferencesFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/reportsAgentContext.xml")
public class PortalPropertiesTest {
	
	@Resource
	PortalProperties portalProperties;

	@Test
	@Ignore
	public void test() {
		portalProperties.setPortletPreferencesFactory(mock(PortletPreferencesFactory.class));
		assertFalse(portalProperties.isReportsEnabled());
	}

}
