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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * A simple logger wrapper. 
 * @author Germano Fronza
 *
 */
public class MainLogger {
	
	private Logger logger;
	
	public MainLogger(String className) {
        this.logger = Logger.getLogger(className);
        PropertyConfigurator.configure(Main.getParamLog4j());
    }
	
	public void info(String logMessage) {
		this.logger.info(logMessage);
	}
	
	public void warn(String logMessage) {
		this.logger.warn(logMessage);
	}
	
	public void warn(String logMessage, Throwable e) {
		this.logger.warn(logMessage, e);
	}
	
	public void error(String logMessage) {
		this.logger.error(logMessage);
	}
	
	public void error(String logMessage, Throwable e) {
		this.logger.error(logMessage, e);
	}
	
	public void debug(String logMessage) {
		this.logger.debug(logMessage);
	}
	
	public void startUpLogger() {
		this.logger.info("===========================");
		this.logger.info("=                         =");
		this.logger.info("=     PresenceTracker     =");
		this.logger.info("=                         =");
		this.logger.info("===========================");
	}

}
