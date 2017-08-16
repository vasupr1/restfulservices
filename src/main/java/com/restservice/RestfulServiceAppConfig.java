package com.restservice;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import javax.ws.rs.ApplicationPath;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;
import com.restservice.resource.UsersResource;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@ApplicationPath("/restservice")
@EnableSwagger2
public class RestfulServiceAppConfig extends ResourceConfig {

	/**
	 * default constructor of RestfulServiceAppConfig
	 */
	public RestfulServiceAppConfig() {
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
		property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
		register(UsersResource.class);
	}

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("restservice-api")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(postPaths())
				.build();
	}
	
	@Bean
	public CloseableHttpClient client() {
		RequestConfig config = RequestConfig.
				custom()
				.setConnectTimeout(60 * 1000)
				.setConnectionRequestTimeout(60 * 1000)
				.setSocketTimeout(60 * 1000)
				.build();

		return HttpClientBuilder
				.create()
				.setDefaultRequestConfig(config)
				.build();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplateBuilder builder = new RestTemplateBuilder();
		return builder.build();
	}

	/**
	 * Object mapper object mapper.
	 * @return object mapper
	 */
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return mapper;
	}

	@SuppressWarnings("unchecked")
	private Predicate<String> postPaths() {
		return or(regex("/restservice/v1/.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Restfulservice")
				.description("Restful service API reference for developers")
				.termsOfServiceUrl("https://google.com")
				.license("Paid License")
				.licenseUrl("http://Google.com")
				.version("1.0")
				.build();
	}
}