/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author govas
 */
//3
public class Cesar {

//    public String cifrarCesar(String mensaje) {
//
//        //Texto a salir (cfrado)
//        String cipher = "";
//        String cipherReverse = "";
//        //Posiciones a adelantar
//        int adelantar = 3;
//
//        //Convertimos el mansaje en un array de caracteres
//        char[] letras = mensaje.toCharArray();
//        for (int i = 0; i < letras.length; i++) {
//            cipher += (char) (((int) letras[i]) + adelantar);
//        }
//
//        for (int j = cipher.length() - 1; j >= 0; j--) {
//            cipherReverse += cipher.charAt(j);
//        }
//        return cipherReverse;
//    }

    public static String Cifrar_Cesar(String mensaje) {
        //Texto a salir (cfrado)
        String cipher = "";
        //Posiciones a adelantar
        int adelantar = 3;

        //Convertimos el mansaje en un array de caracteres
        char[] letras = mensaje.toCharArray();

        //Vamos por cada caracter sumandole 3
        for (int i = 0; i < letras.length; i++) {

            // de esta manera obtenemos el codigo ascii del caracter
            //  ((int) letras[i]) y luego a ese numero le sumamos 3
            // ( ((int) letras[i])+ adelantar) <- quedaria asi
            // y luego convertimos ese numero en la letra a la que hace
            // referencia en el codigo ascii solo casteando el numero a (char)
            cipher += (char) (((int) letras[i]) + adelantar);
        }
        //Texto cifrado
        return cipher;
    }

    public static String Descifrar_Cesar(String cipher) {

        String mensaje = "";
        //Posiciones a atrasar
        int adelantar = 3;
        //Caracteres del mensaje
        char[] letras = cipher.toCharArray();
        for (int i = 0; i < letras.length; i++) {
            mensaje += (char) (((int) letras[i]) - adelantar);
        }
        return mensaje;
    }
}
