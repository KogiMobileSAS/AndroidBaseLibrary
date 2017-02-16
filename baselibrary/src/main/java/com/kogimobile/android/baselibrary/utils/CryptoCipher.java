package com.kogimobile.android.baselibrary.utils;

import android.util.Base64;

import java.security.AlgorithmParameters;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Julian Cardona on 9/22/16.
 */

public class CryptoCipher {

    private byte[] salt;
    private int iterationCount = 1024;
    private int keyStrength = 256;
    private SecretKey key;
    private byte[] iv;
    private Cipher cipher;

    CryptoCipher(String passPhrase,String definedSalt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        salt = new String(definedSalt).getBytes();
        KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount, keyStrength);
        SecretKey tmp = factory.generateSecret(spec);
        key = new SecretKeySpec(tmp.getEncoded(), "AES");
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }

    public String encrypt(String data) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters params = cipher.getParameters();
        iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] utf8EncryptedData = cipher.doFinal(data.getBytes());
        return Base64.encodeToString(utf8EncryptedData,Base64.DEFAULT);
    }

    public String decrypt(String base64EncryptedData) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] utf8 = cipher.doFinal(Base64.decode(base64EncryptedData,Base64.DEFAULT));
        return new String(utf8, "UTF8");
    }

}
