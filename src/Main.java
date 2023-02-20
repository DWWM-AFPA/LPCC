import Export.*;
import User.Config;
import User.Graphic;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, FileException {
/*        File file = new File("input","LPCC");

        Compilator compTest = new Compilator(file.read());
        compTest.compile();
       // Node.getAllNodes();
        Config matt= new Config("matt",new File("matt","config"));
     //   matt.createConfig();
        matt.loadConfig();*/

        Config.setMainInputFileName("tester.LPC");
        Graphic.draw();


    }
}