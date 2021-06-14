package com.aoslec.contactproject.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.aoslec.contactproject.Bean.People;
import com.aoslec.contactproject.Bean.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask extends AsyncTask<Integer, String, Object> {
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<People> people;
    ArrayList<User> user;

    // Network Task를 검색, 입력, 수정, 삭제 구분없이 하나로 사용키 위해 생성자 변수 추가.
    String where = null;

    //다이얼로그는 생성자로 만들 필요가 없다
    public NetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.people = new ArrayList<People>();
        this.user = new ArrayList<User>();
        this.where = where;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Get....");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String result = null;


        //----------------------------기본 양식----------------
        try{
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);  //10초
            Log.v("ggg","getTry");

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                Log.v("ggg","getIf");


                while (true){
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");

                }  //----------------------------------------------------------------

                Log.v("ggg","while");

                if (where.equals("list")) {
                    Log.v("ggg", "list");
                    parserList(stringBuffer.toString());

                }else if (where.equals("insert")) {
                    Log.v("ggg", "insert");
                    result = parserInsert(stringBuffer.toString());

                }else if (where.equals("login")) {
                    Log.v("ggg", "login");
                    parserLogin(stringBuffer.toString());

                }else if (where.equals("group")) {
                    Log.v("ggg", "group");
                    parserGroup(stringBuffer.toString());

                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            if (where.equals("list")){
                return people;
            }
            else if (where.equals("insert")){
                return result;

            }else if (where.equals("login")){
                return user;

            }else if (where.equals("group")){
                return people;

            }else{
                return result;

            }
        }
    }

    private void parserLogin(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user"));
            user.clear();

            Log.v("ggg","parserLogin" +jsonArray.length());

                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.v("ggg", "parserLogin for");
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String email = jsonObject1.getString("email");
                    User u = new User(email);
                    user.add(u);
                }
                if (jsonArray.length()==0) {
                    Log.v("ggg", "parserLogin if");
                    String email = "";
                    User u = new User(email);
                    user.add(u);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String parserInsert(String str){
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
            Log.v("ggg", "parserInsert");
        }catch (Exception e){
            e.printStackTrace();
        }

        return  returnValue;
    }

    private  void parserList(String str){
        try {
            Log.v("ggg","parserList");
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("people"));
            people.clear();

            for(int i=0; i<jsonArray.length(); i++){
                Log.v("ggg", "parserList for");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String no  = jsonObject1.getString("no");
                String name = jsonObject1.getString("name");
                String tel = jsonObject1.getString("tel");
                String img = jsonObject1.getString("img");
                String group = jsonObject1.getString("group");
                String favorite = jsonObject1.getString("favorite");

                Log.v("ggg","name  " + name);
                People p = new People(no, name, tel, img, group, favorite);
                people.add(p);
            }
            if (jsonArray.length()==0) {
                String name = "false";
                People p = new People(name);
                people.add(p);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parserGroup(String str) {
        try {
            Log.v("ggg","parserGroup");
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("people"));
            people.clear();

            for(int i=0; i<jsonArray.length(); i++){
                Log.v("ggg", "parserGroup for");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String group = jsonObject1.getString("group");
                String count = jsonObject1.getString("count");

                Log.v("ggg","group  " + group + count);
                People p = new People(group, count);
                people.add(p);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

} // NetworkTask
