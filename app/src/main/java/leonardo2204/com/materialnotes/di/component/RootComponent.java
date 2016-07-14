package leonardo2204.com.materialnotes.di.component;

import dagger.Component;
import leonardo2204.com.materialnotes.ActionBarOwner;
import leonardo2204.com.materialnotes.UIThread;
import leonardo2204.com.materialnotes.controller.RootController;
import leonardo2204.com.materialnotes.di.DaggerScope;
import leonardo2204.com.materialnotes.di.module.RootModule;
import leonardo2204.com.materialnotes.domain.executor.PostExecutionThread;
import leonardo2204.com.materialnotes.domain.executor.ThreadExecutor;

/**
 * Created by leonardo on 7/14/16.
 */

@Component(modules = RootModule.class)
@DaggerScope(RootComponent.class)
public interface RootComponent {
    ActionBarOwner actionBarOwner();

    PostExecutionThread postExecutionThread();

    UIThread uiThread();

    ThreadExecutor threadExecutor();

    void inject(RootController rootController);
}
