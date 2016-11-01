package net.xdstar.rememberutil.Controller;

import android.content.Context;

import net.xdstar.rememberutil.DataBase.DBController;
import net.xdstar.rememberutil.Model.UnitModel;
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

    public PresentController(Context context, PresentView presentView) {
        this.context = context;
        this.presentView = presentView;
    }

    public UnitModel addUnit(int id) {
        arrayList.add(id);
        UnitModel newUnit = createUnits(id);
        return newUnit;
    }

    public void subUnit(int id) {

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
}
