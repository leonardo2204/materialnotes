package leonardo2204.com.materialnotes.presenter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import leonardo2204.com.materialnotes.model.Checkboxes;
import leonardo2204.com.materialnotes.view.CheckboxView;

/**
 * Created by leonardo on 9/14/16.
 */

public class CheckboxPresenter extends MvpBasePresenter<CheckboxView> implements CheckboxView {

    @Override
    public void attachView(CheckboxView view) {
        super.attachView(view);
    }

    @Override
    public void saveCheckbox(Checkboxes checkboxes) {
    }
}
