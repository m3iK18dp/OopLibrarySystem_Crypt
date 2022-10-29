package com.library.core_lib.crypt_aes;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionAES {
    public EncryptionAES() {

    }

    public String encrypt(String data) {
        try {
            FileInputStream fis = new FileInputStream("OopLibrarySystem\\Key.aes");
            ObjectInputStream objectIn = new ObjectInputStream(fis);
            SecretKeySpec key = (SecretKeySpec) objectIn.readObject();
            objectIn.close();

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
