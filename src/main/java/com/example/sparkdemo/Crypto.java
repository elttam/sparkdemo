package com.example.sparkdemo;

import java.security.GeneralSecurityException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Crypto {

    private Key key;
    private GCMParameterSpec gcmParameterSpec;

    public Crypto() {
        byte[] keyBytes = "YELLOW_SUBMARINE".getBytes();
        key = new SecretKeySpec(keyBytes, "AES");
        gcmParameterSpec = new GCMParameterSpec(128, keyBytes);
    }

    private synchronized byte[] transform(byte[] data, int mode) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(mode, key, gcmParameterSpec);
            return cipher.doFinal(data);
        } catch (GeneralSecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String encrypt(String data) {
        return DatatypeConverter.printHexBinary(transform(data.getBytes(), Cipher.ENCRYPT_MODE)).toLowerCase();
    }

    public String decrypt(String data) {
        return new String(transform(DatatypeConverter.parseHexBinary(data), Cipher.DECRYPT_MODE));
    }
}
