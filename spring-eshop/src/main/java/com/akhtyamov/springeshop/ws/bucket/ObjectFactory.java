//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.02.13 at 06:23:14 PM YEKT 
//


package com.akhtyamov.springeshop.ws.bucket;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.akhtyamov.springeshop.ws.bucket package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.akhtyamov.springeshop.ws.bucket
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetBucketRequest }
     * 
     */
    public GetBucketRequest createGetBucketRequest() {
        return new GetBucketRequest();
    }

    /**
     * Create an instance of {@link GetBucketResponse }
     * 
     */
    public GetBucketResponse createGetBucketResponse() {
        return new GetBucketResponse();
    }

    /**
     * Create an instance of {@link Bucket }
     * 
     */
    public Bucket createBucket() {
        return new Bucket();
    }

}
