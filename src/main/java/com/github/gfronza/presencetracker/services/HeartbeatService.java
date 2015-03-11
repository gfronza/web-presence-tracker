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

import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;

import com.github.gfronza.presencetracker.tasks.PresenceUpdater;

/**
 * This service is used to update presence.
 * @author Germano Fronza.
 *
 */
@Service
public class HeartbeatService {

    private PresenceUpdater presenceUpdater = new PresenceUpdater();
    
    @Listener("/service/heartbeat")
    public void heartbeat(ServerSession session, ServerMessage message) {
        presenceUpdater.update(message.getChannel(), (String)message.getDataAsMap().get("userId"));
    }
    
}
