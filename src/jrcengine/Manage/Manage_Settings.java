package jrcengine.Manage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import jrcengine.Interface.IFace_FileIO;


public class Manage_Settings {

	public static boolean soundEnabled = true;
	public static boolean gravityEnabled = false;
	public static boolean autoAttackEnabled = false;
	public static boolean vibrationEnabled = true;
	public static boolean immortal= false;
	public static int difficult = 0;				//게임 난이도.
    public static int[] highscores = new int[] { 0, 0, 0, 0, 0 };
    public final static String file = ".ppsave";
    
    public static void load(IFace_FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(files.readFile(file)));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            gravityEnabled = Boolean.parseBoolean(in.readLine());
            autoAttackEnabled = Boolean.parseBoolean(in.readLine());
            vibrationEnabled = Boolean.parseBoolean(in.readLine());
            difficult = Integer.parseInt(in.readLine());
            immortal = Boolean.parseBoolean(in.readLine());
            for(int i = 0; i < 5; i++) {
                highscores[i] = Integer.parseInt(in.readLine());
            }
        } catch (IOException e) {
            // :( It's ok we have defaults
        } catch (NumberFormatException e) {
            // :/ It's ok, defaults save our day
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }
    
    public static void save(IFace_FileIO iFace_FileIO) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    iFace_FileIO.writeFile(file)));
            out.write(Boolean.toString(soundEnabled));
            out.write("\n");
            out.write(Boolean.toString(gravityEnabled));
            out.write("\n");
            out.write(Boolean.toString(autoAttackEnabled));
            out.write("\n");
            out.write(Boolean.toString(vibrationEnabled));
            out.write("\n");
            out.write(Integer.toString(difficult));
            out.write("\n");
            out.write(Boolean.toString(immortal));
            out.write("\n");
            for(int i = 0; i < 5; i++) {
                out.write(Integer.toString(highscores[i]));
                out.write("\n");
            }

        } catch (IOException e) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
    }

    public static void addScore(int score) {
        for(int i=0; i < 5; i++) {
            if(highscores[i] < score) {
                for(int j= 4; j > i; j--)
                    highscores[j] = highscores[j-1];
                highscores[i] = score;
                break;
            }
        }
    }

	
}
