/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 *
 * @author boss
 */
public class Sound {

    /**
     *
     */
    static String filename = "/usr/share/skype/sounds/ChatIncomingInitial.wav";
    public static final AudioClip WAV = Applet.newAudioClip(Sound.class.getResource(filename));
}
