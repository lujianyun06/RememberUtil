package net.xdstar.rememberutil.Application;

import android.app.Application;

import net.xdstar.rememberutil.DataBase.DBController;

/**
 * Created by ljy on 16/10/24.
 */

public class RememberApplication extends Application {
    private static RememberApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        DBController.create(this);
//        startService(new Intent(this, LongRunningService.class));
    }

    public static RememberApplication instance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBController.destroy();
        instance = null;
    }
}
