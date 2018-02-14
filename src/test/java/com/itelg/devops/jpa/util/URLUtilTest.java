package com.itelg.devops.jpa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class URLUtilTest
{
    @Test
    public void testGetHost()
    {
        assertEquals("jenkins", URLUtil.getHost("http://jenkins/git/notify=xyz"));
        assertEquals("jenkins.itelg.com", URLUtil.getHost("http://jenkins.itelg.com/git/notify=xyz"));
        assertEquals("jenkins.itelg.com", URLUtil.getHost("http://jenkins.itelg.com:8080/git/notify=xyz"));
    }

    @Test
    public void testGetHostWithInvalidUrl()
    {
        try
        {
            URLUtil.getHost("jenkins/git/notify=xyz");
            fail("Exception expected");
        }
        catch (Exception e)
        {
            assertEquals(RuntimeException.class, e.getClass());
            assertEquals("java.net.MalformedURLException: no protocol: jenkins/git/notify=xyz", e.getMessage());
        }
    }
}