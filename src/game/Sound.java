package game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * Klasa odpowiedzialna za dźwięki w grze
 */
public class Sound {

    Clip clip;
    URL soundURL[] = new URL[10];

    /**
     * Konstruktor klasy  tworzący tablicę z wgranymi dźwiękami
     */
    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/melody.wav");
        soundURL[1] = getClass().getResource("/sounds/miau.wav");

    }

    /**
     * Metoda włączająca muzykę
     */
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0); // Resetuje pozycję odtwarzania
            clip.start();
        } else {

            System.err.println("Clip jest null, nie można odtworzyć dźwięku.");
        }
    }

    /**
     * Metoda stopująca muzykę
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Metoda zapętlająca muzykę
     */
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    //public void setVolume(float volume) {}

    /**
     * Metoda ustawiająca wybrany plik
     * @param i numer pliku w tablicy
     */
    public void setFile(int i){
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(inputStream);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
