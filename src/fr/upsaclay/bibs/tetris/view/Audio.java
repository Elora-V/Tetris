package fr.upsaclay.bibs.tetris.view;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Audio {
    private String Music = "03. A-Type Music (Korobeiniki).wav";

    private Clip musiClip;

    public Audio(){
        try {
            musiClip = AudioSystem.getClip();
            musiClip.open(AudioSystem.getAudioInputStream(new File(Music)));

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void musicPlay(){
        musiClip.setFramePosition(0);
        musiClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void musicStop(){
        musiClip.stop();
    }

}
