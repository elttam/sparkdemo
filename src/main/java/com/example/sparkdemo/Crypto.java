package com.example.sparkdemo;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Crypto {

    private final Key key;
    private final GCMParameterSpec gcmParameterSpec;

    public Crypto(String keyValue) {
        byte[] keyBytes = keyValue.getBytes(StandardCharsets.UTF_8);
        key = new SecretKeySpec(keyBytes, "AES");
        gcmParameterSpec = new GCMParameterSpec(128, keyBytes);        
    }

    public String encrypt(final String data) {
        byte[] pt = data.getBytes();
        byte[] ct = transform(pt, Cipher.ENCRYPT_MODE);
        
        return DatatypeConverter.printHexBinary(ct).toLowerCase();
    }

    public String decrypt(final String data) {
        byte[] ct = DatatypeConverter.parseHexBinary(data);
        byte[] pt = transform(ct, Cipher.DECRYPT_MODE);
        
        return new String(pt);
    }

    private synchronized byte[] transform(byte[] data, int mode) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(mode, key, gcmParameterSpec);
//            cipher.updateAAD("".getBytes(StandardCharsets.UTF_8));
            return cipher.doFinal(data);
        } catch (GeneralSecurityException ex) {
            throw new RuntimeException(ex);
        }
    }
}
