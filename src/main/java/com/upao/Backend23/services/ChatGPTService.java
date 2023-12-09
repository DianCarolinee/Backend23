package com.upao.Backend23.services;

import com.upao.Backend23.config.ChatGPTConfig;
import com.upao.Backend23.models.ChatGPTRequest;
import com.upao.Backend23.models.ChatGPTResponse;
import okhttp3.*;
import com.google.gson.*;

import java.io.IOException;

public class ChatGPTService {

    private OkHttpClient httpClient = new OkHttpClient();

    public ChatGPTResponse sendMessage(ChatGPTRequest request) throws IOException {
        Gson gson = new Gson();
        String requestBody = gson.toJson(request);

        Request httpRequest = new Request.Builder()
                .url(ChatGPTConfig.API_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ChatGPTConfig.API_KEY)
                .post(RequestBody.create(requestBody, MediaType.get("application/json; charset=utf-8")))
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String responseString = responseBody.string();
                JsonElement jsonElement = JsonParser.parseString(responseString);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonObject choices = jsonObject.getAsJsonArray("choices").get(0).getAsJsonObject();
                return new ChatGPTResponse(choices.get("text").getAsString());
            } else {
                return new ChatGPTResponse("No response body");
            }
        }
    }
}

