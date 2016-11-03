package net.xdstar.rememberutil.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.xdstar.rememberutil.DataBase.DBController;
import net.xdstar.rememberutil.Model.UnitModel;
import net.xdstar.rememberutil.R;
import net.xdstar.rememberutil.Util.RememberUtil;

/**
 * Created by ljy on 16/11/1.
 */

public class UnitView extends RelativeLayout implements View.OnClickListener {
    private Context context;
    private int id;
    private String updateTime;
    private double priority;
    private int reviseCount;
    private TextView tvId;
    private TextView tvTime;
    private TextView tvReviseCount;
    private TextView tvPriority;
    private boolean isNew;
    private ViewGroup container;


    public UnitView(Context context, final boolean isNew, final int id, final String updateTime, final int reviseCount, final double priority) {
        super(context);
        setParams(isNew, id, updateTime, reviseCount, priority);
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_unit_view, null);
        container = (ViewGroup) viewGroup.findViewById(R.id.container);
        tvId = (TextView) viewGroup.findViewById(R.id.tv_id);
        tvTime = (TextView) viewGroup.findViewById(R.id.tv_time);
        tvReviseCount = (TextView) viewGroup.findViewById(R.id.tv_revise_count);
        tvPriority = (TextView) viewGroup.findViewById(R.id.tv_priority);
        this.setOnClickListener(this);
        this.addView(viewGroup);
        showView();
    }

    public UnitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public void onClick(View v) {
        if (!isNew) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("确定已经完成这个单元的复习了吗？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RememberUtil.getInstance().updateUnit(id);
                            updateView();
                        }
                    })
                    .show();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void updateView() {
        UnitModel unit = DBController.instance().getUnit(id);
        if (unit == null) {
            return;
        }
        container.setBackground(getResources().getDrawable(R.drawable.view_frame_green));
        setParams(false, unit.getId(), unit.getUpdateTime(), unit.getReviseTime(), unit.getPriority());
        showView();
    }

    private void setParams(final boolean isNew, final int id, final String updateTime, final int reviseCount, final double priority) {
        this.id = id;
        this.isNew = isNew;
        this.updateTime = updateTime;
        this.priority = priority;
        this.reviseCount = reviseCount;
    }

    private void showView() {
        tvId.setText(String.valueOf(id) + "单元");
        tvTime.setText(updateTime);
        tvReviseCount.setText("复习次数:" + String.valueOf(reviseCount));
        String priorityStr = String.format("%.4f", priority);
        tvPriority.setText(priorityStr);
    }
}
