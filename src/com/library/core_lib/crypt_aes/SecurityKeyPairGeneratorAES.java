package com.library.core_lib.crypt_aes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class SecurityKeyPairGeneratorAES {
    public SecurityKeyPairGeneratorAES() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, IOException {
        File KeyFile = new File("OopLibrarySystem\\Key.aes");
        if (!KeyFile.exists()) {
            String SECRET_KEY = "AT160846 - Pham Doan Kiem @#AT16";
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            // Save Public Key
            FileOutputStream fos = new FileOutputStream(KeyFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fos);
            objectOut.writeObject(key);
            objectOut.close();
        }
    }
}