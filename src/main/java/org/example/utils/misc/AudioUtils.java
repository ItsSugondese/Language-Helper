package org.example.utils.misc;

import javazoom.jl.player.Player;

import java.io.ByteArrayInputStream;

public class AudioUtils {

    public static void playAudio(byte[] audioData) {
        if(audioData != null) {
            try {
                // Create a Player instance with the audio byte stream
                ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
                Player player = new Player(bais);

                // Play the audio in a new thread to avoid blocking the main thread
                new Thread(() -> {
                    try {
                        player.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
