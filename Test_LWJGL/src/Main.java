
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

public class Main {

    // вектора в трех направлениях 
    Vector3f vector = new Vector3f();
    Vector3f rotation = new Vector3f();

    public Main() {
        // TODO Auto-generated constructor stub 
    }

    // загрузка важных методов 
    public void load() {
        displayApp();
        GL3D();
        running();
    }

    // обновление камеры 
    private void updateCamera() {
        inputCamera(speedCamera * Timer.getDt());
    }

    // позиция камеры 
    private void rotationCamera() {
        // вращение 
        GL11.glRotatef(rotation.x, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(rotation.y, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(rotation.z, 0.0f, 0.0f, 1.0f);
        // позиция 
        GL11.glTranslatef(-vector.x, -vector.y, -vector.z);
    }

    // управление камерой 
    private void inputCamera(float distance) {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            vector.x += distance * (float) Math.sin(rotation.y * Math.PI / 180);
            vector.z += -distance * (float) Math.cos(rotation.y * Math.PI / 180);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            vector.x -= distance * (float) Math.sin(rotation.y * Math.PI / 180);
            vector.z -= -distance * (float) Math.cos(rotation.y * Math.PI / 180);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            vector.x += distance * (float) Math.sin((rotation.y - 90) * Math.PI / 180);
            vector.z += -distance * (float) Math.cos((rotation.y - 90) * Math.PI / 180);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            vector.x += distance * (float) Math.sin((rotation.y + 90) * Math.PI / 180);
            vector.z += -distance * (float) Math.cos((rotation.y + 90) * Math.PI / 180);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            vector.y += distance;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            vector.y -= distance;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
            light_position = new float[]{vector.x, vector.y, vector.z, 1.0f};
            System.out.println(light_position[0] + " " + light_position[1] + " " + light_position[2]);
        }
        // обновление 
        mouseUpdate();
    }

    // управление мышкой 
    private void mouseUpdate() {
        if (Mouse.isGrabbed()) {
            float mouseDX = Mouse.getDX() * 0.8f * 0.16f;
            float mouseDY = Mouse.getDY() * 0.8f * 0.16f;
            if (rotation.y + mouseDX >= 360) {
                rotation.y = rotation.y + mouseDX - 360;
            } else if (rotation.y + mouseDX < 0) {
                rotation.y = 360 - rotation.y + mouseDX;
            } else {
                rotation.y += mouseDX;
            }
            if (rotation.x - mouseDY >= -89 && rotation.x - mouseDY <= 89) {
                rotation.x += -mouseDY;
            } else if (rotation.x - mouseDY < -89) {
                rotation.x = -89;
            } else if (rotation.x - mouseDY > 89) {
                rotation.x = 89;
            }
        }
    }

    // создание окна 
    private void displayApp() {
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.setTitle("JregcTutorialLighting");
            Display.create();
        } catch (LWJGLException e) {
            // TODO Auto-generated catch block 
            e.printStackTrace();
            exit(true);
        }
    }

    // настройка графики 
    private void GL3D() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(50.0f, 800.0f / 600.0f, 1.0f, 9600.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearDepth(1.0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        // включение нормализации 
        GL11.glEnable(GL11.GL_NORMALIZE);
        // включение цвета материала 
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        // включение первого источника света, можно включить и больше GL11.glEnable(GL_LIGHT1); 
        GL11.glEnable(GL11.GL_LIGHT0);
        // включение освещения 
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, (FloatBuffer) temp.asFloatBuffer().put(light_ambient).flip());
    }

