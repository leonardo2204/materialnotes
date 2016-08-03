package leonardo2204.com.materialnotes.data.repository;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import leonardo2204.com.materialnotes.domain.repository.ItemsRepository;
import leonardo2204.com.materialnotes.model.Checkboxes;
import leonardo2204.com.materialnotes.model.ImageItem;
import leonardo2204.com.materialnotes.model.Item;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by leonardo on 7/29/16.
 */

public class ItemsRepositoryImpl implements ItemsRepository {

    @Override
    public Observable<List<Item>> fetchAllItems() {
        final List<Item> items = new ArrayList<>();
        final Realm realm = Realm.getDefaultInstance();

        final RealmQuery<Checkboxes> queryCheck = realm.where(Checkboxes.class);
        final RealmQuery<ImageItem> queryImage = realm.where(ImageItem.class);

        return queryCheck.findAll()
                .asObservable()
                .flatMap(new Func1<RealmResults<Checkboxes>, Observable<RealmResults<ImageItem>>>() {
                    @Override
                    public Observable<RealmResults<ImageItem>> call(RealmResults<Checkboxes> checkboxes) {
                        for (Checkboxes checks : checkboxes)
                            items.add(realm.copyFromRealm(checks));

                        return queryImage.findAll().asObservable();
                    }
                })
                .flatMap(new Func1<RealmResults<ImageItem>, Observable<List<Item>>>() {
                    @Override
                    public Observable<List<Item>> call(RealmResults<ImageItem> imageItems) {
                        for (ImageItem images : imageItems)
                            items.add(realm.copyFromRealm(images));

                        return Observable.create(new Observable.OnSubscribe<List<Item>>() {
                            @Override
                            public void call(Subscriber<? super List<Item>> subscriber) {
                                subscriber.onNext(items);
                            }
                        });
                    }
                }).doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        realm.close();
                    }
                });
    }
}
