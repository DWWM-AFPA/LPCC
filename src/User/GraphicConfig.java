package User;

import Util.Config;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphicConfig extends JFrame implements TableModelListener, TextListener, DocumentListener {
    private Config config;
    private JLabel configName;
    private JTextField nameTextField;
    private JTable table1;
    private JTable table2;
    private JButton ok;

    public static void main(String[] args) {
        new GraphicConfig(Config.getConfigSingleton("matt"),null);    }
    public GraphicConfig(Config config,GraphicMain parent) {

        super("Configuration window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.config=config;
        this.configName = new JLabel("Configuration Name");
        this.nameTextField = new JTextField(config.getName());
      //  this.nameTextField.setMinimumSize(new Dimension(20,10));
      //  this.nameTextField.setSize(new Dimension(20,10));
        this.ok = new JButton("ok");





        String[] columns = new String[] {"language","input Directory","main Input File name","output directory","left tags delimiter","right tags delimiter","user tag","dev tag","lpc tag closer","lpc tag position","escaping string"};
        String[][] data = new String[][] {
                {config.getLanguage(),
                config.getInputFolder().getPath(),
                config.getMainInputFileName(),
                config.getOutputFolder().getPath(),
                config.getTagsDelimiter()[0],
                config.getTagsDelimiter()[1],
                config.getUserTag(),
                config.getDevTag(),
                config.getTagCloser()[0],
                config.getTagCloser()[1],
                config.getEscapingString()}
        };


        table1=new JTable(data,columns);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        ArrayList<String > tagList = config.getStyleTagList();

        String[] columnsTblExport = new String[] {"LPC","HTML","LATEX"};
        String[][] tags = new String[tagList.size()][3];
        for (int i = 0; i < tagList.size(); i++) {
            tags[i][0]=tagList.get(i);
            tags[i][1]=config.getHTMLBindings().get(tagList.get(i));
            tags[i][2]=config.getHTMLBindings().get(tagList.get(i));
        }


        table2=new JTable(tags,columnsTblExport);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3,1));
        p.add(this.configName);
        p.add(this.nameTextField);

        getContentPane().setLayout(new BorderLayout());

        JScrollPane tbl = new JScrollPane(this.table1);

        JScrollPane tbl2 = new JScrollPane(this.table2);
        tbl.createHorizontalScrollBar();



        this.setLayout(new GridBagLayout());

        // Crée un objet de contraintes
        GridBagConstraints c = new GridBagConstraints();
     //   c.insets = new Insets(10,10,10,10);

        // colonne 0
        c.gridx = 0;
        // ligne 0
        c.gridy = 0;
      //  c.gridwidth=1;
        // augmente la largeur des composants de 10 pixels
        c.ipadx = 60;
        // augmente la hauteur des composants de 50 pixels
        c.ipady = 0;

        getContentPane().add(p,c);

        // ligne 1
        c.gridy = 1;
        c.gridwidth=6;

        // augmente la largeur des composants de 90 pixels
        c.ipadx = 800;

        // augmente la hauteur des composants de 40 pixels
        c.ipady = 0;

        getContentPane().add(tbl,c);



        // ligne 2
        c.gridy = 2;

        // augmente la largeur des composants de 20 pixels
        c.ipadx = 800;

        // augmente la hauteur des composants de 20 pixels
        c.ipady = 0;

        // Ajouter les contraintes
        getContentPane().add(tbl2,c);
        c.gridx=0;
        c.gridwidth=3;
        c.weightx=3;
        c.gridy+=1;
        c.ipadx=50;
        getContentPane().add(ok,c);

        // Création d'un objet de "WindowAdapter"
        WindowListener winAdap = new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                // quitter le système
                System.exit(0);
            }
        };

        // ajouter le listener "windowlistener "
        addWindowListener(winAdap);

        pack();
       // setSize(400, 250);
        setVisible(true);
        nameTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setName(nameTextField.getText());
                System.out.println(config.getName());
                pack();
            }
        });
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    config.updateConfig();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                parent.updateChooseBox();
                dispose();
            }
        });

        table1.getModel().addTableModelListener(this);
        table2.getModel().addTableModelListener(this);
nameTextField.getDocument().addDocumentListener(this);



    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        String data = (String) model.getValueAt(row, column);
        //...// Do something with the data...
        if (model==table1.getModel()) {
            switch (column) {
                case 0 -> this.config.setLanguage(data);
                case 1 -> this.config.setInputFolder(new File(data));
                case 2 -> this.config.setMainInputFileName(data);
                case 3 -> this.config.setOutputFolder(new File(data));
                case 4 -> this.config.getTagsDelimiter()[0] = data;
                case 5 -> this.config.getTagsDelimiter()[1] = data;
                case 6 -> this.config.setUserTag(data);
                case 7 -> this.config.setDevTag(data);
                case 8 -> this.config.getTagCloser()[0] = data;
                case 9 -> this.config.getTagCloser()[1] = data;
                case 10 -> this.config.setEscapingString(data);
            }
        }
            else if (model==table2.getModel()) {
            ArrayList<String> styleList= this.config.getStyleTagList();
            HashMap<String, String> htmlMap = this.config.getHTMLBindings();
            HashMap<String, String> latexMap = this.config.getLatexBindings();
            switch (column) {
                case 0 -> {
                    String rm = styleList.get(row);
                    htmlMap.remove(rm);
                    htmlMap.put(data, (String) model.getValueAt(row, 1));
                    latexMap.put(data, (String) model.getValueAt(row, 2));
                    latexMap.remove(rm);
                    styleList.remove(row);
                    styleList.add(row, data);
                }
                case 1 -> htmlMap.replace((String) model.getValueAt(row, 0), data);
                case 2 -> latexMap.replace((String) model.getValueAt(row, 0), data);
            }
        }
    }

    @Override
    public void textValueChanged(TextEvent e) {
        System.out.println(config.getName());
        config.setName(nameTextField.getText());
        System.out.println(config.getName());
        pack();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        config.setName(nameTextField.getText());
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        config.setName(nameTextField.getText());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        config.setName(nameTextField.getText());
    }
}