    // главный цикл программы 
    private void running() {
        Timer.setLastFPS(Timer.getTime());
        Mouse.setGrabbed(true);
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            render();
        }
        exit(false);
    }

    // шар 
    Sphere sphere = new Sphere();

    ByteBuffer temp = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder());

    // рендер сцены 
    private void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
        // установка позиции камеры 
        rotationCamera();
        // установка материала 
        GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_DIFFUSE, (FloatBuffer) temp.asFloatBuffer().put(mat_diffuse).flip());
        GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_SPECULAR, (FloatBuffer) temp.asFloatBuffer().put(mat_specular).flip());
        GL11.glMaterialf(GL11.GL_FRONT_AND_BACK, GL11.GL_SHININESS, 50.0f);
        // отключение освещения 
        GL11.glDisable(GL11.GL_LIGHT0);
        GL11.glDisable(GL11.GL_LIGHTING);
        //  меньший шар - источник освещения 
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glTranslatef(light_position[0], light_position[1], light_position[2]);
        sphere.draw(1.0f, 20, 20);
        GL11.glPopMatrix();
        // включение освещения 
        GL11.glEnable(GL11.GL_LIGHT0);
        GL11.glEnable(GL11.GL_LIGHTING);
        // установка источника освещения 
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, (FloatBuffer) temp.asFloatBuffer().put(light_position).flip());
        // рисование большого шара 
        GL11.glPushMatrix();
        sphere.draw(10.0f, 50, 50);
        GL11.glPopMatrix();
        // рисование пирамиды 
        GL11.glPushMatrix();
        GL11.glTranslatef(20.0f, 2.0f, 0.0f);
        GL11.glBegin(GL11.GL_TRIANGLES);
        // создание нормали стороны пирамиды 
        GL11.glNormal3f(0.0f, 0.0f, 1.0f);
        GL11.glVertex3f(0.0f, 10.0f, 0.0f);                  // Верх треугольника (Передняя) 
        GL11.glVertex3f(-10.0f, -10.0f, 10.0f);                  // Левая точка 
        GL11.glVertex3f(10.0f, -10.0f, 10.0f);                  // Правая точка 

        GL11.glNormal3f(1.0f, 0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 10.0f, 0.0f);                  // Верх треугольника (Правая) 
        GL11.glVertex3f(10.0f, -10.0f, 10.0f);                  // Лево треугольника (Правая) 
        GL11.glVertex3f(10.0f, -10.0f, -10.0f);                 // Право треугольника (Правая) 

        GL11.glNormal3f(0.0f, 0.0f, -1.0f);
        GL11.glVertex3f(0.0f, 10.0f, 0.0f);                  // Низ треугольника (Сзади) 
        GL11.glVertex3f(10.0f, -10.0f, -10.0f);                 // Лево треугольника (Сзади) 
        GL11.glVertex3f(-10.0f, -10.0f, -10.0f);                 // Право треугольника (Сзади) 

        GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 10.0f, 0.0f);                  // Верх треугольника (Лево) 
        GL11.glVertex3f(-10.0f, -10.0f, -10.0f);                  // Лево треугольника (Лево) 
        GL11.glVertex3f(-10.0f, -10.0f, 10.0f);                  // Право треугольника (Лево) 
        GL11.glEnd();
        GL11.glPopMatrix();
        // обновление таймера 
        Timer.syncUpdateApp();
        Timer.updateFPS();
        // обновление камеры 
        updateCamera();
        // обновление окна 
        Display.update();
        // максимальный фпс - 120 
        Display.sync(120);
    }

    // разрушение приложения и освобождение ресурсов 
    private void exit(boolean asCrash) {
        Display.destroy();
        System.exit(asCrash ? 1 : 0);
    }

    // инитиализация 
    public static void main(String[] args) {
        new Main().load();
    }

    // скорость камеры 
    private float speedCamera = 20.0f;
    // позиция источника света 
    private float light_position[] = {31.84215f, 36.019997f, 28.262873f, 1.0f};
    // цвет фонового света 
    private float light_ambient[] = {0.0f, 0.0f, 0.3f, 1.0f};
    // настройка материалов 
    private float mat_specular[] = {1.0f, 1.0f, 1.0f, 1.0f};
    private float mat_diffuse[] = {0.5f, 0.5f, 0.5f, 1.0f};
}
