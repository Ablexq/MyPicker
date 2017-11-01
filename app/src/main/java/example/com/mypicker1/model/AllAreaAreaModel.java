package example.com.mypicker1.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 区
 */

public class AllAreaAreaModel {

    public String id;//
    public String name;//

    public static ArrayList<AllAreaAreaModel> getModelWithJson(JSONArray jsonArray) {

        ArrayList<AllAreaAreaModel> lists = new ArrayList<>();

        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    AllAreaAreaModel allAreaCityModel = new AllAreaAreaModel();
                    if (jsonObject.has("id")) {
                        allAreaCityModel.id = jsonObject.getString("id");
                    }
                    if (jsonObject.has("name")) {
                        allAreaCityModel.name = jsonObject.getString("name");
                    }
                    lists.add(allAreaCityModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return lists;
    }

}
