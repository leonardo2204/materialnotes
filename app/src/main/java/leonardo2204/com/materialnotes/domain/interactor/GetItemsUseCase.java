package leonardo2204.com.materialnotes.domain.interactor;

import javax.inject.Inject;

import leonardo2204.com.materialnotes.domain.executor.PostExecutionThread;
import leonardo2204.com.materialnotes.domain.executor.ThreadExecutor;
import leonardo2204.com.materialnotes.domain.repository.ItemsRepository;
import rx.Observable;

/**
 * Created by leonardo on 7/29/16.
 */

public class GetItemsUseCase extends UseCase {

    private final ItemsRepository itemsRepository;

    @Inject
    public GetItemsUseCase(ItemsRepository itemsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.itemsRepository = itemsRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.itemsRepository.fetchAllItems();
    }
}
