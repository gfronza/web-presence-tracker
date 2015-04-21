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

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.UnavailableException;

import com.github.gfronza.presencetracker.tasks.InactiveSessionsCleanup;
import com.github.gfronza.presencetracker.tasks.UsersCountEmitter;

/**
 * This class is responsible for initializing the scheduled tasks.
 * @author Germano Fronza
 *
 */
@SuppressWarnings("serial")
public class Initializer extends GenericServlet {

    private final MainLogger logger = new MainLogger(Initializer.class.getName());
    
    @Inject
    private UsersCountEmitter usersCountEmitter;
    
    @Inject
    private InactiveSessionsCleanup inactiveSessionsCleanup;
    
    @Override
    public void init() throws ServletException {
        logger.info("Starting periodic tasks...");

        usersCountEmitter.start();
        inactiveSessionsCleanup.start();
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        throw new UnavailableException("Initializer");
    }
}
