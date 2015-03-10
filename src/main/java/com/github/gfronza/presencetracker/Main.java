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

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Application main class (standalone). It creates a Jetty HTTP Server.
 * @author Germano Fronza.
 *
 */
public class Main {
	
	private static String PARAM_ENV;
	private static String PARAM_LOG4J;
	private static String PARAM_CONFIG;

	private static JedisPool jedisPool;
	
	static {
	    jedisPool = new JedisPool(new JedisPoolConfig(), Settings.getRedisHost(), Settings.getRedisPort());
	}
	
	private static String getParamValue(String arg) {
		if ( arg.indexOf("=") > 0 ) {
			String[] param = arg.split("=");
			if ( param[1].isEmpty() == false && param[1] != null ) {
				return param[1];
			}
		}
		return "";
		
	}
	
	private static void printHelp() {
		System.out.println("Usage: java -jar presence-tracker.jar [--webxml=path/to/web.xml] [--config=path/to/config/properties] [--log4j=path/to/log4j/properties] [--env=environment_name ]");
		System.exit(0);
	}
	
	private static void startWebContext(String webxml) throws Exception {
//		Server server = new Server(); // TODO: there's a constructor that accepts a ThreadPool
//		
//		SelectChannelConnector http = new SelectChannelConnector();
//		
//		http.setHost(Settings.getJettyAddr());
//		http.setPort(Settings.getJettyPort());
//		
//		server.setConnectors(new Connector[]{ http });
//
//        WebAppContext webContext = new WebAppContext();
//        webContext.setDescriptor(webxml);
//        webContext.setResourceBase("public/");
//        webContext.setContextPath("/");
//        webContext.setServer(server);
//        webContext.setParentLoaderPriority(true);
//        server.setHandler(webContext);
//	
//        server.start();
//		server.join();
	}
	
	public static void main(String[] args) throws Exception {
		
		String env = "devel";
		String log4j = "log4j.properties";
		String config = "config.properties";
		String webxml = "web.xml";
		
		if ( args.length == 0 ) {
			printHelp();
		}
		
		for (int i = 0; i < args.length; i++) {
			if ( args[i].startsWith("--config") ) {
				config = getParamValue(args[i]);
				PARAM_CONFIG = config;
			} else if ( args[i].startsWith("--log4j") ) {
				log4j = getParamValue(args[i]);
				PARAM_LOG4J = log4j;
			} else if ( args[i].startsWith("--env") ) {
				env = getParamValue(args[i]);
				PARAM_ENV = env;
			} else if ( args[i].startsWith("--webxml") ) {
				webxml = getParamValue(args[i]);
				PARAM_ENV = env;
			} else if ( args[i].startsWith("--help") ) {
				printHelp();
			}
		}
		
		try {
			startWebContext(webxml);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static String getParamEnv() {
		return PARAM_ENV;
	}

	public static String getParamLog4j() {
		return PARAM_LOG4J;
	}

	public static String getParamConfig() {
		return PARAM_CONFIG;
	}
	
	public static JedisPool getJedisPool() {
        return jedisPool;
    }

}
