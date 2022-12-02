package com.example.sqlproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqlLiteDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="Demo_db";
    private static final int VERSION=1;

    public SqlLiteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_QUERY="CREATE TABLE registerdata(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,password TEXT,father TEXT,pincode TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS registerdata");
        onCreate(db);

    }

    // insert data in sqlite database---------------------------------------------------------------
    public boolean registerUserDetails(String name,String email,String password,String father,String pincode){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("father",father);
        contentValues.put("pincode",pincode);

        long l=sqLiteDatabase.insert("registerdata",null,contentValues);
        sqLiteDatabase.close();
        if(l>0){
            return true;
        }else {
            return false;
        }
    }

//For login ----------------------------------------------------------------------------------------

    boolean loggedin;
  public boolean login(String email,String password){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM registerdata WHERE email='"+email+"'AND password='"+password+"'",null);
        if(cursor.moveToFirst()){
            loggedin=true;
        }else {
            loggedin=false;
        }
return loggedin;
  }


//  Fetch data -------------------------------------------------------------------------------------
    public ArrayList<UserModal> getLoggedInUserDetails(String email1){
      ArrayList<UserModal>arrayList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String query="SELECT *FROM registerdata WHERE email='"+email1+"'";
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()){

            String name=cursor.getString(1);
            String email=cursor.getString(2);
            String pass=cursor.getString(3);
            String father=cursor.getString(4);
            String pincode=cursor.getString(5);
            UserModal userModal=new UserModal();
            userModal.setName(name);
            userModal.setEmail(email);
            userModal.setPassword(pass);
            userModal.setFather(father);
            userModal.setPincode(pincode);

            arrayList.add(userModal);
        }
        return arrayList;
    }

// get All User details-----------------------------------------------------------------------------
    public ArrayList getAllUserDetails(){
      ArrayList alUsers=new ArrayList();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT *FROM registerdata",null);
        if(cursor.moveToFirst()){
            do{
                ArrayList al =new ArrayList();
                String name=cursor.getString(1);
                String email=cursor.getString(2);
                String pass=cursor.getString(3);
                String father=cursor.getString(4);
                String pincode=cursor.getString(5);

                al.add(name);
                al.add(email);
                al.add(pass);
                al.add(father);
                al.add(pincode);

                alUsers.add(al);


            }while (cursor.moveToNext());
        }
        return alUsers;
    }


    public boolean updateProfileHelper(String name,String email1,String pass,String father,String pincode){
                    SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
                    ContentValues values=new ContentValues();
                    values.put("name",name);
                    values.put("password",pass);
                    values.put("father",father);
                    values.put("pincode",pincode);

                    int i=sqLiteDatabase.update("registerdata",values,"email=?",new String[]{email1});
                    if(i>0){
                        return true;

                    }else {
                        return false;
                    }
    }

    public boolean deleteProfileHelper(String email1){
      SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        int i= sqLiteDatabase.delete("registerdata","email=?",new String[]{email1});
        if(i>0){
            return true;
        }else {
            return false;
        }

    }

}


