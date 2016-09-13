package leonardo2204.com.materialnotes.di;

import dagger.Module;
import dagger.Provides;
import leonardo2204.com.materialnotes.ActionBarOwner;
import leonardo2204.com.materialnotes.UIThread;
import leonardo2204.com.materialnotes.data.executor.JobExecutor;
import leonardo2204.com.materialnotes.data.repository.ItemsRepositoryImpl;
import leonardo2204.com.materialnotes.di.component.RootComponent;
import leonardo2204.com.materialnotes.domain.executor.PostExecutionThread;
import leonardo2204.com.materialnotes.domain.executor.ThreadExecutor;
import leonardo2204.com.materialnotes.domain.repository.ItemsRepository;

/**
 * Created by leonardo on 7/29/16.
 */
@Module
public class ExposedModule {

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

    @Provides
    @DaggerScope(RootComponent.class)
    public ItemsRepository providesItemsRepository() {
        return new ItemsRepositoryImpl();
    }

}
