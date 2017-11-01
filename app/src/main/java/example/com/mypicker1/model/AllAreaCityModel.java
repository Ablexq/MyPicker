package example.com.mypicker1.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 市
 */

public class AllAreaCityModel {

    public String id;//
    public String name;//
    public ArrayList<AllAreaAreaModel> list;//

    public static ArrayList<AllAreaCityModel> getModelWithJson(JSONArray jsonArray) {

        ArrayList<AllAreaCityModel> lists=new ArrayList<>();

        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    AllAreaCityModel allAreaCityModel = new AllAreaCityModel();
                    if (jsonObject.has("id")) {
                        allAreaCityModel.id = jsonObject.getString("id");
                    }
                    if (jsonObject.has("name")) {
                        allAreaCityModel.name = jsonObject.getString("name");
                    }
                    if (jsonObject.has("list")) {
                        JSONArray datas = jsonObject.getJSONArray("list");
                        allAreaCityModel.list= AllAreaAreaModel.getModelWithJson(datas);
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
