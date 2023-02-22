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

public class Graphic {
    private  Compilator compil;

    private  Config config;


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
        JFrame frame = new JFrame("LPCC");
        frame.setLocation(screenSize.width/2-frameWidth/2,screenSize.height/2-frameHeight/2);
        frame.setSize(frameWidth,frameHeight);

        JPanel panelcompile = new JPanel();

        JTextArea path= new JTextArea(Paths.get("").toAbsolutePath().toString());
        panel.setBackground(Color.lightGray);

        JButton choose = new JButton("Choose");
        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graphic.chooseDirectory(ChooserType.OPEN);
            }
        });

        JButton compile = new JButton("Compile");
        compile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(config==null) {
                    try {
                        config = new Config();
                        File destination=new File(config.getDestinationfolder());
                        System.out.println("destination path "+destination.getPath());
                        LPCFile.setOutputDirectory(destination);
                        File target=LPCFile.getMainFile(config);
                        compil=new Compilator(target);
                    } catch (FileException | IOException ex) {
                        //TODO FATIH
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    try {
                        config.setDestinationfolder(LPCFile.getInputDirectory().getPath());
                        File target=LPCFile.getMainFile(config);
                        compil = new Compilator(target);
                    } catch (FileException ex) {
                        //TODO fatih gere l'exception
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        //TODO fatih gere l'exception
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        JButton exportCode = new JButton("Export Code");

        exportCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(compil!=null)
                    compil.accept(new Code());
            }
        });

        JButton exportHTMLDoc = new JButton("Export HTML Doc");
        exportHTMLDoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(compil!=null)
                    compil.accept(new HTMLDoc());
            }
        });
        JButton exportLatEXDoc = new JButton("Export LateX documentation");
     //   exportUserDoc.setBounds(10,10,10,10);
        exportLatEXDoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(compil!=null) {
                    try {
                        compil.accept(new LateXDoc(config.getLatexTag()));
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        JMenu menu;
        JMenuItem e1,e2;
        JMenuBar menubar = new JMenuBar();
        // Créer le menu
        menu = new JMenu("Menu");
        // Créer le sous menu
        e2 = new ConfigSelection(this);
        // Créer les éléments du menu et sous menu
        e1 = new JMenuItem("Create Config");
        CreateConfig create=new CreateConfig(this);

        e1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelcompile.setVisible(false);
                create.setVisible(true);
            }
        });
        // Ajouter les éléments au menu
        menu.add(e1);
        menu.add(e2);
        // Ajouter le menu au barre de menu
        menubar.add(menu);
        // Ajouter la barre de menu au frame
        frame.setJMenuBar(menubar);
        //frame.setLayout(null);
        path.setLocation(500,500);
        panelcompile.add(path);
        frame.setContentPane(panel);
        panel.add(panelcompile);
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
