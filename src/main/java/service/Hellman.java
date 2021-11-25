/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


//import Sun.misc.BASE64Decoder;
//import Sun.misc.BASE64Encoder;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.*;
/**
 *
 * @author govas
 */
public class Hellman {
        private PublicKey publickey;
    KeyAgreement keyAgreement;
    byte[] sharedsecret;

    String ALGO = "AES";

    Hellman() {
        makeKeyExchangeParams();
    }

    private void makeKeyExchangeParams() {
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("EC");
            kpg.initialize(128);
            KeyPair kp = kpg.generateKeyPair();
            publickey = kp.getPublic();
            keyAgreement = KeyAgreement.getInstance("ECDH");
            keyAgreement.init(kp.getPrivate());

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public void setReceiverPublicKey(PublicKey publickey) {
        try {
            keyAgreement.doPhase(publickey, true);
            sharedsecret = keyAgreement.generateSecret();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String msg) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(msg.getBytes());
//            return new BASE64Encoder().encode(encVal);
        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return msg;
    }

//    public String decrypt(String encryptedData) throws InvalidKeyException, IllegalBlockSizeException {
//        Key key = generateKey();
//        Cipher c = Cipher.getInstance(ALGO);
//        c.init(Cipher.DECRYPT_MODE, key);
//            byte[] decordedValue = null;
//        //            byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
//        byte[] decValue = c.doFinal(decordedValue);
//        return new String(decValue);
//        return encryptedData;
//    }

    public PublicKey getPublickey() {
        return publickey;
    }

    protected Key generateKey() {
        return new SecretKeySpec(sharedsecret, ALGO);
    }
    
}
