package davidswann.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, Db.DbName, null, Db.DbVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Db.NewTask.Tasks);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + Db.NewTask.Tasks + " ( " +
                Db.NewTask._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Db.NewTask.Title + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }
}

