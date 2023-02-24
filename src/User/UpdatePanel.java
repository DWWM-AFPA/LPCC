package User;

import Util.Config;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;

public class UpdatePanel extends JPanel {

    private Config config;

    private Graphic fenetre;

    private Hashtable<String,String> latextags;

    private Hashtable<String,String> htmltags;

    private String newdirectorypath;

    private JButton changresultdirectorypath;

    private JButton addtag;

    private JButton validirectory;

    private JButton defintag;

    private JLabel infos;

    private JTextField nomconfig;

    private JTextField actualpath;

    private JTextField newpath;

    private JPanel name;

    private JPanel tag;

    private JButton validmain;

    private String entrypoint;

    private JTextField main;

    private JTextField newmain;

    private JTable tagdisplayer;

    private String newmainname;
    private JLabel mainlabel;

    private JLabel newmainlabel;

    private JButton annuler;

    private JButton annuler1;

    private JButton annuler2;

    private JButton goback;

    private JButton goback1;

    private Hashtable<Integer,Integer> filepos;

    private JPanel pathdealer;

    private JPanel maindealer;

    private JPanel tagdealer;

    //builder
    public UpdatePanel(Graphic fenetre){
        //on initialise
        this.fenetre=fenetre;
        latextags=new Hashtable<>();
        htmltags=new Hashtable<>();
        pathdealer=new JPanel();
        maindealer=new JPanel();
        tagdealer=new JPanel();

        //on cree les elements graphiques de pathdealer

        actualpath=new JTextField(10);
        actualpath.setVisible(true);
        actualpath.setEditable(false);
        actualpath.setRequestFocusEnabled(false);

        changresultdirectorypath=new JButton("Change");
        changresultdirectorypath.setVisible(true);
        changresultdirectorypath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser choose=new JFileChooser();
                int buttonResult;
                File f=null;
                choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                buttonResult = choose.showOpenDialog(null);
                if(buttonResult == JFileChooser.APPROVE_OPTION)
                    f = choose.getSelectedFile();
                System.out.println("path=" +f.getPath());
                newpath.setText(f.getPath());
                newpath.setColumns(newpath.getText().length());
                revalidate();
                repaint();
                fenetre.getFrame().pack();
            }
        });

        newpath=new JTextField(10);
        newpath.setEditable(false);
        newpath.setRequestFocusEnabled(false);
        newpath.setVisible(true);

        validirectory =new JButton("Valider changement directorie");
        validirectory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!newpath.getText().isEmpty()) {
                    newdirectorypath = newpath.getText();
                }
                pathdealer.setVisible(false);
                maindealer.setVisible(true);
                fenetre.getFrame().pack();
            }
        });
        validirectory.setVisible(true);

        annuler=new JButton("Annuler");
        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                fenetre.getPanelcompile().setVisible(true);
                reset();
                fenetre.getFrame().revalidate();
                fenetre.getFrame().repaint();
                fenetre.getFrame().pack();
            }
        });

        //on cree les élements de maindealer

        mainlabel=new JLabel("Old entry point name");

        main=new JTextField();
        main.setEditable(false);
        main.setRequestFocusEnabled(false);

        newmainlabel=new JLabel("New entry point name");

        newmain=new JTextField(10);


        validmain=new JButton("Valider changement");
        validmain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!newmain.getText().isEmpty())
                    newmainname=newmain.getText();
                maindealer.setVisible(false);
                tagdealer.setVisible(true);
            }
        });

        annuler1=new JButton("Annuler");
        annuler1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                fenetre.getUpdatePanel().setVisible(false);
                fenetre.getPanelcompile().setVisible(true);
                fenetre.getFrame().pack();
                fenetre.getFrame().revalidate();
                fenetre.getFrame().repaint();
            }
        });

        goback=new JButton("Etape precedente");
        goback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pathdealer.setVisible(true);
                maindealer.setVisible(false);
            }
        });

        //on construit tag dealer

        tagdisplayer=new JTable(new String[3][] ,new String[] {"lpctag","latextag","htmltag"});
        tagdisplayer.addColumn(new TableColumn());


        maindealer.add(mainlabel);
        maindealer.add(main);
        maindealer.add(newmainlabel);
        maindealer.add(newmain);
        maindealer.add(validmain);
        maindealer.add(goback);
        maindealer.add(annuler1);
        maindealer.setVisible(false);



        pathdealer.add(actualpath);
        pathdealer.add(changresultdirectorypath);
        pathdealer.add(newpath);
        pathdealer.add(validirectory);
        pathdealer.add(annuler);
        this.add(pathdealer);
        this.add(maindealer);
        this.setVisible(false);
    }





    //getters et setters



    public void setConfig(String config) {
        this.config = new Config(config);
        main.setText(this.config.getMainInputFileName().replace(".lpc",""));
        main.setColumns(main.getText().length());
        main.repaint();
        main.revalidate();
        actualpath.setText(this.config.getDestinationfolder());
        actualpath.setColumns(actualpath.getText().length());
        actualpath.revalidate();
        actualpath.repaint();
    }


    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Graphic getFenetre() {
        return fenetre;
    }

    public void setFenetre(Graphic fenetre) {
        this.fenetre = fenetre;
    }

    public JTextField getNewpath() {
        return newpath;
    }

    public void setNewpath(JTextField newpath) {
        this.newpath = newpath;
    }

    public JTextField getMain() {
        return main;
    }

    public void setMain(JTextField main) {
        this.main = main;
    }

    public JTextField getNewmain() {
        return newmain;
    }

    public void setNewmain(JTextField newmain) {
        this.newmain = newmain;
    }

    public JButton getAnnuler2() {
        return annuler2;
    }

    public void setAnnuler2(JButton annuler2) {
        this.annuler2 = annuler2;
    }

    public JButton getGoback() {
        return goback;
    }

    public void setGoback(JButton goback) {
        this.goback = goback;
    }

    public JButton getGoback1() {
        return goback1;
    }

    public void setGoback1(JButton goback1) {
        this.goback1 = goback1;
    }

    public Hashtable<Integer, Integer> getFilepos() {
        return filepos;
    }

    public void setFilepos(Hashtable<Integer, Integer> filepos) {
        this.filepos = filepos;
    }

    public JPanel getPathdealer() {
        return pathdealer;
    }

    public void setPathdealer(JPanel pathdealer) {
        this.pathdealer = pathdealer;
    }

    public JPanel getMaindealer() {
        return maindealer;
    }

    public void setMaindealer(JPanel maindealer) {
        this.maindealer = maindealer;
    }

    public JPanel getTagdealer() {
        return tagdealer;
    }

    public void setTagdealer(JPanel tagdealer) {
        this.tagdealer = tagdealer;
    }

    public Hashtable<String, String> getLatextags() {
        return latextags;
    }

    public void setLatextags(Hashtable<String, String> latextags) {
        this.latextags = latextags;
    }

    public Hashtable<String, String> getHtmltags() {
        return htmltags;
    }

    public void setHtmltags(Hashtable<String, String> htmltags) {
        this.htmltags = htmltags;
    }

    public String getNewdirectorypath() {
        return newdirectorypath;
    }

    public void setNewdirectorypath(String newdirectorypath) {
        this.newdirectorypath = newdirectorypath;
    }

    public JButton getChangresultdirectorypath() {
        return changresultdirectorypath;
    }

    public void setChangresultdirectorypath(JButton changresultdirectorypath) {
        this.changresultdirectorypath = changresultdirectorypath;
    }

    public JButton getAddtag() {
        return addtag;
    }

    public void setAddtag(JButton addtag) {
        this.addtag = addtag;
    }

    public JButton getValidirectory() {
        return validirectory;
    }

    public void setValidirectory(JButton validirectory) {
        this.validirectory = validirectory;
    }

    public JButton getDefintag() {
        return defintag;
    }

    public void setDefintag(JButton defintag) {
        this.defintag = defintag;
    }

    public JLabel getInfos() {
        return infos;
    }

    public void setInfos(JLabel infos) {
        this.infos = infos;
    }

    public JTextField getNomconfig() {
        return nomconfig;
    }

    public void setNomconfig(JTextField nomconfig) {
        this.nomconfig = nomconfig;
    }

    public JTextField getActualpath() {
        return actualpath;
    }

    public void setActualpath(JTextField actualpath) {
        this.actualpath = actualpath;
    }

    public void setName(JPanel name) {
        this.name = name;
    }

    public JPanel getTag() {
        return tag;
    }

    public void setTag(JPanel tag) {
        this.tag = tag;
    }

    public JButton getValidmain() {
        return validmain;
    }

    public void setValidmain(JButton validmain) {
        this.validmain = validmain;
    }

    public String getEntrypoint() {
        return entrypoint;
    }

    public void setEntrypoint(String entrypoint) {
        this.entrypoint = entrypoint;
    }

    public JLabel getMainlabel() {
        return mainlabel;
    }

    public void setMainlabel(JLabel mainlabel) {
        this.mainlabel = mainlabel;
    }

    public JButton getAnnuler() {
        return annuler;
    }

    public void setAnnuler(JButton annuler) {
        this.annuler = annuler;
    }

    public JButton getAnnuler1() {
        return annuler1;
    }

    public void setAnnuler1(JButton annuler1) {
        this.annuler1 = annuler1;
    }

    //methodes

    private void reset(){
        this.newpath.setText("");
        this.newmain.setText("");
        this.pathdealer.setVisible(true);
        this.maindealer.setVisible(false);
        this.tagdealer.setVisible(false);
    }

}
