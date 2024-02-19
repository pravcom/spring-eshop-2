package com.akhtyamov.springeshop.config;

import com.akhtyamov.springeshop.endpoint.BucketEndpoint;
import com.akhtyamov.springeshop.endpoint.GreetingEndpoint;
import com.akhtyamov.springeshop.endpoint.ProductsEndpoint;
import com.akhtyamov.springeshop.ws.greeting.Greeting;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

    //регистрируем точку в контексте, где будут находиться веб сервисы
    @Bean
    public ServletRegistrationBean messageDispatchersServlet(ApplicationContext applicationContext){
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet,"/ws/*");
    }

    @Bean(name = "greeting")
    public DefaultWsdl11Definition defaultWsdl11Definition(){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("GreetingPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(GreetingEndpoint.NAMESPACE_URI); //не имееет значения
        wsdl11Definition.setSchema(xsdGreetingSchema());
        return wsdl11Definition;
    }

    @Bean("greetingSchema")
    public XsdSchema xsdGreetingSchema(){
        return new SimpleXsdSchema(new ClassPathResource("ws/greeting.xsd"));
    }
//---------Products
    @Bean(name = "products")
    public DefaultWsdl11Definition productsWsdl11Definition(){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ProductsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(ProductsEndpoint.NAMESPACE_URI); //не имееет значения
        wsdl11Definition.setSchema(xsdProductsSchema());
        return wsdl11Definition;
    }

    @Bean("productsSchema")
    public XsdSchema xsdProductsSchema(){
        return new SimpleXsdSchema(new ClassPathResource("ws/products.xsd"));
    }

//-----------Bucket
@Bean(name = "bucket")
public DefaultWsdl11Definition bucketWsdl11Definition(){
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("BucketPort");
    wsdl11Definition.setLocationUri("/ws");
    wsdl11Definition.setTargetNamespace(BucketEndpoint.NAMESPACE_URI); //не имееет значения
    wsdl11Definition.setSchema(xsdBucketSchema());
    return wsdl11Definition;
}

    @Bean("BucketSchema")
    public XsdSchema xsdBucketSchema(){
        return new SimpleXsdSchema(new ClassPathResource("ws/bucket.xsd"));
    }
}
