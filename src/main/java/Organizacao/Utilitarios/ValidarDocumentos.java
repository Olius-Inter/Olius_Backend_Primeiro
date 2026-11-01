package Organizacao.Utilitarios;
import java.util.regex.Pattern;
public class ValidarDocumentos {



        private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

        // ---------- CPF ----------
        public static boolean validarCpf(String cpf) {
            if (cpf == null) return false;
            String d = cpf.replaceAll("[.\\-\\s]", "");
            if (!d.matches("\\d{11}")) return false;
            if (todosIguais(d)) return false;

            int dv1 = digitoCpf(d, 9);
            int dv2 = digitoCpf(d, 10);
            return dv1 == d.charAt(9) - '0' && dv2 == d.charAt(10) - '0';
        }

        // Calcula o dígito usando os primeiros 'qtd' dígitos, com pesos (qtd+1) até 2.
        private static int digitoCpf(String d, int qtd) {
            int soma = 0;
            for (int i = 0; i < qtd; i++) {
                soma += (d.charAt(i) - '0') * (qtd + 1 - i);
            }
            int resto = soma % 11;
            return resto < 2 ? 0 : 11 - resto;
        }

        // ---------- CNPJ (numérico e alfanumérico) ----------
        private static final int[] PESOS_CNPJ_1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        private static final int[] PESOS_CNPJ_2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        public static boolean validarCnpj(String cnpj) {
            if (cnpj == null) return false;
            String c = cnpj.replaceAll("[.\\-/\\s]", "").toUpperCase();
            // 12 primeiras posições: letras ou números; 2 últimas: só números
            if (!c.matches("[A-Z0-9]{12}\\d{2}")) return false;
            if (todosIguais(c)) return false;

            int dv1 = digitoCnpj(c, 12, PESOS_CNPJ_1);
            int dv2 = digitoCnpj(c, 13, PESOS_CNPJ_2);
            return dv1 == c.charAt(12) - '0' && dv2 == c.charAt(13) - '0';
        }

        private static int digitoCnpj(String c, int qtd, int[] pesos) {
            int soma = 0;
            for (int i = 0; i < qtd; i++) {
                int valor = c.charAt(i) - 48; // número vale o próprio valor; A=17, B=18...
                soma += valor * pesos[i];
            }
            int resto = soma % 11;
            return resto < 2 ? 0 : 11 - resto;
        }

        // ---------- CNH ----------
        public static boolean validarCnh(String cnh) {
            if (cnh == null) return false;
            String d = cnh.replaceAll("[\\-\\s]", "");
            if (!d.matches("\\d{11}")) return false;
            if (todosIguais(d)) return false;

            int soma1 = 0;
            for (int i = 0; i < 9; i++) {
                soma1 += (d.charAt(i) - '0') * (9 - i);
            }
            int dv1 = soma1 % 11;
            int desconto = 0;
            if (dv1 >= 10) {
                dv1 = 0;
                desconto = 2;
            }

            int soma2 = 0;
            for (int i = 0; i < 9; i++) {
                soma2 += (d.charAt(i) - '0') * (i + 1);
            }
            int dv2 = (soma2 % 11) - desconto;
            if (dv2 < 0) dv2 += 11;
            if (dv2 >= 10) dv2 = 0;

            return dv1 == d.charAt(9) - '0' && dv2 == d.charAt(10) - '0';
        }

        // ---------- E-mail (só formato) ----------
        public static boolean validarEmail(String email) {
            if (email == null) return false;
            String e = email.trim();
            if (e.length() > 254) return false;
            return EMAIL.matcher(e).matches();
        }

        // ---------- Auxiliar ----------
        private static boolean todosIguais(String s) {
            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) != s.charAt(0)) return false;
            }
            return true;
        }

}

