package net.xdstar.rememberutil.Controller;

import android.content.Context;
import android.widget.Toast;

import net.xdstar.rememberutil.DataBase.DBController;
import net.xdstar.rememberutil.Model.UnitModel;
import net.xdstar.rememberutil.R;
import net.xdstar.rememberutil.Util.TextUtil;
import net.xdstar.rememberutil.View.PresentView;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by ljy on 16/10/24.
 */

public class PresentController {
    private Context context;
    private PresentView presentView;
    private ArrayList<Integer> arrayList = new ArrayList<>();
    public enum DELETE_RESULT{
        SUCCESS,
        FAIL
    }


    public PresentController(Context context, PresentView presentView) {
        this.context = context;
        this.presentView = presentView;
    }

    public UnitModel addUnit(int id) {
        UnitModel newUnit = createUnits(id);
        return newUnit;
    }

    public void subUnit(int id) {
        DELETE_RESULT result = DBController.instance().deleteUnit(id);
        if(result == DELETE_RESULT.SUCCESS) {
            Toast.makeText(context, context.getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getString(R.string.delete_fail), Toast.LENGTH_SHORT).show();
        }
    }

    private UnitModel createUnits(int id) {
        ArrayList<Integer> curIds = new ArrayList<>();
        RealmResults<UnitModel> results = DBController.instance().getAllUnits();
        for (UnitModel unitModel : results) {
            if (unitModel.getId() == id) {
                return null;
            }
        }

        UnitModel newUnit = createNewUnit(id);
        DBController.instance().setUnit(newUnit);
        arrayList.add(id);
        return newUnit;
    }


    private UnitModel createNewUnit(int id) {
        UnitModel newUnit = new UnitModel();
        newUnit.setId(id);
        Calendar calendar = Calendar.getInstance();
        String updateTime = TextUtil.Calendar2String(calendar);
        newUnit.setUpdateTime(updateTime);
        newUnit.setReviseTime(0);
        newUnit.setPriority(1.6);
        return newUnit;
    }

//    private ArrayList<UnitModel> checkRevise() {
//        RealmResults<UnitModel> results = DBController.instance().getAllUnits();
//        for (UnitModel unitModel : results) {
////            String updateTime =
//        }
//    }

    public ArrayList<UnitModel> getUnitToBeRevised(int unitCounts) {
        ArrayList<UnitModel> unitModels = new ArrayList<>();
        DBController.instance().updatePriority();
        RealmResults<UnitModel> units = DBController.instance().getAllUnits();
        units.sort("priority", Sort.DESCENDING);
        if (units.size() <= unitCounts)
            unitCounts = units.size();
        unitCounts = (units.size() <= unitCounts)?units.size():unitCounts;
        for (int i = 0; i < unitCounts; i++) {
            unitModels.add(units.get(i));
        }
        return unitModels;
    }

    public UnitModel getUnit(final int id) {
        UnitModel unitModel = DBController.instance().getUnit(id);
        return  unitModel;
    }
}
