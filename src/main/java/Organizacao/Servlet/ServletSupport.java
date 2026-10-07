package Organizacao.Servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

final class ServletSupport {

    private ServletSupport() {
    }

    static void prepare(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    static void prepareRequest(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
    }

    static String requiredText(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + name + "' é obrigatório.");
        }
        return value.trim();
    }

    static int requiredInt(HttpServletRequest request, String name) {
        String value = requiredText(request, name);
        try {
            int number = Integer.parseInt(value);
            if (number <= 0) {
                throw new NumberFormatException();
            }
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "O parâmetro '" + name + "' deve ser um inteiro positivo.", e);
        }
    }

    static double requiredNonNegativeDouble(HttpServletRequest request, String name) {
        String value = requiredText(request, name);
        try {
            double number = Double.parseDouble(value);
            if (!Double.isFinite(number) || number < 0) {
                throw new NumberFormatException();
            }
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "O parâmetro '" + name + "' deve ser um número válido não negativo.", e);
        }
    }

    static LocalDate requiredDate(HttpServletRequest request, String name) {
        String value = requiredText(request, name);
        try {
            return LocalDate.parse(value);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(
                    "O parâmetro '" + name + "' deve usar o formato yyyy-MM-dd.", e);
        }
    }

    static String json(String value) {
        if (value == null) {
            return "null";
        }
        StringBuilder result = new StringBuilder("\"");
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            switch (character) {
                case '"':
                    result.append("\\\"");
                    break;
                case '\\':
                    result.append("\\\\");
                    break;
                case '\b':
                    result.append("\\b");
                    break;
                case '\f':
                    result.append("\\f");
                    break;
                case '\n':
                    result.append("\\n");
                    break;
                case '\r':
                    result.append("\\r");
                    break;
                case '\t':
                    result.append("\\t");
                    break;
                default:
                    if (character < 0x20) {
                        result.append(String.format("\\u%04x", (int) character));
                    } else {
                        result.append(character);
                    }
            }
        }
        return result.append('"').toString();
    }

    static void error(HttpServletResponse response, int status, String message)
            throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":" + json(message == null ? "Erro interno." : message) + "}");
    }

    static String message(Exception exception) {
        return exception.getMessage() == null
                ? exception.getClass().getSimpleName() : exception.getMessage();
    }

}
