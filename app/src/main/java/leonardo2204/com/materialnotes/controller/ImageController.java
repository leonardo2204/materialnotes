package leonardo2204.com.materialnotes.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import butterknife.BindView;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.controller.base.BaseController;

/**
 * Created by leonardo on 7/1/16.
 */

public class ImageController extends BaseController {

    private final File file;
    private final String url;
    @BindView(R.id.image_result)
    ImageView imageView;
    @BindView(R.id.picture_progress)
    ProgressBar progressBar;

    public ImageController(Bundle bundle) {
        this.file = (File) bundle.getSerializable("image");
        this.url = bundle.getString("url");
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.picture_controller, container, false);
    }

    @Override
    protected void onViewCreated(@NonNull View v) {
        if (file != null)
            Glide.with(getActivity()).load(file).listener(new RequestListener<File, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, File model, Target<GlideDrawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, File model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(imageView);
        else
            Glide.with(getActivity()).load(url).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(imageView);
    }
}
