package leonardo2204.com.materialnotes.data.repository;

import java.util.ArrayList;
import java.util.List;

import leonardo2204.com.materialnotes.domain.repository.ItemsRepository;
import leonardo2204.com.materialnotes.model.Item;
import rx.Observable;

/**
 * Created by leonardo on 7/29/16.
 */

public class ItemsRepositoryImpl implements ItemsRepository {

    @Override
    public Observable<List<Item>> fetchAllItems() {
        return Observable.just(new ArrayList<>());
    }
}
