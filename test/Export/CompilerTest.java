package Export;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompilerTest {
    @Test
    void testNext(){
        assertEquals("a",new Compiler("a").next());
        assertEquals("z",new Compiler("az").next());
    }

    @Test
    void testEraseTagSpaces() {
        assertEquals("<code> Hello World <code/>",new Compiler("< code > Hello World <code/>").eraseTagSpaces());
        assertEquals("<code> Hello World <code/>",new Compiler("<  code > Hello World <code/>").eraseTagSpaces());
        assertEquals("<code> Hello World <code/>",new Compiler("< code  > Hello World < code />").eraseTagSpaces());
        assertEquals("<code> Hello World <code/>",new Compiler("< code  > Hello World < code />").eraseTagSpaces());
     //   assertThrows(StringIndexOutOfBoundsException.class, () -> { new Compilator("< code  > Hello World < code      /").eraseTagSpaces();});


    }
    @Test
    void testCompilator(){

        Compiler compTest = new Compiler("<dev> ceci est du code : \" +\n" +
                "                            \"<codeTest> code code code <codeTest/>\"+\n" +
                "                        \"<dev/>\"+\n" +
                "                \"<code> azerty <test> zob <code/>\" +\n" +
                "                        \"<user> <bd> a <it>  He <bd/><it/> t <user/> \" +\n" +
                "                \"<user> test <bd> azerty<bd/> <user/>");
        CodeNode nodeTest = (CodeNode) compTest.compile();
        assertEquals("Hello World code", nodeTest.getText() + " "+nodeTest.getName());


        Compiler comp = new Compiler("< code > Hello <test> World <code/>");
        CodeNode node = (CodeNode) comp.compile();
        assertEquals("Hello World code", node.getText() + " "+node.getName());


        comp = new Compiler("<code> Hello World < code />");
        node = (CodeNode) comp.compile();
        assertEquals("Hello World code", node.getText() + " "+node.getName());

        comp = new Compiler("< code > Hello World < code />");
        node = (CodeNode) comp.compile();
        assertEquals("Hello World code", node.getText() + " "+node.getName());

        comp = new Compiler("<code> Hello World <code/>");
        node = (CodeNode) comp.compile();
        assertEquals("Hello World code", node.getText() + " "+node.getName());


        comp = new Compiler("<code>Hello World<code/>");
        node = (CodeNode) comp.compile();
        assertEquals("Hello World code", node.getText() + " "+node.getName());

        comp = new Compiler("<code>  Hello World  <code/>");
        node = (CodeNode) comp.compile();
        assertEquals("Hello World code", node.getText() + " "+node.getName());


        assertEquals("Hello World", new Compiler("<user> Hello World <user/>").compile().getText());

        assertEquals("Hello World", new Compiler("<dev>Hello World<dev/>").compile().getText());

        assertEquals("Hello World", new Compiler("<user>Hello World<user/>").compile().getText());

        assertEquals("Hello World", new Compiler("<dev>  Hello World  <dev/>").compile().getText());

        assertEquals("Hello World", new Compiler("<user>  Hello World  <user/>").compile().getText());



        // assertThrows(StringIndexOutOfBoundsException.class, () -> { new Compilator("<> Hello World </>").compile();});
        //assertEquals("Hello World",new Compilator("<> Hello World </>").compile().getText());

       // comp = new Compilator(" Hello World");
      //  node = (CodeNode) comp.compile();
    //    assertEquals("Hello World code", node.getText() + " "+node.getName());

    }
        }
