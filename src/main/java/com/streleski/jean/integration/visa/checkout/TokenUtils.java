/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.streleski.jean.integration.visa.checkout;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jean
 */
public class TokenUtils {
    private final static String SECRETKEY = "zenpfCE9qz}PNSHDsgN0d4lg/SGje5b5Ps6rd+xI";

    public final static String generateXToken(String payload, String apiKey) {
        String timestamp = timeStamp();
        //String beforeHash = SECRETKEY + timestamp + URI + "apikey=" + APIKEY + payload;
        //XPayToken = "xv2:"+ timestamp + ":" + SHA256HMAC(shared_secret, message)        
        String token = "xv2:"+ timestamp + ":" + sha256Digest(SECRETKEY,payload);
        //String token = "x:" + timestamp + ":" + hash;
        return token;
    }

    private static String timeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000L);
    }

    private static String sha256Digest(String secretKey, String payload) {
        try {
            return getDigest("SHA-256", secretKey+payload, true);
        } catch (SignatureException ex) {
            return "";
        }
    }

    private static String getDigest(String algorithm, String data, boolean toLower) throws SignatureException {
        try {
            MessageDigest mac = MessageDigest.getInstance(algorithm);
            mac.update(data.getBytes("UTF-8"));
            return toLower ? new String(toHex(mac.digest()))
                    .toLowerCase() : new String(toHex(mac.digest()));
        } catch (Exception e) {
            throw new SignatureException(e);
        }
    }

    private static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }

}
