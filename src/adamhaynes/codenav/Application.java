package adamhaynes.codenav;

import adamhaynes.util.Print;

/**
 * Created with IntelliJ IDEA.
 * User: Haynesy
 * Date: 3/09/12
 * Time: 1:12 PM
 */
public class Application {

    public static void main(String[] args){
        Print.line("Running");

        Codenav.start();

        Print.line("Done");
    }
}
