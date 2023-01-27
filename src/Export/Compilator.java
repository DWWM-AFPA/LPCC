package Export;

import java.util.ArrayList;

public class Compilator {
    protected String openTag;
    protected String closeTag;
    protected static int pos=0;
    protected static char token='\0';


    //getters
    public String getOpenTag () {
            return openTag;
        }
    public String getCloseTag () {
            return closeTag;
        }

    public static int getPos() {
        return pos;
    }

    public static char getToken() {
        return token;
    }

    //setters
    public void setCloseTag (String closeTag){
            this.closeTag = closeTag;
        }
    public void setOpenTag (String openTag){
            this.openTag = openTag;
        }

    public static void setPos(int pos) {
        Compilator.pos = pos;
    }

    public static void setToken(char token) {
        Compilator.token = token;
    }

    //builders
    public Compilator() {
            this.setOpenTag(new String());
            this.setCloseTag(new String());
        }
    public Compilator(String openTag, String closeTag) {
            this.setCloseTag(closeTag);
            this.setOpenTag(openTag);
        }

    public static void  main(String[] args) {
        Compilator Comp = new Compilator();
        cursor("kdjfhfyy <dev> Hello world <dev/>");
        System.out.println(token);
        System.out.println(pos);
    }
    public ArrayList<Node> compile (String file){


        return null;
    }
    protected static void cursor(String doc) {
        setToken(doc.charAt(getPos()));
    }
    public static void next(){
        pos++;
    }
    public static void previous(){
        pos--;
    }
    protected static void reset(){
        pos=0;
    }
    private Object expr () {
            return null;
        }

    private Object exprend () {
            return null;
        }

    private Object mainTag () {
            return null;
        }

    private Object text () {
            return null;
        }

    private Object tag () {
            return null;
        }

    private Object tagEnd () {
            return null;
        }

    private Object word () {
            return null;
        }

}

