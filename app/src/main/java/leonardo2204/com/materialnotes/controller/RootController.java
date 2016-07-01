package leonardo2204.com.materialnotes.controller;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import butterknife.BindView;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.controller.base.BaseController;
import leonardo2204.com.materialnotes.listener.NavigationListener;

/**
 * Created by leonardo on 6/29/16.
 */

public class RootController extends BaseController implements NavigationListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.child_container)
    FrameLayout childContainer;
    @BindView(R.id.navigation_view)
    protected NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    private int menuItemId;
    private Router childRouter;

    public RootController() {
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(navigationView);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        if(childContainer != null && getChildRouter(childContainer,null) != null && !getChildRouter(childContainer,null).hasRootController())
            getChildRouter(childContainer,null).setRoot(RouterTransaction.with(new ItemsController()));
            //getChildRouter(childContainer,null).pushController(RouterTransaction.with(new ItemsController()));
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.root_controller, container, false);
    }

    @Override
    protected void onViewCreated(@NonNull View v) {
        setupUI();
    }

    private void setupUI() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        childRouter = getChildRouter(childContainer, null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (menuItemId == item.getItemId()) {
                    drawerLayout.closeDrawer(navigationView);
                    return false;
                }

                final Controller destination;
                String tag;

//                switch (item.getItemId()) {
//                    case R.id.menu_entries:
//                        tag = "HOME";
//                        destination = new HomeController(RootController.this);
//                        break;
//                    case R.id.menu_drafts:
//                        tag = "DRAFT";
//                        destination = new DraftController(RootController.this);
//                        break;
//                    default:
//                        tag = "HOME";
//                        destination = new HomeController(RootController.this);
//                        break;
//                }

//                if (childRouter.getControllerWithTag(tag) == null)
//                    childRouter.pushController(RouterTransaction.with(destination)
//                            .pushChangeHandler(new SplitHalfChangeHandler(1300))
//                            .popChangeHandler(new SplitHalfChangeHandler())
//                            .tag(tag));
//                else
//                    childRouter.popToTag(tag);

                drawerLayout.closeDrawer(navigationView);
                item.setChecked(true);
                menuItemId = item.getItemId();
                return true;
            }
        });

    }

    @Override
    public void setToolbarTitle(String title) {

    }

    @Override
    public void setMenuItemSelected(@IdRes int menuId) {

    }
}