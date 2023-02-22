package Export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Compilator {
    protected String openTag;
    protected String closeTag;

    protected int pos=0;
    public Node mainNode;
    public String style;
    protected StringBuilder content=new StringBuilder();
    protected static ArrayList<String> tagList = new ArrayList<>(List.of("it","bd","ul"));
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
    public String getContent() {
        return content.toString();
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
    public int setPos(int pos) {
        return this.pos = pos;
    }

    public void setContent(String content) {
        this.content.append(content);
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

    public Compilator(String textToCompile) {
        this.setOpenTag("<");
        this.setCloseTag(">");
        this.setDoc(textToCompile);
    }
    public Compilator(String openTag, String closeTag) {
        this.setCloseTag(closeTag);
        this.setOpenTag(openTag);
    }
    public Compilator(String openTag, String closeTag, String textToCompile) {
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
        String  retour = String.valueOf(doc.charAt(getPos()));
        retour = !retour.equals(" ") ? String.valueOf(doc.charAt(getPos())):next();

        return setToken(retour.toLowerCase());
    }
    public String next(){
        // System.out.println(this.getDoc().length()+" "+this.pos);
        // if (this.getDoc().length()-1<this.pos)
        this.pos++;

        //retourne this.token
        return this.cursor(this.getPos());
    }
    public String getTagIfOpentag(){
        if (this.getToken().equals(this.getOpenTag())) {//next();
            return this.findTagContent();
        }

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

    /** Fonction qui permet de trouver un nom de Tag donné entre deux char ouvrants et fermants.
     * En sortant de la fonction, le compilateur se trouve après le tag fermant.
     *
     * @return le nom du tag sans les char ouvrants et fermants
     */
    private String findTagContent(){
        StringBuilder retour=new StringBuilder();
        if (this.getToken().equals(this.getOpenTag())) {
            while (!Objects.equals(next(), this.getCloseTag())) {
                if (!this.getToken().equals(this.getCloseTag()))
                    retour.append(getToken());
            }
            next();
            return retour.toString().trim();
        }
        System.err.println("erreur sur la fonction findTagContent");
        return null;
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
        return mainTag();
    }
    private Node mainTag() {
        debug();
        String mainTag = findTagContent();
        if(this.mainNode instanceof CodeNode)
            System.err.println("INSTANCE DE CODE NODE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if (mainTag.equals("user")){
            mainNode =new DocumentationNode(mainTag);
            System.out.println(mainNode);
            next();
            return style();//new DocumentationNode();
        }
        else if (mainTag.equals("dev")) {
            //   tagContent =mainTag;
            mainNode =new DocumentationNode(mainTag);
            next();
            return style();//new DocumentationNode();
        }
        //TODO check next ligne to create codeNodes
        else if (!mainTag.equals("")&&!mainTag.equals(">")) {
            // tagContent =mainTag;
            //TODO take the hashmap into account
            HashMap<String, Node> map= Node.getNodeRegistry();
            if (!Node.getNodeRegistry().containsKey(mainTag))
                mainNode =new CodeNode(mainTag);
            else
                mainNode=map.get(mainTag);
            int save = this.getPos();
            next();
            return text();//new CodeNode();
        }

        else if (!endOfString()) {
            System.out.println("continue !!!!!!!!!!!!");
         //   next();
            mainTag();
        }

        else if (endOfString()) {
            System.out.println("stop !!!!!!!!!!!!");
            return null;
        }
       // debug();
        return null;
    }

    private Node mainTagEnd() {
        debug();
        // this.findOpenTag();
        int position=this.getPos();
        String end = getTagIfOpentag();;

        if (end.equals("user/")||end.equals("dev/")) {
            System.out.println(end);
            // next();
        }
        //est-ce un node de fin de code, et il correspond au Node entrant ?
        //TOOD replace this tagcontent with object.name ?
        else if (end.equals(this.mainNode.getName() + "/")) {
           // next();
            System.out.println("ClosingEndTag");

            return mainTag();
        }
        //il a trouvé la balise, mais ce n'est pas le code qui l'a ouvert
        else {
            //TODO vérifier les erreurs en trouvant les balises différentes : EXCEPTION
            //if()
            cursor(this.setPos(position));
            return style();
        }
        return mainTag();
    }

    private Node style() {
        debug();
        String end = getTagIfOpentag();
        if (tagList.contains(end) || end.contains(";") || end.contains("#")) {
            this.style=end;
            next();
        }

        return text();
    }

    private Node styleEnd() {
        debug();
        // this.findOpenTag();
        String end = this.findTagContent();
        if (end.equals("it/") || end.contains("title/") || end.equals("bd/") || end.equals("ul/") || end.equals("img/") || end.equals("link/")) {
            //node.add(new DocumentationNode("test",end));
            this.style=null;
            next();
            return mainTagEnd(); //mainTagEnd(node);
        }
        return text();
        //return text();
    }

    private Node text () {
        // this.findOpenTag();
        // String end = this.findTagContent();
        debug();
        StringBuilder content=new StringBuilder();
        while (!token.equals(this.getOpenTag())) {
            content.append(token);
            next();
        }
        //TODO gérer les instances de code et doc nodes si important
        System.out.println(this.mainNode instanceof CodeNode);
        if(this.style!=null) {
            mainNode.add(new DocumentationNode(content.toString(),style));
            return styleEnd();
        }
        mainNode.add(new DocumentationNode(content.toString(),style));
        return mainTagEnd();
    }


}

