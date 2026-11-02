package Organizacao.Utilitarios;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

// protege o CPF no banco e recupera o valor para o formulario do administrador
public class CpfUtil {

    // a chave fica no ambiente, separada do codigo e do banco
    private static SecretKeySpec obterChave() {
        String valor = System.getenv("CPF_CHAVE");
        if (valor == null || valor.trim().isEmpty()) valor = System.getProperty("CPF_CHAVE");
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalStateException("Configure CPF_CHAVE no ambiente do Tomcat.");
        }
        try {
            byte[] chave = Base64.getDecoder().decode(valor.trim());
            if (chave.length != 32) throw new IllegalArgumentException();
            return new SecretKeySpec(chave, "AES");
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("CPF_CHAVE deve ser uma chave de 32 bytes em Base64.");
        }
    }

    // cada gravacao usa um numero aleatorio novo, mesmo para o mesmo CPF
    public static String criptografar(String cpf) {
        if (cpf == null) return null;
        try {
            byte[] numero = new byte[12];
            new SecureRandom().nextBytes(numero);
            Cipher cifra = Cipher.getInstance("AES/GCM/NoPadding");
            cifra.init(Cipher.ENCRYPT_MODE, obterChave(), new GCMParameterSpec(128, numero));
            byte[] dados = cifra.doFinal(cpf.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return "aesgcm$" + Base64.getEncoder().encodeToString(numero) + "$"
                    + Base64.getEncoder().encodeToString(dados);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Nao foi possivel proteger o CPF.", e);
        }
    }

    // aceita os registros antigos enquanto a conversao do banco nao foi executada
    public static String descriptografar(String valor) {
        if (valor == null || !valor.startsWith("aesgcm$")) return valor;
        try {
            String[] partes = valor.split("\\$", -1);
            if (partes.length != 3) throw new IllegalArgumentException();
            byte[] numero = Base64.getDecoder().decode(partes[1]);
            if (numero.length != 12) throw new IllegalArgumentException();
            byte[] dados = Base64.getDecoder().decode(partes[2]);
            Cipher cifra = Cipher.getInstance("AES/GCM/NoPadding");
            cifra.init(Cipher.DECRYPT_MODE, obterChave(), new GCMParameterSpec(128, numero));
            return new String(cifra.doFinal(dados), java.nio.charset.StandardCharsets.UTF_8);
        } catch (GeneralSecurityException | IllegalArgumentException e) {
            throw new IllegalStateException("Nao foi possivel ler o CPF. Confira CPF_CHAVE.", e);
        }
    }

    // executar uma vez para gerar a chave e guardar no ambiente do servidor
    public static void main(String[] args) {
        byte[] chave = new byte[32];
        new SecureRandom().nextBytes(chave);
        System.out.println("CPF_CHAVE=" + Base64.getEncoder().encodeToString(chave));
    }
}
