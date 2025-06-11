package com.htc.gulimall.search;

import cn.hutool.core.io.LineHandler;
import cn.hutool.json.JSON;
import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import co.elastic.clients.elasticsearch.core.search.SourceFilter;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author huotengchao
 * @version V1.0
 * @className SearchApplicationTests
 * @description
 * @since 2025/4/25 14:57
 */
@Slf4j
@SpringBootTest
public class SearchApplicationTests {
    
    @Autowired
    private ElasticsearchClient esClient;
    @Autowired
    private ElasticsearchAsyncClient asyncClient;
    
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Goods {
        private String name;
        private String desc;
        private String brand;
        private BigDecimal price;
        private String lv;
        private String type;
        private Date createtime;
        private List<String> tags;
        
    }
    
    
    @Test
    public void SearchData() throws IOException {
    //     GET goods/_search
        // {
        //   "aggs": {
        //     "agg_pip": {
        //       "terms": {
        //         "field": "brand.keyword"
        //       },
        //       "aggs": {
        //         "avg_agg": {
        //           "avg": {
        //             "field": "price"
        //           }
        //         }
        //       }
        //     },
        //     "min_avg": {
        //       "min_bucket": {
        //         "buckets_path": "agg_pip>avg_agg"
        //       }
        //     }
        //   }
        // }
        // 写成java代码
        
        // 1. 条件
        Function<SearchRequest.Builder, ObjectBuilder<SearchRequest>> request = s -> s
            .index("goods")
            .aggregations("agg_pip", a -> a
                .terms(t -> t.field("brand.keyword"))
                .aggregations("avg_agg", a1 -> a1.avg(avg -> avg.field("price"))))
            .aggregations("min_avg", a -> a.minBucket(min -> min.bucketsPath(path -> path.single("agg_pip>avg_agg"))));
        System.out.println(request.apply(new SearchRequest.Builder()).build());
        // 2. 查询
        SearchResponse<Goods> search = esClient.search(request, Goods.class);
        // 3. 结果
        System.out.println(search);
        HitsMetadata<Goods> hits = search.hits();
        
        Map<String, Aggregate> aggregations = search.aggregations();
        
        Aggregate aggPip = aggregations.get("agg_pip");
        System.out.println(aggPip._kind());
        boolean sterms = aggPip.isSterms();
        if (sterms) {
            StringTermsAggregate sterms2 = aggPip.sterms();
            Buckets<StringTermsBucket> buckets = sterms2.buckets();
            List<StringTermsBucket> array = buckets.array();
            for (StringTermsBucket stringTermsBucket : array) {
                FieldValue key = stringTermsBucket.key();
                Map<String, Aggregate> aggregations1 = stringTermsBucket.aggregations();
                Aggregate aggregate = aggregations1.get("avg_agg");
                double value = aggregate.avg().value();
                System.out.println("key: " + key.stringValue() + " value: " + value);
            }
        }
        Aggregate minAvg = aggregations.get("min_avg");
        BucketMetricValueAggregate bucketMetricValueAggregate = minAvg.bucketMetricValue();
        double value = bucketMetricValueAggregate.value();
        List<String> keys = bucketMetricValueAggregate.keys();
        System.out.println("value: " + value + " keys: " + keys);
        
        
    }
    
    @Test
    public void contextLoads() throws IOException {
        System.out.println(esClient);
    }
    
    @Data
    public static class Product {
        private String sku;
        private String skuName;
        private BigDecimal price;
        
    }
    
    @Test
    public void indexData1() throws IOException {
        
        Product product = new Product();
        product.setSku("1234");
        product.setSkuName("华为手机");
        product.setPrice(new BigDecimal("2999.00"));
        // 1.
        IndexResponse response = esClient.index(i -> i
            .index("products")
            .id(product.getSku())
            .document(product)
        );
        
        System.out.println(response);
    }
    
    
    @Test
    public void indexData2() throws IOException {
        
        Product product = new Product();
        product.setSku("1234");
        product.setSkuName("华为手机");
        product.setPrice(new BigDecimal("2999.00"));
        
        // 2.
        IndexRequest<Product> request = IndexRequest.of(i -> i
            .index("products")
            .id(product.getSku())
            .document(product)
        );
        IndexResponse response = esClient.index(request);
        System.out.println(response);
        
        
    }
    
    
    @Test
    public void indexData3() throws IOException {
        
        Product product = new Product();
        product.setSku("1234");
        product.setSkuName("华为手机");
        product.setPrice(new BigDecimal("2999.00"));
        
        // 3.
        IndexRequest.Builder<Product> indexReqBuilder = new IndexRequest.Builder<>();
        indexReqBuilder.index("product");
        indexReqBuilder.id(product.getSku());
        indexReqBuilder.document(product);
        
        IndexResponse response = esClient.index(indexReqBuilder.build());
        System.out.println(response);
        
        
    }
    
    
    @Test
    public void indexData4() throws IOException {
        
        Product product = new Product();
        product.setSku("1234");
        product.setSkuName("华为手机");
        product.setPrice(new BigDecimal("2999.00"));
        // 4. 异步客户端
        asyncClient.index(i -> i
            .index("products")
            .id(product.getSku())
            .document(product)
        ).whenComplete((response, exception) -> {
            if (exception != null) {
                log.error("Failed to index", exception);
            } else {
                log.info("Indexed with version " + response.version());
            }
        });
    }
    
    @Test
    public void indexData5() throws IOException {
        // 5. 直接使用json
        Reader input = new StringReader(
            "{'@timestamp': '2022-04-08T13:55:32Z', 'level': 'warn', 'message': 'Some log message'}"
                .replace('\'', '"'));
        
        IndexRequest<JsonData> request = IndexRequest.of(i -> i
            .index("logs")
            .withJson(input)
        );
        IndexResponse response = esClient.index(request);
        System.out.println(response);
        
    }
    
    
}


