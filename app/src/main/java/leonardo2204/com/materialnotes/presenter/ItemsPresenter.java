package leonardo2204.com.materialnotes.presenter;

import java.util.ArrayList;
import java.util.List;

import leonardo2204.com.materialnotes.model.Item;
import leonardo2204.com.materialnotes.presenter.base.MvpLceRxPresenter;
import leonardo2204.com.materialnotes.view.ItemView;

/**
 * Created by leonardo on 9/9/16.
 */

public class ItemsPresenter extends MvpLceRxPresenter<ItemView, List<Item>> {

    public void fetchItems(boolean pullToRefresh) {
        final List<Item> items = new ArrayList<>();

//        subscribe(Observable.merge(
//                queryCheck.findAllAsync().asObservable(),
//                queryImage.findAllAsync().asObservable())
//                .filter(RealmResults::isLoaded)
//                .filter(RealmResults::isValid)
//                .map(new Func1<RealmResults<? extends RealmObject>, List<Item>>() {
//                    @Override
//                    public List<Item> call(RealmResults<? extends RealmObject> realmObjects) {
//
//                        for (RealmObject obj : realmObjects)
//                            items.add((Item) obj);
//
//                        return items;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(AndroidSchedulers.mainThread()), pullToRefresh);
//    }
    }
}
