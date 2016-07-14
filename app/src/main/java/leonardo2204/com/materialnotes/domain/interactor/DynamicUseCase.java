package leonardo2204.com.materialnotes.domain.interactor;

import android.os.Bundle;

import leonardo2204.com.materialnotes.domain.executor.PostExecutionThread;
import leonardo2204.com.materialnotes.domain.executor.ThreadExecutor;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


/**
 * Created by Leonardo on 09/11/2015.
 */
public abstract class DynamicUseCase extends UseCase {

    protected DynamicUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    public abstract Observable buildUseCaseObservable(Bundle bundle);

    @Override
    protected Observable buildUseCaseObservable() {
        throw new UnsupportedOperationException("Usar o buildUseCaseObservable(Bundle bundle)");
    }

    @SuppressWarnings("unchecked")
    public void execute(Subscriber UseCaseSubscriber, Bundle bundle) {
        this.subscription = this.buildUseCaseObservable(bundle)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(UseCaseSubscriber);
    }
}
