package leonardo2204.com.materialnotes.di.component;

import dagger.Component;
import io.realm.Realm;
import leonardo2204.com.materialnotes.controller.RootController;
import leonardo2204.com.materialnotes.di.DaggerScope;
import leonardo2204.com.materialnotes.di.ExposedComponent;
import leonardo2204.com.materialnotes.di.ExposedModule;
import leonardo2204.com.materialnotes.di.module.RootModule;

/**
 * Created by leonardo on 7/14/16.
 */

@Component(modules = {RootModule.class, ExposedModule.class})
@DaggerScope(RootComponent.class)
public interface RootComponent extends ExposedComponent {
    Realm realm();
    void inject(RootController rootController);
}
