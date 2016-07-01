package leonardo2204.com.materialnotes.adapter.delegates;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.model.CheckboxItem;
import leonardo2204.com.materialnotes.model.CheckboxList;
import leonardo2204.com.materialnotes.model.Item;

/**
 * Created by leonardo on 6/30/16.
 */

public class CheckboxDelegate extends AbsListItemAdapterDelegate<CheckboxList, Item, CheckboxDelegate.CheckboxViewHolder> {

    private final LayoutInflater layoutInflater;

    public CheckboxDelegate(Activity activity) {
        this.layoutInflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull Item item, List<Item> items, int position) {
        return item instanceof CheckboxList;
    }

    @NonNull
    @Override
    public CheckboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new CheckboxViewHolder(layoutInflater.inflate(R.layout.adapter_checkbox, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull CheckboxList item, @NonNull CheckboxViewHolder viewHolder) {
        for(CheckboxItem checkboxItem : item) {
            CheckBox checkBox = createCheckbox(checkboxItem, viewHolder.container.getContext());
            viewHolder.container.addView(checkBox);
        }
    }

    private CheckBox createCheckbox(CheckboxItem item, Context context) {
        CheckBox checkBox = new CheckBox(context);
        ViewGroup.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        checkBox.setLayoutParams(params);

        checkBox.setChecked(item.isChecked());
        checkBox.setText(item.getText());
        checkBox.setPadding(16,16,16,16);

        return checkBox;
    }

    static class CheckboxViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkbox_container)
        LinearLayout container;

        public CheckboxViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
