package com.example.sqlitedbapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog.Builder;
import android.view.View;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends Activity {

    EditText etSdntID;
    EditText etStdntName;
    EditText etStdntProg;
    Button btAdd;
    Button btDelete;
    Button btSearch;
    Button btView;
    SQLiteDatabase db;
    Object view = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etSdntID = (EditText)findViewById(R.id.etStndID);
        etStdntName = (EditText)findViewById(R.id.etStdntName);
        etStdntProg = (EditText)findViewById(R.id.etStdntProg);
        btAdd = findViewById(R.id.button);
        btDelete = findViewById(R.id.button2);
        btSearch = findViewById(R.id.button4);
        btView = findViewById(R.id.button3);

        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(stdnt_id VARCHAR, stdnt_name VARCHAR, stdnt_prog VARCHAR);");

        etSdntID.requestFocus();
        etStdntName.requestFocus();
        etStdntProg.requestFocus();

    }

    public void onClick(View view) {
        if (view == btAdd) {
            db.execSQL("INSERT INTO student VALUES('" + etSdntID.getText() + "','" + etStdntName.getText() + "','" + etStdntProg.getText() + "');");
            showMessage("Sucess", "Record added");
            clearText();
        } else if (view == btDelete) {
            Cursor c = db.rawQuery("SELECT * FROM student WHERE stdnt_id='" + etSdntID.getText() + "'", null);
            if(c.moveToFirst()){
                db.execSQL("DELETE FROM student Where stdnt_id='" + etSdntID.getText() + "'");
                showMessage("Sucess", "Record Deleted");
            }

            clearText();
        }
        else if (view == btSearch) {
            Cursor c = db.rawQuery("SELECT * FROM student WHERE stdnt_id='" + etSdntID.getText() + "'", null);
            StringBuffer buffer = new StringBuffer();

            if(c.moveToFirst()){
                buffer.append("Name: " + c.getString(1) + "\n");
                buffer.append("Program: " + c.getString(2) + "\n\n");
            }

            showMessage("Student Details", buffer.toString());
        }
        else if (view == btView) {
            Cursor c = db.rawQuery("SELECT * FROM student", null);
            if (c.getCount()==0){
                showMessage("Error", "No Records found.");
                return;
            }

            StringBuffer buffer= new StringBuffer();
            while (c.moveToNext()){
                buffer.append("ID: " + c.getString(0) + "\n");
                buffer.append("Name: " + c.getString(1) + "\n");
                buffer.append("Program: " + c.getString(2) + "\n\n");
            }
            showMessage("Student Details", buffer.toString());
        }
    }
    public void showMessage(String title, String message){
        Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }


    public void clearText(){
        etSdntID.getText().clear();
        etStdntName.getText().clear();
        etStdntProg.getText().clear();
    }

}