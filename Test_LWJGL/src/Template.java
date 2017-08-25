
import java.nio.FloatBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Template {

    String windowTitle = "Temlpate";
    public boolean closeRequested = false;
    long lastFrameTime;     // used to calculate delta
    float quadAngle;
    float distance = -16.0f;
    float x = 0.0f;

    private void initGL() {

        /* OpenGL */
        int width = Display.getDisplayMode().getWidth();
        int height = Display.getDisplayMode().getHeight();

        GL11.glViewport(0, 0, width, height);       // Reset The Current Viewport
        GL11.glMatrixMode(GL11.GL_PROJECTION);      // Select The Projection Matrix
        GL11.glLoadIdentity();                      // Reset The Projection Matrix
        GLU.gluPerspective(45.0f, ((float) width / (float) height), 0.1f, 100.0f); // Calculate The Aspect Ratio Of The Window
        GL11.glMatrixMode(GL11.GL_MODELVIEW);       // Select The Modelview Matrix
        GL11.glLoadIdentity();                      // Reset The Modelview Matrix

        GL11.glShadeModel(GL11.GL_SMOOTH);          // Enables Smooth Shading
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);  // Black Background
        GL11.glClearDepth(1.0f);                    // Depth Buffer Setup
        GL11.glEnable(GL11.GL_DEPTH_TEST);          // Enables Depth Testing
        GL11.glDepthFunc(GL11.GL_LEQUAL);           // The Type Of Depth Test To Do
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations

        //GL11.glEnable(GL11.GL_LIGHTING);
        //GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, (FloatBuffer) temp.asFloatBuffer().put(light_ambient).flip()); 
    }

    private void updateLogic(int delta) {
        quadAngle -= 0.1f * delta;     // Decrease The Rotation Variable For The Quads
    }

    private void draw_qards() {
        GL11.glBegin(GL11.GL_QUADS);            // Start Drawing The Cube

        GL11.glColor3f(0.0f, 1.0f, 0.0f);       // Set The Color To Green                
        GL11.glVertex3f(1.0f, 1.0f, -1.0f);     // Top Right Of The Quad (Top)
        GL11.glVertex3f(-1.0f, 1.0f, -1.0f);    // Top Left Of The Quad (Top)
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);     // Bottom Left Of The Quad (Top)
        GL11.glVertex3f(1.0f, 1.0f, 1.0f);      // Bottom Right Of The Quad (Top)

        GL11.glColor3f(1.0f, 0.5f, 0.0f);       // Set The Color To Orange
        GL11.glVertex3f(1.0f, -1.0f, 1.0f);     // Top Right Of The Quad (Bottom)
        GL11.glVertex3f(-1.0f, -1.0f, 1.0f);    // Top Left Of The Quad (Bottom)
        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);   // Bottom Left Of The Quad (Bottom)
        GL11.glVertex3f(1.0f, -1.0f, -1.0f);    // Bottom Right Of The Quad (Bottom)

        GL11.glColor3f(1.0f, 0.0f, 0.0f);       // Set The Color To Red
        GL11.glVertex3f(1.0f, 1.0f, 1.0f);      // Top Right Of The Quad (Front)
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);     // Top Left Of The Quad (Front)
        GL11.glVertex3f(-1.0f, -1.0f, 1.0f);    // Bottom Left Of The Quad (Front)
        GL11.glVertex3f(1.0f, -1.0f, 1.0f);     // Bottom Right Of The Quad (Front)

        GL11.glColor3f(1.0f, 1.0f, 0.0f);       // Set The Color To Yellow
        GL11.glVertex3f(1.0f, -1.0f, -1.0f);    // Bottom Left Of The Quad (Back)
        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);   // Bottom Right Of The Quad (Back)
        GL11.glVertex3f(-1.0f, 1.0f, -1.0f);    // Top Right Of The Quad (Back)
        GL11.glVertex3f(1.0f, 1.0f, -1.0f);     // Top Left Of The Quad (Back)

        GL11.glColor3f(0.0f, 0.0f, 1.0f);       // Set The Color To Blue
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);     // Top Right Of The Quad (Left)
        GL11.glVertex3f(-1.0f, 1.0f, -1.0f);    // Top Left Of The Quad (Left)
        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);   // Bottom Left Of The Quad (Left)
        GL11.glVertex3f(-1.0f, -1.0f, 1.0f);    // Bottom Right Of The Quad (Left)

        GL11.glColor3f(1.0f, 0.0f, 1.0f);       // Set The Color To Violet
        GL11.glVertex3f(1.0f, 1.0f, -1.0f);     // Top Right Of The Quad (Right)
        GL11.glVertex3f(1.0f, 1.0f, 1.0f);      // Top Left Of The Quad (Right)
        GL11.glVertex3f(1.0f, -1.0f, 1.0f);     // Bottom Left Of The Quad (Right)
        GL11.glVertex3f(1.0f, -1.0f, -1.0f);    // Bottom Right Of The Quad (Right)

        GL11.glEnd();                           // Done Drawing The Quad
    }

    private void draw_plane() {
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex3f(10.0f,  -10.0f, 10.0f);     // Top Right Of The Quad (Bottom)
        GL11.glVertex3f(-10.0f, -10.0f, 10.0f);     // Top Left Of The Quad (Bottom)
        GL11.glVertex3f(-10.0f, -10.0f, -10.0f);    // Bottom Left Of The Quad (Bottom)
        GL11.glVertex3f(10.0f,  -10.0f, -10.0f);    // Bottom Right Of The Quad (Bottom)
        
//        GL11.glColor3f(1.0f, 0.0f, 1.0f);       // Set The Color To Violet
//        GL11.glVertex3f(10.0f, 10.0f,  -10.0f); // Top Right Of The Quad (Right)
//        GL11.glVertex3f(10.0f, 10.0f,  10.0f);  // Top Left Of The Quad (Right)
//        GL11.glVertex3f(10.0f, -10.0f, 10.0f);  // Bottom Left Of The Quad (Right)
//        GL11.glVertex3f(10.0f, -10.0f, -10.0f); // Bottom Right Of The Quad (Right)

        GL11.glEnd();                           // Done Drawing The Quad
    }
    
    private void createWindow() {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setVSyncEnabled(true);
            Display.setTitle(windowTitle);
            Display.create();
        } catch (LWJGLException e) {
            Sys.alert("Error", "Initialization failed!\n\n" + e.getMessage());
            System.exit(0);
        }
    }

    private void renderGL() {
        //---
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // Clear The Screen And The Depth Buffer
        GL11.glLoadIdentity();                              // Reset The View
        //---
        GL11.glPushMatrix();
        GL11.glTranslatef(x, 5.0f, -21.0f);
        draw_plane();
        GL11.glPopMatrix();
        //---
        GL11.glTranslatef(x, 0.0f, distance);
        GL11.glRotatef(quadAngle, 1.0f, 1.0f, 1.0f);
        //---
        // включение нормализации 
        GL11.glEnable(GL11.GL_NORMALIZE);
        // включение цвета материала 
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        // включение первого источника света, можно включить и больше GL11.glEnable(GL_LIGHT1); 
        GL11.glEnable(GL11.GL_LIGHT0);
//        GL11.glEnable(GL11.GL_LIGHT1);
//        GL11.glEnable(GL11.GL_LIGHT2);
//        GL11.glEnable(GL11.GL_LIGHT3);
//        GL11.glEnable(GL11.GL_LIGHT4);
//        GL11.glEnable(GL11.GL_LIGHT5);
//        GL11.glEnable(GL11.GL_LIGHT6);
//        GL11.glEnable(GL11.GL_LIGHT7);
        GL11.glEnable(GL11.GL_LIGHTING);
        //---
        GL11.glPushMatrix();
        draw_qards();
        GL11.glPopMatrix();
        //---
        //---
        //GL11.glPushMatrix();
        //draw_plane();
        //GL11.glPopMatrix();
        //---
    }

    public void pollInput() {

        // scroll through key events
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
                    distance -= 0.5f;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
                    distance += 0.5f;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                    x -= 0.5f;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                    x += 0.5f;
                }
                
                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                    closeRequested = true;
                }
            }
        }

        if (Display.isCloseRequested()) {
            closeRequested = true;
        }
    }

    public int getDelta() {
        long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        int delta = (int) (time - lastFrameTime);
        lastFrameTime = time;

        return delta;
    }

    private void run() {
        createWindow();
        getDelta(); // Initialise delta timer
        initGL();

        while (!closeRequested) {
            pollInput();
            updateLogic(getDelta());
            renderGL();
            Display.update();
        }

        cleanup();
    }

    private void cleanup() {
        Display.destroy();
    }

    public static void main(String[] args) {
        Template template = new Template();
        template.run();
    }
}
