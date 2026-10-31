package Organizacao.Utilitarios;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// Métodos comuns usados pelos servlets (resposta JSON e leitura de parâmetros).
// Os métodos de leitura lançam IllegalArgumentException quando o valor é inválido;
// o servlet transforma isso em resposta HTTP 400.
public class ServletUtil {

    // ---------- Resposta ----------
    public static void prepararResposta(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    public static void enviar(HttpServletResponse resp, int status, String json) throws IOException {
        resp.setStatus(status);
        resp.getWriter().write(json);
    }

    public static void enviarMensagem(HttpServletResponse resp, int status, String mensagem) throws IOException {
        enviar(resp, status, "{\"mensagem\":" + json(mensagem) + "}");
    }

    public static void enviarErro(HttpServletResponse resp, int status, String mensagem) throws IOException {
        enviar(resp, status, "{\"erro\":" + json(mensagem == null ? "Erro interno." : mensagem) + "}");
    }

    public static String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }

    // Converte texto em string JSON (com aspas e caracteres especiais escapados)
    public static String json(String texto) {
        if (texto == null) {
            return "null";
        }
        StringBuilder saida = new StringBuilder("\"");
        for (char c : texto.toCharArray()) {
            switch (c) {
                case '"': saida.append("\\\""); break;
                case '\\': saida.append("\\\\"); break;
                case '\n': saida.append("\\n"); break;
                case '\r': saida.append("\\r"); break;
                case '\t': saida.append("\\t"); break;
                default:
                    if (c < 0x20) saida.append(String.format("\\u%04x", (int) c));
                    else saida.append(c);
            }
        }
        return saida.append('"').toString();
    }

    // ---------- Leitura de parâmetros ----------
    public static String texto(HttpServletRequest req, String nome) {
        String valor = req.getParameter(nome);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }
        return valor.trim();
    }

    public static boolean temValor(HttpServletRequest req, String nome) {
        String valor = req.getParameter(nome);
        return valor != null && !valor.trim().isEmpty();
    }

    public static int inteiro(HttpServletRequest req, String nome) {
        try {
            int numero = Integer.parseInt(texto(req, nome));
            if (numero <= 0) {
                throw new NumberFormatException();
            }
            return numero;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve ser um inteiro positivo.");
        }
    }

    // Inteiro positivo que pode ficar em branco (retorna null)
    public static Integer inteiroOpcional(HttpServletRequest req, String nome) {
        return temValor(req, nome) ? Integer.valueOf(inteiro(req, nome)) : null;
    }

    public static int inteiroNaoNegativo(HttpServletRequest req, String nome) {
        try {
            int numero = Integer.parseInt(texto(req, nome));
            if (numero < 0) {
                throw new NumberFormatException();
            }
            return numero;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve ser um inteiro não negativo.");
        }
    }

    public static BigDecimal decimalPositivo(HttpServletRequest req, String nome) {
        try {
            BigDecimal valor = new BigDecimal(texto(req, nome).replace(',', '.'));
            if (valor.signum() <= 0) {
                throw new NumberFormatException();
            }
            return valor;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve ser um número maior que zero.");
        }
    }

    // Data no formato AAAA-MM-DD; se vier em branco usa a data de hoje
    public static LocalDate dataOuHoje(HttpServletRequest req, String nome) {
        if (!temValor(req, nome)) {
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(texto(req, nome));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve estar no formato AAAA-MM-DD.");
        }
    }
}
