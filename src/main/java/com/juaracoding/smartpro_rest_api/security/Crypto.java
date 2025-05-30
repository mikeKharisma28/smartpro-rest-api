package com.juaracoding.smartpro_rest_api.security;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESLightEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class Crypto {
    private static final String defaultKey = "7cf79176bfc48d17ea3c84d26399404244a4f0d3d3e2d8c01d5a6d9b37bbf7e8";

    public static String performEncrypt(String plainText) {
        return encrypt(defaultKey, plainText);
    }

    public static String performDecrypt(String hashedText) {
        return decrypt(defaultKey, hashedText);
    }

    private static String encrypt (String keyText, String plainText) {
        try {
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] ptBytes = plainText.getBytes();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(true, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(ptBytes.length)];
            int oLen = cipher.processBytes(ptBytes, 0, ptBytes.length, rv, 0);
            cipher.doFinal(rv, oLen);

            return new String(Hex.encode(rv));
        }
        catch (Exception ex) {
            return "Error while encrypting!";
        }
    }

    private static String decrypt(String keyText, String hashedText) {
        try {
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] cipherText = Hex.decode(hashedText.getBytes());
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(false, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(cipherText.length)];
            int oLen = cipher.processBytes(cipherText, 0, cipherText.length, rv, 0);
            cipher.doFinal(rv, oLen);

            return new String(rv).trim();
        } catch (Exception e) {
            return "Error while decrypting!";
        }
    }
}
