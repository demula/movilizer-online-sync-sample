/*
 * Copyright 2012-2013 the original author or authors.
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

package com.movilizer.mds.onlinesync.sample;

import com.movilizer.mds.onlinesync.sample.webservices.HelloMoveletWebservice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws22.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import javax.xml.ws.Endpoint;

@SpringBootApplication
@ImportResource({"classpath:META-INF/cxf/cxf.xml"})
public class OnlineSyncApplication {
    private static Log logger = LogFactory.getLog(OnlineSyncApplication.class);

	public static void main(String[] args) throws Exception {
        logger.debug("Starting Movilizer Online Sync backend...");
        SpringApplication app = new SpringApplication(OnlineSyncApplication.class);
        app.setShowBanner(false);
        app.run(args);
	}

    // For CXF implementation
    @Bean
    public ServletRegistrationBean webserviceDispatcherServlet() {
        ServletRegistrationBean servletReg = new ServletRegistrationBean(new CXFServlet(), "/services/*");
        servletReg.setLoadOnStartup(1);
        return servletReg;
    }

    @Bean
    @Autowired
    public Endpoint helloEndpoint(Bus bus, HelloMoveletWebservice helloMoveletWebservice) {
        Endpoint endpoint = new EndpointImpl(bus, helloMoveletWebservice);
        endpoint.publish("/helloMoveletWebservice");
        return endpoint;
    }

}
