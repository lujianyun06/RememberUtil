package net.xdstar.rememberutil.Model;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by ljy on 16/11/3.
 */

public class RevisedModel implements Serializable{

    private String date;
    private HashSet<Integer> revisedIdList = new HashSet<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashSet<Integer> getRevisedIdList() {
        return revisedIdList;
    }

    public void setRevisedIdList(HashSet<Integer> revisedIdList) {
        this.revisedIdList = revisedIdList;
    }
}
