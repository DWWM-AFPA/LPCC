package User;

import Util.Config;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigItem extends JMenuItem {
    private String configname;

    private Graphic fenetre;

    public ConfigItem(Graphic fenetre,String configname){
        super();
        this.configname=configname;
        this.fenetre=fenetre;
        this.setText(configname);
        this.addActionListener(e -> fenetre.setConfig(new Config(configname)));
        this.setVisible(true);
    }
}
