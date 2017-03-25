package com.example.emma_guo.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        text = (EditText) findViewById(R.id.editTodo);
        String oldText = getIntent().getStringExtra("oldText");
        text.setText(oldText);
    }

    public void onSaveEditItem(View view){
        Intent data = new Intent();
        text = (EditText) findViewById(R.id.editTodo);
        data.putExtra("newData", text.getText().toString());
        setResult(RESULT_OK, data);

        this.finish();
    }

}
