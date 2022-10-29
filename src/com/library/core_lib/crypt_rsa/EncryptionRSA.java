package com.library.core_lib.crypt_rsa;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class EncryptionRSA {
    public EncryptionRSA() {

    }

    public String encrypt(String data) {
        try {
            FileInputStream fis = new FileInputStream("OopLibrarySystem\\publicKey.rsa");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();

            X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = factory.generatePublic(spec);

            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.getEncoder().encodeToString(c.doFinal(("" + data).getBytes()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}