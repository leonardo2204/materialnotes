package leonardo2204.com.materialnotes.adapter;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.adapter.interfaces.ItemTouchHelpeAdapter;
import leonardo2204.com.materialnotes.model.CheckboxItem;

/**
 * Created by leonardo on 8/3/16.
 */

public class CheckboxItemsAdapter extends RecyclerView.Adapter<CheckboxItemsAdapter.CheckboxViewHolder> implements ItemTouchHelpeAdapter {

    private final OnStartDragListener onStartDragListener;
    private List<CheckboxItem> items = new ArrayList<>();

    public CheckboxItemsAdapter(OnStartDragListener onStartDragListener) {
        this.onStartDragListener = onStartDragListener;
    }

    public CheckboxItemsAdapter(List<CheckboxItem> items, OnStartDragListener onStartDragListener) {
        this.items = items;
        this.onStartDragListener = onStartDragListener;
    }

    public void setItems(List<CheckboxItem> items) {
        this.items = items;
    }

    public void addItem(CheckboxItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    @Override
    public CheckboxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CheckboxViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_adapter_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final CheckboxViewHolder holder, int position) {
        CheckboxItem item = items.get(position);

        holder.checkBox.setChecked(item.isChecked());
        holder.checkBox.setText(item.getText());

        holder.handler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN)
                    onStartDragListener.onStartDrag(holder);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        CheckboxItem prev = items.remove(fromPosition);
        items.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    static class CheckboxViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkbox_item)
        CheckBox checkBox;
        @BindView(R.id.handle)
        ImageView handler;

        public CheckboxViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
