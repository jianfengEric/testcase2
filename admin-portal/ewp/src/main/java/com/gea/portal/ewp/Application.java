/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gea.portal.ewp;

import com.tng.portal.common.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@ComponentScan(basePackages = {"com.tng.portal.ana", "com.gea.portal.ewp", "com.tng.portal.common"})
@EnableJpaRepositories(basePackages = {"com.tng.portal.ana.repository", "com.gea.portal.ewp.repository"})
@EntityScan(basePackages = {"com.tng.portal.ana.entity", "com.gea.portal.ewp.entity"})
//@ServletComponentScan(basePackages = {"com.tng.portal", "com.gea.portal"})
public class Application {

    public static void main(String[] args) {
    	PropertiesUtil.setFilter(args);
        SpringApplication.run(Application.class, args);
    }
    
}
