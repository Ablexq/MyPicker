package example.com.mypicker1.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 省
 */

public class AllAreaProvinceModel {

    public String id;//
    public String name;//
    public ArrayList<AllAreaCityModel> list;//

    public static ArrayList<AllAreaProvinceModel> getModelWithJson(JSONArray jsonArray) {

        ArrayList<AllAreaProvinceModel> lists = new ArrayList<>();

        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    AllAreaProvinceModel allAreaProvinceModel = new AllAreaProvinceModel();
                    if (jsonObject.has("id")) {
                        allAreaProvinceModel.id = jsonObject.getString("id");
                    }
                    if (jsonObject.has("name")) {
                        allAreaProvinceModel.name = jsonObject.getString("name");
                    }
                    if (jsonObject.has("list")) {
                        JSONArray datas = jsonObject.getJSONArray("list");
                        allAreaProvinceModel.list = AllAreaCityModel.getModelWithJson(datas);
                    }
                    lists.add(allAreaProvinceModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return lists;
    }

}
