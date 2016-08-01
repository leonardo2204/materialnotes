package leonardo2204.com.materialnotes.data.repository;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import leonardo2204.com.materialnotes.domain.repository.ItemsRepository;
import leonardo2204.com.materialnotes.model.Checkboxes;
import leonardo2204.com.materialnotes.model.ImageItem;
import leonardo2204.com.materialnotes.model.Item;
import rx.Observable;

/**
 * Created by leonardo on 7/29/16.
 */

public class ItemsRepositoryImpl implements ItemsRepository {

    @Override
    public Observable<List<Item>> fetchAllItems() {

        Realm realm = null;
        final List<Item> items = new ArrayList<>();

        try {
            realm = Realm.getDefaultInstance();

            final RealmResults<Checkboxes> checkboxes = realm.where(Checkboxes.class).findAll();
            final RealmResults<ImageItem> imageItems = realm.where(ImageItem.class).findAll();

            for (Checkboxes checks : checkboxes)
                items.add(realm.copyFromRealm(checks));
            for (ImageItem images : imageItems)
                items.add(realm.copyFromRealm(images));
        } finally {
            if (realm != null)
                realm.close();
        }

        return Observable.just(items);
    }
}
