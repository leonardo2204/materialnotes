package leonardo2204.com.materialnotes.di.component;

import dagger.Component;
import leonardo2204.com.materialnotes.controller.CheckboxController;
import leonardo2204.com.materialnotes.di.DaggerScope;
import leonardo2204.com.materialnotes.di.module.CheckboxModule;

/**
 * Created by leonardo on 8/2/16.
 */
@Component(modules = CheckboxModule.class, dependencies = RootComponent.class)
@DaggerScope(CheckboxComponent.class)
public interface CheckboxComponent {
    void inject(CheckboxController checkboxController);
}
