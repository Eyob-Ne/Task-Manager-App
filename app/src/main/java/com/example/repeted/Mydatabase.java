package com.example.repeted;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Mydatabase extends SQLiteOpenHelper {
    private static final String DB_NAME="crud";
    private static final String TABLE_NAME="department";
    private static final String ID="id";
    private static final String DEPT_NAME="name";
    Context context;


    public Mydatabase(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
String query="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +DEPT_NAME+" TEXT)";
db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addDepartment(String name){
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DEPT_NAME,name);
        long result = sql.insert(TABLE_NAME, null, cv);
        if(result!=-1){
            Toast.makeText(context, "Task added successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "failed to add Task", Toast.LENGTH_SHORT).show();
        }

    }
    public Cursor getDeptlist(){
        SQLiteDatabase sql =this.getReadableDatabase();
        String query="SELECT * FROM " +TABLE_NAME;
        return sql.rawQuery(query,null);

    }

    public int delDepartment(int str) {
        SQLiteDatabase sql=this.getWritableDatabase();
     int result= sql.delete(TABLE_NAME,"id=?",new String[] {String.valueOf(str)});
      return result;
    }

    public int updateDepartment(String deptName, int deptId) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cont=new ContentValues();
        cont.put(DEPT_NAME,deptName);
        int update = db.update(TABLE_NAME, cont, "id=?", new String[] {String.valueOf(deptId)});
return update;
    }
    public boolean isTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        boolean isEmpty = true;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            isEmpty = (count == 0);
        }
        cursor.close();
        return isEmpty;
    }

}
