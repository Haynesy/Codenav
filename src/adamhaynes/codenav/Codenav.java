package adamhaynes.codenav;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 * Created with IntelliJ IDEA.
 * User: Haynesy
 * Date: 3/09/12
 * Time: 1:20 PM
 */
public class Codenav {

    private final int WIDTH = 640;
    private final int HEIGHT = 480;
    private boolean running = false;
    private InputHandler input;
    private String TITLE = "Codenav";
    private CodeDisplay display;

    public Codenav(){

        initialise();

        running = true;
        run();
    }

    private void run() {

        int frames = 0;
        double nsPerFrame = 1000000000.0 / 60.0;
        double unProcessed = 0;
        long lastFrameTime = System.nanoTime();
        long currentFrameTime = System.currentTimeMillis();

        while(running){
            long now = System.nanoTime();
            unProcessed += (now - lastFrameTime) / nsPerFrame;
            lastFrameTime = now;

            if(Display.isCloseRequested())
                running = false;

            while(unProcessed > 1){
                unProcessed -= 1;
                tick();
                display.render();
            }

            frames++;

            if(System.currentTimeMillis() - currentFrameTime > 1000){
                currentFrameTime += 1000;
                //Print.line(frames + " fps");
                Display.setTitle(frames + " fps");
                frames = 0;

            }
            Display.sync(60);
            Display.update();

        }

        Display.destroy();
    }

    private void tick() {

        // Update input
        input.update();
        if(input.escape)
            running = false;


    }

    private void initialise() {
        DisplayMode mode = new DisplayMode(WIDTH, HEIGHT);
        Display.setTitle(TITLE);

        input = new InputHandler();
        display = new CodeDisplay(input);

        try {
            Display.setDisplayMode(mode);
            Display.setResizable(false);
            Display.create();

            if(!GLContext.getCapabilities().OpenGL33)
                System.err.printf("You must have at least OpenGL 3.3 to run this program\n");
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Set clear color

        glShadeModel(GL_SMOOTH);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);


        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL); // Less than or equal
        glClearDepth(1.0);

        establishProjectionMatrix();

        //glEnable(GL_LIGHTING);
        //glEnable(GL_LIGHT0);
    }

    private void establishProjectionMatrix() {
        //glViewport(0, 0, WIDTH, HEIGHT);

        // Reset Projection matrix
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        gluPerspective(45.0f, (float)WIDTH / (float)HEIGHT, 0.1f, 200.0f);

        // Try to use the nicest calc
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glEnable(GL_PERSPECTIVE_CORRECTION_HINT);

    }

    public static void start() {
        new Codenav();
    }
}
