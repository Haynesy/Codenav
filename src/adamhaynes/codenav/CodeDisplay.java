package adamhaynes.codenav;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class CodeDisplay {
    private InputHandler input;

    private int xPos = 0;
    private int zPos = -20;
    private float rotateX = 45;
    private float rotateY = 45;


    public CodeDisplay(InputHandler input) {
        this.input = input;

    }

    void render() {

        // Clear the screen
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // Reset the Model view
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();


        xPos += input.xPos;
        zPos += input.zPos;

        rotateY += input.leftright;
        rotateX += input.forwardback;

        GL11.glTranslatef(xPos, 0, zPos);
        GL11.glRotatef(rotateX, 1, 0, 0);
        GL11.glRotatef(rotateY, 0, 1, 0);

        // Do actual rendering
        cube();
    }

    private static void cube() {
        glBegin(GL_QUADS);

        // Top
        glColor3f(1.0f, 0.5f, 0.0f);
        glVertex3f( 1.0f, 1.0f, -1.0f);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glVertex3f( 1.0f, 1.0f, 1.0f);

        // Bottom
        glColor3f(0.0f, 1.5f, 0.0f);
        glVertex3f( 1.0f, -1.0f, -1.0f);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glVertex3f(-1.0f, -1.0f,  1.0f);
        glVertex3f( 1.0f, -1.0f,  1.0f);

        // Front
        glColor3f(1.0f, 0.0f, 0.0f);
        glVertex3f( 1.0f,  1.0f, 1.0f);
        glVertex3f(-1.0f,  1.0f, 1.0f);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glVertex3f( 1.0f, -1.0f, 1.0f);

        // Back
        glColor3f(1.0f, 1.0f, 0.0f);
        glVertex3f( 1.0f,  1.0f, -1.0f);
        glVertex3f(-1.0f,  1.0f, -1.0f);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glVertex3f( 1.0f, -1.0f, -1.0f);

        // Left
        glColor3f(0.0f, 0.0f, 1.0f);
        glVertex3f(-1.0f, 1.0f,  1.0f);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glVertex3f(-1.0f,-1.0f, -1.0f);
        glVertex3f(-1.0f,-1.0f,  1.0f);

        // Right
        glColor3f(1.0f, 0.0f, 1.0f);
        glVertex3f( 1.0f, 1.0f,  1.0f);
        glVertex3f( 1.0f, 1.0f, -1.0f);
        glVertex3f( 1.0f,-1.0f, -1.0f);
        glVertex3f( 1.0f,-1.0f,  1.0f);

        glEnd();
    }
}