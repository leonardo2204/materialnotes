package leonardo2204.com.materialnotes.di;

import leonardo2204.com.materialnotes.ActionBarOwner;
import leonardo2204.com.materialnotes.UIThread;
import leonardo2204.com.materialnotes.domain.executor.PostExecutionThread;
import leonardo2204.com.materialnotes.domain.executor.ThreadExecutor;
import leonardo2204.com.materialnotes.domain.repository.ItemsRepository;

/**
 * Created by leonardo on 7/29/16.
 */

public interface ExposedComponent {

    ActionBarOwner actionBarOwner();

    PostExecutionThread postExecutionThread();

    UIThread uiThread();

    ThreadExecutor threadExecutor();

    ItemsRepository itemsRepository();

}
