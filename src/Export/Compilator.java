package Export;

import java.sql.SQLOutput;
import java.util.*;

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
    //protected ArrayList<String> containedCode = new HashMap<>();

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
    public String eraseTagSpaces() {
        try {
            while (this.getPos() < this.getDoc().length() - 2) {
                findOpenTag();

                while (!this.getToken().equals(">"))
                    if (next().equals(" ")) {
                        this.setDoc(this.getDoc().substring(0, pos) + this.getDoc().substring(pos + 1));
                    }
            }
        }
        catch (StringIndexOutOfBoundsException e){
            System.err.println("Closing tag missing near "+pos);
            System.exit(-1);
        }
        return this.getDoc();
    }

    private String findTextContent(){
        return this.getDoc().substring(ft1+1,ot2-1).trim();
    }  
    private String findTagContent(){
        StringBuilder retour=new StringBuilder();
        while (!Objects.equals(next(), this.getCloseTag())) {
            retour.append(getToken());
        }
        return retour.toString().trim();
    }
    private String findOpenTag(){
        while (!Objects.equals(this.getToken(), this.getOpenTag())) {
            next();
        }
        return this.getToken();
    }
    private String findCloseTag(){
        while (!Objects.equals(this.getToken(), this.getCloseTag())) {
            next();
        }
        return this.getToken();
    }

    public Node compile () {
        debug(true);
        debug();
        eraseTagSpaces();
        reset();
        this.findOpenTag();
        this.setDoc(this.doc.substring(pos));
        reset();
        return expr();
    }
    private Node expr () {
        debug();
        this.setOt1(getPos());
        String next =this.findTagContent();
        if (next.equals("user")){
            setFt1(getPos());
            tagContent =next;
            return tag();//new DocumentationNode();
        }
        else if (next.equals("dev")) {
            setFt1(getPos());
            tagContent =next;
                return tag();//new DocumentationNode();
            }
        //TODO check next ligne
        else if (!next.equals("")) {
            setFt1(getPos());
            tagContent =next;
            return exprEnd();//new CodeNode();
            }

        else if (!endOfString()) {
            System.out.println("continue !!!!!!!!!!!!");
            expr();
        }
        debug();
        return null;
    }


    private Node exprEnd () {
        debug();
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
            return new CodeNode(findTextContent(),tagContent);
        }
        //il a trouvé la balise, mais ce n'est pas le code qui l'a ouvert
        else {
            //position relative dans le codeNode
            int deb=pos-end.length()-1-tagContent.length()-3;
            int fin=deb+end.length()+1;
            //TODO singleton for the code nodes
            CodeNode node = new CodeNode(end,this.getTagContent(),deb,fin);
            System.out.println("expr ENd "+end);
            return exprEnd();
        }
       // return expr();
    }

    private Node tag () {
        debug();
        findOpenTag();
        String end = this.findTagContent();
        if (end.equals("it") || end.contains("title") || end.equals("bd") || end.equals("ul") || end.equals("img") || end.equals("link")) {
            System.out.println("contenu");
            return tagEnd();
        }
        else if (end.contains(";")||end.contains("#")) {
            System.out.println("couleur");
            return tagEnd();
        }
        return null;
    }

    private Node tagEnd () {
        debug();
        this.findOpenTag();
        String end = this.findTagContent();
        if (end.equals("it/") || end.contains("title/") || end.equals("bd/") || end.equals("ul/") || end.equals("img/") || end.equals("link/")) {
            System.out.println("contenu");
            return exprEnd();
        }
        return text();
    }

    private Node text () {
        this.findOpenTag();
        String end = this.findTagContent();
        System.out.println(end);
        return exprEnd();
    }


}

