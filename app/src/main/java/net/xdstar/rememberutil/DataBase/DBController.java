package net.xdstar.rememberutil.DataBase;

import android.content.Context;

import net.xdstar.rememberutil.Controller.PresentController;
import net.xdstar.rememberutil.Model.UnitModel;
import net.xdstar.rememberutil.Util.RememberUtil;
import net.xdstar.rememberutil.Util.TextUtil;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by ljy on 16/10/24.
 */

public class DBController {
    private static final String TAG = "DBController";

    private static final String DB_NAME_PREFIX = "RememberUtil";
    private static final String DB_NAME_SURFIX = ".realm";
    private static DBController instance = null;

    private static final int VERSION = 1;

    private Context mContext;
    private Realm mRealm;
    RealmConfiguration mConfig;


    private DBController(Context context) {
        mContext = context;
        openDB("NewEast");
    }

    public static void create(Context context) {
        instance = new DBController(context);
    }

    public static void destroy() {
        instance = null;
    }

    public static DBController instance() {
        return instance;
    }

    public void openDB(String id) {
        String dbname = DB_NAME_PREFIX + id + DB_NAME_SURFIX;
        if (mConfig != null) {
            mRealm.close();
        }

        mConfig = new RealmConfiguration
                .Builder(mContext)
                .name(dbname)
                .schemaVersion(VERSION)
//                .migration(new DBMigration())
                .build();

        try {
            mRealm = Realm.getInstance(mConfig);
        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(mConfig);
                mRealm = Realm.getInstance(mConfig);
            } catch (Exception ex) {
                throw ex;
            }
        }
//        mRealm.beginTransaction();
//        mRealm.deleteAll();
//        mRealm.commitTransaction();
    }

    public void setUnits(ArrayList<UnitModel> units) {
        mRealm.beginTransaction();
        for (UnitModel unit : units) {
            mRealm.copyToRealm(unit);
        }
        mRealm.commitTransaction();
    }

    public void setUnit(UnitModel unit) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(unit);
        mRealm.commitTransaction();
    }

    public UnitModel getUnit(final int id) {
        UnitModel unit = mRealm.where(UnitModel.class).equalTo("id", id).findAll().get(0);
        return unit;
    }

    public PresentController.DELETE_RESULT deleteUnit(final int id) {
        PresentController.DELETE_RESULT delete_result = PresentController.DELETE_RESULT.FAIL;
        mRealm.beginTransaction();
        RealmResults results = mRealm.where(UnitModel.class).equalTo("id", id).findAll();
        if (results.size() > 0) {
            results.remove(0);
            delete_result = PresentController.DELETE_RESULT.SUCCESS;
        }
        mRealm.commitTransaction();
        return delete_result;
    }

    public RealmResults<UnitModel> getAllUnits() {
        RealmResults<UnitModel> results = mRealm.where(UnitModel.class).findAll();
        return results;
    }


    public void updateUnit(int id) {
        UnitModel unit = getUnit(id);
        if (unit == null) {
            return;
        }
        mRealm.beginTransaction();
        String newUpdateTime = TextUtil.calendar2String(Calendar.getInstance());
        unit.setReviseTime(unit.getReviseTime() + 1);
        unit.setUpdateTime(newUpdateTime);
        mRealm.commitTransaction();
    }

    public void updatePriority() {
        mRealm.beginTransaction();
        RealmResults<UnitModel> results = getAllUnits();
        for (int i = 0; i < results.size(); i++) {
            UnitModel unit = results.get(i);
            String oldUpdateTime = unit.getUpdateTime();
            String newUpdateTime = TextUtil.calendar2String(Calendar.getInstance());
            double newPriority = RememberUtil.getInstance().computePriority(oldUpdateTime, newUpdateTime, unit.getReviseTime());
            unit.setRememberRatio(newPriority);
        }
        mRealm.commitTransaction();
    }

//    public void createOrUpdateRevisedModel(final String date, final int id) {
//        mRealm.beginTransaction();
//        RealmResults<RevisedModel> results = getRevisedModel(date);
//        RevisedModel revisedModel = null;
//        if (results.size() <= 0) {
//            revisedModel = new RevisedModel();
//            revisedModel.setDate(date);
//        } else {
//            revisedModel = results.get(0);
//        }
//        revisedModel.getRevisedIdList().add(id);
//        mRealm.copyToRealm(revisedModel);
//        mRealm.commitTransaction();
//    }
//
//    public RealmResults<RevisedModel> getRevisedModel(final String date) {
//        RealmResults<RevisedModel> results = mRealm.where(RevisedModel.class).equalTo("date", date).findAll();
//        return results;
//    }


}
