package leonardo2204.com.materialnotes.adapter;

import android.app.Activity;

import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter;

import java.util.List;

import leonardo2204.com.materialnotes.adapter.delegates.CheckboxDelegate;
import leonardo2204.com.materialnotes.adapter.delegates.ImageDelegate;
import leonardo2204.com.materialnotes.model.Item;

/**
 * Created by leonardo on 6/30/16.
 */

public class ItemAdapter extends ListDelegationAdapter<List<Item>> {

    private final CheckboxDelegate.CheckboxClickListener checkboxClickListener;
    private final ImageDelegate.ImageClickListener imageClickListener;

    public ItemAdapter(Activity activity, List<Item> items, CheckboxDelegate.CheckboxClickListener checkboxClickListener, ImageDelegate.ImageClickListener imageClickListener) {
        this.checkboxClickListener = checkboxClickListener;
        this.imageClickListener = imageClickListener;
        delegatesManager.addDelegate(new CheckboxDelegate(activity, this.checkboxClickListener));
        delegatesManager.addDelegate(new ImageDelegate(activity, this.imageClickListener));

        setItems(items);
    }
}
