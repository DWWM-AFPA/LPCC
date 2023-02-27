package User;

import Util.LPCFile;

import javax.swing.*;
import java.io.File;

public class ConfigDeletionList extends JMenu {
    private Graphic fenetre;

    public ConfigDeletionList(Graphic fenetre){
        super();
        this.fenetre=fenetre;
        this.setText("Supprimer une config");
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
            if(!fullname.equals("default.config"))
                this.add(new DeleteConfigItem(this.fenetre,fullname));
        }
    }
}
