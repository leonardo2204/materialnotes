package leonardo2204.com.materialnotes.adapter.delegates;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.model.ImageItem;
import leonardo2204.com.materialnotes.model.Item;

/**
 * Created by leonardo on 6/30/16.
 */

public class ImageDelegate extends AbsListItemAdapterDelegate<ImageItem, Item, ImageDelegate.ImageViewHolder> {

    private final LayoutInflater layoutInflater;
    private final ImageClickListener onClickListener;

    public ImageDelegate(Activity activity, @NonNull ImageClickListener onClickListener) {
        this.layoutInflater = activity.getLayoutInflater() ;
        this.onClickListener = onClickListener;
    }

    @Override
    protected boolean isForViewType(@NonNull Item item, List<Item> items, int position) {
        return item instanceof ImageItem;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ImageViewHolder(layoutInflater.inflate(R.layout.adapter_image, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ImageItem item, @NonNull ImageViewHolder viewHolder) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null)
                    onClickListener.onClick(item);
            }
        });

        Glide.with(viewHolder.image.getContext()).load(item.getImageUrl()).into(viewHolder.image);
    }

    public interface ImageClickListener {
        void onClick(ImageItem imageItem);
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_container)
        ImageView image;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
