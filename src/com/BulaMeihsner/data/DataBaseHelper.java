package com.BulaMeihsner.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;

import java.io.File;

public class DataBaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EyeTrackerDataBase";
    private static final String TABLE_EYE = "Eye";
    private static final String TABLE_SCENE = "Scene";
    private static final String TABLE_VALUE_X = "X";
    private static final String TABLE_VALUE_Y = "Y";
    private static final String TABLE_VALUE_DATETIME = "DateTime";
    private static final String TABLE_VALUE_IDEYE = "IdEye";
    
	public DataBaseHelper(Context context)
    {
        super(context,"/sdcard/Eyetracker/" +DATABASE_NAME+".db",null,DATABASE_VERSION);
        File file = new File("/sdcard/Eyetracker");
        file.mkdirs();
        SQLiteDatabase.openOrCreateDatabase("/sdcard/Eyetracker/"+DATABASE_NAME+".db",null); // zapis bazy n akarcie pamiêci
    }

	@Override
	public void onCreate(SQLiteDatabase dataBase) {
		
        Log.d("create database","create start");
        String createTableEye= "CREATE TABLE "+TABLE_EYE+" ( Id INTEGER PRIMARY KEY AUTOINCREMENT, X FLOAT, Y FLOAT, DateTime TEXT)";
        String createTableScene= "CREATE TABLE "+TABLE_SCENE+" ( Id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "X FLOAT, Y FLOAT, IdEye INTEGER )";

        dataBase.execSQL(createTableEye);
        dataBase.execSQL(createTableScene);

        Log.d("create database","create end");
	}

	@Override
	public void onUpgrade(SQLiteDatabase dataBase, int oldVersion, int newVersion) {
        dataBase.execSQL("DROP TABLE IF EXISTS "+TABLE_EYE+"");

        this.onCreate(dataBase);
		
	}
	
	public void addData(EyeData eyeData, SceneData sceneData)
	{
        Integer idEyeData;
        SQLiteDatabase dataBase= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(TABLE_VALUE_X,eyeData.getX());
        values.put(TABLE_VALUE_Y,eyeData.getY());
        values.put(TABLE_VALUE_DATETIME,eyeData.getDate());
        dataBase.insert(TABLE_EYE, null, values);
        idEyeData= getLastInsertId(dataBase);
        
        if(idEyeData > 0 )
        {
            values= new ContentValues();
            values.put(TABLE_VALUE_X, sceneData.getX());
            values.put(TABLE_VALUE_Y,sceneData.getY());
            values.put(TABLE_VALUE_IDEYE,idEyeData);
            dataBase.insert(TABLE_SCENE,null,values);
        }
        dataBase.close();
	}
	
    private Integer getLastInsertId(SQLiteDatabase dataBase)
    {
        Integer id;
        String query="SELECT last_insert_rowid()";
        Cursor cursor= dataBase.rawQuery(query,null);

        if (cursor.moveToFirst())
        {
            do{
               id = Integer.parseInt(cursor.getString(0));
            }while(cursor.moveToNext());
        }else return -1;
        return id;
    }
	
}
