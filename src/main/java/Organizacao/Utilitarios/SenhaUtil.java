package Organizacao.Utilitarios;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

// Gera e confere hash de senha com PBKDF2 (já vem no Java, não precisa de biblioteca).
// No banco fica só o hash, nunca a senha digitada.
// Formato guardado: pbkdf2$iteracoes$sal$hash
public class SenhaUtil {

    private static final int ITERACOES = 120000;
    private static final int TAMANHO_BITS = 256;

    // Cria o hash de uma senha nova (com sal aleatório)
    public static String gerarHash(String senha) {
        byte[] sal = new byte[16];
        new SecureRandom().nextBytes(sal);
        return "pbkdf2$" + ITERACOES + "$" + codificar(sal) + "$" + codificar(derivar(senha, sal, ITERACOES));
    }

    // Confere a senha digitada com o hash guardado no banco
    public static boolean conferir(String senha, String hashGuardado) {
        if (senha == null || hashGuardado == null) {
            return false;
        }
        String[] partes = hashGuardado.split("\\$");
        if (partes.length != 4 || !partes[0].equals("pbkdf2")) {
            return false;
        }
        try {
            int iteracoes = Integer.parseInt(partes[1]);
            byte[] sal = Base64.getDecoder().decode(partes[2]);
            byte[] esperado = Base64.getDecoder().decode(partes[3]);
            // MessageDigest.isEqual compara em tempo constante (evita ataque por tempo)
            return MessageDigest.isEqual(esperado, derivar(senha, sal, iteracoes));
        } catch (IllegalArgumentException e) {
            return false; // hash guardado com formato inválido
        }
    }

    private static byte[] derivar(String senha, byte[] sal, int iteracoes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), sal, iteracoes, TAMANHO_BITS);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Não foi possível calcular o hash da senha.", e);
        }
    }

    private static String codificar(byte[] dados) {
        return Base64.getEncoder().encodeToString(dados);
    }

    // Uso: rode esta classe passando a senha como argumento para obter o hash
    // e colar no INSERT do administrador.
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Informe a senha como argumento. Ex.: java SenhaUtil minhaSenha123");
            return;
        }
        System.out.println(gerarHash(args[0]));
    }
}
