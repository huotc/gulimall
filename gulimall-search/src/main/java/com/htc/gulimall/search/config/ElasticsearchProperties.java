package com.htc.gulimall.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huotengchao
 * @version V1.0
 * @className ElasticsearchProperties
 * @description
 * @since 2025/4/25 17:03
 */
@Data
@Component
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchProperties {
    private String host;
    private int port;
    private String apiKey;
}
