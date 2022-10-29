package com.library.core_lib.crypt_rsa;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class SecurityKeyPairGeneratorRSA {

    public SecurityKeyPairGeneratorRSA() {
        try {
            File publicKeyFile = new File("OopLibrarySystem\\publicKey.rsa");
            File privateKeyFile = new File("OopLibrarySystem\\privateKey.rsa");
            if (!publicKeyFile.exists() || !privateKeyFile.exists()) {
                SecureRandom sr = new SecureRandom();
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(4096, sr);

                KeyPair kp = kpg.genKeyPair();
                PublicKey publicKey = kp.getPublic();
                PrivateKey privateKey = kp.getPrivate();

                FileOutputStream fos = new FileOutputStream(publicKeyFile);
                fos.write(publicKey.getEncoded());
                fos.close();
                fos = new FileOutputStream(privateKeyFile);
                fos.write(privateKey.getEncoded());
                fos.close();
                System.out.println("Generate key successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
