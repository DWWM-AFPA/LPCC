package Util;

import Export.FileException;
import jdk.jshell.spi.ExecutionControl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Config {
    protected static Config currentConfig;
    protected String name;
    protected File inputFolder;
    protected String mainInputFileName;
    protected File outputFolder;
    protected HashMap<String,String> HTMLBindings;

    public Config() {
        setCurrentConfig(this);
        this.setName("default");
        this.HTMLBindings=new HashMap<>();
        try{
        this.loadConfig(LPCFile.getConfigDirectory());
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

    public HashMap<String, String> getHTMLBindings() {
        return HTMLBindings;
    }

    public void setHTMLBindings(HashMap<String, String> HTMLBindings) {
        this.HTMLBindings = HTMLBindings;
    }

    public void loadConfig(File file) throws FileException, IOException {
        Scanner scan = new Scanner(LPCFile.read(file));
        int line =0;
        boolean LPCTag =false;
        while (scan.hasNext()) {
          //  if (!LPCTag) {
                String s = scan.nextLine();
                if (s.equals("") || s.contains("//"))
                    line++;
                else if (s.contains("LPC")) {
                    LPCTag = !LPCTag;
                    String[] tagTypes = s.split(";");
                    for (String stype:tagTypes)
                        System.out.println("types : " +stype);
                }
                else if (LPCTag) {
                    String[] tags = s.split(";");
                    for (String st:tags)
                        System.out.println("tags : " +st.trim());
                    //TODO arrayList compiler à transferer dans la config
                }
                 else {
                    System.out.println("next : " + s);
                    line++;
                }

       //     else

        }

            }

    public void createConfig() throws IOException {
        LPCFile.create(LPCFile.getConfigDirectory(),"DefaultConfig","cfg","");
    }
    public static void createEmptyConfig() throws IOException {
        LPCFile.create(LPCFile.getConfigDirectory(),"DefaultConfig","cfg",
               "Default Configuration.\n" +
                       "//Current Configuration\n" +
                       "\n" +
                       "//language\n" +
                       ".java\n" +
                       "\n" +
                       "//input directory (if null must be in my Documen\n" +
                       "\n" +
                       "//main input file name\n" +
                       "main\n" +
                       "//output directory (if null must be in my Docume\n" +
                       "\n" +
                       "\n" +
                       "\n" +
                       "//Tags Delimiter\n" +
                       "<   >\n" +
                       "//user and developer documentation tags \n" +
                       "user dev\n" +
                       "\n" +
                       "//LPC Tag closer\n" +
                       "/\n" +
                       "\n" +
                       "//LPC Tag position (can be start:\"/it\" or end:\"i\n" +
                       "end\n" +
                       "\n" +
                       "//Tags must be in the end of the file\n" +
                       "LPC    ; HTML                         ;         \n" +
                       "title1 ;     h1                       ;      \n" +
                       "title2 ;     h2                       ;      \n" +
                       "title3 ;     h3                       ;      \n" +
                       "title4 ;     h4                       ;      \n" +
                       "title5 ;     h5                       ;      \n" +
                       "title6 ;     h6                       ;      \n" +
                       "it     ;     i                        ;      \n" +
                       "bd     ;     b                        ;      \n" +
                       "ul     ;     u                        ;      \n" +
                       "color  ;     #                        ;      \n" +
                       "image  ; <img src=\"dinosaur.jpg\">     ;         "
        );
    }

}
