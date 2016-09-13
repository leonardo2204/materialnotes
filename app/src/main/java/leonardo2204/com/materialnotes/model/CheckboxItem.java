package leonardo2204.com.materialnotes.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by leonardo on 6/30/16.
 */

public class CheckboxItem extends RealmObject implements Item {
    private boolean isChecked;
    @Required
    private String text;

    public CheckboxItem() {
    }

    public CheckboxItem(boolean isChecked, String text) {
        this.isChecked = isChecked;
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
