/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author govas
 */
public class Aes {
    // Asegúrate de añadir la clave de 32 dígitos para encriptar con AES 256
    private static final String secretKeyAES = "la clave mas larga es de 256bits"; // frase con 256 bits
    private static final String saltAES = "huwlzo@a*&t8tr83e$16hiy#k+vl!0cr"; // bits aleatorios, evita que las clave se
                                                                              // repita

    public Aes() {
    }

    public String getAESEncripty(String data) {
        try {
            byte[] iv = new byte[16];// Vector de inicializacion
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);// parametro de especificacion que se va a
                                                                      // consumir por Cipher
            /**
             * PBKDF2 (funciones de derivación clave que reduce ataque por fuerza bruta)
             * hHmac (hash criptográfica en combinación con una llave criptográfica
             * secreta.) SHA256 (algoritmo de digestion que encripta pero no se desencripta)
             **/
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            /**
             * Especificamos la clave con los parametros de PBE encriptacion basada en
             * password.
             **/
            /**
             * enviamos la clave como array de caracteres + sal de bits aleatoios
             * Especificamos la iteracion de 65536 Especificamos que la longitud sera de 256
             * bits.
             **/
            KeySpec keySpec = new PBEKeySpec(secretKeyAES.toCharArray(), saltAES.getBytes(), 65536, 256);
            SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);// genera la llave secreta a partir de la
                                                                               // especificacion de la llave
            SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");// obtenermos la llave
                                                                                           // codificada que sera de
                                                                                           // tipo AES
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// obtenemos la instancia AES, cifrado por
                                                                       // bloques con tamaño de bloque de 16 bytes
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);// iniciamos cipher del tipo ENCRYPT_MODE, con
                                                                         // la llave secreta y las especificaiones
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));// retornamos un Array de
                                                                                              // bytes pasado a String
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAESDecrypt(String data) {
        /**
         * Para descencriptar es el mismo procedimiento solo cambiamos a DECRYPT_MODE
         */
        byte[] iv = new byte[16];
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(secretKeyAES.toCharArray(), saltAES.getBytes(), 65536, 256);
            SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
            SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);// DECRYPT_MODE
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Metodo de ejecución
//    public static void main(String[] args) {
//        Aes aes = new Aes();
//        System.out.println(aes.getAES("AES USES A MATRIX"));
//        System.out.println(aes.getAESDecrypt("vZo2b0iH+tceEZrgAjsPpF1uHNrFyFs/nK5sfgfLd1E="));
//    }
}
