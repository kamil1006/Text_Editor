package editor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextEditor extends JFrame {

    String TITLE="Text editor";
    JTextArea jTextArea;
    String PLIK="doc.txt";
    JTextField jTextField;
    JTextField txtSearch;

    JFileChooser fileChooser;
    File plik=null;
    JCheckBox checkBox;
    boolean czyRegex=false;
   // int[] indeksy;
   ArrayList<Integer> indeksy;
    ArrayList<Integer> indeksyKonca;

   int idBiezacy=0,idPoprzedni=0,idNastepny=0,idPozycja;

    String textZrodlowy="";
    String tekstSzukany="";


    //-------------------------------------------------------------------------------------------------------------
    public TextEditor() {
        fileChooser= new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());//.getDefaultDirectory());
        fileChooser.setName("FileChooser");
        add(fileChooser);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setTitle(TITLE);
       // setLocationRelativeTo(null);
        //setLayout(new GridBagLayout());
        setLayout(new BorderLayout());
        //-----------------------------------------------------
        init();
        //dodajAkcje();

        //-----------------------------------------------------
        setVisible(true);
       // setLayout(null);

    }
    //-------------------------------------------------------------------------------------------------------------
    public void init(){
        JPanel jPanel=new JPanel();
        jPanel.setLayout(new FlowLayout(SwingConstants.LEADING, 10, 10));

        //-----------------------------------------------------

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem loadMenuItem = new JMenuItem("Load");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        loadMenuItem.setName("MenuLoad");
        openMenuItem.setName("MenuOpen");
        saveMenuItem.setName("MenuSave");
        exitMenuItem.setName("MenuExit");

        loadMenuItem.addActionListener(e->otworz());
        openMenuItem.addActionListener(e->wybierzPlik());
       // saveMenuItem.addActionListener(e->zapisz());
        saveMenuItem.addActionListener(e->zapiszPlik());
        exitMenuItem.addActionListener(e->{

            dispose();
            System.exit(0);

        });

        fileMenu.add(loadMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        //-----------------------------------------------------
        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        searchMenu.setMnemonic(KeyEvent.VK_S);

        JMenuItem startSearchItem = new JMenuItem("Start search");
        JMenuItem previousMatchItem = new JMenuItem("Previous search");
        JMenuItem nextMatchItem = new JMenuItem("Next match");
        JMenuItem useRegexItem = new JMenuItem("Use regular expressions");

        startSearchItem.setName("MenuStartSearch");
        previousMatchItem.setName("MenuPreviousMatch");
        nextMatchItem.setName("MenuNextMatch");
        useRegexItem.setName("MenuUseRegExp");

        // dodac listenery
        startSearchItem.addActionListener(e->szukaj());
        nextMatchItem.addActionListener(e->nastepny());
        previousMatchItem.addActionListener(e->poprzedni());
        useRegexItem.addActionListener(e->{

            checkBox.setSelected(true);
            czyRegex=true;
        });

        searchMenu.add(startSearchItem);
        searchMenu.add(previousMatchItem);
        searchMenu.add(nextMatchItem);

        searchMenu.add(useRegexItem);


        //-----------------------------------------------------
        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        setJMenuBar(menuBar);



        //-----------------------------------------------------
        jTextField =new JTextField(15);
        //jTextField.setName("FilenameField");
        jTextField.setName("SearchField");

        // jTextField.setBounds(50,50,100,50);
        jTextField.setFont(jTextField.getFont().deriveFont(30f));
       // jTextField.setText(PLIK);
      //  jPanel.add(jTextField);
        //-----------------------------------------------------


        Icon iconSave = new ImageIcon("/media/kamil/Nowy/dokumenty/IdeaPRoject2021 05/Text Editor/icons/iconfinder_save_1608823.png");


        JButton saveBtn=new JButton(iconSave);
        saveBtn.setName("SaveButton");
       // saveBtn.setText("Save");

        saveBtn.setFont(saveBtn.getFont().deriveFont(30f));
        // saveBtn.setBounds(50,50,100,50);
        //saveBtn.addActionListener(e->zapisz());
        saveBtn.addActionListener(e->zapiszPlik());
        jPanel.add(saveBtn);
        //-----------------------------------------------------
        Icon iconLoad = new ImageIcon("/media/kamil/Nowy/dokumenty/IdeaPRoject2021 05/Text Editor/icons/open-file_40455.png");

        JButton loadBn=new JButton(iconLoad);
        //loadBn.setName("LoadButton");
        loadBn.setName("OpenButton");
       // loadBn.setText("Load");
        loadBn.setFont(loadBn.getFont().deriveFont(30f));
        loadBn.addActionListener(e->wybierzPlik());

        jPanel.add(loadBn);
        //-----------------------------------------------------
        txtSearch =new JTextField(15);
        txtSearch.setName("SearchField");
        // jTextField.setBounds(50,50,100,50);
        txtSearch.setFont(txtSearch.getFont().deriveFont(30f));
        // jTextField.setText(PLIK);
        jPanel.add(txtSearch);
        //-----------------------------------------------------
        Icon iconSearch = new ImageIcon("/media/kamil/Nowy/dokumenty/IdeaPRoject2021 05/Text Editor/icons/search.png");

        JButton searchBtn=new JButton(iconSearch);
        searchBtn.setName("StartSearchButton");
        searchBtn.addActionListener(e->szukaj());
      //  loadBn.addActionListener(e->otworz());
        jPanel.add(searchBtn);
        //-----------------------------------------------------
        Icon iconSleft = new ImageIcon("/media/kamil/Nowy/dokumenty/IdeaPRoject2021 05/Text Editor/icons/left.png");

        JButton leftBtn=new JButton(iconSleft);
        leftBtn.setName("PreviousMatchButton");
        leftBtn.addActionListener(e->poprzedni());
        //  loadBn.addActionListener(e->otworz());
        jPanel.add(leftBtn);
        //-----------------------------------------------------
        Icon iconRight = new ImageIcon("/media/kamil/Nowy/dokumenty/IdeaPRoject2021 05/Text Editor/icons/right.png");

        JButton rightBtn=new JButton(iconRight);
        rightBtn.setName("NextMatchButton");
        rightBtn.addActionListener(e->nastepny());
        //  loadBn.addActionListener(e->otworz());
        jPanel.add(rightBtn);
        //-----------------------------------------------------
        checkBox= new JCheckBox();
        checkBox.setName("UseRegExCheckbox");
        checkBox.setText("Use regex");
        checkBox.setFont(checkBox.getFont().deriveFont(30f));
        checkBox.addActionListener(e->ustawRegex());
        checkBox.setSelected(false);
        jPanel.add(checkBox);

        //-----------------------------------------------------
        jTextArea=new JTextArea();//new JTextArea(15,14);
        jTextArea.setName("TextArea");
        // jTextArea.setSize(250,250);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(jTextArea.getFont().deriveFont(30f));

        //-----------------------------------------------------
        JScrollPane scrollPane= new JScrollPane(jTextArea);
        scrollPane.setName("ScrollPane");
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);



        //add(jTextArea,BorderLayout.CENTER);
        //-----------------------------------------------------
        add(new JPanel(),BorderLayout.WEST);
        add(new JPanel(),BorderLayout.EAST);
        add(new JPanel(),BorderLayout.SOUTH);
        //-----------------------------------------------------
        add(jPanel,BorderLayout.NORTH);
        // add(new JButton("North"), BorderLayout.NORTH);
        // add(new JButton("South"), BorderLayout.SOUTH);
        //  add(new JButton("West"), BorderLayout.WEST);
        //  add(new JButton("East"), BorderLayout.EAST);
        // add(new JButton("Center"), BorderLayout.CENTER);
        add(scrollPane,BorderLayout.CENTER);
        //-----------------------------------------------------

    }
    //-------------------------------------------------------------------------------------------------------------
    private void ustawRegex() {
        if(checkBox.isEnabled()==true)
        czyRegex=true;
        else czyRegex=false;

    }
    //-------------------------------------------------------------------------------------------------------------
    private void wybierzPlik() {
        fileChooser.setName("fileChooser");
        int returnValue = fileChooser.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            otworzPlik(selectedFile);
        }


    }

    //-------------------------------------------------------------------------------------------------------------

    //-------------------------------------------------------------------------------------------------------------
    public void otworz(){

        System.out.println("----------------------------------otwieranie");
        String sciezka="/media/kamil/Nowy/dokumenty/IdeaPRoject2021 05/Text Editor/Text Editor/task/src/";

        String pathname;
        if(jTextField.getText().length()>0){
            pathname= sciezka + jTextField.getText();
        }else {
            pathname= sciezka + "doc.txt";
        }

        File fileName= new File(pathname);
        plik=fileName;

        String lines="";
        //String line="";
        String line1="";
        String line2="";
        String result = null;
        try{
           // BufferedReader reader = new BufferedReader(new FileReader(fileName));
           //  lines = reader.lines().map(line -> line + "\n").collect(Collectors.joining());
           // reader.close();



            DataInputStream reader = new DataInputStream(new FileInputStream(pathname));
            int nBytesToRead = reader.available();
            if(nBytesToRead > 0) {
                byte[] bytes = new byte[nBytesToRead];
                reader.read(bytes);
                result = new String(bytes);
                lines=result;
            }



        }catch (IOException e){
            e.printStackTrace();
        }

        if(lines.length()!=0)
        jTextArea.setText(lines);
        else jTextArea.setText("");

        System.out.println(lines);
        System.out.println("----------------------------koniec otwierania");

    }
    //-------------------------------------------------------------------------------------------------------------
    private void zapisz() {
        System.out.println("-----------------------zapisywanie");

        String sciezka="/media/kamil/Nowy/dokumenty/IdeaPRoject2021 05/Text Editor/Text Editor/task/src/";


        String pathname;
        if(jTextField.getText().length()>0){
            pathname= sciezka + jTextField.getText();
        }else {
            pathname= sciezka + "doc.txt";
        }

        File fileName= new File(pathname);

        String str = null;
        str= jTextArea.getText();
        System.out.println(str);

      try {
         /* BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,false));
          writer.write(str);
          writer.close();*/
          FileOutputStream outputStream = new FileOutputStream(fileName);
          byte[] strToBytes = str.getBytes();
          outputStream.write(strToBytes);

          outputStream.close();


      }
      catch (IOException e){
        e.printStackTrace();
      }
        System.out.println("-------------------------------koniec zapisu");
    }

    //-------------------------------------------------------------------------------------------------------------
    public void otworzPlik(File file){



        File fileName= file;
        plik=fileName;

        String pathname=fileName.getAbsolutePath();

        String lines="";

        String result = null;
        try{


            DataInputStream reader = new DataInputStream(new FileInputStream(pathname));
            int nBytesToRead = reader.available();
            if(nBytesToRead > 0) {
                byte[] bytes = new byte[nBytesToRead];
                reader.read(bytes);
                result = new String(bytes);
                lines=result;
            }



        }catch (IOException e){
            e.printStackTrace();
        }

        if(lines.length()!=0)
            jTextArea.setText(lines);
        else jTextArea.setText("");



    }
    //-------------------------------------------------------------------------------------------------------------
    private void zapiszPlik() {

        if(plik!=null) {
            File fileName = plik;

            String str = null;
            str = jTextArea.getText();
            System.out.println(str);

            try {

                FileOutputStream outputStream = new FileOutputStream(fileName);
                byte[] strToBytes = str.getBytes();
                outputStream.write(strToBytes);

                outputStream.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //-------------------------------------------------------------------------------------------------------------
    public void szukaj(){

        //String tekst, boolean czyRegex
        boolean wybor = czyRegex;
         textZrodlowy=jTextArea.getText();
         tekstSzukany=txtSearch.getText();
        int index = 0;
        int fromIndex = index;
        indeksy = new ArrayList<>();
        indeksyKonca= new ArrayList<>();

        int length = tekstSzukany.length();
        if(!wybor) {

             index = textZrodlowy.indexOf(tekstSzukany, 0);
             int ostatniIndeks = textZrodlowy.lastIndexOf(tekstSzukany);
             do {
                 indeksy.add(index);
                 index = textZrodlowy.indexOf(tekstSzukany, index + length);
             } while (index <= ostatniIndeks && index >= 0);

             index = textZrodlowy.indexOf(tekstSzukany, 0);







         }
         else {

             Pattern javaPattern = Pattern.compile(tekstSzukany, Pattern.CASE_INSENSITIVE);
             Matcher matcher = javaPattern.matcher(textZrodlowy);
             while (matcher.find()) {
               //  System.out.println("group: " + matcher.group() + ", start: " + matcher.start());
                 indeksy.add(matcher.start());
                 indeksyKonca.add(matcher.end());
                 length=matcher.end()-matcher.start();
             }

         }

        idBiezacy=0;
        idPoprzedni=0;
        idNastepny=0;

        if(indeksy.size()>0){
            idBiezacy=indeksy.get(0);
            idPozycja=0;
        }

       // System.out.println("indeks"+index);
        if(index==-1) length=0;
        if(idBiezacy==-1) idBiezacy=0;

        jTextArea.setCaretPosition(idBiezacy + length);
        jTextArea.select(idBiezacy, idBiezacy + length);
        jTextArea.grabFocus();
        System.out.println("-------tekst szukany--------");
        System.out.println(tekstSzukany);
        System.out.println("******* tekst zrodlowy **********");
        System.out.println(textZrodlowy);
        System.out.println("######################33");
    }
    //-------------------------------------------------------------------------------------------------------------
    public void nastepny(){
        System.out.println("-----------------nastepny");
      try {
          if (idPozycja < indeksy.size() - 1) {
              idPozycja++;
              idBiezacy = indeksy.get(idPozycja);
          } else {

          }
          int length = tekstSzukany.length();
          if(czyRegex) length=indeksyKonca.get(idPozycja)-indeksy.get(idPozycja);


          if(idBiezacy==-1) {
              idBiezacy=0;
              length=0;
          }

          jTextArea.setCaretPosition(idBiezacy + length);
          jTextArea.select(idBiezacy, idBiezacy + length);
          jTextArea.grabFocus();
      }catch (NullPointerException e){
          System.out.println("pusta");
      }

    }

    //-------------------------------------------------------------------------------------------------------------
    public void poprzedni() {
        System.out.println("************************poprzedni");
        try {
            if (idPozycja > 0) {
                idPozycja--;
                idBiezacy = indeksy.get(idPozycja);
            } else {

                int rozmiarTablicy = indeksy.size();
               // System.out.println(rozmiarTablicy);
                for(int x:indeksy){
                    //System.out.println(x);
                }
                idPozycja=rozmiarTablicy-1;
                idBiezacy = indeksy.get(rozmiarTablicy-1);
                System.out.println(idPozycja);
            }

            int length = tekstSzukany.length();
            if(czyRegex) {
                length=indeksyKonca.get(idPozycja)-indeksy.get(idPozycja);



            }

            if(idBiezacy==-1) {
                idBiezacy=0;
                length=0;
            }

            jTextArea.setCaretPosition(idBiezacy + length);
            jTextArea.select(idBiezacy, idBiezacy + length);
            jTextArea.grabFocus();
        }catch (NullPointerException e){
            System.out.println("pusta");
        }
        System.out.println("kareta="+jTextArea.getCaretPosition());
    }
    //-------------------------------------------------------------------------------------------------------------

}
