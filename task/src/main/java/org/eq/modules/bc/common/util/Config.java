package org.eq.modules.bc.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Config
{

    public Config()
    {
    }

    public static String getProperty(String key)
    {
        return mConfig.getProperty(key);
    }
    
    public static Integer getIntegerProperty(String key)
    {
    	String value = getProperty(key);
    	
        return Integer.parseInt(value);
    }

    public static boolean getBooleanProperty(String name)
    {
        String value = getProperty(name);
        if(value == null)
            return false;
        else
            return (new Boolean(value)).booleanValue();
    }

    
    public static void setProperty(String key,String value) throws IOException {
    	mConfig.setProperty(key, value);
    	OutputStream fos = new FileOutputStream(default_config);

    	//Writer w=new FileWriter(new File(default_config));
    	mConfig.store(fos,  key );

    	//mConfig.store(w, key);
    	fos.close();
	}

    public static String default_config;
    private static Properties mConfig;
    private static Log mLogger;

    static 
    {
        default_config = "/config.properties";
        mLogger = LogFactory.getFactory().getInstance(Config.class);
        mConfig = new Properties();
        try
        {
            java.io.InputStream is =  Config.class.getResourceAsStream(default_config);
            mConfig.load(is);
            mLogger.info("successfully loaded default properties.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}