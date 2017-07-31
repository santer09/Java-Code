package Final;

import java.applet.*;
import java.net.*;

/**
 * Play a sound file from the network using the java.applet.Applet API.
 * CODE PROVIDED BY PROFESOR SCOTT.
 */
public class PlaySound
{
    public static void myPlay(String url) throws InterruptedException
    {
        try
           {
                long delay = 2;
                AudioClip clip = Applet.newAudioClip(new URL(url));
                clip.play();
                Thread.sleep(delay);

            }
        catch (MalformedURLException murle)
        {
                System.out.println(murle);
        }
    }
}