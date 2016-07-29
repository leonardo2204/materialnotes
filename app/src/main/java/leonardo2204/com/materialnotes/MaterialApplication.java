package leonardo2204.com.materialnotes;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by leonardo on 7/28/16.
 */

public class MaterialApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
