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
package com.github.gfronza.presencetracker.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.github.gfronza.presencetracker.Main;
import com.github.gfronza.presencetracker.MainLogger;

/**
 * Checks status of the app.
 * @author Germano Fronza
 *
 */
@SuppressWarnings("serial")
public class Probe extends HttpServlet {
	
	static final MainLogger logger = new MainLogger(Probe.class.getName());
	private final JedisPool jedisPool;
		
	public Probe() {
	    this.jedisPool = Main.getJedisPool();
    }
	
	private boolean checkRedis() {
	    try (Jedis jedis = jedisPool.getResource()) {
            return jedis.ping().equals("PONG");
        }
	    catch (Exception e) {
	        logger.error("Exception checking redis connection", e);
            return false;
        }
	}

	private String checkAllServices() {
		boolean redisStatus = checkRedis();
		boolean overallStatus = redisStatus;

		return String.format("status:%s,redis:%s",
				String.valueOf(overallStatus), String.valueOf(redisStatus));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        
        response.getWriter().println(checkAllServices());
    }
	
}
