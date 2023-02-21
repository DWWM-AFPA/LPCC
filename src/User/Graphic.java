package User;

import Export.*;
import jdk.jshell.SourceCodeAnalysis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class Graphic {
    private static Compilator compil;

    private static Config config;


    public static void draw() {
        int frameWidth=500;
        int frameHeight=200;
        compil=null;
        config=null;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("LPCC");
        frame.setLocation(screenSize.width/2-frameWidth/2,screenSize.height/2-frameHeight/2);
        frame.setSize(frameWidth,frameHeight);

        JPanel panel = new JPanel();

        JTextArea path= new JTextArea(Paths.get("").toAbsolutePath().toString());
        panel.setBackground(Color.lightGray);

        JButton choose = new JButton("Choose");
        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Graphic.chooseDirectory(ChooserType.OPEN);
            }
        });

        JButton compile = new JButton("Valide Selection");
        compile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(target!=null) {
                    /*try {
                        LPCFile.getMainFile();
                    } catch (FileException | IOException ex) {
                        //TODO FATIH
                        throw new RuntimeException(ex);
                    }
                    */
                    try {
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
      //  exportDevDoc.setBounds(10,10,10,10);
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

        JMenu menu, smenu;
        JMenuItem e1, e2, e3, e4, e5, e6;
        JMenuBar menubar = new JMenuBar();
        // Créer le menu
        menu = new JMenu("Menu");
        // Créer le sous menu
        smenu = new JMenu("Load Config");
        // Créer les éléments du menu et sous menu
        e1 = new JMenuItem("Create Config");
        // Ajouter les éléments au menu
        menu.add(e1);
        menu.add(smenu);
        // Ajouter le menu au barre de menu
        menubar.add(menu);
        // Ajouter la barre de menu au frame
        frame.setJMenuBar(menubar);

        //frame.setLayout(null);

        path.setLocation(500,500);
        panel.add(path);
        frame.add(panel);
        panel.add(choose);
        panel.add(exportCode);
        panel.add(exportLatEXDoc) ;
        panel.add(compile);
        panel.add(exportHTMLDoc);
        frame.setVisible(true);
    }
    public enum ChooserType {SAVE,OPEN}


    public static File chooseDirectory(ChooserType chooseButtonType){
        File outputName=new File("LPCC");
        String outputDir= LPCFile.desktopPath+"\\Projet\\LPCC";
        JFileChooser choose=new JFileChooser(outputDir    /*.getHomeDirectory()/**/ );
        choose.setBackground(Color.BLUE);
        choose.setDialogTitle("Choix fichier LPCC :");
        choose.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int buttonResult = -1;
        switch (chooseButtonType) {
            case SAVE -> {
                buttonResult = choose.showSaveDialog(null);
            }

            case OPEN -> {
                buttonResult = choose.showOpenDialog(null);
                if(buttonResult == JFileChooser.APPROVE_OPTION) {
                    File file=choose.getSelectedFile();
                    target=file;
                    /*if(file.isFile())
                        file= file.getParentFile();
                    */
                    LPCFile.setInputDirectory(choose.getSelectedFile().getParentFile());
                }
            }
        }
        return choose.getSelectedFile();
    }

}
