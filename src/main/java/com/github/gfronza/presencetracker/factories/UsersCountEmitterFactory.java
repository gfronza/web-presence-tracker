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
package com.github.gfronza.presencetracker.factories;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.github.gfronza.presencetracker.Settings;
import com.github.gfronza.presencetracker.tasks.UsersCountEmitter;

/**
 * Factory to create instance of UsersCountEmitter.
 * @author Germano Fronza
 *
 */
public class UsersCountEmitterFactory {

    @Produces @ApplicationScoped
    public UsersCountEmitter createUsersCountEmitter() {
        return new UsersCountEmitter(Settings.getUsersCountEmitterTimeout());
    }
    
}
