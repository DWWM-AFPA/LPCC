package Util;

import Export.FileException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Config {
    protected static Config currentConfig;
    protected String name;
    protected String language;
    protected File inputFolder;
    protected String mainInputFileName;
    protected File outputFolder;
    protected String[] tagsDelimiter;
    protected String userTag;
    protected String devTag;
    protected String[] tagCloser;
    protected String escapingString;
    protected HashMap<String,String> HTMLBindings = new HashMap<>();
    protected HashMap<String,String> LatexBindings = new HashMap<>();
    protected ArrayList<String> styleTagList = new ArrayList<>();

    public Config() {
        setCurrentConfig(this);
        this.setName("default");
        this.HTMLBindings=new HashMap<>();
        try{
        this.loadConfig("DefaultConfig");
        }
        catch (IOException io){
            System.err.println("config file problem");
            System.exit(-1);
        }
        catch (FileException fe){
            System.err.println("File Exception problem");
        }

    }    public Config(String name) {
        setCurrentConfig(this);
        this.setName(name);
        this.HTMLBindings=new HashMap<>();
        this.HTMLBindings.put("title1","h1");
        this.HTMLBindings.put("it","i");
        this.HTMLBindings.put("bold","b");
        // this.HTMLBindings.put("titre1","h1");
    }

    public static Config getCurrentConfig() {
        if (currentConfig==null)
            try{
            Config.loadConfig("DefaultConfig"); }
        catch (IOException| FileException exs) {
            System.err.println("config non existante, création d'une config par défaut");
            try{ Config.createEmptyConfig();
            } catch (IOException ignored) {
        }}
        if (currentConfig==null)
            System.err.println("errrrrrreur de config putain de merde");
        return currentConfig;
    }

    public static void setCurrentConfig(Config currentConfig) {
        Config.currentConfig = currentConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public File getInputFolder() {
        return inputFolder;
    }

    public void setInputFolder(File inputFolder) {
        this.inputFolder = inputFolder;
    }

    public String getMainInputFileName() {
        return mainInputFileName;
    }

    public void setMainInputFileName(String mainInputFileName) {
        this.mainInputFileName = mainInputFileName;
    }

    public File getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(File outputFolder) {
        this.outputFolder = outputFolder;
    }

    public String[] getTagsDelimiter() {
        return tagsDelimiter;
    }

    public void setTagsDelimiter(String[] tagsDelimiter) {
        this.tagsDelimiter = tagsDelimiter;
    }

    public String getUserTag() {
        return userTag;
    }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }

    public String getDevTag() {
        return devTag;
    }

    public void setDevTag(String devTag) {
        this.devTag = devTag;
    }

    public String[] getTagCloser() {
        return tagCloser;
    }

    public void setTagCloser(String[] tagCloser) {
        this.tagCloser = tagCloser;
    }

    public String getEscapingString() {
        return escapingString;
    }

    public void setEscapingString(String escapingString) {
        this.escapingString = escapingString;
    }

    public HashMap<String, String> getHTMLBindings() {
        return HTMLBindings;
    }

    public void setHTMLBindings(HashMap<String, String> HTMLBindings) {
        this.HTMLBindings = HTMLBindings;
    }

    public HashMap<String, String> getLatexBindings() {
        return LatexBindings;
    }

    public void setLatexBindings(HashMap<String, String> latexBindings) {
        LatexBindings = latexBindings;
    }

    public ArrayList<String> getStyleTagList() {
        return styleTagList;
    }

    public void setStyleTagList(ArrayList<String> styleTagList) {
        this.styleTagList = styleTagList;
    }

    public static Config loadConfig(String fileConfigName) throws FileException, IOException {
        Config currentConfig = new Config(fileConfigName);
        Scanner scan = new Scanner(LPCFile.read(new File(LPCFile.getConfigDirectory().getPath()+"\\"+fileConfigName+".cfg")));
        int line =0;
        boolean LPCTag =false;
        while (scan.hasNext()) {
                String s = scan.nextLine().trim();
                line++;
                if (s.contains("LPC")&&!s.contains("//")) {
                    LPCTag = !LPCTag;
                   // String[] tagTypes = s.split(";");
                    //for (String stype:tagTypes)
                    //    System.out.print("types         : " +stype);
                }
                else if (LPCTag&&!s.contains("//")) {
                    String[] tags = s.split(";");
                    for (String st:tags){
                        currentConfig.getStyleTagList().add(tags[0].trim());
                        currentConfig.getHTMLBindings().put(tags[0].trim(),tags[1].trim());
                        currentConfig.getLatexBindings().put(tags[0].trim(),tags[2].trim());
                    //TODO arrayList compiler à transferer dans la config
                    }
                }
                else {
                     switch(line){
                         //Current Configuration else if higher
                         case 2:
                             if(fileConfigName.equals("DefaultConfig")&&!s.equals("DefaultConfig")) {
                                 System.out.println("defaut chargement config :"+ s);
                                 return loadConfig(s);
                             }
                             break;
                             //language
                         case 5:
                            currentConfig.setLanguage(s);
                             break;
                            //inputDir
                         case 8:
                            currentConfig.setInputFolder(new File(s));
                             break;
                            //main Input File name
                         case 11:
                             currentConfig.setMainInputFileName(s);
                             break;
                             //output Dir
                         case 14:
                             currentConfig.setOutputFolder(new File(s));
                             break;
                             //tags delimiters
                         case 17:
                             currentConfig.setTagsDelimiter(s.split(";"));
                             break;
                             //user and dev tags
                         case 20:
                             String[] tags = s.split(";");
                             currentConfig.setUserTag(tags[0]);
                             currentConfig.setDevTag(tags[1]);
                             break;
                             //tag closer +
                         case 23:
                            currentConfig.setTagCloser(s.split(";"));
                            break;
                            //tag closer +
                         case 26:
                            currentConfig.setEscapingString(s);
                            break;
                     }

                }
        }
        return currentConfig;
        }



    public void createConfig() throws IOException {
        LPCFile.create(LPCFile.getConfigDirectory(),"DefaultConfig","cfg","");
    }
    public static void createEmptyConfig() throws IOException {
        LPCFile.create(LPCFile.getConfigDirectory(),"DefaultConfig","cfg",
               "//Current Configuration\n" +
                       "DefaultConfig\n" +
                       "\n" +
                       "//language\n" +
                       ".java\n" +
                       "\n" +
                       "//input directory (if null must be in my Documents)\n" +
                       "path\n" +
                       "\n" +
                       "//main input file name\n" +
                       "main\n" +
                       "\n" +
                       "//output directory (if null must be in my Docume\n" +
                       "\n" +
                       "\n" +
                       "//Tags Delimiter\n" +
                       "<  ; >\n" +
                       "\n" +
                       "//user and developer documentation tags \n" +
                       "user ; dev\n" +
                       "\n" +
                       "//LPC Tag closer + LPC Tag position (can be start:\"/it\" or end:\"it/\")\n" +
                       "/ ; end\n" +
                       "\n" +
                       "//LPC character escaping\n" +
                       "//\n" +
                       "\n" +
                       "//Tags must be in the end of the file\n" +
                       "LPC    ;           HTML                      ;      LATEX\n" +
                       "title1 ;            h1                       ;      \\title{ }\n" +
                       "title2 ;            h2                       ;      \\chapter{chapter}\n" +
                       "title3 ;            h3                       ;      \\section{section}\n" +
                       "title4 ;            h4                       ;      \\subsection{subsection}\n" +
                       "title5 ;            h5                       ;      \\subsubsection{subsubsection}\n" +
                       "title6 ;            h6                       ;      \\paragraph{paragraph}\n" +
                       "//title 7 ;       notInHTML                  ;      \\subparagraph{subparagraph}\n" +
                       "it     ;            i                        ;      \\emph{accident}\n" +
                       "//or \\textit for italic\n" +
                       "bd     ;            b                        ;      \\textbf{greatest} \n" +
                       "ul     ;            u                        ;      \\underline{science} \n" +
                       "color  ;            #                        ;      \\color{blue}\n" +
                       "image  ;        <img src=\"dinosaur.jpg\">     ;      \\includegraphics{universe}"
        );
    }

    public void UpdateConfig() throws IOException {
        StringBuilder config = new StringBuilder();
        String ln = System.lineSeparator();
        config.append("//Current Configuration").append(ln)
            .append(Config.getCurrentConfig().getName())
            .append(ln).append(ln)
            .append("//language").append(ln)
            .append(this.getLanguage())
            .append(ln).append(ln)
            .append("//input directory (if null must be in my Documents)").append(ln)
            .append(this.getInputFolder().getPath())
            .append(ln).append(ln)
            .append("//main input file name").append(ln)
            .append(this.getMainInputFileName())
            .append(ln).append(ln)
            .append("//output directory (if null must be in my Documents)").append(ln)
            .append(this.getOutputFolder())
            .append(ln).append(ln)
            .append("//Tags Delimiter").append(ln)
            .append(this.getTagsDelimiter()[0]).append(';').append(this.getTagsDelimiter()[1])
            .append(ln).append(ln)
            .append("//user and developer documentation tags ").append(ln)
            .append(this.getUserTag()).append(';').append(this.getDevTag())
            .append(ln).append(ln)
            .append("//LPC Tag closer + LPC Tag position (can be start:\"/it\" or end:\"it/\")" ).append(ln)
            .append(this.getTagCloser()[0]).append(';').append(this.getTagCloser()[1])
            .append(ln).append(ln)
            .append("//LPC character escaping" ).append(ln)
            .append(this.getEscapingString())
            .append(ln).append(ln)
            .append("//Tags must be in the end of the file").append(ln);

        HashMap<String, String> htmlMap=this.getHTMLBindings();
        HashMap<String, String> latexMap=this.getHTMLBindings();

        for (String style :this.getStyleTagList())
            config.append(style)
                    .append(';')
                    .append(htmlMap.get(style))
                    .append(';')
                    .append(latexMap.get(style))
                    .append(System.lineSeparator());

        LPCFile.create(LPCFile.getConfigDirectory(),this.getName(),"cfg",config.toString());
    }


}
