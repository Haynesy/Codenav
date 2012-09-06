package adamhaynes.codenav;

import adamhaynes.util.Print;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created with IntelliJ IDEA.
 * User: Haynesy
 * Date: 3/09/12
 * Time: 1:20 PM
 */
public class Codenav {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private boolean running = false;
    private InputHandler input;
    private String TITLE = "Codenav";
    private int vertexBuffer;
    private int theProgram;

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

            while(unProcessed > 1){
                unProcessed -= 1;
                tick();
                render();
            }

            frames++;

            if(System.currentTimeMillis() - currentFrameTime > 1000){
                currentFrameTime += 1000;
                Print.line(frames + " fps");
                frames = 0;

            }
            Display.sync(60);
            Display.update();

        }

        Display.destroy();
    }

    public void reshape(int width, int height){
        glViewport(0, 0, width, height);
    }

    private void render() {

        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glEnableVertexAttribArray(0); // Use the current bound buffer

        // Param 1: Unk
        // Param 2: Each position contains 4 numbers
        // Param 3: Numbers are of type float
        // Param 4: Unk
        // Param 5: Spacing between values
        // Param 6: 0 offset in the buffer
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);

        // Param 1: Draw Triangles
        // Param 2: Start index
        // Param 3: Number of indexes
        glDrawArrays(GL_TRIANGLES, 0, 3);

    }

    private void tick() {

        // Update input
        input.update();
        if(Display.isCloseRequested() || input.escape)
            running = false;
    }

    private void initialise() {
        DisplayMode mode = new DisplayMode(WIDTH, HEIGHT);
        Display.setTitle(TITLE);
        input = new InputHandler();

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

        // Clear the screen
        glClearColor(0.4f, 0.6f, 0.9f, 0f);
        glClear(GL_COLOR_BUFFER_BIT);

        createVertexBuffer();
        initialiseProgram();
    }

    private String vertexShader = "#version 330\n\nlayout(location = 0) in vec4 position;\nvoid main()\n{\ngl_Position = position;\n}";
    private String fragmentShader = "#version 330\n\n out vec4 outputColor;void main(){outputColor = vec4(1.0f, 1.0f, 1.0f, 1.0f);}";
    
    private void initialiseProgram() {
        int[] shaderList = new int[2];
        shaderList[0] = CreateShader(GL_VERTEX_SHADER, vertexShader);
        shaderList[1] = CreateShader(GL_FRAGMENT_SHADER, fragmentShader);

        theProgram = CreateProgram(shaderList);
        for(int item : shaderList)
            glDeleteShader(item);

    }

    private int CreateProgram(int[] shaderList) {
        return 0;
    }

    private int CreateShader(int shaderType, String shader) {
        int theShader = glCreateShader(shaderType);
        glShaderSource(theShader, shader);

        glCompileShader(theShader);

        int status;
        status = glGetShader(theShader, GL_COMPILE_STATUS);
        if(status == GL_FALSE){

        }
        return 0;
    }

    private static final float vertexPositions[] = {
            0.75f, 0.75f, 0.0f, 1.0f,
            0.75f, -0.75f, 0.0f, 1.0f,
            -0.75f, -0.75f, 0.0f, 1.0f,
        };
    private void createVertexBuffer() {
        vertexBuffer = glGenBuffers(); // Create a buffer
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer); // Bind the buffer

        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(vertexPositions.length);
        floatBuffer.put(vertexPositions);
        floatBuffer.flip();

        // Fill buffer with data
        glBufferData(GL_ARRAY_BUFFER, floatBuffer, GL_STATIC_DRAW);

        // Unbind the buffer
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public static void start() {
        new Codenav();
    }
}
