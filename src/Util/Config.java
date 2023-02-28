package Util;

import java.io.IOException;
import java.util.HashMap;

public class Config {
    protected static Config currentConfig;
    protected String name;
    protected static String mainInputFileName;
    protected HashMap<String,String> HTMLBindings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Config(String name) {
        this.setCurrentConfig(this);
        this.setName(name);
        this.HTMLBindings=new HashMap<>();
        this.HTMLBindings.put("title1","h1");
        this.HTMLBindings.put("it","i");
        this.HTMLBindings.put("bold","b");
       // this.HTMLBindings.put("titre1","h1");
    }



    public void setCurrentConfig(Config currentConfig) {
        this.currentConfig = currentConfig;
    }

    public static void setMainInputFileName(String mainInputFileName) {
        Config.mainInputFileName = mainInputFileName;
    }

    public static String getMainInputFileName() {
        return mainInputFileName;
    }
    public static Config getCurrentConfig() {
        return Config.currentConfig;
    }

    public HashMap<String, String> getHTMLBindings() {
        return HTMLBindings;
    }

    public void setHTMLBindings(HashMap<String, String> HTMLBindings) {
        this.HTMLBindings = HTMLBindings;
    }

    /*    public void loadConfig() throws FileException, IOException {
                Scanner scan = new Scanner(this.LPCFile.read());

                while (scan.hasNext()) {
                    String key = scan.next();
                    String value = scan.next();
                    System.out.println("Key : "+key+" Value : "+value);
                }

            }*/
    public void createConfig() throws IOException {
     //   this.LPCFile.create("config"+System.lineSeparator()+"test");
    }

}
