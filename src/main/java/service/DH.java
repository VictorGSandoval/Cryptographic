/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
 
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

/**
 *
 * @author govas
 */
public class DH {
    private final String ALGORITHM = "DH";
 
   /**
         * Número de byte de tecla predeterminado
    * 
    * <pre>
         * El tamaño de la clave predeterminado es 1024, la longitud de la llave debe ser múltiple de 64, de 512 a 1024
    * </pre>
    */
   private final int KEY_SIZE = 1024;
 
   /** El cifrado DH requiere un algoritmo de cifrado simétrico para cifrar datos, aquí usamos DES u otros algoritmos de cifrado simétrico. **/
   public String SECRET_ALGORITHM = "DES";
   /** Tecla de clave pública **/
   public static final String PUBLIC_KEY = "DHPublicKey";
   /** Clave de clave privada **/
   public static final String PRIVATE_KEY = "DHPrivateKey";
 
   /**
         * Inicializando la clave secundaria.
    * 
    * @return
    * @throws Exception
    */
   public Map<String, byte[]> initKey() throws Exception
   {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator
            .getInstance(ALGORITHM);
      keyPairGenerator.initialize(KEY_SIZE);
 
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
 
      // party a corporativo
      DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
 
      // fiesta una clave privada
      DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
 
      Map<String, byte[]> keyMap = new HashMap<String, byte[]>(2);
 
      keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
      keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
      return keyMap;
   }
 
   /**
         * Inicializar la clave B
    * 
    * @param key
         * Fiesta a corporativa
    * @return
    * @throws Exception
    */
   public Map<String, byte[]> initKey(byte[] key) throws Exception
   {
      X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
      KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
      PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
 
      // construir una clave b clave por el ACK
      DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();
 
      KeyPairGenerator keyPairGenerator = KeyPairGenerator
            .getInstance(keyFactory.getAlgorithm());
      keyPairGenerator.initialize(dhParamSpec);
 
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
 
      // Política de fiesta B
      DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
 
      // Party B Clave privada
      DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
 
      Map<String, byte[]> keyMap = new HashMap<String, byte[]>(2);
 
      keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
      keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
 
      return keyMap;
   }
 
   /**
         * Cifrado <br>
    * 
    * @param data
         * Datos de espera
    * @param publicKey
         * Fiesta a corporativa
    * @param privateKey
         * Party B Clave privada
    * @return
    * @throws Exception
    */
   public byte[] encrypt(byte[] data, byte[] publicKey, byte[] privateKey)
         throws Exception
   {
      // Generar clave local
      SecretKey secretKey = getSecretKey(publicKey, privateKey);
 
      // cifrado de datos
      Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
 
      return cipher.doFinal(data);
   }
 
   /**
         * Descifrado <br>
    * 
    * @param data
         * Para resolver datos
    * @param publicKey
         * Política de Party B
    * @param privateKey
         * Party B Clave privada
    * @return
    * @throws Exception
    */
   public byte[] decrypt(byte[] data, byte[] publicKey, byte[] privateKey)
         throws Exception
   {
      // Generar clave local
      SecretKey secretKey = getSecretKey(publicKey, privateKey);
      // descifrado de datos
      Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
 
      return cipher.doFinal(data);
   }
 
   /**
         * Construir una llave
    * 
    * @param publicKey
         * Llave pública
    * @param privateKey
         * Llave privada
    * @return
    * @throws Exception
    */
   private SecretKey getSecretKey(byte[] publicKey, byte[] privateKey)
         throws Exception
   {
      KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
      X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
      PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
 
      PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
      Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);
 
      KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory
            .getAlgorithm());
      keyAgree.init(priKey);
      keyAgree.doPhase(pubKey, true);
 
      // Generar clave local
      SecretKey secretKey = keyAgree.generateSecret(SECRET_ALGORITHM);
 
      return secretKey;
   }
 
}
