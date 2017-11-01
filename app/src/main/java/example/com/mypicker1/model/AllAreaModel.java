package example.com.mypicker1.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 返回所有区域信息
 */

public class AllAreaModel {

    public String status;//
    public ArrayList<AllAreaProvinceModel> data;//

    public static AllAreaModel getModelWithJson(String response) {

        AllAreaModel allAreaModel = new AllAreaModel();

        try {

            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("status")) {
                allAreaModel.status = jsonObject.getString("status");
            }

            if (jsonObject.has("data")) {
                JSONArray datas = jsonObject.getJSONArray("data");
                allAreaModel.data = AllAreaProvinceModel.getModelWithJson(datas);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allAreaModel;
    }

}
