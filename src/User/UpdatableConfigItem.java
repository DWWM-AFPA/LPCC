package User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatableConfigItem extends JMenuItem {
    private String configname;

    private Graphic fenetre;

    public UpdatableConfigItem(Graphic fenetre,String configname) {
        super();
        this.configname = configname;
        this.fenetre = fenetre;
        this.setText(configname);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fenetre.getUpdatePanel().setConfig(configname);
                fenetre.getUpdatePanel().setVisible(true);
                fenetre.getCreate().setVisible(false);
                fenetre.getPanelcompile().setVisible(false);
                fenetre.getFrame().repaint();
                fenetre.getFrame().revalidate();
                fenetre.getFrame().pack();
            }
        });

    }

}
