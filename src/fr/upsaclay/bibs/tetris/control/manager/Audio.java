package fr.upsaclay.bibs.tetris.control.manager;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Audio { // La classe Audio s'occupe de jouer la musique du jeu et le son de gameover

    // Le chemin des fichiers audio est défini dans les variables Music et GameOver
    private String Music = "Audio_sound/03. A-Type Music (Korobeiniki).wav";
    private String GameOver = "Audio_sound/gameover.wav";

    private Clip musiClip,gameoverClip;

    // Les clips audio sont initialisés dans le constructeur de la classe
    public Audio(){
        try {
            musiClip = AudioSystem.getClip();
            musiClip.open(AudioSystem.getAudioInputStream(new File(Music)));

            gameoverClip = AudioSystem.getClip();
            gameoverClip.open(AudioSystem.getAudioInputStream(new File(GameOver)));


        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Les méthodes musicStop() et GameOverStop() permettent de jouer respectivement la musique et le son de gameover
    public void musicPlay(){
        musiClip.setFramePosition(0); //Cette ligne de code permet de positionner le curseur de lecture du clip
                                      // de son "musicPlay" au début de sa piste. Ainsi, lors de la prochaine
                                      // lecture du clip, celui-ci débutera depuis le début

        musiClip.loop(Clip.LOOP_CONTINUOUSLY); // Cette ligne de code permet de jouer en boucle la musique du jeu
    }

    public void GameOverPlay(){
        gameoverClip.setFramePosition(0); //Cette ligne de code permet de positionner le curseur de lecture du clip
                                          // de son "gameoverClip" au début de sa piste. Ainsi, lors de la prochaine
                                          // lecture du clip, celui-ci débutera depuis le début.
        gameoverClip.start();
    }

    // La méthode musicRunning() permet de vérifier si la musique est en train de jouer ou non
    public boolean musicRunning(){
        if(musiClip.isRunning()){
            return true;
        }
        return false;
    }

    // La méthode musicStop() permet d'arrêter la musique
    public void musicStop(){
        musiClip.stop();
    }

}
