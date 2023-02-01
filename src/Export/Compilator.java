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

    protected String codeopen;

    protected String codeclose;
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

    public String getCodeclose() {
        return codeclose;
    }

    public String getCodeopen() {
        return codeopen;
    }

    //setters


    public void setCodeclose(String codeclose) {
        this.codeclose = codeclose;
    }

    public void setCodeopen(String codeopen) {
        this.codeopen = codeopen;
    }

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
        this.setCodeclose("$");
        this.setCodeopen("$");
        this.setArgs(new ArrayList<>());
    }
    public Compilator(String textToCompile) {
        this.setOpenTag("<");
        this.setCloseTag(">");
        this.setCodeclose("$");
        this.setCodeopen("$");
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
        while (!(Objects.equals(this.getToken(), openTag) || Objects.equals(this.getToken(), codeopen)) ) {
            System.out.println("token ="+this.getToken());
            this.next();
        }
        this.cursor();
        return this.getToken();
    }

    public ArrayList<Node> compile() throws Exception {
        this.reset();
        ArrayList<Node> retour=new ArrayList<>();
        cursor();
        while (pos<doc.length()-1) {
            if(Objects.equals(this.getToken(), codeopen)) {
                retour.add(this.getCodeNode());
                System.out.println("j'ajoute bien un codenode ");
                System.out.println("le token a la sortie du codenode est "+ this.getToken());
            }
            else {
                DocumentationNode ajout = this.getDocNode();
                if (ajout != null) {
                    System.out.println("ajout args=" + ajout.getArgs());
                    retour.add(ajout);
                }
            }
        }
        if(!args.isEmpty())
            throw new LPCSyntaxException("Erreur sur les balises");
        return retour;
    }

    private String getarg(){
        String arg="";
        while(!Objects.equals(this.getToken(), closeTag)){
            arg=arg+getToken();
            this.next();
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
        while(!Objects.equals(this.token, "/")){
            if(Objects.equals(this.token, codeopen)){
                String callname=this.getName();
                System.out.println("je sors bien de getname");
                callcode.put(posrel,callname);
            }
            else {
                code+=this.getToken();
                this.next();
                posrel++;
            }
        }
        this.next();
        this.getName();
        if(callcode.isEmpty())
            return new CodeNode(code,null,name,".java");
        return new CodeNode(code,callcode,name,".java");
    }

    private String getName(){
        String name="";
        while (!Objects.equals(this.next(),codeclose)){
            name=name+this.getToken();
        }
        this.next();
        return name;
    }

    private DocumentationNode getDocNode(){
        String txt="";
        while(Objects.equals(this.token,openTag)){
            this.next();
            if(Objects.equals(this.getToken(), "/")) {
                this.next();
                this.args.remove(getarg());
            }
            else {
                this.args.add(getarg());
            }
            System.out.println("token= "+this.getToken());
            System.out.println("pos= "+this.getPos());
        }
        if(Objects.equals(token, codeopen) ||args.isEmpty())
            return null;
        while (!Objects.equals(this.getToken(),this.getOpenTag())&&!Objects.equals(this.getToken(),codeopen)){
            txt=txt+this.getToken();
            this.next();
            System.out.println("texte= "+txt);
            System.out.println("pos= "+this.getPos());
            System.out.println("token= "+this.getToken());
        }
        System.out.println(this.getArgs());
        ArrayList<String> argnode=new ArrayList<>(this.getArgs());
        System.out.println("argnode= "+argnode);
        return new DocumentationNode(false,txt,argnode);
    }

}

