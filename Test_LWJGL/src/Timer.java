
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public class Timer {

    public Timer() {
        // TODO Auto-generated constructor stub 
    }

    public static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public static void updateFPS() {
        if (getTime() - getLastFPS() > 1000) {
            Display.setTitle("JregcTutorialLighting " + "FPS: " + fps);
            fps = 0;
            setLastFPS(getLastFPS() + 1000);
        }
        fps++;
    }

    public static void syncUpdateApp() {
        time = Sys.getTime();
        setDt((time - lastTime) / 1000.0f);
        lastTime = time;
    }

    public static float getDt() {
        return dt;
    }

    public static void setDt(float adt) {
        dt = adt;
    }

    public static long getLastFPS() {
        return lastFPS;
    }

    public static void setLastFPS(long alastFPS) {
        lastFPS = alastFPS;
    }

    private static float dt = 0.0f;
    private static long lastFPS;
    private static float lastTime = 0.0f;
    private static float time = 0.0f;
    private static int fps;
    // private static int maxFPS; 
}
