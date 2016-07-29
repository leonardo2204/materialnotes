package leonardo2204.com.materialnotes.di.component;

import dagger.Component;
import leonardo2204.com.materialnotes.controller.ItemsController;
import leonardo2204.com.materialnotes.di.DaggerScope;
import leonardo2204.com.materialnotes.di.ExposedComponent;
import leonardo2204.com.materialnotes.di.module.ItemModule;

/**
 * Created by leonardo on 7/29/16.
 */

@Component(dependencies = RootComponent.class, modules = ItemModule.class)
@DaggerScope(ItemComponent.class)
public interface ItemComponent extends ExposedComponent {
    void inject(ItemsController itemsController);
}
