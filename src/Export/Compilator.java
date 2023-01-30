package Export;

import java.util.ArrayList;
import java.util.Objects;

public class Compilator {
    protected String openTag;
    protected String closeTag;
    protected static int pos=0;
    protected String token= new String();
    protected String doc=new String();


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
    public String getToken() {
        return token;
    }    public String getDoc() {
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
    public String setToken(String token) {
        return this.token = token;
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

    //debug
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

    //main pour fatih
    public static void  main(String[] args) {
        //   Compilator comp = new Compilator("kdjfhfyy <dev> Hello world <dev/>");
        //  debug(false);
        //   comp.compile(comp.getDoc());
        Compilator code = new Compilator("kdjfhfyy <code> Hello world <code/>");
        debug(true);
        code.compile(code.getDoc());
    }


    protected String cursor() {
        return setToken(String.valueOf(doc.charAt(getPos())));
    }
    public String next(){
        this.pos++;
        //retourne this.token
        return this.cursor().toLowerCase();
    }
    public String previous(){
        this.pos--;
        this.cursor();
        return this.getToken();
    }
    protected String reset(){
        this.pos=0;
        this.cursor();
        return this.getToken();
    }

    public /*ArrayList<Node>*/Node compile (String file){
        cursor();
        while (!Objects.equals(this.getToken(), this.getOpenTag())) {
            debug(token);
            next();
        }

        return expr( this.doc.substring(pos));
    }
    private Node expr (String expr) {
        if (next().equals("c")) {
            System.out.print(this.doc.substring(pos-1,pos+"code>".length()));
            return new CodeNode();
        }
        if (next().equals("u")||next().equals("d"))
            return new DocumentationNode();
        debug(token);
        return null;
    }


    private Node exprend () {
        return null;
    }

    private Node mainTag () {
        return null;
    }

    private Node text () {
        return null;
    }

    private Node tag () {
        return null;
    }

    private Node tagEnd () {
        return null;
    }

    private Node word () {
        return null;
    }

}

