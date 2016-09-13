package leonardo2204.com.materialnotes.controller;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.jakewharton.rxbinding.support.design.widget.RxNavigationView;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import leonardo2204.com.materialnotes.ActionBarOwner;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.controller.base.BaseController;
import leonardo2204.com.materialnotes.di.ExposedModule;
import leonardo2204.com.materialnotes.di.component.DaggerRootComponent;
import leonardo2204.com.materialnotes.di.component.RootComponent;
import leonardo2204.com.materialnotes.di.module.RootModule;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by leonardo on 6/29/16.
 */

public class RootController extends BaseController implements ActionBarOwner.ActionBarCallbacks {

    @BindView(R.id.navigation_view)
    protected NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;
    @Inject
    protected ActionBarOwner actionBarOwner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.child_container)
    FrameLayout childContainer;
    @BindDrawable(R.drawable.ic_menu)
    Drawable menuDrawable;
    private int menuItemId;
    private Router childRouter;
    private RootComponent rootComponent;
    private Subscription navigationMenuSubscription;

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
    protected void onCreate() {
        rootComponent = DaggerRootComponent.builder()
                .rootModule(new RootModule())
                .exposedModule(new ExposedModule())
                .build();
        rootComponent.inject(this);
        //MockClass.mockList();
    }

    public RootComponent getRootComponent() {
        return rootComponent;
    }

    @Override
    protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        if(childContainer != null && getChildRouter(childContainer,null) != null && !getChildRouter(childContainer,null).hasRootController())
            getChildRouter(childContainer,null).setRoot(RouterTransaction.with(new ItemsController()));
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.root_controller, container, false);
    }

    @Override
    protected void onViewCreated(@NonNull View v) {
        setupUI();
    }

    @Override
    protected void onDestroy() {
        actionBarOwner.setActionBarCallbacks(null);
        actionBarOwner = null;
        super.onDestroy();
    }

    @Override
    protected void onDestroyView(View view) {
        super.onDestroyView(view);
        navigationMenuSubscription.unsubscribe();
    }

    private void setupUI() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        actionBarOwner.setActionBarCallbacks(this);
        actionBarOwner.setConfig(new ActionBarOwner.Config(true, true, menuDrawable, "Items"));
        childRouter = getChildRouter(childContainer, null);
        navigationMenuSubscription = RxNavigationView.itemSelections(navigationView).subscribe(new Action1<MenuItem>() {
            @Override
            public void call(MenuItem menuItem) {
                if (menuItemId == menuItem.getItemId()) {
                    drawerLayout.closeDrawer(navigationView);
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
                menuItem.setChecked(true);
                menuItemId = menuItem.getItemId();
            }
        });
    }

    @Override
    public void setShowHomeEnabled(boolean enabled) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayShowHomeEnabled(enabled);
    }

    @Override
    public void setShowUpButtonEnabled(boolean enabled) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(enabled);
    }

    @Override
    public void setHomeUpDrawable(Drawable drawable) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setHomeAsUpIndicator(drawable);
    }

    @Override
    public void setTitle(CharSequence title) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(title);
    }
}
