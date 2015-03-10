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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for performing a cleanup on the sorted sets (old presences).
 * If the session ends up empty, it is removed.
 * @author Germano Fronza
 *
 */
public class InactiveSessionsCleanup implements Runnable {

    private static final long DEFAULT_CLEANUP_INTERVAL = 3 * 60;
    
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final long cleanupInterval;
    
    /**
     * Create a new emitter with a custom interval (in seconds).
     * @param emitterInterval
     */
    public InactiveSessionsCleanup(long cleanupInternal) {
        this.cleanupInterval = cleanupInternal;
    }
    
    /**
     * Create a new emitter with the defaul interval (@see DEFAULT_EMITTER_INTERVAL).
     */
    public InactiveSessionsCleanup() {
        this.cleanupInterval = DEFAULT_CLEANUP_INTERVAL;
    }

    /**
     * Start the emitter job.
     */
    public void start() {
        scheduler.execute(this);
    }

    /**
     * Stop te emitter job.
     */
    public void stop() {
        scheduler.shutdownNow();
    }

    /**
     * Execute the users count job itself. 
     */
    public void run() {
        // ZREMRANGEBYSCORE session_XPTO -inf <MIN_TIMESTAMP>
        // COUNT session_XPTO
        // DEL session_XPTO
        
        scheduler.schedule(this, this.cleanupInterval, TimeUnit.SECONDS);
    }
}


