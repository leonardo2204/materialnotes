package leonardo2204.com.materialnotes.presenter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import io.realm.Realm;
import leonardo2204.com.materialnotes.model.Checkboxes;
import leonardo2204.com.materialnotes.view.CheckboxView;

/**
 * Created by leonardo on 9/14/16.
 */

public class CheckboxPresenter extends MvpBasePresenter<CheckboxView> implements CheckboxView {

    private Realm realm;

    @Override
    public void attachView(CheckboxView view) {
        super.attachView(view);

        realm = Realm.getDefaultInstance();
    }

    @Override
    public void saveCheckbox(Checkboxes checkboxes) {
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(checkboxes));
    }
}
