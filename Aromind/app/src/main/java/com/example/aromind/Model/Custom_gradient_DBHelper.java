package com.example.aromind.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aromind.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Custom_gradient_DBHelper extends SQLiteOpenHelper {

    Context context;
    MainActivity activity;
    String tb_name;
    int version;
    SQLiteDatabase.CursorFactory factory;

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public Custom_gradient_DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        tb_name = name;
        this.version = version;
        this.factory = factory;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + name +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "custom_name VARCHAR2(100)," +
                " color INT, " +
                "FOREIGN KEY(custom_name) REFERENCES custom_power(custom_name));");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //데이터 베이스 데이터의 갯수를 출력
    public long getDataSize() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, tb_name);
        db.close();
        return count;
    }

    //새로운 데이터 입력
    public void insert(String custom_name, int color) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        ContentValues values = new ContentValues();
        values.put("custom_name", custom_name);
        values.put("color", color);
        db.insert(tb_name, null, values);
        db.close();
    }

    //기존에 존재하는 데이터 업데이트
    public void update(int id, String patient_name, int patient_age, int patient_gender, int patient_number, int patient_time, double ave_speed, double physiological_age, double fraility_index, String raw_file_path) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        ContentValues values = new ContentValues();
        values.put("patient_name", patient_name);
        values.put("patient_age", patient_age);
        values.put("patient_gender", patient_gender);
        values.put("patient_number", patient_number);
        values.put("patient_time", patient_time);
        values.put("ave_speed", ave_speed);
        values.put("physiological_age", physiological_age);
        values.put("fraility_index", fraility_index);
        values.put("raw_file_path", raw_file_path);
        db.update(tb_name, values, "_id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    //특정 id를 갖는 데이터를 json으로 출력
    public JSONArray getData(String custom_name) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + tb_name + " WHERE custom_name = ?";
            Cursor cursor = db.rawQuery(query, new String[]{custom_name});
            JSONArray resultSet = new JSONArray();
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();
                for(int i=0 ; i< totalColumn ; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            if (cursor.getString(i) != null) {
                                rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                            } else {
                                rowObject.put(cursor.getColumnName(i), "");
                            }
                        } catch (Exception e) {

                        }
                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }
            cursor.close();
            return resultSet;


    }

    //시작 id부터 일정 갯수의 데이터를 json array로 얻어온다
    public JSONArray getResult(int start_index, int number_of_data) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM " + tb_name, null);
        JSONArray resultSet = new JSONArray();
        cursor.moveToPosition(start_index);
        int index = 0;
        while (index < number_of_data) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {

                    }
                }

            }
            resultSet.put(rowObject);
            cursor.moveToNext();
            index = index + 1;
        }

        cursor.close();
        return resultSet;
    }

    //최근 데이터로 부터 일정 갯수의 데이터를 json array 로 얻어온다.
    public JSONArray getRecentData(int number_of_data) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM " + tb_name, null);
        JSONArray resultSet = new JSONArray();
        cursor.moveToLast();
        int index = 0;
        while (index < number_of_data) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {

                    }
                }

            }
            resultSet.put(rowObject);
            cursor.moveToPrevious();
            index = index + 1;
        }

        cursor.close();
        return resultSet;
    }

    //Primary key인 id를 이용해서 검색 후 삭제
    public void delete(String item) throws JSONException {
        // 입력한 항목과 일치하는 행 삭제
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + tb_name + " WHERE custom_name=\""+item+"\" ");
        db.close();
    }

    //모든 데이터 삭제
    public void removeAll() {
        SQLiteDatabase db = getReadableDatabase(); // helper is object extends SQLiteOpenHelper
        db.execSQL("DROP TABLE " + tb_name);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tb_name + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "time DATETIME DEFAULT (datetime('now','localtime')), patient_name VARCHAR2(100), patient_age INT, patient_gender INT, " +
                "patient_number INT, patient_time INT, ave_speed DOUBLE, physiological_age DOUBLE, fraility_index DOUBLE," +
                "raw_file_path VARCHAR2(100));");

    }

}