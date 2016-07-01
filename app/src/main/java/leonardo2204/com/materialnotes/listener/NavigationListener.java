package leonardo2204.com.materialnotes.listener;

import android.support.annotation.IdRes;

/**
 * Created by leonardo on 6/30/16.
 */

public interface NavigationListener {
    void setToolbarTitle(String title);
    void setMenuItemSelected(@IdRes int menuId);
}
