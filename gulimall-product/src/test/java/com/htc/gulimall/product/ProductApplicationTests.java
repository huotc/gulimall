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
    public void testUpload() throws Exception {
        // // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        // String endpoint = "oss-cn-beijing.aliyuncs.com";
        // // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        // EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // // 填写Bucket名称，例如examplebucket。
        // String bucketName = "gulimall-htc";
        // // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        // String objectName = "仙剑.jpg";
        // // 创建OSSClient实例。
        // OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);
        // try {
        //     // InputStream inputStream = Files.newInputStream(Paths.get("C:\\Users\\ishuo\\Pictures\\93089067_p0 (1).jpg"));
        //     // ossClient.putObject(bucketName, objectName, inputStream);
        // } catch (OSSException oe) {
        //     System.out.println("Caught an OSSException, which means your request made it to OSS, "
        //             + "but was rejected with an error response for some reason.");
        //     System.out.println("Error Message:" + oe.getErrorMessage());
        //     System.out.println("Error Code:" + oe.getErrorCode());
        //     System.out.println("Request ID:" + oe.getRequestId());
        //     System.out.println("Host ID:" + oe.getHostId());
        // } finally {
        //     if (ossClient != null) {
        //         ossClient.shutdown();
        //     }
        // }

    }

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
