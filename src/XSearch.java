import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public interface XSearch {

    static JsonArray getList() {

        JsonArray list = null;
        try {
            URL url = new URL("https://opendata-download-metobs.smhi.se/api/version/latest/parameter/22.json");
            InputStream is = url.openStream();
            JsonReader rdr = Json.createReader(is);
            JsonObject obj = rdr.readObject();
            System.out.println(obj);

            JsonObject data = obj.getJsonObject("xsearch");
            list = data.getJsonArray("list");

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    static JsonArray getListFromSearch(String keyWord) {
        JsonArray data = null;

        try {
            URL url = new URL("https://opendata-download-metobs.smhi.se/api/version/latest/parameter/22.json");
            InputStream is = url.openStream();
            JsonReader rdr = Json.createReader(is);
            JsonObject obj = rdr.readObject();

            data = obj.getJsonArray(keyWord);

            //list = data.getJsonArray("list");
        } catch (Exception error) {
            error.printStackTrace();
        }

        return data;
    }

    static void printList(JsonArray list) {
        for(int i = 0; i< list.size(); i++)
        {
            System.out.print(list.getJsonObject(i).getString("creator")+ " ");
            System.out.print(list.getJsonObject(i).getString("title")+" ");
            System.out.println(list.getJsonObject(i).getString("date"));
            System.out.println("**************");

        }
    }
}
