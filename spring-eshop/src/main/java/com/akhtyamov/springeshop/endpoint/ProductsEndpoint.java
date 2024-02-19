package com.akhtyamov.springeshop.endpoint;

import com.akhtyamov.springeshop.domain.Product;
import com.akhtyamov.springeshop.dto.ProductDTO;
import com.akhtyamov.springeshop.service.GreetingService;
import com.akhtyamov.springeshop.service.ProductService;
import com.akhtyamov.springeshop.ws.greeting.GetGreetingRequest;
import com.akhtyamov.springeshop.ws.greeting.GetGreetingResponse;
import com.akhtyamov.springeshop.ws.products.GetProductsRequest;
import com.akhtyamov.springeshop.ws.products.GetProductsResponse;
import com.akhtyamov.springeshop.ws.products.ProductsWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
@Endpoint
public class ProductsEndpoint {
    public static final String NAMESPACE_URI = "http://akhtyamov.com/springeshop/ws/products";
    private ProductService productService;

    @Autowired
    public ProductsEndpoint(ProductService productsService) {
        this.productService = productsService;
    }

    // как RequestMapping
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductsRequest")
    @ResponsePayload // полезная нагрузка
    public GetProductsResponse getProductsWs(@RequestPayload GetProductsRequest request) throws DatatypeConfigurationException {
        GetProductsResponse response = new GetProductsResponse();
        productService.getAll()
                .forEach(productDTO -> response.getProducts().add(createProductWs(productDTO)));
        return response;
    }

    private ProductsWS createProductWs(ProductDTO dto){
        ProductsWS ws = new ProductsWS();
        ws.setId(dto.getId());
        ws.setTitle(dto.getTitle());
        ws.setPrice(Double.parseDouble(dto.getPrice().toString()));

        return ws;
    }
}
