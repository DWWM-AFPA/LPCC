package Export;

import java.util.ArrayList;

import java.util.Objects;

public class Compilator {
    protected String openTag;
    protected String closeTag;
    protected int pos=0;
    protected String token= new String();
    protected String doc=new String();

    //getters
    public String getOpenTag() {
        return openTag;
    }

    public String getCloseTag() {
        return closeTag;
    }

    public int getPos() {
        return pos;
    }

    public String getToken() {
        return token;
    }

    public String getDoc() {
        return doc;
    }

    //setters
    public void setCloseTag(String closeTag) {
        this.closeTag = closeTag;
    }
    public void setOpenTag(String openTag) {
        this.openTag = openTag;
    }
    public void setPos(int pos) {
        this.pos = pos;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setDoc(String doc) {
        this.doc = doc;
    }

    //builders
    public Compilator() {
            this.setOpenTag("<");
            this.setCloseTag(">");
        }
    public Compilator(String textToCompile) {
            this.setOpenTag("<");
            this.setCloseTag(">");
            this.setDoc(textToCompile);
        }
    public Compilator(String openTag, String closeTag) {
            this.setCloseTag(closeTag);
            this.setOpenTag(openTag);
        }
    public Compilator(String openTag, String closeTag,String textToCompile) {
            this.setCloseTag(closeTag);
            this.setOpenTag(openTag);
            this.setDoc(textToCompile);
        }

    public static void  main(String[] args) {
        Compilator comp = new Compilator("kdjfhfyy <dev> Hello world <dev/>");
        debug(true);
        comp.compile(comp.getDoc());
    }
    protected void cursor() {
        setToken(String.valueOf(doc.charAt(getPos())));
    }
    public void next(){
        this.pos++;
        this.cursor();
    }
    public void previous(){
        this.pos--;
        this.cursor();
    }
    protected void reset(){
        this.pos=0;
        this.cursor();
    }

    public static boolean debug=false;
    public static void debug(Boolean debug){
        Compilator.debug=debug;
    }
    public static void debug(String expr) {
        if (Compilator.debug) {
            String ls = System.lineSeparator();
            String dash = "-".repeat(26);
            System.err.printf("%s%s%s%s%s",ls, dash, expr, dash, ls);
        }
    }

    public /*ArrayList<Node>*/Node compile (String file){
        cursor();
        while (!Objects.equals(this.getToken(), this.getOpenTag())) {
            debug(token);
            next();
        }
        debug(token);
        return expr(null);
    }
    private Node expr (Node expr) {
        debug(token);
        return null;
        }


    //méthodes
    private Object exprend(){
        return null;
    }

    private Object mainTag(){
     return null;
    }

    private Object text(){
        return null;
    }

    private Object tag(){
        return null;
    }

    private Object tagEnd(){
        return null;
    }

    private Object word(){
        return null;
    }

}
