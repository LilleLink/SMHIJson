import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class tablePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private Object[] headers = {"Station","Value"};
    private ArrayList<Station> stations;

    public tablePanel(ArrayList<Station> stations) {
        this.setLayout(new BorderLayout());
        table = new JTable();
        model = new DefaultTableModel(headers, 0);
        table.setModel(model);
        this.stations = stations;
        //Uppdaterar table så att tabellen laddas in vid programstart
        updateTable("");
        this.add(new JScrollPane(table));
    }


    public void updateTable(String searchText) {

        // Filtrera bort icke fungerande/oönskade stationer genom att skapa en ny lista
        ArrayList<Station> filteredStations = new ArrayList<Station>();
        for (int i = 0; i < stations.size(); i++) {
            if (!stations.get(i).isFaulty() && stations.get(i).getName().contains(searchText)) {
                filteredStations.add(stations.get(i));
            }

        }


        model = new DefaultTableModel(headers, filteredStations.size());

        //Fyller tabellen med värdena från filteredStations
        for (int i = 0; i < filteredStations.size(); i++) {
            model.setValueAt(filteredStations.get(i).getName(), i, 0);
            model.setValueAt(filteredStations.get(i).getValue(), i, 1);

        }

        table.setModel(model);
        repaint();
    }

}
