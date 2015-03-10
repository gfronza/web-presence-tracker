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

import java.util.EventListener;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.github.gfronza.presencetracker.Main;
import com.github.gfronza.presencetracker.Settings;

/**
 * This class is responsible for performing the actual users count of active sessions. 
 * @author Germano Fronza
 *
 */
public class UsersCountEmitter implements Runnable {

    private static final long DEFAULT_EMITTER_INTERVAL = 60;
    
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final List<Listener> listeners = new CopyOnWriteArrayList<Listener>();
    private final long emitterInterval;
    private final JedisPool jedisPool = Main.getJedisPool();
    
    /**
     * Create a new emitter with a custom interval (in seconds).
     * @param emitterInterval
     */
    public UsersCountEmitter(long emitterInterval) {
        this.emitterInterval = emitterInterval;
    }
    
    /**
     * Create a new emitter with the defaul interval (@see DEFAULT_EMITTER_INTERVAL).
     */
    public UsersCountEmitter() {
        this.emitterInterval = DEFAULT_EMITTER_INTERVAL;
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
    * Adds a new listener to receive users count updates.
    * @param listener
    */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    /**
     * Execute the users count job itself. 
     */
    public void run() {
        // Jedis instance will be auto-closed after the last statement.
        try (Jedis jedis = jedisPool.getResource()) {
            long currentTime = System.currentTimeMillis();
            long presenceTimeout = currentTime - (Settings.getPresenceTimeoutInSeconds() * 1000);
        
            // KEYS session_*
            Set<String> keys = jedis.keys(Settings.getSessionsKeyPrefix());
            
            for (String session: keys) {
                // ZCOUNT session_XPTO <MIN_TIMESTAMP> <CURRENT_TIMESTAMP>
                Long zcount = jedis.zcount(session, presenceTimeout, currentTime);
            
                // Notify listeners about this count.
                for (Listener listener : listeners) {
                    listener.onUpdate(session, zcount);
                }
            }
        }
        
        scheduler.schedule(this, this.emitterInterval, TimeUnit.SECONDS);
    }
    
    public interface Listener extends EventListener {
        void onUpdate(String session, long usersCount);
    }
}


