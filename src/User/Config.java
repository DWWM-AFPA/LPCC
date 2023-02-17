package User;

import Export.LPCFile;
import Export.FileException;

import java.io.IOException;
import java.util.Scanner;

public class Config {
    protected String name;
    protected LPCFile LPCFile;
    protected static String mainInputFile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LPCFile getFile() {
        return LPCFile;
    }

    public void setFile(LPCFile LPCFile) {
        this.LPCFile = LPCFile;
    }

    public Config(String name, LPCFile LPCFile) {
        this.name = name;
        this.LPCFile = LPCFile;
    }

    public static String getMainInputFile() {
        return mainInputFile;
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
