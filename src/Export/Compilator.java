package Export;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Compilator {
    protected String openTag;
    protected String closeTag;
    protected int pos=0;
    public Node mainNode;
    //TODO Etudier le stack pour permettre d'imbriquer les nodes à la lecture
    public Stack<Node> containedStackNode =new Stack<>();
    public ArrayList<String> style=new ArrayList<>();
    public String text;//=new ArrayList<>();
    protected StringBuilder content=new StringBuilder();
    protected static ArrayList<String> tagListClose = new ArrayList<>(List.of("it/","bd/","ul/","title1/"));
    protected static ArrayList<String> styleTagList = new ArrayList<>(List.of("it","bd","ul","title1"));
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

    protected String cursor(int pos,boolean keepSpaces) {
        String  retour = String.valueOf(doc.charAt(getPos()));
        retour = !retour.equals(" ") || keepSpaces ? String.valueOf(doc.charAt(getPos())):next();

        return setToken(retour.toLowerCase());
    }
    public String next(){
        if (this.getPos()+1<this.getDoc().length())
            this.pos++;

        //retourne this.token
        return this.cursor(this.getPos(),false);
    }
    public String next(boolean keepSpaces){
        if (this.getPos()+1<this.getDoc().length())
            this.pos++;

        //retourne this.token
        return this.cursor(this.getPos(),keepSpaces);
    }
    public String getTagIfOpentag(){
        if (this.getToken().equals(this.getOpenTag())) {
            return this.findTagContent();
        }

        //retourne this.token
        return null; //this.cursor(this.getPos());
    }
    public String previous(){
        this.pos--;
        return this.cursor(this.getPos(),true);
    }
    protected String reset(){
        this.pos=0;
        return this.cursor(this.getPos(),true);
    }
    protected String reset(int pos){
        this.pos=pos;
        return this.cursor(this.getPos(),true);
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

    /** Check if the parameters has a closing Tag corresponding
     *
     * @param openingTag : Opening string without the delimiters
     * @return a boolean "true" if contains a closingNode. Else 'false'
     */
    private boolean hasCloseTagContent(String openingTag) {
        int savePosition = this.getPos();
        findOpenTag();
        String tag= this.findTagContent();
        //TODO choisir entre les deux : while (!endOfString()){
        while (!tag.equals(this.mainNode.getName()+"/")){
            findOpenTag();
            tag= this.findTagContent();
            if (tag.substring(0,tag.length()-1).equals(openingTag)) {
                reset(savePosition);
                return true;
            }
        }

        reset(savePosition);
        return false;
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
        System.out.println(doc);
        reset();
        return mainTag();
    }
    private Node mainTag() {
        debug();
        int savePos=this.getPos();
        String currentMainTag = getTagIfOpentag();
        if (currentMainTag!=null) {
            if (currentMainTag.equals("user")) {
                mainNode = new DocumentationNode(currentMainTag);
                return style();//new DocumentationNode();
            } else if (currentMainTag.equals("dev")) {
                mainNode = new DocumentationNode(currentMainTag);
                return style();//new DocumentationNode();
            }
            //C'est un pas une dev ou user et ça doit être un CodeNode
            else if (!currentMainTag.equals(">") &&
                    !(currentMainTag.charAt(currentMainTag.length() - 1) == '/') &&
                    !styleTagList.contains(currentMainTag)) {

                //  boolean closedNode = this.hasCloseTagContent(currentMainTag);
                if (mainNode == null) {
                    //TODO bannir le code dans la doc user
                    mainNode = CodeNode.getCodeNodeInstance(currentMainTag);
                    return style();//new CodeNode();
                    // si on a pas encore de noeuds imbriqués
                } else {
                    if (mainNode.getName().equals("user")){
                        JOptionPane.showMessageDialog(null,
                                "Attention vous avez inseré du Code en balise dans la documentation utilisateur."
                                        +System.lineSeparator()+
                                "Le programme va tranquillement te ramener vers l'interface graphique pour réparer tes conneries !!");
                        return null;
                        //System.exit(2);
                    }

                    //on ajoute le noeud au noeud parent
                    if (containedStackNode.empty())
                        mainNode.add(CodeNode.getCodeNodeInstance(currentMainTag));
                    else
                        containedStackNode.peek().add(CodeNode.getCodeNodeInstance(currentMainTag));
                    //on empile si il y a une fin
                    if (!this.hasCloseTagContent(currentMainTag))
                        return this.mainTag();
                    containedStackNode.add(CodeNode.getCodeNodeInstance(currentMainTag));
                /*} else {
                    reset(savePos);
                    System.err.println("MainTag CodeNode else, on peut pas penser à tout !!");
                */
                }
                return mainTagEnd();
            } else if (!endOfString()) {
                this.reset(savePos);
                return mainTagEnd();
            } else if (endOfString()) {
                return null;
            } else
                System.err.println("il y a vraiment un soucis dans la méthode mainTag");
        }

        reset(savePos);
        return mainTagEnd();
    }

    private Node mainTagEnd() {
         debug();
        int savePos=this.getPos();
        String end = getTagIfOpentag();
        if (end!=null) {
            String beginEnd=end.substring(0,end.length() - 1);
            boolean isEnd = end.charAt(end.length() - 1) == '/';
            boolean isStyle = styleTagList.contains(beginEnd);
            if (isEnd && !isStyle) {
                if (end.equals("user/") || end.equals("dev/")) {
                    mainNode = null;
                    return mainTag();
                }
                //est-ce un node de fin de code qui correspond au Node entrant principal ?
                else if (end.equals(this.mainNode.getName() + "/")) {
                    //on a un noeud imbriqué ?
                    if (!containedStackNode.empty()) {
                        //on doit ajouter le codeNode au code node et ajouter le texte aussi au bon endroit
                        containedStackNode.pop();
                    } else {
                        System.err.println("MainTagEnd Error ??");

                    }
                    mainNode = null;
                    return mainTag();
                    //il y a forcément un noeud imbriqué, ferme t'il le premier ?
                } else { //end.equals(this.containedStackNode.pop().getName() + "/")) {
                    while (!containedStackNode.empty()&& !Objects.equals(this.containedStackNode.peek().getName() + "/", end))
                        this.containedStackNode.pop();

            //        containedStackNode.add(new CodeNode("txt", content.toString()));
                    containedStackNode.pop();
                    return mainTag();
                }
            }
        }
        reset(savePos);
        return style();
    }

    private Node style() {
        debug();
        int savePos=this.getPos();
        String end = getTagIfOpentag();
        if (end!=null&& (styleTagList.contains(end) || end.contains(";") || end.contains("#")) )
            this.style.add(end);
        else
          this.reset(savePos);


        return text();
    }

    private Node styleEnd() {
        debug();
        int savePos=this.getPos();
        String end = getTagIfOpentag();
        if (end!=null) {
            String beginEnd = end.substring(0, end.length() - 1);
            if (styleTagList.contains(beginEnd) ){ //|| end.contains("title/") || end.equals("bd/") || end.equals("ul/") || end.equals("img/") || end.equals("link/")) {
                this.style = null;
                return mainTagEnd(); //mainTagEnd(node);
            }
        }
        this.reset(savePos);
        return mainTag();
        //return text();
    }

    private Node text () {
        // this.findOpenTag();
        // String end = this.findTagContent();
        debug();
        StringBuilder content=new StringBuilder();

        while (!token.equals(this.getOpenTag())) {
            content.append(token);
            next(true);
        }
        //TODO gérer les instances de code et doc nodes en fonction de la pile de node
       // System.out.println(this.mainNode instanceof CodeNode);
        if (style!=null&&!content.toString().equals(""))
            if (!containedStackNode.empty()){
                containedStackNode.peek().add(new DocumentationNode(content.toString(),style));
            } else
                mainNode.add(new DocumentationNode(content.toString(),style));

        return styleEnd();
    }


}

