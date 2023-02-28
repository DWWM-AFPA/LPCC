package User;

import Export.*;
import Util.Config;
import Util.LPCFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.InputMismatchException;

public class Graphic {
    private  Compilator compil;

    private  Config config;


    private JFrame frame;

    private CreateConfig create;

    private JPanel panelcompile;

    private ConfigSelection configsel;

    private UpdatePanel updatePanel;

    private ConfigUpdate configUpdate;

    private ConfigDeletionList configdelete;

    //getters and setters


    public ConfigDeletionList getConfigdelete() {
        return configdelete;
    }

    public void setConfigdelete(ConfigDeletionList configdelete) {
        this.configdelete = configdelete;
    }

    public ConfigUpdate getConfigUpdate() {
        return configUpdate;
    }

    public void setConfigUpdate(ConfigUpdate configUpdate) {
        this.configUpdate = configUpdate;
    }

    public UpdatePanel getUpdatePanel() {
        return updatePanel;
    }

    public void setUpdatePanel(UpdatePanel updatePanel) {
        this.updatePanel = updatePanel;
    }

    public void setCreate(CreateConfig create) {
        this.create = create;
    }

    public ConfigSelection getConfigsel() {
        return configsel;
    }

    public void setConfigsel(ConfigSelection configsel) {
        this.configsel = configsel;
    }

    public JPanel getCreate() {
        return create;
    }


    public JPanel getPanelcompile() {
        return panelcompile;
    }

    public void setPanelcompile(JPanel panelcompile) {
        this.panelcompile = panelcompile;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public  Compilator getCompil() {
        return compil;
    }

    public  void setCompil(Compilator compil) {
        this.compil = compil;
    }

    public  Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public  void draw() {
        int frameWidth=500;
        int frameHeight=200;
        compil=null;
        config=null;
        JPanel panel=new JPanel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("LPCC");
        frame.setLocation(screenSize.width/2-frameWidth/2,screenSize.height/2-frameHeight/2);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panelcompile = new JPanel();
        updatePanel=new UpdatePanel(this);

        JTextArea path= new JTextArea(Paths.get("").toAbsolutePath().toString());
        panel.setBackground(Color.lightGray);

        JButton choose = new JButton("Choose");
        choose.addActionListener(e -> Graphic.chooseDirectory(ChooserType.OPEN));

        JButton compile = new JButton("Compile");
        compile.addActionListener(e -> {
            if(config==null) {
                try {
                    config = new Config();
                    File destination=new File(config.getDestinationfolder());
                    LPCFile.setOutputDirectory(destination);
                    File target=LPCFile.getMainFile(config);
                    compil=new Compilator(target);
                } catch (FileException | IOException ex) {
                    //TODO FATIH
                    JOptionPane.showInputDialog(null,"Le fichier spécifié semble introuvable","File error",JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                try {
                    File destination=new File(config.getDestinationfolder());
                    LPCFile.setOutputDirectory(destination);
                    File target=LPCFile.getMainFile(config);
                    compil = new Compilator(target);
                } catch (FileException ex) {
                    JOptionPane.showInputDialog(null,"Le fichier spécifié semble introuvable","File error",JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    //TODO fatih gere l'exception
                    JOptionPane.showInputDialog(null,"Le fichier spécifié semble introuvable","File error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton exportCode = new JButton("Export Code");

        exportCode.addActionListener(e -> {
            if(compil!=null) {
                boolean success=false;
                String s=new String();
                while (!success) {
                    try {
                        s = JOptionPane.showInputDialog("Entrer l'extension de votre language(java,ect..)");
                        success = true;
                    } catch (InputMismatchException eim) {
                        JOptionPane.showMessageDialog(null,"Rentrer un nom d'extension !");
                    }
                }
                compil.accept(new Code(s));
            }
        });

        JButton exportHTMLDoc = new JButton("Export HTML Doc");
        exportHTMLDoc.addActionListener(e -> {
            if(compil!=null) {
                try {
                    compil.accept(new HTMLDoc(config.getHtmlTag(),compil.getSource().getName()));
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null,"Erreur lors de la lecture de la config","Config Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton exportLatEXDoc = new JButton("Export LateX documentation");
        exportLatEXDoc.addActionListener(e -> {
            if(compil!=null) {
                try {
                    compil.accept(new LateXDoc(config.getLatexTag(),compil.getSource().getName()));
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null,"Erreur lors de la lecture de la config","Config Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JMenu menu;
        JMenuItem newconfig;
        JMenuBar menubar = new JMenuBar();
        // Créer le menu
        menu = new JMenu("Menu");
        // Créer les sous menu
        configsel = new ConfigSelection(this);
        configUpdate=new ConfigUpdate(this);
        configdelete=new ConfigDeletionList(this);
        // Créer les éléments du menu et sous menu
        newconfig = new JMenuItem("Create Config");
        create=new CreateConfig(this);

        newconfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelcompile.setVisible(false);
                updatePanel.setVisible(false);
                create.setVisible(true);
                frame.pack();
                frame.revalidate();
                frame.repaint();
            }
        });
        // Ajouter les éléments au menu
        menu.add(newconfig);
        menu.add(configsel);
        menu.add(configUpdate);
        menu.add(configdelete);
        // Ajouter le menu au barre de menu
        menubar.add(menu);
        // Ajouter la barre de menu au frame
        frame.setJMenuBar(menubar);
        frame.setLayout(new FlowLayout());
        path.setLocation(500,500);
        panelcompile.add(path);
        frame.setContentPane(panel);
        panel.add(panelcompile);
        panel.add(updatePanel);
        panel.add(create);
        panelcompile.add(choose);
        panelcompile.add(exportCode);
        panelcompile.add(exportLatEXDoc) ;
        panelcompile.add(compile);
        panelcompile.add(exportHTMLDoc);
        panelcompile.setVisible(true);
        frame.pack();
        frame.setVisible(true);
    }
    public enum ChooserType {SAVE,OPEN}


    public static File chooseDirectory(ChooserType chooseButtonType){
        File outputName=new File("LPCC");
        String outputDir= LPCFile.desktopPath+"\\Projet\\LPCC";
        JFileChooser choose=new JFileChooser(outputDir);
        choose.setBackground(Color.BLUE);
        choose.setDialogTitle("Choix fichier LPCC :");
        choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int buttonResult = -1;
        switch (chooseButtonType) {
            case SAVE -> {
                buttonResult = choose.showSaveDialog(null);
            }
            case OPEN -> {
                buttonResult = choose.showOpenDialog(null);
                if(buttonResult == JFileChooser.APPROVE_OPTION) {
                    LPCFile.setInputDirectory(choose.getSelectedFile());
                }
            }
        }
        return choose.getSelectedFile();
    }

}
