package net.xdstar.rememberutil.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.xdstar.rememberutil.Controller.PresentController;
import net.xdstar.rememberutil.Model.UnitModel;
import net.xdstar.rememberutil.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PresentView{

    private GridLayout gridNewUnits;
    private GridLayout gridOldUnits;
    private Button btnAddNew;
    private Button btnSubNew;
    private Button btnSubOld;
    private ArrayList<Integer> newIdList = new ArrayList<>();
    private PresentController controller;
    private MainActivity activity = this;
    private static int DELETE_OLD = 0;
    private static int DELETE_NEW = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new PresentController(this, this);
        gridNewUnits = (GridLayout) findViewById(R.id.grid_new_unit);
        gridOldUnits = (GridLayout) findViewById(R.id.grid_old_unit);
        btnAddNew = (Button) findViewById(R.id.btn_add_unit);
        btnSubNew = (Button) findViewById(R.id.btn_sub_unit);
        btnSubOld = (Button) findViewById(R.id.btn_sub_old);
        btnAddNew.setOnClickListener(this);
        btnSubNew.setOnClickListener(this);
        btnSubOld.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showOldUnit();

    }

    @Override
    public void onClick(View v) {
        if (v == btnAddNew) {
           onClickAddUnit();
        } else if (v == btnSubNew) {
            onClickSubNew();
        } else if (v == btnSubOld) {
            onClickSubOld();
        }
    }

    public void onClickAddUnit() {
        DialogPresenter.getInstance().showAddNewDialog(this);
    }

    public void onClickSubNew() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.dialog_layout, null);
//                (ViewGroup) LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        final EditText editText = (EditText) viewGroup.findViewById(R.id.edit);
        final TextView tvTip = (TextView) viewGroup.findViewById(R.id.tv_tip);
        tvTip.setText(getResources().getString(R.string.input_unit_tip));
        builder.setView(viewGroup)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String num = editText.getText().toString();
                            int i = Integer.parseInt(num);
                            if (newIdList.contains(i)) {
                                DialogPresenter.getInstance().showSubTipDialog(activity, i, DELETE_NEW);
                            } else {
                                Toast.makeText(activity, "你没有新添加这个单元!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(activity, "输入整数!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void onClickSubOld() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.dialog_layout, null);
//                (ViewGroup) LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        final EditText editText = (EditText) viewGroup.findViewById(R.id.edit);
        final TextView tvTip = (TextView) viewGroup.findViewById(R.id.tv_tip);
        tvTip.setText(getResources().getString(R.string.input_unit_tip));
        builder.setView(viewGroup)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String num = editText.getText().toString();
                            int i = Integer.parseInt(num);
                            DialogPresenter.getInstance().showSubTipDialog(activity, i, DELETE_OLD);

                        } catch (Exception e) {
                            Toast.makeText(activity, "输入整数!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }


    public void addNewUnitView(UnitModel unit) {
        if (unit == null) {
            Toast.makeText(this, "这个单元已经添加过了", Toast.LENGTH_SHORT).show();
            return;
        }
        newIdList.add(unit.getId());
        updateNewUnitsView();
    }

    public void showOldUnit() {
        gridOldUnits.removeAllViews();
        ArrayList<UnitModel> units = controller.getUnitToBeRevised(4);
        for (UnitModel unit : units) {
            UnitView unitView = new UnitView(this, false, unit.getId(), unit.getUpdateTime(), unit.getReviseTime(), unit.getPriority());
            gridOldUnits.addView(unitView);
        }
    }

    public PresentController getPresentController() {
        return controller;
    }

    public void updateNewUnitsView() {
        gridNewUnits.removeAllViews();
        for (Integer i : newIdList) {
            UnitModel unit = controller.getUnit(i);
            UnitView unitView = new UnitView(this, true, unit.getId(), unit.getUpdateTime(), 0, unit.getPriority());
            gridNewUnits.addView(unitView);
        }
    }

    @Override
    public void updateView(int flag, int id) {
        deleteUnitFromList(id);
        if (flag == DELETE_NEW) {
            updateNewUnitsView();
        } else if (flag == DELETE_OLD) {
            showOldUnit();
        }
    }

    public void deleteUnitFromList(final int id) {
        if (newIdList.contains(id)) {
            Integer idObj  = id;
            newIdList.remove(idObj);
        }
    }

}
