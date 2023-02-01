package Export;

import java.util.ArrayList;
import java.util.Objects;

public class Compilator {
    protected String openTag;
    protected String closeTag;
    protected int pos=0;
    protected int firstTag;
    protected String contentTag;
    protected String token= new String();
    protected String doc=new String();


    //getters
    public String getOpenTag () {
        return openTag;
    }
    public String getCloseTag () {
        return closeTag;
    }

    public int getPos() {
        return this.pos;
    }
    public String getToken() {
        return token;
    }
    public String getToken(int pos) {
        return String.valueOf(this.doc.charAt(pos));
    }
    public String getDoc() {
        return doc;
    }

    public boolean getDebug() {
        return debug;
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
        reset();
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
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
    public  boolean debug=false;
    public  void debug(Boolean debug){
        this.setDebug(debug);
    }
    public  void debug(/*String expr*/) {
        if (this.getDebug()) {
            String expr = Thread.currentThread().getStackTrace()[2].getMethodName();
            String ls = System.lineSeparator();
            String dash = "-".repeat(26);
            System.err.printf("%s loc : %s %s token : %s    pos : %s %s remaining : %s%s%S",ls,expr,ls+dash+ls, getToken(),getPos(), ls+dash+ls,this.getDoc().substring(getPos()),ls+dash, ls);
        }
    }

    //main pour fatih
    public static void  main(String[] args) {
        //   Compilator comp = new Compilator("kdjfhfyy <dev> Hello world <dev/>");
        //  debug(false);
        //   comp.compile(comp.getDoc());
        Compilator code = new Compilator("kdjfhfyy <code> Hello world <code/>");
        code.setDebug(true);
        code.compile(code.getDoc());
    }


    protected String cursor(int pos) {
        String retour = String.valueOf(doc.charAt(getPos()));
        retour = retour != " "? String.valueOf(doc.charAt(getPos())):next();
        return setToken(retour.toLowerCase());
    }
    public String next(){
        this.pos++;
        //retourne this.token
        return this.cursor(this.getPos());
    }
    public String previous(){
        this.pos--;
        return this.cursor(this.getPos());
    }
    protected String reset(){
        this.pos=0;
        return this.cursor(this.getPos());
    }

    protected boolean endOfString(){
        boolean retour = false;
        try {
            next();
        } catch (StringIndexOutOfBoundsException e){
            retour= true;
        }
        return retour;
    }

    private String getTagContent(){
        StringBuilder retour=new StringBuilder();
        while (!Objects.equals(next(), this.getCloseTag()))
            retour.append(getToken());
        return retour.toString();
    }
    private void findOpenTag(){
        while (!Objects.equals(this.getToken(), this.getOpenTag())) {
            debug();
            next();
        }
        next();
    }
    private void findCloseTag(){
        while (!Objects.equals(this.getToken(), this.getCloseTag())) {
            debug();
            next();
        }
    }

    public /*ArrayList<Node>*/Node compile (String file){
        this.findOpenTag();
        this.setDoc(this.doc.substring(pos));
        return expr( this.getDoc());
    }
    private Node expr (String expr) {
        int firstPos=pos;
        debug();
        String next =this.getTagContent();
        if (next.equals("user")){
            pos+="user>".length()-1;
            return exprend();//new DocumentationNode();
        }
        else if (next.equals("dev")) {

                pos+="dev>".length()-1;
                return exprend();//new DocumentationNode();
            }
        //TODO check next ligne
        else if (!next.equals("")) {
         //   next();
            firstPos = getPos();
            System.out.println("Nom balise code  : " + next);
            return exprend();//new CodeNode();
            }

        else if (!endOfString()) {
            expr(getDoc());
        }
        debug();
        return null;
    }


    private Node exprend () {
        debug();
        this.findOpenTag();
        this.getTagContent();
        if (this.getToken()==">")
            next();
        //if()
        debug();
        return new CodeNode();
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

