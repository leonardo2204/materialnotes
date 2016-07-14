package leonardo2204.com.materialnotes;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * Created by leonardo on 7/14/16.
 */

public final class ActionBarOwner {

    private Config config;
    private ActionBarCallbacks actionBarCallbacks;

    public ActionBarOwner() {
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
        update();
    }

    public ActionBarCallbacks getActionBarCallbacks() {
        return actionBarCallbacks;
    }

    public void setActionBarCallbacks(ActionBarCallbacks actionBarCallbacks) {
        this.actionBarCallbacks = actionBarCallbacks;
    }

    private void update() {
        if (actionBarCallbacks != null) {
            actionBarCallbacks.setShowHomeEnabled(config.showHomeEnabled);
            actionBarCallbacks.setShowUpButtonEnabled(config.showUpEnabled);

            if (!TextUtils.isEmpty(config.title))
                actionBarCallbacks.setTitle(config.title);
            if (config.setHomeUpDrawable != null)
                actionBarCallbacks.setHomeUpDrawable(config.setHomeUpDrawable);
        }
    }

    public interface ActionBarCallbacks {
        void setShowHomeEnabled(boolean enabled);

        void setShowUpButtonEnabled(boolean enabled);

        void setHomeUpDrawable(Drawable drawable);

        void setTitle(CharSequence title);
    }

    public static class Config {
        public final boolean showHomeEnabled;
        public final boolean showUpEnabled;
        public final Drawable setHomeUpDrawable;
        public final CharSequence title;

        public Config(boolean showHomeEnabled, boolean showUpEnabled, Drawable setHomeUpDrawable, CharSequence title) {
            this.showHomeEnabled = showHomeEnabled;
            this.showUpEnabled = showUpEnabled;
            this.setHomeUpDrawable = setHomeUpDrawable;
            this.title = title;
        }
    }
}
