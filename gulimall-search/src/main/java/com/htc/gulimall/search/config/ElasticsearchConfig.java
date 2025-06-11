package com.htc.gulimall.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huotengchao
 * @version V1.0
 * @className ElasticsearchConfig
 * @description
 * @since 2025/4/25 14:48
 */
@Configuration
public class ElasticsearchConfig {
    @Autowired
    private ElasticsearchProperties esProperties;
    
    @Bean
    public ElasticsearchClient esClient() {
        // 创建低级别的 RestClient
        RestClient restClient = RestClient
            .builder(new HttpHost(esProperties.getHost(), esProperties.getPort(), "http"))
            .setDefaultHeaders(new Header[]{
                new BasicHeader("Authorization", "ApiKey " + esProperties.getApiKey())
            })
            .build();
        // 使用 Jackson 作为 JSON 处理器
        RestClientTransport transport = new RestClientTransport(
            restClient, new JacksonJsonpMapper()
        );
        return new ElasticsearchClient(transport);
    }
    
    @Bean
    public ElasticsearchAsyncClient esAsyncClient() {
        // 创建低级别的 RestClient
        RestClient restClient = RestClient
            .builder(new HttpHost(esProperties.getHost(), esProperties.getPort(), "http"))
            .setDefaultHeaders(new Header[]{
                new BasicHeader("Authorization", "ApiKey " + esProperties.getApiKey())
            })
            .build();
        
        // 使用 Jackson 作为 JSON 处理器
        RestClientTransport transport = new RestClientTransport(
            restClient, new JacksonJsonpMapper()
        );
        return new ElasticsearchAsyncClient(transport);
    }
}
