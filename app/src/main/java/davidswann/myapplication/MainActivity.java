package davidswann.myapplication;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import davidswann.myapplication.db.Db;
import davidswann.myapplication.db.DbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DbHelper Help;
    private ListView TaskList;
    private ArrayAdapter<String> Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Help = new DbHelper(this);
        TaskList = (ListView) findViewById(R.id.List);

        updateUI();
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = Help.getReadableDatabase();
        Cursor cursor = db.query(Db.NewTask.Tasks,
                new String[]{Db.NewTask._ID, Db.NewTask.Title},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(Db.NewTask.Title);
            taskList.add(cursor.getString(idx));
        }

        if (Adapter == null) {
            Adapter = new ArrayAdapter<>(this,
                    R.layout.todo,
                    R.id.Task,
                    taskList);
            TaskList.setAdapter(Adapter);
        } else {
            Adapter.clear();
            Adapter.addAll(taskList);
            Adapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    public void Add(View view) {
        final EditText NewTask = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New Task")
                .setView(NewTask)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(NewTask.getText());
                        SQLiteDatabase db = Help.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(Db.NewTask.Title, task);
                        db.insertWithOnConflict(Db.NewTask.Tasks,
                                null,
                                values,
                                SQLiteDatabase.CONFLICT_REPLACE);
                        db.close();
                        updateUI();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void Delete(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.Task);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = Help.getWritableDatabase();
        db.delete(Db.NewTask.Tasks,
                Db.NewTask.Title + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }


}