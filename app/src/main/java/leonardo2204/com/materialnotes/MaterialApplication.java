package leonardo2204.com.materialnotes;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import timber.log.Timber;

/**
 * Created by leonardo on 7/28/16.
 */

public class MaterialApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build());
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }
}
