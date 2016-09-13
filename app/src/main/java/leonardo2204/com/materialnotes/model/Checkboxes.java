package leonardo2204.com.materialnotes.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by leonardo on 7/1/16.
 */

public class Checkboxes extends RealmObject implements Item {
    private RealmList<CheckboxItem> items;

    public RealmList<CheckboxItem> getItems() {
        return items;
    }
}
