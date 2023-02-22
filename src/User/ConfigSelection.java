package User;

import Util.LPCFile;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class ConfigSelection extends JMenu {
    private Graphic fenetre;

    public ConfigSelection(Graphic fenetre){
        super();
        this.fenetre=fenetre;
        this.setText("Load Config");
        readconfignames();
    }

    public Graphic getFenetre() {
        return fenetre;
    }

    public void setFenetre(Graphic fenetre) {
        this.fenetre = fenetre;
    }

    public void readconfignames(){
        this.removeAll();
        for (File s : LPCFile.getConfigDirectory().listFiles()) {
            String fullname=s.getName();
            this.add(new ConfigItem(this.fenetre,fullname.substring(0,fullname.length()-7)));
        }
        }
}
