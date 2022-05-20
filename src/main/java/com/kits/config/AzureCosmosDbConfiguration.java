package com.kits.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.Nullable;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.DirectConnectionConfig;
import com.azure.cosmos.GatewayConnectionConfig;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.config.CosmosConfig;
import com.azure.spring.data.cosmos.core.ResponseDiagnostics;
import com.azure.spring.data.cosmos.core.ResponseDiagnosticsProcessor;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;

@Configuration
@EnableCosmosRepositories(basePackages = "com.kits.repository")
@EnableConfigurationProperties(CosmosProperties.class)
@PropertySource("classpath:application.properties")
public class AzureCosmosDbConfiguration extends AbstractCosmosConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(AzureCosmosDbConfiguration.class);
	@Autowired
	private CosmosProperties properties;
//    @Value("${azure.cosmosdb.uri}")
//    private String uri;
//
//    @Value("${azure.cosmosdb.key}")
//    private String key;
//
//    @Value("${azure.cosmosdb.secondaryKey}")
//    private String secondaryKey;
//
//    @Value("${azure.cosmosdb.database}")
//    private String dbName;
//
//    @Value("${azure.cosmosdb.queryMetricsEnabled}")
//    private boolean queryMetricsEnabled;

	private AzureKeyCredential azureKeyCredential;

//    @Bean
//    public CosmosClientBuilder cosmosClientBuilder() {
//        DirectConnectionConfig directConnectionConfig = DirectConnectionConfig.getDefaultConfig();
//        return new CosmosClientBuilder().endpoint(properties.getUri()).key(properties.getKey())
//                .directMode(directConnectionConfig);
//    }
	@Bean
	public CosmosClientBuilder getCosmosClientBuilder() {
		this.azureKeyCredential = new AzureKeyCredential(properties.getKey());
		DirectConnectionConfig directConnectionConfig = new DirectConnectionConfig();
		GatewayConnectionConfig gatewayConnectionConfig = new GatewayConnectionConfig();
		return new CosmosClientBuilder().endpoint(properties.getUri()).credential(azureKeyCredential)
				.directMode(directConnectionConfig, gatewayConnectionConfig);
	}

	@Override
	public CosmosConfig cosmosConfig() {
		return CosmosConfig.builder().enableQueryMetrics(properties.isQueryMetricsEnabled())
				.responseDiagnosticsProcessor(new ResponseDiagnosticsProcessorImplementation()).build();
	}

//    public void switchToSecondaryKey() {
//        this.azureKeyCredential.update(properties.getSecondaryKey());
//    }

	@Override
	protected String getDatabaseName() {
		return "SampleDB";
	}

	private static class ResponseDiagnosticsProcessorImplementation implements ResponseDiagnosticsProcessor {

		@Override
		public void processResponseDiagnostics(@Nullable ResponseDiagnostics responseDiagnostics) {
			LOGGER.info("Response Diagnostics {}", responseDiagnostics);
		}
	}

}