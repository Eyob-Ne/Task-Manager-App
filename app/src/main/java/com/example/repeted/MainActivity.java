package com.example.repeted;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText department;
    Button add,del,up;
    ListView display;
    Mydatabase db ;
    int deptId;
    String deptName;
    List<String> Dept_name;
    List<Integer> Dept_id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        department=findViewById(R.id.taskid);
        add=findViewById(R.id.insert);
        del=findViewById(R.id.delete);
        up=findViewById(R.id.update);
        display=findViewById(R.id.mylist);
        db=new Mydatabase(this);
        if (db.isTableEmpty()) {
            db.addDepartment("push your code to Github");
            db.addDepartment("Copy your link and paste in the website");
            db.addDepartment("Click Submit when you finished");
        }

        showList();
        add.setOnClickListener(v->{
            String str=department.getText().toString().trim();
            if(str.isEmpty()){
                department.setError("please enter the department name");
            }
            else{
                db.addDepartment(str);
                showList();
                department.setText("");

            }
        });
        del.setOnClickListener(view->{

            int result=db.delDepartment(deptId);
            if(result!=-1){
                Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                showList();
                department.setText("");
            }
            else{
                Toast.makeText(this, "failed to delete Task", Toast.LENGTH_SHORT).show();
            }

        });
        up.setOnClickListener(v->{
            int update=db.updateDepartment(department.getText().toString(),deptId);
            if(update!=-1){
                Toast.makeText(this, "Task updated Successfully", Toast.LENGTH_SHORT).show();
                showList();
                department.setText("");

            }
            else{
                Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SuspiciousIndentation")
    private void showList() {
        Cursor cu=db.getDeptlist();
        Dept_name=new ArrayList<String>();
        Dept_id=new ArrayList<Integer>();
        if(cu.moveToFirst()){
            do{
              deptId=cu.getInt(0);
              deptName=cu.getString(1);
              Dept_name.add(deptName);
              Dept_id.add(deptId);
            }while(cu.moveToNext());
            cu.close();
            ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,Dept_name);
            display.setAdapter(adapter);

            display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    department.setText(Dept_name.get(position));
                    deptId=Dept_id.get(position);
                    Toast.makeText(MainActivity.this, "The task is Completed ✔️ ", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}