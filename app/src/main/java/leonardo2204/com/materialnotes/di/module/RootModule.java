package leonardo2204.com.materialnotes.di.module;

import dagger.Module;
import dagger.Provides;
import leonardo2204.com.materialnotes.ActionBarOwner;
import leonardo2204.com.materialnotes.UIThread;
import leonardo2204.com.materialnotes.data.executor.JobExecutor;
import leonardo2204.com.materialnotes.di.DaggerScope;
import leonardo2204.com.materialnotes.di.component.RootComponent;
import leonardo2204.com.materialnotes.domain.executor.PostExecutionThread;
import leonardo2204.com.materialnotes.domain.executor.ThreadExecutor;

/**
 * Created by leonardo on 7/14/16.
 */
@Module
public class RootModule {

    @Provides
    @DaggerScope(RootComponent.class)
    public ActionBarOwner providesActionBarOwner() {
        return new ActionBarOwner();
    }

    @Provides
    @DaggerScope(RootComponent.class)
    public UIThread provideUIThread() {
        return new UIThread();
    }

    @Provides
    @DaggerScope(RootComponent.class)
    public ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @DaggerScope(RootComponent.class)
    public PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

}
