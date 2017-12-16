package davidswann.myapplication.db;

import android.provider.BaseColumns;

public class Db {
    public static final String DbName = "davidswann.myapplication.db";
    public static final int DbVersion = 1;

    public class NewTask implements BaseColumns {
        public static final String Title = "title";
        public static final String Tasks = "tasks";
    }
}