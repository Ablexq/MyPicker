package example.com.mypicker1;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.LinkagePicker;
import example.com.mypicker1.model.AllAreaAreaModel;
import example.com.mypicker1.model.AllAreaCityModel;
import example.com.mypicker1.model.AllAreaModel;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> provinceList = new ArrayList<>();
    private ArrayList<ArrayList<String>> cityList = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> areaList = new ArrayList<>();

    private ArrayList<String> provinceIdList = new ArrayList<>();
    private ArrayList<String> provinceNameList = new ArrayList<>();

    private ArrayList<String> cityIdList = new ArrayList<>();
    private ArrayList<String> cityNameList = new ArrayList<>();

    private ArrayList<String> areaIdList = new ArrayList<>();
    private ArrayList<String> areaNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAddressDatas();

        initData();
    }

    private void initData() {
        LinkagePicker.DataProvider provider = new LinkagePicker.DataProvider() {

            @Override
            public boolean isOnlyTwo() {
                return false;
            }

            @NonNull
            @Override
            public List<String> provideFirstData() {
                getNameAndId(provinceList, provinceIdList, provinceNameList);
                return provinceNameList;
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public List<String> provideSecondData(int firstIndex) {
                ArrayList<String> stringsList = cityList.get(firstIndex);//获取省所有市
                getNameAndId(stringsList, cityIdList, cityNameList);

                return cityNameList;
            }

            @Nullable
            @Override
            public List<String> provideThirdData(int firstIndex, int secondIndex) {
                ArrayList<ArrayList<String>> arrayLists1 = areaList.get(firstIndex);//获取省所有区
                ArrayList<String> arrayList2 = arrayLists1.get(secondIndex);//获取市所有区
                getNameAndId(arrayList2, areaIdList, areaNameList);

                return areaNameList;
            }

        };
        LinkagePicker picker = new LinkagePicker(MainActivity.this, provider);
        picker.setCycleDisable(true);
        picker.setUseWeight(true);
        picker.setOnStringPickListener(new LinkagePicker.OnStringPickListener() {
            @Override
            public void onPicked(String first, String second, String third) {
                if (areaNameList.contains(third)) {
                    int index = areaNameList.indexOf(third);
                    String areaId = areaIdList.get(index);
                    Toast.makeText(MainActivity.this, "省：" + first + "  市：" + second + "  区：" + third + " 区的id===" + areaId, Toast.LENGTH_SHORT).show();
                }

            }
        });
        picker.show();
    }

    private void getNameAndId(ArrayList<String> sourceList, ArrayList<String> idList, ArrayList<String> nameList) {
        idList.clear();
        nameList.clear();
        if (sourceList != null && sourceList.size() > 0) {
            for (int i = 0; i < sourceList.size(); i++) {
                String string = sourceList.get(i);
                if (string.contains(",")) {
                    String[] split = string.split(",");
                    if (!idList.contains(split[0])) {
                        idList.add(split[0]);
                    }

                    if (!nameList.contains(split[1])) {
                        nameList.add(split[1]);
                    }
                }
            }
        }
    }

    private void getAddressDatas() {
        //获取json数据
        String json = readAssertResource(MainActivity.this, "city.json");
        //解析json数据
        AllAreaModel allArea = AllAreaModel.getModelWithJson(json);

        if (allArea != null && allArea.data != null && allArea.data.size() > 0) {
            for (int i = 0; i < allArea.data.size(); i++) {
                String provinceName = allArea.data.get(i).name;
                String provinceId = allArea.data.get(i).id;
                provinceList.add(provinceId + "," + provinceName);
                ArrayList<AllAreaCityModel> list = allArea.data.get(i).list;

                ArrayList<String> provinceOfCityList = new ArrayList<>();
                ArrayList<ArrayList<String>> provinceOfAreaList = new ArrayList<>();
                if (list != null && list.size() > 0) {
                    for (int j = 0; j < list.size(); j++) {
                        String cityName = list.get(j).name;
                        String cityId = list.get(j).id;
                        if (cityName.equals("0")) {
                            cityName = "暂无数据";
                        }
                        provinceOfCityList.add(cityId + "," + cityName);
                        ArrayList<AllAreaAreaModel> list2 = list.get(j).list;

                        ArrayList<String> cityOfAreaList = new ArrayList<>();
                        if (list2 != null && list2.size() > 0) {
                            for (int k = 0; k < list2.size(); k++) {

                                String areaName = list2.get(k).name;
                                String areaId = list2.get(k).id;
                                if (areaName.equals("0")) {
                                    areaName = "暂无数据";
                                }
                                cityOfAreaList.add(areaId + "," + areaName);
                            }
                        } else {
                            cityOfAreaList.add(",暂无数据");
                        }

                        provinceOfAreaList.add(cityOfAreaList);
                    }
                } else {
                    provinceOfCityList.add(",暂无数据");
                }
                cityList.add(provinceOfCityList);
                areaList.add(provinceOfAreaList);
            }
        }
    }


    public String readAssertResource(Context context, String strAssertFileName) {
        AssetManager assetManager = context.getAssets();
        String strResponse = "";
        try {
            InputStream ims = assetManager.open(strAssertFileName);
            strResponse = getStringFromInputStream(ims);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    private String getStringFromInputStream(InputStream a_is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(a_is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}

