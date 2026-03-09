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
}
