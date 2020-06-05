package in.cshare.android.kisantodo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
abstract class TasksDatabase extends RoomDatabase {
    public abstract TaskDao getDao();
}
