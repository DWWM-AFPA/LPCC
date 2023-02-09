package User;

import Export.File;
import Export.FileException;

import java.io.IOException;
import java.util.Scanner;

public class Config {
    protected String name;
    protected File file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Config(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public void loadConfig() throws FileException, IOException {
        Scanner scan = new Scanner(this.file.read());

        while (scan.hasNext()) {
            String key = scan.next();
            String value = scan.next();
            System.out.println("Key : "+key+" Value : "+value);
        }

    }
    public void createConfig() throws IOException {
        this.file.create("config"+System.lineSeparator()+"test");
    }
}
