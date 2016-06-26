package com.itelg.devops.jpa.util;

import java.net.URL;

public class URLUtil
{
    public static String getHost(String url)
    {
        try
        {
            return new URL(url).getHost();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}