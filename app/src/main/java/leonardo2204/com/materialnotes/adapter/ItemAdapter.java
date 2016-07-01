package leonardo2204.com.materialnotes.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter;

import java.util.List;

import leonardo2204.com.materialnotes.adapter.delegates.CheckboxDelegate;
import leonardo2204.com.materialnotes.adapter.delegates.ImageDelegate;
import leonardo2204.com.materialnotes.model.Item;

/**
 * Created by leonardo on 6/30/16.
 */

public class ItemAdapter extends ListDelegationAdapter<List<Item>> {

    private OnItemClickListener onItemClickListener;

    public ItemAdapter(Activity activity, List<Item> items) {
        delegatesManager.addDelegate(new CheckboxDelegate(activity));
        delegatesManager.addDelegate(new ImageDelegate(activity));

        setItems(items);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null)
                    onItemClickListener.onClick();
            }
        });
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick();
    }

}
