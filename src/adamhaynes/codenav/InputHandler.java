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

    public void update(){
        mouseX = Mouse.getX();
        mouseY = Mouse.getY();

        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
            escape = !escape;
    }
}
