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
package com.github.gfronza.presencetracker.tasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is responsible for updating user presence.
 * 
 * It executes the updates on a FixedThreadPool executor (number of available processors).
 * @author Germano Fronza
 *
 */
public class PresenceUpdater {

    private final ExecutorService scheduler;
    
    public PresenceUpdater() {
        this.scheduler = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }
    
    public void update(String session, String userId) {
        this.scheduler.execute(new UpdatePresence(session, userId));
    }

    private class UpdatePresence implements Runnable {

        private String session;
        private String userId;

        public UpdatePresence(String session, String userId) {
            this.session = session;
            this.userId = userId;
        }
        
        public void run() {
            // ZADD session_XPTO <CURRENT_TIMESTAMP> <USER_ID>
        }
        
    }
}
