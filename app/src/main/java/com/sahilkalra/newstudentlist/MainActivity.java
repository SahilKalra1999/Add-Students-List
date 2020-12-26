package com.sahilkalra.newstudentlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sahilkalra.newstudentlist.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    TextView txtName;
    EditText etName;
    TextView txtRollNo;
    EditText etRollNo;
    TextView txtAddress;
    EditText etAddress;
    Button btnAddStudent;
    SQLiteDatabase db;
    ArrayList<Student> arrayList = new ArrayList<>();
    StudentAdapter recyclerAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName = findViewById(R.id.txtName);
        etName = findViewById(R.id.etName);
        txtRollNo = findViewById(R.id.txtRollNo);
        etRollNo = findViewById(R.id.etRollNo);
        txtAddress = findViewById(R.id.txtAddress);
        etAddress = findViewById(R.id.etAddress);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);


        recyclerAdapter = new StudentAdapter(arrayList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);

        try {
            db = openOrCreateDatabase("Students", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS students (roll_no int PRIMARY KEY, name text, address text)");
            Cursor cursor = db.rawQuery("SELECT * FROM students", null);
            if (cursor.moveToFirst()) {
                do {
                    String roll_no = cursor.getString(cursor.getColumnIndex("roll_no"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String address = cursor.getString(cursor.getColumnIndex("address"));
                    arrayList.add(new Student(roll_no, name, address));
                    recyclerAdapter.notifyDataSetChanged();
                    // do what ever you want here

                } while (cursor.moveToNext());
            }
            cursor.close();
        }  catch (SQLiteException e) {
            Log.e("exc", e.toString());
        }


        btnAddStudent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       if (etRollNo.length()>0 && etName.length()>0 && etAddress.length()>0) {
                           String s1 = etRollNo.getText().toString();
                           String s2 = etName.getText().toString();
                           String s3 = etAddress.getText().toString();



                               ContentValues values = new ContentValues();
                               values.put("roll_no", s1);
                               values.put("name", s2);
                               values.put("address", s3);
                               db.insert("students", null, values);


                               arrayList.add(new Student(s1, s2, s3));
                               recyclerAdapter.notifyDataSetChanged();
                               // do what ever you want here
                               etRollNo.setText("");
                               etName.setText("");
                               etAddress.setText("");

                               Collections.sort(arrayList, new Comparator<Student>() {
                                   @Override
                                   public int compare(Student o1, Student o2) {

                                       return o1.getRoll_no().compareTo(o2.getRoll_no());
                                   }
                               });
                           }

                       else{
                           Toast.makeText(MainActivity.this, "Please Enter Full Details of the Student", Toast.LENGTH_SHORT).show();
                       }


                    }
                }
        );


    }
}