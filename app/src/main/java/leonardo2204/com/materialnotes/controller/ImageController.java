package leonardo2204.com.materialnotes.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.controller.base.BaseController;

/**
 * Created by leonardo on 7/1/16.
 */

public class ImageController extends BaseController {

    @BindView(R.id.image_result)
    ImageView imageView;
    private final File file;

    public ImageController(Bundle bundle) {
        this.file = (File) bundle.getSerializable("image");
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.picture_controller, container, false);
    }

    @Override
    protected void onViewCreated(@NonNull View v) {
        Glide.with(getActivity()).load(file).into(imageView);
    }
}
