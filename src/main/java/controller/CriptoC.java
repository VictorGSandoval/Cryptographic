/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Data;
import model.Cripto;
import service.Aes;
import service.Blowfish;
import service.Cesar;
import service.DH;
import service.Hash;
import service.RSA;
import service.RSAv2;

@Named(value = "criptoC") //@ManagedBean
@SessionScoped
@Data
public class CriptoC implements Serializable {

    private Cripto model;
    private Cesar cesar;
    private Hash hash;
    private RSA rsa;
    private DH dh;
    private Blowfish blowfish;
    private Aes aes;

    public CriptoC() {
        model = new Cripto();
        cesar = new Cesar();
        hash = new Hash();
        blowfish = new Blowfish();
        rsa = new RSA();
        aes = new Aes();
    }

    public void limpiar() {
        model = new Cripto();
        cesar = new Cesar();
        hash = new Hash();
        blowfish = new Blowfish();
        rsa = new RSA();
    }

    public void cifrar() {
        try {
            String password = model.getInput();
            String result;
//            String rrsa = rsa.Decrypt(model.getInputD());
//            result = hash.md5(hash.sha1(cesar.cifrarCesar(password)));
//            result = blowfish.Encriptar(cesar.Cifrar_Cesar(aes.getAESEncripty(password)));
            
//            result = blowfish.Encriptar(cesar.Cifrar_Cesar(rsa.encrypt(password)));
            result = rsa.encrypt(cesar.Cifrar_Cesar(blowfish.Encriptar(password)));
//            result = 
            model.setRInput(result);
            System.out.println(result);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Correto", "Cifrado Realizado con éxito"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Cifrado a Fallado"));
            System.out.println("error en cifrar" + e.getMessage());
        }
    }

    public void descifrar() {
        try {
            String password1 = model.getInputD();
            String result ;
//            result = aes.getAESDecrypt(cesar.Descifrar_Cesar(blowfish.Desencriptar(password)));
            String rss = blowfish.Desencriptar(cesar.Descifrar_Cesar(rsa.decrypt(password1)));
//            String rss1 = rsa.decrypt(cesar.Descifrar_Cesar(blowfish.Desencriptar(password1)));
            
//            String rss1 = blowfish.Desencriptar(cesar.Descifrar_Cesar(rsa.decrypt(password)));
//            String rss2 = cesar.Descifrar_Cesar(blowfish.Desencriptar(password));
//            System.out.println(rss);
//            System.out.println(rss1);
//            System.out.println(rss2);
            
//             result = blowfish.Desencriptar(rsa.Decrypt(cesar.Descifrar_Cesar(password)));
//          result = hash.md5(hash.sha1(cesar.cifrarCesar(password)));
            model.setRInputD(rss);
            System.out.println(rss);
//            System.out.println(rss1);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Correto", "Descifrado Realizado con éxito"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Descifrado a Fallado"));
            System.out.println("error en cifrarB " + e.getMessage());
        }
    }

}
