package org.example;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.example.constants.url.ApiUrlConstants;

public class ApiGateway {

    public static byte[] elevenLabsTextToSpeechAudioBytes(String apiKey, String text) throws Exception {
        HttpResponse<byte[]> response = Unirest.post(ApiUrlConstants.ELEVEN_LABS_TEXT_TO_SPEECH_IN_MP3_URL)
                .header("xi-api-key", apiKey)
                .header("Content-Type", "application/json")
                .body("{\"text\": \"" + text + "\", \"model_id\": \"eleven_multilingual_v2\"}")
                .asBytes();

        return response.getBody();
    }
}
