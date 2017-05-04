/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.streleski.jean.integration.visa.checkout;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author jean
 */
public class PaymentDataHandler {
    private String payload;
    private String product;
    private String currency;
    private String price; 
    private JSONObject payloadJson;
    
    public PaymentDataHandler(String payload, String product, String currency, String price){
        this.payload = payload;
        this.product = product;
        this.currency = currency;
        this.price = price;    
        try {        
            payloadJson = new JSONObject(payload);
            if (payloadJson !=null){
                System.out.println(payloadJson.getString("encKey"));
                System.out.println(payloadJson.getString("encPaymentData"));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            System.out.println("Error no json");            
        }
    }
    
    public String doProcess() throws GeneralSecurityException, JSONException{
        byte[] decryptedData = Decrypter.decrypt(payloadJson.getString("encKey").getBytes(), payloadJson.getString("encPaymentData").getBytes());
        System.out.println(Arrays.toString(decryptedData));
        return Arrays.toString(decryptedData);
        
        //APIRestClient apiClient = new APIRestClient();
        //return apiClient.getPaymentInformation(callId);        
    }
    
    private String getCallIdFromPayload(){
        int index = payload.indexOf("callid")+9;        
        return payload.substring(index, index+19);
        
    }
    
    
    
}
