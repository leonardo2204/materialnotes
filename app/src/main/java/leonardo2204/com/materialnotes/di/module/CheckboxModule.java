package leonardo2204.com.materialnotes.di.module;

import dagger.Module;
import dagger.Provides;
import leonardo2204.com.materialnotes.di.DaggerScope;
import leonardo2204.com.materialnotes.di.component.CheckboxComponent;
import leonardo2204.com.materialnotes.presenter.CheckboxPresenter;

/**
 * Created by leonardo on 8/2/16.
 */
@Module
public class CheckboxModule {

    @Provides
    @DaggerScope(CheckboxComponent.class)
    public CheckboxPresenter providesCheckboxPresenter() {
        return new CheckboxPresenter();
    }

}
