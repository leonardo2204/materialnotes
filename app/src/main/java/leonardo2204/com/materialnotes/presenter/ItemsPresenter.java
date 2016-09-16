package leonardo2204.com.materialnotes.presenter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import leonardo2204.com.materialnotes.model.Checkboxes;
import leonardo2204.com.materialnotes.model.ImageItem;
import leonardo2204.com.materialnotes.model.Item;
import leonardo2204.com.materialnotes.presenter.base.MvpLceRxPresenter;
import leonardo2204.com.materialnotes.view.ItemView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by leonardo on 9/9/16.
 */

public class ItemsPresenter extends MvpLceRxPresenter<ItemView, List<Item>> {

    private Realm realm;

    @Override
    public void attachView(ItemView view) {
        super.attachView(view);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (!retainInstance) {
            realm.removeAllChangeListeners();
        }
        super.detachView(retainInstance);
    }

    public void fetchItems(boolean pullToRefresh) {
        final RealmQuery<Checkboxes> queryCheck = realm.where(Checkboxes.class);
        final RealmQuery<ImageItem> queryImage = realm.where(ImageItem.class);
        final List<Item> items = new ArrayList<>();

        subscribe(Observable.merge(
                queryCheck.findAllAsync().asObservable(),
                queryImage.findAllAsync().asObservable())
                .filter(RealmResults::isLoaded)
                .filter(RealmResults::isValid)
                .map(new Func1<RealmResults<? extends RealmObject>, List<Item>>() {
                    @Override
                    public List<Item> call(RealmResults<? extends RealmObject> realmObjects) {

                        for (RealmObject obj : realmObjects)
                            items.add((Item) obj);

                        return items;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread()), pullToRefresh);
    }
}
