import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import java.util.Map;

public class WeatherGUI extends JFrame{
    private Button findButton = new Button("Wyszukaj");
    private Button saveButton = new Button("Zapisz");
    private Button loadButton = new Button("Wczytaj");
    private Button clearButton = new Button("Wyczyść");
    private Button deleteButton = new Button("Usuń");
    private JScrollPane scroll;

    private String cityToFind = "";
    private String[] fileNames;
    private Data data = null;
    private JTextField
            city = new JTextField(10),
            date = new JTextField(15),
            showCity = new JTextField(10),
            text[] = new JTextField[5];
    private ImagePanel buffimages[] = new ImagePanel[5];
    private JLabel images[] = new JLabel[5];
    private JList filesPane = new JList();

    public WeatherGUI(){
        for(int i = 0; i < 5; i++)
            text[i] = new JTextField(10);
        fileNames = loadFileNames();
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findCityDataAndUpdateFields();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveData();
                    remove(scroll);
                    prepJscroll();
                    scroll.validate();
                    scroll.repaint();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData(filesPane.getSelectedValue().toString());
                if(data == null)
                    return;
                updateFields(data);
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i =0; i < text.length; i++){
                    text[i].setText("");
                }
                city.setText("");
                date.setText("");
                showCity.setText("");
                data= null;
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File(Weather.PATH + filesPane.getSelectedValue().toString());
                remove(scroll);
                file.delete();
                prepJscroll();
                scroll.validate();
                scroll.repaint();
            }
        });

        addAndConfigureElements();

    }

    private String[] loadFileNames(){
        return new File(Weather.PATH).list();
    }
    private void findCityDataAndUpdateFields(){
        if(city.getText().equals("")) {
            city.setText("wprowadz miasto");
            return;
        }
        if(city.getText().equals(cityToFind))
            return;
        cityToFind = city.getText();
        if(cityToFind.equals("wprowadz miasto"))
                return;
        for(int i = 0; i < 5; i++)
            text[i].setText("");
        DataCreator dc = new DataCreator();
        dc.createDataRecords(cityToFind);
        Data data = dc.getData();
        this.data = data;
        updateFields(data);
    }
    private void updateFields(Data data){
        Map<String, Integer> temperaturesOnPages = data.getTemperaturesOnPages();
        String degree = "\u00B0" + "C";

        if(temperaturesOnPages.containsKey("pogodynka.pl"))
            text[0].setText(temperaturesOnPages.get("pogodynka.pl")+degree);
        else
            text[0].setText("Brak Danych");
        if(temperaturesOnPages.containsKey("infometeo.pl"))
            text[1].setText(temperaturesOnPages.get("infometeo.pl")+degree);
        else
            text[1].setText("Brak Danych");
        if(temperaturesOnPages.containsKey("pogodnie.pl"))
            text[2].setText(temperaturesOnPages.get("pogodnie.pl")+degree);
        else
            text[2].setText("Brak Danych");
        if(temperaturesOnPages.containsKey("meteoprog.pl"))
            text[3].setText(temperaturesOnPages.get("meteoprog.pl")+degree);
        else
            text[3].setText("Brak Danych");
        if(temperaturesOnPages.containsKey("google.pl"))
            text[4].setText(temperaturesOnPages.get("google.pl")+degree);
        else
            text[4].setText("Brak Danych");

        date.setText(data.getSimpleDate());
        showCity.setText(PageParser.cityFromCapital(data.getCity()));
        filesPane.validate();
        filesPane.repaint();
    }
    private void saveData(){
        if(data == null)
            return;
        try {
            data.saveData();
        } catch(IOException e){
            data = null;
        }
    }
    private void loadData(String fileName){
        if(fileName.equals(""))
            return;
        try {
            data = Data.loadData(fileName);
        } catch(Exception e){
            e.printStackTrace();
        }
        updateFields(data);

    }
    private void addAndConfigureElements(){
        buffimages[0] = new ImagePanel("src\\pogodynka.png");
        buffimages[1] = new ImagePanel("src\\infometeo.png");
        buffimages[2] = new ImagePanel("src\\pogodnie.png");
        buffimages[3] = new ImagePanel("src\\meteoprog.png");
        buffimages[4] = new ImagePanel("src\\google.png");


        setLayout(null);
        add(date);
        for(int i =0; i < 5; i++) {
            images[i] = buffimages[i].createImage();
            add(images[i]);
        }
        for(int i =0; i < 5; i++){
            add(text[i]);
            text[i].setEditable(false);
            text[i].setHorizontalAlignment(JTextField.CENTER);
        }
        date.setEditable(false);
        date.setBounds(10,10, 140, 20);
        date.setHorizontalAlignment(JTextField.CENTER);

        add(city);
        city.setHorizontalAlignment(JTextField.CENTER);
        city.setBounds(465+20, 50,110,20);
        city.setToolTipText("wprowadz miasto");
        city.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findCityDataAndUpdateFields();
            }
        });
        add(showCity);
        showCity.setEditable(false);
        showCity.setBounds(160, 10, 90, 20);
        showCity.setHorizontalAlignment(JTextField.CENTER);

        for(int i=0; i <3; i++) {
            images[i].setBounds(33 + i*155, 50, 110, 60);
            text[i].setBounds(33 + i*155, 120, 110, 20);
        }
        for(int i=3; i <5; i++) {
            images[i].setBounds(110 + (i-3)*155, 170, 110, 60);
            text[i].setBounds(110 + (i-3)*155, 240, 110, 20);
        }
        add(findButton);
        findButton.setBounds(485, 72, 110,20);
        add(clearButton);
        clearButton.setBounds(485, 94, 110,20);
        add(saveButton);
        saveButton.setBounds(485, 116, 110, 20);
        add(loadButton);
        loadButton.setBounds(485, 138, 110, 20);
        add(deleteButton);
        deleteButton.setBounds(485, 362, 110, 20);
        prepJscroll();
    }
    private void prepJscroll(){
        fileNames = loadFileNames();
        filesPane = new JList(fileNames);
        filesPane.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scroll = new JScrollPane(filesPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //ADD ACTION LSITENER ODUBLE CLICK
        add(scroll);
        scroll.setBounds(485, 160, 140, 200);
    }
}


