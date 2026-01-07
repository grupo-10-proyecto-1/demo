package com.sentiment.backend.service;

import com.sentiment.backend.exception.InvalidInputException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class LimpiadorText {
    // Detecta URLs básicas
    private static final Pattern URL_PATTERN =
            Pattern.compile("(?i)\\b(https?://|www\\.)\\S+\\b");

    // Solo para “limpieza” visual, no para cambiar el significado
    public static String limpiarOrThrow(String raw) {

        // Trim + colapsar espacios
        String text = raw.trim().replaceAll("\\s+", " ");

        // Si es una URL “pura” o casi todo es URL
        // Regla simple: si contiene URL y casi no hay más contenido, lo rechazamos

        boolean hasUrl = URL_PATTERN.matcher(text).find();
        if (hasUrl) {
            // Si quitamos URLs y queda muy poco, es básicamente una URL
            String withoutUrls = URL_PATTERN.matcher(text).replaceAll("").trim();
            if (withoutUrls.length() < 3) {
                throw new InvalidInputException("El texto no puede ser solo una URL");
            }
        }

        // Contar caracteres alfanuméricos (letras o dígitos)
        int alnum = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetterOrDigit(c)) alnum++;
        }

        // Si casi todo son símbolos (emojis, signos, etc.), rechazamos
        double ratio = (double) alnum / (double) text.length();
        if (ratio < 0.30) {
            throw new InvalidInputException("El texto no parece un comentario válido (muchos símbolos)");
        }

        // Repetición exagerada: "XD XD XD XD" o "jaja jaja jaja"
        // Tokenizamos por espacios, y contamos la frecuencia del token más común
        String[] tokens = text.split(" ");
        if (tokens.length >= 3) {
            Map<String, Integer> freq = new HashMap<>();
            for (String t : tokens) {
                String norm = t.toLowerCase(Locale.ROOT);
                freq.put(norm, freq.getOrDefault(norm, 0) + 1);
            }
            int max = freq.values().stream().max(Integer::compareTo).orElse(1);
            double maxShare = (double) max / (double) tokens.length;

            // Si una sola “palabra” domina el texto, suena a spam
            if (maxShare >= 0.70) {
                throw new InvalidInputException("El texto parece repetitivo (spam)");
            }
        }

        return text;
    }
}
