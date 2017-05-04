/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.streleski.jean.integration.visa.checkout;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;

/**
 *
 * @author jean
 */
public class APIRestClient {
    private String HEADER_X_TOKEN="x-pay-token";
    private String APIKEY = "D4PZLT61I8V62Y6W2O7M21jMvIYyH-L2SSeFLpx9azcL65tj4";    
    
    @Produces(MediaType.APPLICATION_JSON)
    //@Path("/data")
    public String getPaymentInformation(String callId){
        String requestBody="";        
        WebClient client = WebClient.create("https://sandbox.secure.checkout.visa.com/wallet-services-web/payment/data/");
        client.header(HEADER_X_TOKEN, TokenUtils.generateXToken(requestBody, APIKEY));
        //client.header(HEADER_X_TOKEN, "xv2:1493865591:8c7bfc2bfc3526325004e6a58f1dddcc6b54d076cbc66ddc0cd67c10f68eee07");
        client.header("Accept","application/json");
        client.header("Content-Type","application/json");
        client.path("{callId}",callId);
        client.query("apiKey", APIKEY);
        client.query("dataLevel", "SUMMARY");

        String payment = client.get(String.class); 
        System.out.println(payment);
        return payment;
    }
    
    //sandbox.secure.checkout.visa.com/wallet-services-web/payment/
    
}
