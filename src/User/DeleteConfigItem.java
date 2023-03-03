package User;

import Util.Config;
import Util.LPCFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DeleteConfigItem extends JMenuItem {
    private String configname;

    private File configfile;
    private Graphic fenetre;

    public DeleteConfigItem(Graphic fenetre,String configname){
        super();
        this.configname=configname;
        this.fenetre=fenetre;
        this.setText(configname.substring(0,configname.length()-7));
        this.configfile=new File(LPCFile.getConfigDirectory().getPath()+"\\"+configname);
        System.out.println("configname =" +configname);
        this.addActionListener(e -> {
                int choix=JOptionPane.showConfirmDialog(null,"Voulez vous vraiment supprimer cette config?","Confirmation",JOptionPane.YES_NO_OPTION);
                if (choix==0) {
                    if(configfile.delete())
                        JOptionPane.showMessageDialog(null,"Config supprimée avec succés");
                    else
                        JOptionPane.showMessageDialog(null,"Echec de la suppression de la config");
                }
                fenetre.getConfigsel().readconfignames();
                fenetre.getConfigdelete().readconfignames();
            });
        this.setVisible(true);
    }
}
