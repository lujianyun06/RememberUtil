package net.xdstar.rememberutil.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ljy on 16/10/24.
 */

public class UnitModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String updateTime;
    private String createTime;
    private double rememberRatio;
    private int reviseCount;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public double getRememberRatio() {
        return rememberRatio;
    }

    public void setRememberRatio(double rememberRatio) {
        this.rememberRatio = rememberRatio;
    }

    public int getReviseTime() {
        return reviseCount;
    }

    public void setReviseTime(int reviseCount) {
        this.reviseCount = reviseCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
