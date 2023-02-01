package Export;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompilatorTest {
    @Test
    void testCompilator(){
        assertEquals("Hello World",new Compilator("<code> Hello World <code/>").compile().getText());
        assertEquals("Hello World",new Compilator("<dev> Hello World <dev/>").compile().getText());
        assertEquals("Hello World",new Compilator("<user> Hello World <user/>").compile().getText());
        assertEquals("Hello World",new Compilator("<code>Hello World<code/>").compile().getText());
        assertEquals("Hello World",new Compilator("<dev>Hello World<dev/>").compile().getText());
        assertEquals("Hello World",new Compilator("<user>Hello World<user/>").compile().getText());

        assertEquals("Hello World",new Compilator("<code>  Hello World  <code/>").compile().getText());
        assertEquals("Hello World",new Compilator("<dev>  Hello World  <dev/>").compile().getText());
        assertEquals("Hello World",new Compilator("<user>  Hello World  <user/>").compile().getText());


    }
}
