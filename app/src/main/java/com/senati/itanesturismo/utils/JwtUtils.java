package com.senati.itanesturismo.utils;

import android.util.Base64;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public final class JwtUtils {

    private JwtUtils() { }

    public static String extractUsernameFromJwt(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length == 3) {
                String payloadBase64 = parts[1];

                byte[] decodedBytes = Base64.decode(payloadBase64, Base64.URL_SAFE);
                String payloadJson = new String(decodedBytes, StandardCharsets.UTF_8);

                JSONObject jsonObject = new JSONObject(payloadJson);
                if (jsonObject.has("username")) {
                    return jsonObject.getString("username");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Usuario";
    }

    public static int extractUserIdFromJwt(String token) {
        if (token == null || token.isEmpty()) return -1;
        try {
            String[] parts = token.split("\\.");
            if (parts.length == 3) {
                String payloadBase64 = parts[1];
                byte[] decodedBytes = Base64.decode(payloadBase64, Base64.URL_SAFE);
                String payloadJson = new String(decodedBytes, StandardCharsets.UTF_8);
                JSONObject jsonObject = new JSONObject(payloadJson);
                // En el JWT de la API, el ID viaja en el "sub" (subject)
                if (jsonObject.has("sub")) {
                    return Integer.parseInt(jsonObject.getString("sub"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
