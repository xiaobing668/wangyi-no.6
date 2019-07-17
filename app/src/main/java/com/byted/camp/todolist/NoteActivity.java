package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoContract;
import com.byted.camp.todolist.db.TodoDbHelper;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;
    private AppCompatRadioButton lowRadio;
    private SQLiteDatabase db;
    private TodoDbHelper mDbHelper;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);
        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        mDbHelper = new TodoDbHelper(this);
        db = mDbHelper.getReadableDatabase();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }
        radioGroup = findViewById(R.id.radio_group);
        addBtn = findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean succeed = saveNote2Database(content.toString().trim(),SelectedPriority());
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        db.close();
        mDbHelper.close();
    }
    private Note.Priority SelectedPriority() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.btn_high:
                return Note.Priority.Higher;
            case R.id.btn_medium:
                return Note.Priority.Medium;
            default:
                return Note.Priority.Lower;
        }
    }
    private boolean saveNote2Database(String content,Note.Priority priority) {
        // TODO 插入一条新数据，返回是否插入成功
        if (db==null|| TextUtils.isEmpty(content))
        {return  false;}
        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoNote.COLUMN_CONTENT, content);
        values.put(TodoContract.TodoNote.COLUMN_STATE, State.TODO.intValue);
        values.put(TodoContract.TodoNote.COLUMN_DATE, System.currentTimeMillis());
        values.put(TodoContract.TodoNote.COLUMN_PRIORITY, priority.intValue);
        long newRowId = db.insert(TodoContract.TodoNote.TABLE_NAME,null,values);
        if (newRowId!=-1)
        { return true;}
        else {
            return  false;
        }

    }
}
