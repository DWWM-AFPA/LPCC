package Export;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class File {
    protected String path;
    protected String name;
    protected boolean hasBeenRead;

    public File(String path) {
        this.setPath(path);
        this.setHasBeenRead(false);
    }
    public File(String path, boolean hasBeenRead) {
        this.setPath(path);
        this.setHasBeenRead(hasBeenRead);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(boolean hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }

    public String read() throws IOException {
    FileReader file= new FileReader(this.getPath());
    int i;
    StringBuilder retour = new StringBuilder();
    while((i= file.read()) != -1)
        retour.append((char)i);
    return retour.toString();
    }
    //System.out.println("Working Directory = " + System.getProperty("user.dir"));

}
