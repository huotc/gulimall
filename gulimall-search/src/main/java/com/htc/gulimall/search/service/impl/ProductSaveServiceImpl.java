package com.htc.gulimall.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import com.htc.gulimall.common.to.es.SkuEsModel;
import com.htc.gulimall.search.constant.EsConstant;
import com.htc.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private ElasticsearchClient esClient;

    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {

        //保存到es
        //1、给es中建立索引。product，建立好映射关系。
        // gulimall-search/src/main/resources/product-mapping.txt
        
        //2、给es中保存这些数据
        List<BulkOperation> operations = new ArrayList<>();
        
        for (SkuEsModel model : skuEsModels) {
            //1、构造保存请求
            operations.add(BulkOperation.of(bo ->
                bo.index(IndexOperation.of(idx ->
                    idx.index(EsConstant.PRODUCT_INDEX)
                        .id(model.getSkuId().toString())
                        .document(model)
                ))));
        }
        
        BulkRequest bulkRequest = new BulkRequest.Builder().operations(operations).build();
        
        BulkResponse bulk = esClient.bulk(bulkRequest);
        //TODO 1、如果批量错误
        for (BulkResponseItem item : bulk.items()) {
            String id = item.id();
            if (item.error() != null) {
                log.error("商品上架错误：{}，错误原因：{}", id, item.error().reason());
            } else {
                log.info("商品上架完成：{}，返回数据：{}", id, item);
            }
        }
        return bulk.errors();

    }
}
