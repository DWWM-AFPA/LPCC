package User;

import Export.Compiler;
import Export.FileException;
import Util.Config;
import Util.LPCFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GraphicMain {
    public static void main(String[] args) {


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Literate Programing Code Compiler");



        frame.setContentPane(new GraphicMain().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocation(screenSize.width/2-frame.getWidth()/2,screenSize.height/2-frame.getHeight()/2);
        frame.setVisible(true);
    }

    private JButton chooseFileButton;
    private JCheckBox userCheckBox;
    private JCheckBox developerCheckBox;
    private JCheckBox codeCheckBox;
    private JButton exportButton;
    private JButton compileButton;
    private JComboBox configComboBox;
    private JButton loadConfigButton;
    private JPanel panel;


    public GraphicMain() {

        for (File config:LPCFile.getConfigDirectory().listFiles()) {
            if (!config.getName().equals("DefaultConfig.cfg")) {
                configComboBox.addItem(config.getName().split("\\.")[0]);
            }
        }
        loadConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = (String) configComboBox.getSelectedItem();
                try{
                Config.loadConfig(name);
                JOptionPane.showMessageDialog(null,"Configuration de "+ name +" chargée.");
                }
                catch (IOException | FileException io){

                }
                //Config.getCurrentConfig().setInputFolder(Graphic.chooseDirectory(Graphic.ChooserType.OPEN));
            }
        });
        configComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        compileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Config config = Config.getCurrentConfig();
                String mainFileContent = null;
                File mainFile=null;
                try {
                    mainFile = LPCFile.getMainFile();
                     mainFileContent = LPCFile.read(mainFile);
                } catch (IOException io)  {
                    throw new RuntimeException(io);
                } catch (FileException fe)  {
                    int i = JOptionPane.showConfirmDialog(null,
                            "attention ce fichier a déjà été compilé.\n" +
                                    "Voulez vous le recompiler ?");
                    switch (i){
                        case (JOptionPane.OK_OPTION):
                            LPCFile.getAlreadyReadFile().remove(mainFile);
                            try {
                                mainFileContent = LPCFile.read(mainFile);
                            } catch (IOException|FileException ignored){}
                            break;
                    }
                }

                new Compiler(mainFileContent).compile();
            }
        });
    }

    public static File chooseDirectory(Graphic.ChooserType chooseButtonType){
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
                }
            }
        }
        return choose.getSelectedFile();
    }

    public void setData(GraphicMain data) {
    }

    public void getData(GraphicMain data) {
    }

    public boolean isModified(GraphicMain data) {
        return false;
    }
}
