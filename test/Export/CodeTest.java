package Export;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CodeTest {
    Hashtable<Integer,String> appelname= new Hashtable<>(){{
        put(12,"rectangle");
    }};

    Hashtable<Integer,String> appelname2= new Hashtable<>(){{
        put(12,"rectangle");
    }};

    Hashtable<Integer,String> getAppelname3=new Hashtable<>(){{
        put(9,"square");
    }};

    Hashtable<Integer,String> Appelname4=new Hashtable<>(){{
        put(12,"rectangle");
        put(13,"square");
    }};

    CodeNode rectangle2=new CodeNode("litteral programing ",null,"rectangle");
    CodeNode square1=new CodeNode("a deux arguments",null,"square");

    ArrayList<CodeNode> refs2=new ArrayList<>(List.of(rectangle2,square1));
    CodeNode rectangle1=new CodeNode("litteral ",getAppelname3,"rectangle");
    CodeNode square=new CodeNode("a deux niveaux",null,"square");

    ArrayList<CodeNode> refs1=new ArrayList<>(List.of(rectangle1,square));


    CodeNode shape=new CodeNode("ceci est du  code",appelname,"shape");
    CodeNode rectangle=new CodeNode("litteral",null,"rectangle");

    CodeNode shape1=new CodeNode("ceci est du  programming",appelname2,"shape");

    CodeNode shape2=new CodeNode("ceci est du  ",Appelname4,"shape");

    ArrayList<CodeNode> args=new ArrayList<>(List.of(shape,rectangle));

    ArrayList<Node> compil=new ArrayList<>(List.of(shape,rectangle));

    ArrayList<CodeNode> failargs=new ArrayList<>(List.of(rectangle1));



    Code c=new Code();

    @org.junit.jupiter.api.Test
    void visit() {
        c.visit(compil);
    }

    @org.junit.jupiter.api.Test
    void extractcode() {
        assertEquals("ceci est du litteral code",c.extractcode(shape,args));
        assertEquals("ceci est du litteral a deux niveaux programming",c.extractcode(shape1,refs1));
        assertEquals("ceci est du litteral programing a deux arguments",c.extractcode(shape2,refs2));
    }
}