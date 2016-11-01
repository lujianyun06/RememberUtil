package net.xdstar.rememberutil.DataBase;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by shaoheng on 6/30/16.
 */
public class DBMigration implements RealmMigration {
    private static final String TAG = "DBMigration";

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        Log.i(TAG, "migrate, oldVersion" + oldVersion + ", newVersion=" + newVersion);
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
//                schema.get(ProjectModel.class.getSimpleName()).addPrimaryKey("id");
            oldVersion ++;
        }
        if (oldVersion == 1) {
            oldVersion ++;
        }
    }
}
