package User;

import Util.LPCFile;

import javax.swing.*;
import java.io.File;

public class ConfigUpdate extends JMenu {
    private Graphic fenetre;

    public ConfigUpdate(Graphic fenetre){
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
            if(s.equals("default.config"))
                this.add(new UpdatableConfigItem(this.fenetre,fullname.substring(0,fullname.length()-7)));
        }
    }
}
