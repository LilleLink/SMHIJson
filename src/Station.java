//import com.sun.org.glassfish.external.amx.AMXGlassfish;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Station {

    private String name;
    private int id;
    private double value;
    private boolean faulty;

    public Station(String name, int id) {
        this.name = name;
        this.id = id;
        this.faulty = false;
        updateValue();
    }

    public void updateValue() {
        // Generating correct link to temperature data with the station ID
        String href = "https://opendata-download-metobs.smhi.se/api/version/latest/parameter/22/station/"+id+"/period/latest-months/data.json";

        // Reading the JSON file
        try {
            URL url = new URL(href);
            InputStream is = url.openStream();
            JsonReader rdr = Json.createReader(is);
            JsonObject obj = rdr.readObject();

            // Hämtar value längst bak i listan då det kan förekommer flera values för olika tider, och den senaste är längst bak.
            String sValue = obj.getJsonArray("value").getJsonObject(obj.getJsonArray("value").size()-1).getString("value");

            value = Double.parseDouble(sValue);

        } catch (FileNotFoundException fnfe) {
            //No Json data availible
            faulty = true;
        } catch (MalformedURLException e) {
            faulty = true;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            faulty = true;
        } catch (ClassCastException cce) {
            faulty = true;
            //cce.printStackTrace();
            //System.out.println("ClassCastException"+name+": "+href);
        }

    }

    public boolean isFaulty() {
        return faulty;
    }

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
