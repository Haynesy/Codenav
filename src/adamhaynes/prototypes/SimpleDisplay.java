package adamhaynes.prototypes;

import adamhaynes.util.Print;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Created with IntelliJ IDEA.
 * User: Haynesy
 * Date: 6/09/12
 * Time: 9:02 AM
 */
public class SimpleDisplay {

    private boolean running;

    public SimpleDisplay(){

        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.setTitle("Simple Display");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        running = true;
        run();

    }

    public static void start(){
        Print.line("Running");

        new SimpleDisplay();

        Print.line("Done");
    }

    public void run(){
        int frames = 0;
        double nsPerFrame = 1000000000.0 / 60.0;
        double unProcessed = 0;
        long lastFrameTime = System.nanoTime();
        long currentFrameTime = System.currentTimeMillis();

        while(running){
            long now = System.nanoTime();
            unProcessed += (now - lastFrameTime) / nsPerFrame;
            lastFrameTime = now;

            while(unProcessed > 1){
                unProcessed -= 1;
                // Tick();

            }

            if(Display.isCloseRequested())
                running = false;

            // Render();
            Display.update();
            frames++;

            if(System.currentTimeMillis() - currentFrameTime > 1000){
                currentFrameTime += 1000;
                Print.line(frames + " fps");
                frames = 0;

            }
            Display.sync(60);

        }

        Display.destroy();
    }
}
