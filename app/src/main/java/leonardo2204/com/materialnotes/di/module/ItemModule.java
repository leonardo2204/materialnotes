package leonardo2204.com.materialnotes.di.module;

import dagger.Module;
import dagger.Provides;
import leonardo2204.com.materialnotes.di.DaggerScope;
import leonardo2204.com.materialnotes.di.component.ItemComponent;
import leonardo2204.com.materialnotes.domain.executor.PostExecutionThread;
import leonardo2204.com.materialnotes.domain.executor.ThreadExecutor;
import leonardo2204.com.materialnotes.domain.interactor.GetItemsUseCase;
import leonardo2204.com.materialnotes.domain.repository.ItemsRepository;

/**
 * Created by leonardo on 7/29/16.
 */

@Module
public class ItemModule {

    @Provides
    @DaggerScope(ItemComponent.class)
    public GetItemsUseCase providesGetItemsUseCase(ItemsRepository itemsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetItemsUseCase(itemsRepository, threadExecutor, postExecutionThread);
    }
}
