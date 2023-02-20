package User;

import Export.Compilator;
import Export.FileException;
import Export.LPCFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Graphic {
    public static void draw() {
        int frameWidth=500;
        int frameHeight=200;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("LPCC");
        frame.setLocation(screenSize.width/2-frameWidth/2,screenSize.height/2-frameHeight/2);
        frame.setSize(frameWidth,frameHeight);

        JPanel panel = new JPanel();

        JTextArea path= new JTextArea(Paths.get("").toAbsolutePath().toString());
        panel.setBackground(Color.lightGray);

        JButton choose = new JButton("Choose");
        // compile.setBounds(50,50, 10,10);
        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    LPCFile.setInputDirectory(Graphic.chooseDirectory(ChooserType.OPEN));
            }
        });

        JButton compile = new JButton("Compile");
        compile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Compilation lancée");
            //    Compilator.compile();
                try {
                    System.out.println(LPCFile.getMainFile());
                } catch (FileException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            //    Compilator compile = new Compilator();
            }
        });
       // compile.setBounds(50,50, 10,10);

        JButton exportCode = new JButton("Export Code");
       // exportCode.setBounds(10,10,10,10);
        exportCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Export code lancé");
                //    Documentation.visit();
            }
        });

        JButton exportDevDoc = new JButton("Export Dev Doc");
      //  exportDevDoc.setBounds(10,10,10,10);
        exportDevDoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Export dev doc lancé");
                //    Documentation.visit();
            }
        });
        JButton exportUserDoc = new JButton("Export User Doc");
     //   exportUserDoc.setBounds(10,10,10,10);
        exportUserDoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Export user doc lancé");
                //    Documentation.visit();
            }
        });

        JMenu menu, smenu;
        JMenuItem e1, e2, e3, e4, e5, e6;
        JMenuBar menubar = new JMenuBar();
        // Créer le menu
        menu = new JMenu("Menu");
        // Créer le sous menu
        smenu = new JMenu("Sous Menu");
        // Créer les éléments du menu et sous menu
        e1 = new JMenuItem("Element 1");
        e2 = new JMenuItem("Element 2");
        e3 = new JMenuItem("Element 3");
        e4 = new JMenuItem("Element 4");
        e5 = new JMenuItem("Element 5");
        e6 = new JMenuItem("Element 6");
        // Ajouter les éléments au menu
        menu.add(e1);
        menu.add(e2);
        menu.add(e3);
        // Ajouter les éléments au sous menu
        smenu.add(e4);
        smenu.add(e5);
        smenu.add(e6);
        // Ajouter le sous menu au menu principale
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
        panel.add(compile);
        panel.add(exportCode);
        panel.add(exportUserDoc) ;
        panel.add(exportDevDoc);
        frame.setVisible(true);
    }
    public enum ChooserType {SAVE,OPEN}
    public static File chooseDirectory(ChooserType chooseButtonType){
        File outputName=new File("LPCC");
        String outputDir= LPCFile.desktopPath+"\\Projet\\LPCC";
        JFileChooser choose=new JFileChooser(outputDir    /*.getHomeDirectory()/**/ );
        choose.setBackground(Color.BLUE);
        choose.setDialogTitle("Choix fichier LPCC :");
        choose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int buttonResult = -1;
        switch (chooseButtonType) {
            case SAVE -> {
                buttonResult = choose.showSaveDialog(null);
            }

            case OPEN -> {
                buttonResult = choose.showOpenDialog(null);
                if(buttonResult == JFileChooser.APPROVE_OPTION) {
                    File file=choose.getSelectedFile();
                    if(file.isFile())
                        file= file.getParentFile();

                    LPCFile.setInputDirectory(choose.getSelectedFile());
                }
            }
        }
        return choose.getSelectedFile();
    }

}
