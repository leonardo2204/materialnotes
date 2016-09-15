package leonardo2204.com.materialnotes.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import leonardo2204.com.materialnotes.model.Checkboxes;

/**
 * Created by leonardo on 9/14/16.
 */

public interface CheckboxView extends MvpView {
    void saveCheckbox(Checkboxes checkboxes);
}
