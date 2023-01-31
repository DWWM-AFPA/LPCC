package Export;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

public class Compilator {
    protected String openTag;
    protected String closeTag;
    protected  int pos=0;
    protected String token;
    protected String doc;

    protected ArrayList<String> args;




    //getters
    public String getOpenTag () {
        return openTag;
    }
    public String getCloseTag () {
        return closeTag;
    }
    public ArrayList<String> getArgs() {
        return args;
    }

    public  int getPos() {
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

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }

    //builders
    public Compilator() {
        this.setOpenTag("<");
        this.setCloseTag(">");
        this.setArgs(new ArrayList<>());
    }
    public Compilator(String textToCompile) {
        this.setOpenTag("<");
        this.setCloseTag(">");
        this.setDoc(textToCompile);
        this.setArgs(new ArrayList<>());
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


    protected String cursor() {
        return setToken(String.valueOf(doc.charAt(getPos())));
    }
    public String next(){
        if (pos<doc.length()-1) {
            this.pos++;
            return this.cursor().toLowerCase();
        }
        else {
            return this.cursor().toLowerCase();
        }
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

    public ArrayList<Node> compile(){
        this.reset();
        ArrayList<Node> retour=new ArrayList<>();
        cursor();
        while (pos<doc.length()-1) {
            if(Objects.equals(this.getToken(), openTag)) {
                if (Objects.equals(next(), openTag)) {
                    this.previous();
                    retour.add(this.getCodeNode());
                } else {
                    this.previous();
                    retour.add(this.getDocNode());
                }
            }
        }
        return retour;
    }

    private String getarg(){
        String arg="";
        while(!Objects.equals(this.next(), closeTag)){
            arg=arg+getToken();
        }
        this.next();
        System.out.println("arg= "+arg);
        return arg;
    }

    private CodeNode getCodeNode(){
        String name=this.getName();
        String code="";
        int posrel=0;
        Hashtable<Integer,String> callcode=new Hashtable<>();
        while(!Objects.equals(this.next(), "/")){
            posrel++;
            if(Objects.equals(this.token, openTag)){
                String callname=this.getName();
                callcode.put(posrel,callname);
            }
            else {
                code+=this.getToken();
            }
        }
        return new CodeNode(code,callcode,name,".java");
    }

    private String getName(){
        String name="";
        while (!Objects.equals(this.next(),closeTag)){
            name=name+this.getToken();
        }
        this.next();
        return name;
    }

    private DocumentationNode getDocNode(){
        String txt="";
        while(Objects.equals(this.next(),openTag)||Objects.equals(this.getToken(),"/")){
            if(Objects.equals(this.getToken(), "/"))
                this.args.remove(getarg());
            else {
                this.args.add(getarg());
            }
            System.out.println("token= "+this.getToken());
            System.out.println("pos= "+this.getPos());
        }
        System.out.println("la position a la sortie de arg est "+this.getPos());
        txt=txt+this.getToken();
        while (!Objects.equals(this.getToken(),"/")){
            txt=txt+this.next();
            System.out.println("texte= "+txt);
            System.out.println("pos= "+this.getPos());
            System.out.println("token= "+this.getToken());
        }
        return new DocumentationNode(false,txt,this.getArgs());
    }

/*
    private Node exprend () {
        return null;
    }

    private Node closednode (){return null;}

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
*/
}

