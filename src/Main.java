import Export.*;
import User.Graphic;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, FileException {
        File file = new File("input","LPCC");

        Compilator compTest = new Compilator(file.read());
        compTest.compile();
       // Node.getAllNodes();


    }
}