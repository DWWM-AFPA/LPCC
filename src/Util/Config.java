package Util;

import java.io.IOException;

public class Config {
    protected String name;
    protected static String mainInputFileName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Config(String name) {
        this.name = name;
    }

    public static void setMainInputFileName(String mainInputFileName) {
        Config.mainInputFileName = mainInputFileName;
    }

    public static String getMainInputFileName() {
        return mainInputFileName;
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
