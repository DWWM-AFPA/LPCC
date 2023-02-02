import Export.*;
import User.Graphic;

public class Main {
    public static void main(String[] args) {
        Compilator compTest = new Compilator("< code > Hello < test  > World <code/>");
        compTest.compile();
        Node.getAllNodes();

    //System.out.println(Graphic.choose(Graphic.TypeChoose.OPEN).getName());

    }
}