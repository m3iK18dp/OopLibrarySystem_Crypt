package com.library.core_lib.crypt_rsa;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class DecryptionRSA {
    public DecryptionRSA() {

    }

    public String decrypt(String data) {
        try {
            FileInputStream fis = new FileInputStream("OopLibrarySystem\\privateKey.rsa");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = factory.generatePrivate(spec);

            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, priKey);
            byte decryptOut[] = c.doFinal(Base64.getDecoder().decode(data));
            return new String(decryptOut);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}