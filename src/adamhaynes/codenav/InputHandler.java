package adamhaynes.codenav;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Created with IntelliJ IDEA.
 * User: Haynesy
 * Date: 3/09/12
 * Time: 1:34 PM
 */
public class InputHandler {

    public int mouseX;
    public int mouseY;
    public boolean escape;
    public int forwardback;
    public int leftright;


    public void update(){
        mouseX = Mouse.getX();
        mouseY = Mouse.getY();

        forwardback = 0;
        leftright = 0;

        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
            escape = !escape;

        if(Keyboard.isKeyDown(Keyboard.KEY_W))
            forwardback += 1;

        if(Keyboard.isKeyDown(Keyboard.KEY_S))
            forwardback -= 1;

        if(Keyboard.isKeyDown(Keyboard.KEY_A))
            leftright -= 1;

        if(Keyboard.isKeyDown(Keyboard.KEY_D))
            leftright += 1;
    }
}
