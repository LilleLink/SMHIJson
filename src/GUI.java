import javax.json.JsonArray;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame implements XSearch {

    private JPanel north;

    private ArrayList<Station> stations = new ArrayList<Station>();

    private JTextField searchField;
    private tablePanel south;

    public GUI() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(100,100);
        this.setPreferredSize(new Dimension(700,700));
        getStations();
        south = new tablePanel(stations);

        north = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchField.setToolTipText("Search...");
        //Documentlistener som lyssnar efter uppdateringar i sökfältet för att få en mer snabb och responsiv sökfunktion
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                update();
            }

            public void update() {
                south.updateTable(searchField.getText());
            }
        });

        searchField.setPreferredSize(new Dimension(690,20));
        north.add(searchField, BorderLayout.CENTER);

        //South setup
        this.add(south, BorderLayout.CENTER);
        this.add(north, BorderLayout.NORTH);
        pack();
        this.setVisible(true);
    }

    //Metod som hämtar lista med stations vid start istället så man inte behöver vänta på att den ska
    //hämta nya värden vid varje sökning
    public void getStations() {
        stations = new ArrayList<Station>();
        JsonArray station = XSearch.getListFromSearch("station");
        for (int i = 0; i < station.size(); i++) {
            stations.add(new Station(station.getJsonObject(i).getString("name"), station.getJsonObject(i).getInt("id")));
        }

    }


}
