/*
 * Copyright (c) 2015 Germano Fronza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.gfronza.presencetracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Application settings. It loads the settings from the properties file, that can be:<br/>
 *  - file indicated on command-line app argument
 *  - <app_root>/<env>.properties
 *  - <app_root>/config.properties
 * @author Germano Fronza
 *
 */
public class Settings {
	
	private static Properties properties = new Properties();
	
	private static final String REDIS_HOST = "redisHost";
	private static final String REDIS_PORT = "redisPort";
	private static final String PRESENCE_TIMEOUT_IN_SECONDS = "presenceTimeoutInSeconds";
	private static final String SESSIONS_KEY_PATTERN = "sessionsKeyPrefix";
	private static final String USERS_COUNT_EMITTER_TIMEOUT_IN_SECONDS = "usersCountEmitterTimeoutInSecond";
	private static final String INACTIVE_SESSIONS_CLEANUP_TIMEOUT_IN_SECONDS = "inactiveSessionsCleanupTimeoutInSecond";
	
	private static final MainLogger _logger = new MainLogger(Settings.class.getName());
	
	static {
		String[] paths = new String[] { Main.getParamConfig(), Main.getParamEnv() + ".properties", "config.properties" };
		
		boolean propertiesOK = false;
		
		for (String string : paths) {
			propertiesOK = tryLoadProperties(string);
			if (propertiesOK) {			
				_logger.info("Properties file loaded: " + string);
		
				break;
			}
		}
		if (propertiesOK == false) {
			_logger.error("No properties file found.");
		}
	}
	
	private static boolean tryLoadProperties(String path) {
		try {
			//load a properties file
			properties.load(new FileInputStream(path));			
			return true;
		} catch (FileNotFoundException e) {
			_logger.warn("The " + path + " file was not found!");
		} catch (IOException e) {
			_logger.warn("The " + path + " file was not accessible! Check if it have read permissions.");
		}
		
		return false;
	}
	
	private static String getProperty(String property) {
		return properties.getProperty(property);
	}
	
	private static String getProperty(String property, String defaultVal) {
		return properties.getProperty(property, defaultVal);
	}

	public static synchronized String getRedisHost() {
		return getProperty(REDIS_HOST);
	}

	public static synchronized int getRedisPort() {
		return Integer.parseInt(getProperty(REDIS_PORT, "6379"));
	}

    public static int getPresenceTimeoutInSeconds() {
        return Integer.parseInt(getProperty(PRESENCE_TIMEOUT_IN_SECONDS, "6379"));
    }

    public static String getSessionsKeyPattern() {
        return getProperty(SESSIONS_KEY_PATTERN, "sessions_*");
    }

    public static long getUsersCountEmitterTimeout() {
        return Long.parseLong(getProperty(USERS_COUNT_EMITTER_TIMEOUT_IN_SECONDS, "-1"));
    }

    public static long getInactiveSessionsCleanupTimeout() {
        return Long.parseLong(getProperty(INACTIVE_SESSIONS_CLEANUP_TIMEOUT_IN_SECONDS, "-1"));
    }

}