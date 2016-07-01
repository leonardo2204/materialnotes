package leonardo2204.com.materialnotes.controller;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.controller.base.BaseController;
import leonardo2204.com.materialnotes.view.EndDrawableEditText;

/**
 * Created by leonardo on 7/1/16.
 */

public class CheckboxController extends BaseController {

    @BindView(R.id.new_item)
    EndDrawableEditText newItemEditText;

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.checkboxes_controller, container, false);
    }

    @Override
    protected void onViewCreated(@NonNull View v) {
        newItemEditText.setSupportBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(),R.color.colorAccent)));
    }
}
