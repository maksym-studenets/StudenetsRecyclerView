package com.mstudenets.studenetsrecyclerview.controller;

import android.content.Context;

import com.mstudenets.studenetsrecyclerview.R;
import com.mstudenets.studenetsrecyclerview.model.Fruit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class JsonManager
{
    private Context context;
    private ArrayList<Fruit> fruitList = new ArrayList<>();

    public JsonManager(Context context) {
        this.context = context;
    }

    public ArrayList<Fruit> parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray mArray = jsonObject.getJSONArray("fruit");

            for (int i = 0; i < mArray.length(); i++) {
                JSONObject mChildNode = mArray.getJSONObject(i);

                String fruitName = mChildNode.optString("name");
                String fruitCountry = mChildNode.optString("country");
                double fruitPrice = Double.parseDouble(mChildNode.optString("price"));

                Fruit fruit = new Fruit(fruitName, fruitCountry, fruitPrice);
                fruitList.add(fruit);
            }
            return fruitList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getJsonRead() {
        String json;
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.data);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }

    public void updateJson() throws JSONException {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();

        for (int i = 0; i < fruitList.size(); i++) {
            JSONObject childNode = new JSONObject();
            childNode.put("name", fruitList.get(i).getName());
            childNode.put("country", fruitList.get(i).getCountry());
            childNode.put("price", String.valueOf(fruitList.get(i).getPrice()));
        }
        object.put("fruit", array);
    }

    public ArrayList<Fruit> getFruitList() {
        return fruitList;
    }
}
