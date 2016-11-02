package net.xdstar.rememberutil.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.xdstar.rememberutil.Model.UnitModel;
import net.xdstar.rememberutil.R;

/**
 * Created by ljy on 16/11/2.
 */

public class DialogPresenter {
    private static DialogPresenter instance = null;

    static DialogPresenter getInstance() {
        if (instance == null) {
            instance = new DialogPresenter();
        }
        return instance;
    }

    public void showAddNewDialog(final MainActivity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.dialog_layout, null);
        final EditText editText = (EditText) viewGroup.findViewById(R.id.edit);
        final TextView tvTip = (TextView) viewGroup.findViewById(R.id.tv_tip);
        tvTip.setText(activity.getResources().getString(R.string.input_unit_tip));
        builder.setView(viewGroup)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String num = editText.getText().toString();
                            int i = Integer.parseInt(num);
                            UnitModel unit = activity.getPresentController().addUnit(i);
                            activity.addNewUnitView(unit);
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

    public void showSubOldDialog(final MainActivity activity, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getString(R.string.delete_old_tip))
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {

                            activity.getPresentController().subUnit(id);
                            activity.showOldUnit();
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
}
