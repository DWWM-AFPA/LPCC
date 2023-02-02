package Export;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompilatorTest {

    @Test
    void testEraseTagSpaces() {
        assertEquals("<code> Hello World <code/>",new Compilator("< code > Hello World <code/>").eraseTagSpaces());
        assertEquals("<code> Hello World <code/>",new Compilator("<  code > Hello World <code/>").eraseTagSpaces());
        assertEquals("<code> Hello World <code/>",new Compilator("< code  > Hello World < code />").eraseTagSpaces());
        assertEquals("<code> Hello World <code/>",new Compilator("< code  > Hello World < code />").eraseTagSpaces());
     //   assertThrows(StringIndexOutOfBoundsException.class, () -> { new Compilator("< code  > Hello World < code      /").eraseTagSpaces();});


    }
    @Test
    void testCompilator(){

        Compilator compTest = new Compilator("< code > Hello < test  > World <code/>");
        CodeNode nodeTest = (CodeNode) compTest.compile();
        assertEquals("Hello World code", nodeTest.getText() + " "+nodeTest.getName());


        Compilator comp = new Compilator("< code > Hello <test> World <code/>");
        CodeNode node = (CodeNode) comp.compile();
        assertEquals("Hello World code", node.getText() + " "+node.getName());


        comp = new Compilator("<code> Hello World < code />");
        node = (CodeNode) comp.compile();
        assertEquals("Hello World code", node.getText() + " "+node.getName());

        comp = new Compilator("< code > Hello World < code />");
        node = (CodeNode) comp.compile();
        assertEquals("Hello World code", node.getText() + " "+node.getName());

        comp = new Compilator("<code> Hello World <code/>");
        node = (CodeNode) comp.compile();
        assertEquals("Hello World code", node.getText() + " "+node.getName());


        comp = new Compilator("<code>Hello World<code/>");
        node = (CodeNode) comp.compile();
        assertEquals("Hello World code", node.getText() + " "+node.getName());

        comp = new Compilator("<code>  Hello World  <code/>");
        node = (CodeNode) comp.compile();
        assertEquals("Hello World code", node.getText() + " "+node.getName());


        assertEquals("Hello World", new Compilator("<user> Hello World <user/>").compile().getText());

        assertEquals("Hello World", new Compilator("<dev>Hello World<dev/>").compile().getText());

        assertEquals("Hello World", new Compilator("<user>Hello World<user/>").compile().getText());

        assertEquals("Hello World", new Compilator("<dev>  Hello World  <dev/>").compile().getText());

        assertEquals("Hello World", new Compilator("<user>  Hello World  <user/>").compile().getText());



        // assertThrows(StringIndexOutOfBoundsException.class, () -> { new Compilator("<> Hello World </>").compile();});
        //assertEquals("Hello World",new Compilator("<> Hello World </>").compile().getText());

       // comp = new Compilator(" Hello World");
      //  node = (CodeNode) comp.compile();
    //    assertEquals("Hello World code", node.getText() + " "+node.getName());

    }
        }
