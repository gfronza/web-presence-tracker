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
package com.github.gfronza.presencetracker.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.bayeux.server.LocalSession;
import org.cometd.bayeux.server.ServerChannel;

import com.github.gfronza.presencetracker.tasks.UsersCountEmitter;

/**
 * This service listen to users count events and publish the results on the bayeux channel.
 * @author Germano Fronza
 *
 */
@Service
public class UsersCountService implements UsersCountEmitter.Listener {
    
    @Inject
    private BayeuxServer bayeuxServer;
    
    @Inject
    private UsersCountEmitter usersCountEmitter;
    
    @Session
    private LocalSession sender;

    @PostConstruct
    public void postConstruct() {
        usersCountEmitter.addListener(this);
    }
    
    public void onUpdate(String session, long usersCount)
    {
        // Create the channel name using the session name.
        String channelName = "/usersCount/" + session;

        // Initialize the channel, making it persistent and lazy
        bayeuxServer.createChannelIfAbsent(channelName, new ConfigurableServerChannel.Initializer()
        {
            public void configureChannel(ConfigurableServerChannel channel)
            {
                channel.setPersistent(true);
                channel.setLazy(true);
            }
        });

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("usersCount", usersCount);

        // Publish to all subscribers
        ServerChannel channel = bayeuxServer.getChannel(channelName);
        channel.publish(sender, data);
    }
}
