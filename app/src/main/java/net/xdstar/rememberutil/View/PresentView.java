package net.xdstar.rememberutil.View;

import java.util.ArrayList;

/**
 * Created by ljy on 16/10/24.
 */

public interface PresentView {
    void updateUnitView(int flag, int id);

    void updateRevisedView(int count, ArrayList<Integer> idList);
}
