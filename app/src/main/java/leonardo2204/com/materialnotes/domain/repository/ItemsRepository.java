package leonardo2204.com.materialnotes.domain.repository;

import java.util.List;

import leonardo2204.com.materialnotes.model.Item;
import rx.Observable;

/**
 * Created by leonardo on 7/29/16.
 */

public interface ItemsRepository {
    Observable<List<Item>> fetchAllItems();
}
