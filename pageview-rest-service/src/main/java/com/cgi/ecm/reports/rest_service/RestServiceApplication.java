package com.cgi.ecm.reports.rest_service;

import com.cgi.ecm.reports.rest_service.csv.CSVMessageConverter;
import com.cgi.ecm.reports.rest_service.multipost.MultiPostConfiguration;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.UnknownHostException;
import java.util.List;

@SpringBootApplication
@Import({MultiPostConfiguration.class})
public class RestServiceApplication {

    public static void main(String[] args) {
	SpringApplication.run(RestServiceApplication.class, args);
    }

    @Bean
    public ResourceProcessor<PagedResources<Resource<PageView>>> pageViewProcessor() {
	    return new PageViewsResourceProcessor();
    }
//tag::csvMessageConverter[]
    @Bean
    public CSVMessageConverter csvMessageConverter() {
        return new CSVMessageConverter();
    }
//end::csvMessageConverter[]
    @Bean
    public WebMvcConfigurerAdapter csvWebMvcConfigurerAdapter(final CSVMessageConverter csvMessageConverter) {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(csvMessageConverter);
            }
        };
    }

    @Bean
    public MongoClient mongoClient(@Value("${mongodb_host}") String host, @Value("${mongodb_port}") int port) throws UnknownHostException {
        return new MongoClient(host, port);
    }

}
