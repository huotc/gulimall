package com.htc.gulimall.product;

import com.htc.gulimall.product.entity.BrandEntity;
import com.htc.gulimall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huotengchao
 * @version V1.0
 * @className ProductApplicationTests
 * @description
 * @since 2024/1/19 15:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationTests {
    
    @Autowired
    BrandService brandService;

    @Test
    public void test() {
        BrandEntity brandEntity = new BrandEntity();
        // brandEntity.setName("小米");

        // brandService.save(brandEntity);
        // System.out.println("保存完成");
        brandEntity.setBrandId(1L);
        brandEntity.setDescript("小米");

        brandService.updateById(brandEntity);









    }
}
