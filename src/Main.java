import Export.*;
import User.Graphic;

public class Main {
    public static void main(String[] args) {
        Compilator compTest = new Compilator("< user > Hello  <it> test it <it/><user/>");
        compTest.compile();
       // Node.getAllNodes();


    }
}