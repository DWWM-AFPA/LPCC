package User;

import Export.Compiler;
import Export.FileException;
import Export.HTMLExportVisitor;
import Export.LATEXExportVisitor;
import Util.Config;
import Util.LPCFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GraphicMain extends JFrame {
    public static void main(String[] args) {


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Literate Programing Code Compiler");

        GraphicMain graphicMain = new GraphicMain();
        frame.setContentPane(graphicMain.panel);
        frame.setJMenuBar(graphicMain.menubar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocation(screenSize.width/2-frame.getWidth()/2,screenSize.height/2-frame.getHeight()/2);
        frame.setVisible(true);
    }
    private JMenu menu;
    private JMenu smenu;
    private JMenuBar menubar = new JMenuBar();
    private JMenuItem open;
    private JMenuItem createConfig;
    private JMenuItem readConfig;
    private JMenuItem updateConfig;
    private JMenuItem deleteConfig;

    private JButton chooseFileButton;
    private JCheckBox userCheckBox;
    private JCheckBox developerCheckBox;
    private JCheckBox codeCheckBox;
    private JButton exportButton;
    private JButton compileButton;
    private JComboBox configComboBox;
    private JButton loadConfigButton;
    private JPanel panel;
    private JProgressBar progressBar1;
    private JCheckBox HTMLCheckBox;
    private JCheckBox latexCheckBox;
    private JTable table1;
    private GraphicMain me;


    public GraphicMain() {

        super("Literate Programing Code Compiler");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        me=this;
        setContentPane(this.panel);
        this.setJMenuBar(this.menubar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocation(screenSize.width/2-this.getWidth()/2,screenSize.height/2-this.getHeight()/2);

        updateChooseBox();
        Config.setCurrentConfig((String) configComboBox.getSelectedItem());

        // Créer le menu
        this.menu = new JMenu("Menu");
        // Créer le sous menu
        this.smenu = new JMenu("Configuration");
        // Créer les éléments du menu et sous menu
        this.open = new JMenuItem("Open");
        this.createConfig = new JMenuItem("Create configuration");
        this.readConfig = new JMenuItem("Read configuration");
        this.updateConfig = new JMenuItem("Update configuration");
        this.deleteConfig = new JMenuItem("Delete configuration");

        // Ajouter les éléments au menu
        this.menu.add(open);
        // Ajouter les éléments au sous menu
        this.smenu.add(createConfig);
        this.smenu.add(readConfig);
        this.smenu.add(updateConfig);
        this.smenu.add(deleteConfig);
        // Ajouter le sous menu au menu principale
        menu.add(smenu);
        // Ajouter le menu au barre de menu
        menubar.add(menu);
        // Ajouter la barre de menu au frame
    //    frame.setJMenuBar(menubar);
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LPCFile.getMainFile();
                } catch (FileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LPCFile.getMainFile();
                } catch (FileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        createConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Config config =Config.getConfigSingleton("");
                new GraphicConfig(config,me);
                configComboBox.addItem("nouvelle Config");

            }
        });
        readConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GraphicConfig(Config.getConfigSingleton(Config.getCurrentConfig().getName()),me);
            }
        });
        updateConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GraphicConfig(Config.getConfigSingleton(Config.getCurrentConfig().getName()),me);
            }
        });
        deleteConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = (String) configComboBox.getSelectedItem();
                if (Config.getCurrentConfig().delete(name)) {
                    JOptionPane.showMessageDialog(panel,"Fichier de configuration "+ name +" supprimé.");
                }
                configComboBox.removeItem(name);

            }
        });
        configComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = (String) configComboBox.getSelectedItem();
                if (name!=null){
                Config.getConfigSingleton(name);
             //   JOptionPane.showMessageDialog(null,"Configuration de "+ name +" chargée.");
                    }
              //  updateChooseBox();

                //Config.getCurrentConfig().setInputFolder(Graphic.chooseDirectory(Graphic.ChooserType.OPEN));
            }
        });
       /* configComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                System.out.println("\n");
                System.out.println((state == ItemEvent.SELECTED) ? "Selected" : "Deselected");
                System.out.println("Item: " + e.getItem());
                ItemSelectable is = e.getItemSelectable();
                System.out.println(", Selected: " + selectedString(is));
            }
            static private String selectedString(ItemSelectable is) {
                Object selected[] = is.getSelectedObjects();
                return ((selected.length == 0) ? "null" : (String) selected[0]);
            }
        });*/

        compileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar1.setValue(5);


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
                        case (JOptionPane.CANCEL_OPTION):
                    }
                }

                progressBar1.setValue(100);
                new Compiler(mainFileContent).compile();
                progressBar1.setValue(0);

            }
        });
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (HTMLCheckBox.isSelected()){
                    HTMLExportVisitor exportVisitor = new HTMLExportVisitor();
                }
                if (latexCheckBox.isSelected()){
                     LATEXExportVisitor latexExportVisitor = new LATEXExportVisitor();
                }
               /* if (developerCheckBox.isSelected()){

                }*/

            }
        });

        pack();
        loadConfigButton.setVisible(false);
        setVisible(true);

    }

    public JComboBox getConfigComboBox() {
        return configComboBox;
    }

    public void updateChooseBox() {
    File[] fileList = LPCFile.getConfigDirectory().listFiles();
    assert fileList != null;
    configComboBox.removeAllItems();
    for (File config : fileList) {
        if (!config.getName().equals("DefaultConfig.cfg")) {
            configComboBox.addItem(config.getName().split("\\.")[0]);
        }
    }
}

    private enum ChooserType {SAVE,OPEN}
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

