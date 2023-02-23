package Export;

import java.io.*;
import java.util.*;

public class Compilator {
    protected String openTag;
    protected String closeTag;
    protected int pos=0;
    public Node mainNode;
    //TODO Etudier le stack pour permettre d'imbriquer les nodes à la lecture
    public Stack<Node> containedNode=new Stack<>();
    public ArrayList<String> style=new ArrayList<>();
    public ArrayList<String> text=new ArrayList<>();
    protected StringBuilder content=new StringBuilder();
    protected static ArrayList<String> tagListClose = new ArrayList<>(List.of("it/","bd/","ul/"));
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
    public  void debug(boolean debug){
        this.setDebug(debug);
        if (debug) {
            try {
            File debugFile = new File((File)null,"debug.txt");
                out=new FileOutputStream(debugFile);
                in=new FileInputStream(debugFile);
            } catch (Exception e) {
                System.err.println("debugFile exception");
            }
        }
    }

    public  void debug() {
        if (this.getDebug()) {
            String expr = Thread.currentThread().getStackTrace()[2].getMethodName();
            String ls = System.lineSeparator();
            String dash = "-".repeat(26);
            String out = String.format("%s loc : %s %s token : %s    pos : %s %s remaining : %s%s%S",ls,expr,ls+dash+ls, getToken(),getPos(), ls+dash+ls,this.getDoc().substring(getPos()),ls+dash, ls);
            try {
          //      byte[] tableauDevant= in.readAllBytes();
          //      byte[] tableauDerriere= out.getBytes();
              //  byte[] tableauCombine= new byte[tableauDevant.length+tableauDerriere.length];

             //   System.arraycopy(tableauDevant, 0, tableauCombine, 0, tableauDevant.length);
             //   System.arraycopy(tableauDerriere, 0, tableauCombine, tableauDevant.length, tableauDerriere.length);

                this.out.write(out.getBytes());
            } catch (IOException ioe) {
                System.err.println(out);
            }
        }
    }

