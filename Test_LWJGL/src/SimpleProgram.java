import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
 
public final class SimpleProgram
{
    private float x, y;
    private float movementSpeed;
    private float rotation;
    private long lastFrame;
    private long lastFPS;
    private int fps;
    private int delta;
    private Texture test;
 
    private SimpleProgram()
    {
        this.x = 400.0F;
        this.y = 300.0F;
        this.movementSpeed = 0.1F;
        this.rotation = 0.0F;
        this.lastFrame = 0;
        this.fps = 0;
        this.lastFPS = 0;
        this.delta = 0;
    }
 
    private void initialize()
    {
        this.createDisplay(800, 600, "SimpleProgram");
        this.createMatrixOrtho();
        this.running();
    }
 
    private void createDisplay(int width, int height, String title)
    {
        try
        {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle(title);
            Display.create();
        }
        catch (LWJGLException ex)
        {
            Logger.getLogger(SimpleProgram.class.getName()).log(Level.SEVERE, null, ex);
            this.clearResources(true);
        }
    }
 
    private void createMatrixOrtho()
    {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, (double) Display.getWidth(), 0.0D, (double) Display.getHeight(), -1.0D, 1.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }
 
    private void running()
    {
        this.bindResources();
        while (!Display.isCloseRequested())
        {
            this.displayUpdate();
        }
        this.clearResources(false);
    }
 
    private void bindResources()
    {
        this.getDelta();
        this.lastFPS = this.getTime();
        this.test = this.loadTexture("resource/texture/test2.png");
    }
 
    private void keyInput()
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_W))
        {
            this.y += this.movementSpeed * this.delta; // delta - время, за которое произойдет перемещение позиции.
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S))
        {
            this.y -= this.movementSpeed * this.delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D))
        {
            this.x += this.movementSpeed * this.delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A))
        {
            this.x -= this.movementSpeed * this.delta;
        }
        this.rotation += 0.1F * this.delta;
    }
 
    private void drawQuad(float x, float y, float rotate, float width, float height, float tX, float tY, float tS)
    {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.test.getTextureID());
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        GL11.glRotatef(rotate, 0.0F, 0.0F, 1.0F); // повернуть объект.
        GL11.glTranslatef(-width / 2, -height / 2, 0.0F); // сдвиг по координате x на половину для вращения в центре.
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(tX, tY + tS);
        GL11.glVertex2f(0.0F, 0.0F);
        GL11.glTexCoord2f(tX + tS, tY + tS);
        GL11.glVertex2f(width, 0.0F);
        GL11.glTexCoord2f(tX + tS, tY);
        GL11.glVertex2f(width, height);
        GL11.glTexCoord2f(tX, tY);
        GL11.glVertex2f(0.0F, height);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
 
    private Texture loadTexture(String resource)
    {
        Texture texture = null;
        try
        {
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(resource));
        }
        catch (IOException ex)
        {
            Logger.getLogger(SimpleProgram.class.getName()).log(Level.SEVERE, null, ex);
            this.clearResources(true);
        }
        return texture;
    }
 
    private void displayUpdate()
    {
        // обновление значение delta.
        this.delta = this.getDelta();
        // обработка событий.
        this.keyInput();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glLoadIdentity();
 
        // рисование квадрата.
        this.drawQuad(400.0F, 200.0F, 50.0F, 50.0F, 50.0F, 0.5F, -0.5F, 0.5F);
        this.drawQuad(this.x, this.y, this.rotation, 50.0F, 50.0F, 0.0F, 0.0F, 0.5F);
 
        GL11.glPushMatrix();
        GL11.glTranslatef(100.0F, 200.0F, 0.0F);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(1.0F, 0.0F, 0.0F);
        GL11.glVertex2f(0.0F, 0.0F);
        GL11.glColor3f(0.0F, 1.0F, 0.0F);
        GL11.glVertex2f(50.0F, 0.0f);
        GL11.glColor3f(0.0F, 0.0F, 1.0F);
        GL11.glVertex2f(50.0F, 50.0F);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glVertex2f(0.0F, 50.0F);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
 
        this.updateFPS(); // обновление FPS.
        Display.update();
        Display.sync(60);
    }
 
    private void clearResources(boolean hasCrash)
    {
        System.out.println(GLU.gluErrorString(GL11.glGetError()));
        Display.destroy();
        System.exit(hasCrash ? 1 : 0);
    }
 
    private void updateFPS()
    {
        if (this.getTime() - this.lastFPS > 1000)
        {
            Display.setTitle("FPS: " + this.fps); // показ FPS.
            this.fps = 0;
            this.lastFPS += 1000;
        }
        this.fps++;
    }
 
    // получить delta.
    private int getDelta()
    {
        long time = this.getTime();
        int delta2 = (int) (time - this.lastFrame);
        this.lastFrame = time;
 
        return delta2;
    }
 
    // получить время в миллисекундах.
    public long getTime()
    {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        new SimpleProgram().initialize();
    }
}
