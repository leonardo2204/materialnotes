package leonardo2204.com.materialnotes.model;

/**
 * Created by leonardo on 6/30/16.
 */

public class ImageItem implements Item {
    private String imageUrl;

    public ImageItem() {
    }

    public ImageItem(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
