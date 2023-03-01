package Export;

import Util.Config;
import Util.LPCFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class Compilator implements Visitable{
    protected String openTag;
    protected String closeTag;
    protected  int pos=0;
    protected String token;
    protected String doc;

    protected File source;

    protected String chapteropen;

    protected String chapterclose;

    protected String codeopen;

    protected String codeclose;
    protected ArrayList<String> args;

    protected String excapch;

    private ArrayList<String> specialchar;

    private ArrayList<Node> compiledfile;



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

    public String getExcapch() {
        return excapch;
    }

    public String getChapterclose() {
        return chapterclose;
    }

    public String getChapteropen() {
        return chapteropen;
    }

    public File getSource() {
        return source;
    }

    //setters


    public void setSource(File source) {
        this.source = source;
    }

    public void setChapteropen(String chapteropen) {
        this.chapteropen = chapteropen;
    }

    public void setChapterclose(String chapterclose) {
        this.chapterclose = chapterclose;
    }

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

    public void setExcapch(String excapch) {
        this.excapch = excapch;
    }

    public void setCompiledfile(ArrayList<Node> compiledfile) {
        this.compiledfile = compiledfile;
    }

    //builders
    public Compilator() {
        this.setOpenTag("<");
        this.setCloseTag(">");
        this.setCodeclose("$");
        this.setCodeopen("$");
        this.setExcapch("\\");
        this.setChapteropen("¤");
        this.setChapterclose("¤");
        try {
            this.setSource(LPCFile.getMainFile(new Config("default.config")));
        } catch (FileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.setDoc(LPCFile.read(source));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (FileException e) {
            throw new RuntimeException(e);
        }
        this.compiledfile=new ArrayList<>();
        this.setArgs(new ArrayList<>());
        this.specialchar=new ArrayList<>();
        this.specialchar.addAll(List.of(this.codeopen
                ,this.closeTag
                ,this.openTag
                ,this.excapch
                ,this.closeTag
                ,this.chapterclose
                ,this.chapteropen
                ,"/"));
    }
    public Compilator(File source) throws FileException, IOException {
        this.setOpenTag("<");
        this.setCloseTag(">");
        this.setCodeclose("$");
        this.setCodeopen("$");
        this.setExcapch("\\");
        this.setChapteropen("¤");
        this.setChapterclose("¤");
        this.setSource(source);
        this.setDoc(LPCFile.read(source));
        this.compiledfile=new ArrayList<>();
        this.setArgs(new ArrayList<>());
        this.specialchar=new ArrayList<>();
        this.specialchar.addAll(List.of(this.codeopen
                ,this.closeTag
                ,this.openTag
                ,this.excapch
                ,this.closeTag
                ,this.chapterclose
                ,this.chapteropen
                ,"/"));
    }
    public Compilator(String openTag, String closeTag) {
        this.setCloseTag(closeTag);
        this.setOpenTag(openTag);
        this.compiledfile=new ArrayList<>();
    }
    public Compilator(String openTag, String closeTag,String textToCompile) {
        this.setCloseTag(closeTag);
        this.setOpenTag(openTag);
        this.setDoc(textToCompile);
        this.compiledfile=new ArrayList<>();
    }

    //debug

    private int countLines(){
        String[] lines = doc.substring(0,pos).split("\r\n|\r|\n");
        return  lines.length;
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
    protected String reset() throws LPCSyntaxException {
        this.pos=0;
        this.cursor();
        this.compiledfile=new ArrayList<>();
        while (!(Objects.equals(this.getToken(), openTag) || Objects.equals(this.getToken(), codeopen))&&pos<doc.length()-1 ) {
            this.next();
        }
        if (pos>=doc.length()-1)
            throw new LPCSyntaxException("Aucune balise detectée");
        this.cursor();
        return this.getToken();
    }

    public ArrayList<Node> compile() throws LPCSyntaxException {
        this.reset();
        cursor();
        while (pos<doc.length()-1) {
            if(Objects.equals(this.getToken(), codeopen)) {
                this.getCodeNode();
            }
            else {
                this.getDocNode();
            }
        }
        if(!args.isEmpty()) {
            System.out.println("erreur ici?");
            throw new LPCSyntaxException("Erreur sur les balises dans le fichier " + source.getName() + " a la ligne " + countLines() +
                    " a proximité de " + "\"" + doc.substring(Math.max(0, pos - 10), Math.min(doc.length(), pos + 10)) + "\"");
        }
        return this.compiledfile;
    }

    //gestion du caractere d'echappement
    private void excape(){
        if(Objects.equals(this.getToken(), this.excapch))
            if(this.specialchar.contains(this.next())) {
                this.getToken();
            }
            else{
                this.previous();
            }
    }

    //récupére l'argument contenu dans une balise

    private String getarg(){
        String arg="";
        while(!Objects.equals(this.getToken(), closeTag)){
            this.excape();
            arg=arg+getToken();
            this.next();
        }
        this.next();
        System.out.println("arg= "+arg);
        return arg;
    }

    //fonction qui recupére un code node

    private void getCodeNode(){
        String name=this.getName();
        String code="";
        int posrel=0;
        Hashtable<Integer,String> callcode=new Hashtable<>();
        while(!Objects.equals(this.token, "/")&&pos<doc.length()-1){
            this.excape();
            if(Objects.equals(this.token, codeopen)){
                String callname=this.getName();
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
        if(callcode.isEmpty()) {
            compiledfile.add(new CodeNode(code, null, name, ".java"));
            return ;
        }
        compiledfile.add(new CodeNode(code,callcode,name,".java"));
    }

    private String getName(){
        String name="";
        while (!Objects.equals(this.next(),codeclose)){
            this.excape();
            name=name+this.getToken();
        }
        this.next();
        return name;
    }

    // Methode qui extrait un doc node (independament de dev/user)

    private void getDocNode() throws LPCSyntaxException {
        String txt="";
        //on determine ici les effets de styles qui s'appliques au docnode
        while(Objects.equals(this.token,openTag)){
            this.excape();
            this.next();
            if(Objects.equals(this.getToken(), "/")) {
                this.next();
                if(!this.args.remove(getarg()))
                    throw new LPCSyntaxException("Erreur sur les balises dans le fichier " + source.getName() + "a la ligne " + countLines() + "a proximité de "
                            + doc.substring(Math.max(0, pos - 10), Math.min(doc.length(), pos + 10)));
            }
            else {
                this.args.add(getarg());
            }
        }
        if(this.getToken().equals(chapteropen)) {
            String chaptername = this.getChapterName();
            File chaptersource=new File(LPCFile.getInputDirectory()+"\\"+chaptername);
            Compilator chapter= null;
            try {
                chapter = new Compilator(chaptersource);
                System.out.println("je défini le chapter");
            } catch (FileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                JOptionPane.showMessageDialog(null,"Une erreur inconnue s'est produite, veuillez redemarrer l'application");
            }
            compiledfile.addAll(chapter.compile());
            return;
        }
        //on recupere le texte en mode docdev
        if(args.contains("dev")) {
            this.devdoc();
            return ;
        }
        //on gere ici le cas ou a la sortie des balises on passe directement a un noeud de code pour eviter de renvoyer des doc nodes dont le texte est vide
        if(Objects.equals(token, codeopen))
            return ;
        //on recupere le texte du docnode
        while (!Objects.equals(this.getToken(),this.getOpenTag())&&!Objects.equals(this.getToken(),codeopen)
                &&!Objects.equals(this.getToken(),chapteropen)&&pos<doc.length()-1){
            this.excape();
            txt=txt+this.getToken();
            this.next();
            System.out.println("texte= "+txt);
            System.out.println("pos= "+this.getPos());
            System.out.println("token= "+this.getToken());
        }
        System.out.println(this.getArgs());
        if(!args.contains("diag")) {
            ArrayList<String> argnode = new ArrayList<>(this.getArgs());
            compiledfile.add(new DocumentationNode(false, txt, argnode));
        }
        else {
            DocumentationNode retour=new DocumentationNode(false,txt,new ArrayList<>());
            retour.setDiagram(true);
            compiledfile.add(retour);
        }
    }


    private String getChapterName(){
        StringBuilder retour=new StringBuilder();
        next();
        while (!this.getToken().equals(chapterclose)) {
            this.excape();
            retour.append(this.getToken());
            next();
        }
        retour.append(".lpc");
        next();
        return retour.toString();
        }


    private void devdoc() throws LPCSyntaxException {
        String txt="";
        int posdeb=this.getPos();
        int posfin=this.getPos();
        while (!Objects.equals(this.getToken(), openTag)&&pos<doc.length()-1){
            this.excape();
            if(Objects.equals(this.getToken(), codeopen)&&!Objects.equals(this.getToken(),chapteropen)){
                posdeb=this.getPos();
                this.getCodeNode();
                posfin=this.getPos();
                txt+=this.getDoc().substring(posdeb,posfin);
            }
            else {
                txt+=this.getToken();
                this.next();
            }
        }

        if(!args.contains("diag")) {
            ArrayList<String> argnode = new ArrayList<>(this.getArgs());
            compiledfile.add(new DocumentationNode(true, txt, argnode));
        }
        else {
            DocumentationNode retour=new DocumentationNode(false,txt,new ArrayList<>());
            retour.setDiagram(true);
            compiledfile.add(retour);
        }
    }

    @Override
    public void accept(Visitor v) {
        try {
            this.compile();
            v.visit(this.compiledfile);
            JOptionPane.showMessageDialog(null,"Fichiers générés avec succés !");
        }
        catch (LPCSyntaxException e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}

