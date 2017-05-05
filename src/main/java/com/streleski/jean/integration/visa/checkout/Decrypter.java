/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.streleski.jean.integration.visa.checkout;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.Mac;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

/**
 *
 * @author jean
 */
public class Decrypter {

    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final int IV_LENGTH = 16, HMAC_LENGTH = 32;
    private static final Charset utf8 = Charset.forName("UTF-8");
    private static final Provider bcProvider;

    static {
        bcProvider = new BouncyCastleProvider();
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(bcProvider);
        }
    }

    public static byte[] decrypt(String encKey, String encPaymentData) throws GeneralSecurityException, Exception {
       
        byte[] decodedData = Base64.decode(encPaymentData);
        byte[] key = Base64.decode(encKey);
        if (decodedData == null || decodedData.length <= IV_LENGTH) {
            throw new RuntimeException("Bad input data.");
        }
        byte[] hmac = new byte[HMAC_LENGTH];
        System.arraycopy(decodedData, 0, hmac, 0, HMAC_LENGTH);
        
        /* I tried to solve this point here but I could not understand why the arrays is not equals*/
        if (!Arrays.equals(hmac, hmac(key, decodedData, HMAC_LENGTH, decodedData.length - HMAC_LENGTH))) {
            throw new Exception("HMAC validation failed.");
        }
        
        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(decodedData, HMAC_LENGTH, iv, 0, IV_LENGTH);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, bcProvider);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(hash(key), "AES"), new IvParameterSpec(iv));
        return cipher.doFinal(decodedData, HMAC_LENGTH + IV_LENGTH, decodedData.length - HMAC_LENGTH - IV_LENGTH);
    }

    private static byte[] hash(byte[] key) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
        md.update(key);
        return md.digest();
    }

    private static byte[] hmac(byte[] key,byte[] data,int offset,int length) throws GeneralSecurityException {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM, bcProvider);
        mac.init(new SecretKeySpec(key,HMAC_ALGORITHM));
        mac.update(data,offset,length);
        return mac.doFinal();
    }

}
