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

import com.movilizer.mds.onlinesync.sample.enpoints.HelloMoveletEndpoint;
import com.movilizer.mds.onlinesync.sample.webservices.HelloMoveletWebservice;
import com.movilizer.mds.webservice.Movilizer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws22.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.xml.ws.Endpoint;

@SpringBootApplication
@EnableWs
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

    //For Spring WS implementation
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "helloMoveletWebservice")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema movilizerSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("HelloMoveletPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(HelloMoveletEndpoint.NAMESPACE_URI);
        wsdl11Definition.setSchema(movilizerSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema movilizerSchema() {
        return new SimpleXsdSchema(new ClassPathResource(Movilizer.SCHEMA));
    }

}
