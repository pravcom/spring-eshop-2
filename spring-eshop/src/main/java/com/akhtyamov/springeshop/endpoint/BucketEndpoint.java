package com.akhtyamov.springeshop.endpoint;

import com.akhtyamov.springeshop.dto.BucketDTO;
import com.akhtyamov.springeshop.dto.BucketDetailDTO;
import com.akhtyamov.springeshop.dto.ProductDTO;
import com.akhtyamov.springeshop.service.BucketService;
import com.akhtyamov.springeshop.service.ProductService;
import com.akhtyamov.springeshop.ws.bucket.Bucket;
import com.akhtyamov.springeshop.ws.bucket.GetBucketRequest;
import com.akhtyamov.springeshop.ws.bucket.GetBucketResponse;
import com.akhtyamov.springeshop.ws.greeting.GetGreetingRequest;
import com.akhtyamov.springeshop.ws.products.GetProductsResponse;
import com.akhtyamov.springeshop.ws.products.ProductsWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import java.security.Principal;

@Endpoint
public class BucketEndpoint {
    public static final String NAMESPACE_URI = "http://akhtyamov.com/springeshop/ws/bucket";
    private BucketService bucketService;

    @Autowired
    public BucketEndpoint(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    // как RequestMapping
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBucketRequest")
    @ResponsePayload // полезная нагрузка
    public GetBucketResponse getBucketWs(@RequestPayload GetBucketRequest request
                                                            , Principal principal) throws DatatypeConfigurationException {
        GetBucketResponse response = new GetBucketResponse();

        BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
        response.setBucket(createBucketWs(bucketDTO));
        return response;
    }

    private Bucket createBucketWs(BucketDTO bucketDTO){
        Bucket ws = new Bucket();
        ws.setSum(bucketDTO.getSum());
        return ws;
    }
}
