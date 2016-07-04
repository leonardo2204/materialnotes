package leonardo2204.com.materialnotes.controller;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import butterknife.BindView;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.controller.base.BaseController;
import leonardo2204.com.materialnotes.model.CheckboxItem;
import leonardo2204.com.materialnotes.view.EndDrawableEditText;

/**
 * Created by leonardo on 7/1/16.
 */

public class CheckboxController extends BaseController implements EndDrawableEditText.OnDrawableClickListener {

    @BindView(R.id.new_item)
    EndDrawableEditText newItemEditText;
    @BindView(R.id.checkboxes_container)
    LinearLayout checkboxesContainer;

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.checkboxes_controller, container, false);
    }

    @Override
    protected void onViewCreated(@NonNull View v) {
        Drawable ds = newItemEditText.getCompoundDrawables()[2];
        ds.setColorFilter(ContextCompat.getColor(getActivity(), R.color.md_black_1000), PorterDuff.Mode.SRC_ATOP);
        newItemEditText.setOnDrawableClickListener(this);
    }

    @Override
    public void onEndDrawableClick() {
        String checkboxText = newItemEditText.getText().toString();
        if (!TextUtils.isEmpty(checkboxText)) {
            checkboxesContainer.addView(createCheckbox(new CheckboxItem(true, checkboxText)));
            newItemEditText.getText().clear();
        }
    }

    private CheckBox createCheckbox(CheckboxItem item) {
        CheckBox checkBox = new CheckBox(getActivity());
        ViewGroup.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        checkBox.setLayoutParams(params);

        checkBox.setChecked(item.isChecked());
        checkBox.setText(item.getText());

        return checkBox;
    }

}
