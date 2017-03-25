package com.example.emma_guo.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAapter;
    int editedId = -1;
    ListView lvItems;
    EditText etEditText;

    private final int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArray();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAapter);
        etEditText = (EditText) findViewById(R.id.editText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                todoItems.remove(position);
                aToDoAapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id){
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                String clickedText = (String) lvItems.getItemAtPosition(pos);
                editedId = pos;
                i.putExtra("oldText", clickedText);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

    }

    public void populateArray(){
        readItems();
        aToDoAapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }
        catch(FileNotFoundException e){
            todoItems = new ArrayList<>();
        }
        catch(IOException e){

        }
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String newText = data.getExtras().getString("newData");
            todoItems.set(editedId, newText);
//            Toast.makeText(this, newText, Toast.LENGTH_SHORT).show();
            writeItems();
            aToDoAapter.notifyDataSetChanged();
        }
    }


    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");

        try{
            FileUtils.writeLines(file, todoItems);
        }
        catch(IOException e){

        }
    }


    public void onAddItem(View view) {
        aToDoAapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }
}
