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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * A very simple and insecure CORS headers.
 * @author Germano Fronza
 *
 */
public class ApiFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response,
    		FilterChain filterChain) throws IOException, ServletException {

        if (response instanceof HttpServletResponse){
        	HttpServletResponse alteredResponse = ((HttpServletResponse)response);
        	addHeadersFor200Response(alteredResponse);
        }

        filterChain.doFilter(request, response);
    }

    private void addHeadersFor200Response(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Accept, Content-Type, Pragma, X-Requested-With");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "86400");
    }

    public void destroy() {
    	
    }

    public void init(FilterConfig filterConfig)throws ServletException{
    	
    }
}