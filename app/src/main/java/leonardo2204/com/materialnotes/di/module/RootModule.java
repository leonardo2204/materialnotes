package leonardo2204.com.materialnotes.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import leonardo2204.com.materialnotes.di.DaggerScope;
import leonardo2204.com.materialnotes.di.component.RootComponent;

/**
 * Created by leonardo on 7/14/16.
 */
@Module
public class RootModule {

    private final Context context;

    public RootModule(Context context) {
        this.context = context;
    }

    @Provides
    @DaggerScope(RootComponent.class)
    public Realm providesRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
        return Realm.getDefaultInstance();
    }

}
