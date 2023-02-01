package Export;

import java.sql.SQLOutput;
import java.util.Objects;

public class Compilator {
    protected String openTag;
    protected String closeTag;

    // ot1  tag1    ft1 content  ot2 tag2   ft2
    // <    code    >   text      < code    />
    protected int pos=0;
    protected int ot1;
    protected int ft1;
    protected int ot2;
    protected int ft2;
    protected String tag1;
    protected String tag2;
    protected String content;
    protected String tagContent;
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

    public int getOt1() {
        return ot1;
    }

    public int getFt1() {
        return ft1;
    }

    public int getOt2() {
        return ot2;
    }

    public int getFt2() {
        return ft2;
    }

    public String getTag1() {
        return tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public String getContent() {
        return content;
    }

    public String getTagContent() {
        return tagContent;
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

    public void setOt1(int ot1) {
        this.ot1 = ot1;
    }

    public void setFt1(int ft1) {
        this.ft1 = ft1;
    }

    public void setOt2(int ot2) {
        this.ot2 = ot2;
    }

    public void setFt2(int ft2) {
        this.ft2 = ft2;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public void setContent(String content) {
        this.content = content;
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
        code.compile();
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

    private String findTextContent(){
        return this.getDoc().substring(ft1+1,ot2-1).trim();
    }  
    private String findTagContent(){
        StringBuilder retour=new StringBuilder();
        pos--;
        while (!Objects.equals(next(), this.getCloseTag()))
            retour.append(getToken());
        return retour.toString().trim();
    }
    private void findOpenTag(){
        while (!Objects.equals(this.getToken(), this.getOpenTag())) {
            next();
        }
        next();
    }
    private void findCloseTag(){
        while (!Objects.equals(this.getToken(), this.getCloseTag())) {
            next();
        }
    }

    public /*ArrayList<Node>*/Node compile (){
        this.findOpenTag();
        this.setDoc(this.doc.substring(pos));
        return expr( this.getDoc());
    }
    private Node expr (String expr) {
        this.setOt1(getPos());
        String next =this.findTagContent();
        if (next.equals("user")){
            ft1 = getPos();
            return exprEnd();//new DocumentationNode();
        }
        else if (next.equals("dev")) {
            ft1 = getPos();
                return exprEnd();//new DocumentationNode();
            }
        //TODO check next ligne
        else if (!next.equals("")) {
            debug();
            ft1 = getPos();
            tagContent =next;
            return exprEnd();//new CodeNode();
            }

        else if (!endOfString()) {
            expr(getDoc());
        }
        debug();
        return null;
    }


    private Node exprEnd () {
        this.findOpenTag();
        String end = this.findTagContent();

        if (end.equals("user/")) {
            this.setOt2(pos-5);
            return new DocumentationNode(findTextContent());
        }
        else if (end.equals("dev/")) {
            this.setOt2(pos-4);
            return new DocumentationNode(true,findTextContent());
        }
        else if (end.equals(this.tagContent + "/")) {
            this.setOt2(pos-this.getTagContent().length()-1);
            debug();
            System.out.println(findTextContent()+"  "+tagContent);
            return new CodeNode(findTextContent(),tagContent);
        }
        else {
            if (this.getToken() == ">") {
                next();
            }
            //if()
            debug();
        }
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