    protected String cursor(int pos) {
        String  retour = String.valueOf(doc.charAt(getPos()));
        retour = !retour.equals(" ") ? String.valueOf(doc.charAt(getPos())):next();

        return setToken(retour.toLowerCase());
    }
    public String next(){
        if (this.getPos()+1<this.getDoc().length())
            this.pos++;

        //retourne this.token
        return this.cursor(this.getPos());
    }
    public String getTagIfOpentag(){
        if (this.getToken().equals(this.getOpenTag())) {
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
    protected String reset(int pos){
        this.pos=pos;
        return this.cursor(this.getPos());
    }

    protected boolean endOfString(){
        return this.getPos()+1==this.getDoc().length();
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
     * En sortant de la fonction, le compilateur se trouve après le tag fermant, sauf au dernier caractère.
     *
     * @return le nom du tag sans les char ouvrants et fermants
     */
    private String findTagContent() {
        StringBuilder retour=new StringBuilder();
        if (this.getToken().equals(this.getOpenTag())) {
            while (!Objects.equals(next(), this.getCloseTag())) {
                if (!this.getToken().equals(this.getCloseTag()))
                    retour.append(getToken());
            }
            next();
            return retour.toString().trim();
        }
        System.err.println("erreur sur la fonction findTagContent !!!!!!!!!");
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

    FileOutputStream out;
    FileInputStream in;
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
        int savePos=this.getPos();
        String mainTag = getTagIfOpentag();
        if (mainTag.charAt(mainTag.length()-1)=='/') {
            reset(savePos);
            return mainTagEnd();
        }
        if (mainTag.equals("user")){
            mainNode =new DocumentationNode(mainTag);
            return style();//new DocumentationNode();
        } else if (mainTag.equals("dev")) {
            mainNode =new DocumentationNode(mainTag);
            return style();//new DocumentationNode();
        }
        //TODO check next ligne to create codeNodes
        else if (!mainTag.equals(">")&& mainNode==null) {
            // tagContent =mainTag;
            //TODO take the hashmap into account
            HashMap<String, Node> map= Node.getNodeRegistry();
            if (!Node.getNodeRegistry().containsKey(mainTag))
                mainNode =new CodeNode(mainTag);
            else
                mainNode=map.get(mainTag);
            int save = this.getPos();
            return text();//new CodeNode();
            //si c'est un code, pas un style, et que ça n'est pas le tag principal
        } else if (!mainTag.equals(this.getCloseTag())&&!tagListClose.contains(mainTag)&&mainNode!=null&&!tagList.contains(mainTag)&&!tagListClose.contains(mainTag)) {
            //si on a pas de noeud imbriqué
            if (containedNode==null) {
                HashMap<String, Node> map = Node.getNodeRegistry();
                if (!Node.getNodeRegistry().containsKey(mainTag))
                    containedNode.add(new CodeNode(mainTag));
                else
                    containedNode.add(map.get(mainTag));
                //le dernier ajouté est toujours le dernier, donc stocke celui qu'on vient d'ajouter
                mainNode.add(containedNode.get(containedNode.size()-1));
                return text();
                //si on a un noeud imbriqué
            } else {
                //boolean ne fermant pas le NodeContenu
                boolean closingOpenendTag =mainTag.contains((containedNode.get(containedNode.size()-1).getName()));
                if (!closingOpenendTag) {
                    HashMap<String, Node> map = Node.getNodeRegistry();
                    CodeNode node;
                    if (!Node.getNodeRegistry().containsKey(mainTag))
                        node = new CodeNode(mainTag);
                    else
                        node = (CodeNode) map.get(mainTag);
                    containedNode.add(node);
                    return mainTagEnd();
                } else  {
                    reset(savePos);
                    return mainTagEnd();
                }
            }
        } else if (!endOfString()) {
            System.out.println("continue !!!!!!!!!!!!");
            this.reset(savePos);
            return style();
        }
        else if (endOfString()) {
            System.out.println("stop !!!!!!!!!!!!");
            return null;
        }
        return null;
    }

    private Node mainTagEnd() {
         debug();
        int savePos=this.getPos();
        String end = getTagIfOpentag();
        if (end.equals("user/")||end.equals("dev/")) {
            System.out.println(end + "fin");
            mainNode=null;
        }
        //est-ce un node de fin de code, et il correspond au Node entrant ?
        //TOOD replace this tagcontent with object.name ?
        else if (end.equals(this.mainNode.getName() + "/")) {
            System.out.println(end + "fin");
            if (containedNode!=null){
                //on doit ajouter le codeNode au code node et ajouter le texte aussi au bon endroit
                //-------------------------------------
                mainNode.add(new CodeNode("",text.get(text.size()-1)));
                containedNode=null;
                //-------------------------------------

            }
            mainNode=null;
            return mainTag();
        }   else if (containedNode!=null && end.equals(this.containedNode.get(containedNode.size()-1).getName() + "/")) {
            containedNode.add(new CodeNode("txt",content.toString()));
            containedNode=null;
            return mainTag();
        }
        //il a trouvé la balise, mais ce n'est pas le code qui l'a ouvert
        else {
            //TODO vérifier les erreurs en trouvant les balises différentes : EXCEPTION
            //if()
            cursor(this.setPos(savePos));
            if (end.charAt(end.length()-1)=='/') {
                reset(savePos);
                return styleEnd();
            }
            return style();
        }
        return mainTag();
    }

    private Node style() {
        debug();
        int savePos=this.getPos();
        String end = getTagIfOpentag();
        if (tagList.contains(end) || end.contains(";") || end.contains("#") )
            this.style.add(end);
        else
          this.reset(savePos);


        return text();
    }

    private Node styleEnd() {
        debug();
        int savePos=this.getPos();
        String end = getTagIfOpentag();
        if (end.equals("it/") || end.contains("title/") || end.equals("bd/") || end.equals("ul/") || end.equals("img/") || end.equals("link/")) {
            //node.add(new DocumentationNode("test",end));
            this.style=null;

            return mainTagEnd(); //mainTagEnd(node);
        }
        this.reset(savePos);
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
       // System.out.println(this.mainNode instanceof CodeNode);
        if (containedNode!=null){
            text.add(content.toString());
            return mainTag();
        }
        mainNode.add(new DocumentationNode(content.toString(),style.get(style.size()-1)));
        return mainTag();
    }


}

