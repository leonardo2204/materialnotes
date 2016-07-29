package leonardo2204.com.materialnotes.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import leonardo2204.com.materialnotes.ActionBarOwner;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.adapter.ItemAdapter;
import leonardo2204.com.materialnotes.adapter.delegates.CheckboxDelegate;
import leonardo2204.com.materialnotes.adapter.delegates.ImageDelegate;
import leonardo2204.com.materialnotes.controller.base.BaseController;
import leonardo2204.com.materialnotes.di.component.DaggerItemComponent;
import leonardo2204.com.materialnotes.di.component.ItemComponent;
import leonardo2204.com.materialnotes.di.module.ItemModule;
import leonardo2204.com.materialnotes.domain.interactor.GetItemsUseCase;
import leonardo2204.com.materialnotes.model.Checkboxes;
import leonardo2204.com.materialnotes.model.ImageItem;
import leonardo2204.com.materialnotes.model.Item;
import pl.aprilapps.easyphotopicker.EasyImage;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by leonardo on 6/30/16.
 */

public class ItemsController extends BaseController {

    @Inject
    protected ActionBarOwner actionBarOwner;
    @Inject
    protected GetItemsUseCase getItemsUseCase;
    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    @BindView(R.id.main_fab)
    FabSpeedDial fab;
    @BindView(R.id.overlay_container)
    FrameLayout overlayContainer;
    @BindView(R.id.refreshItemsLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private StaggeredGridLayoutManager sglm;
    private ItemComponent itemComponent;
    private Subscription scrollEventObservable;
    private Subscription swipeRefreshSubscription;

    private ItemAdapter itemAdapter;
    private CheckboxDelegate.CheckboxClickListener checkboxClickListener = new CheckboxDelegate.CheckboxClickListener() {
        @Override
        public void onClick(Checkboxes checkboxItems) {
            launchCheckboxController();
        }
    };

    private ImageDelegate.ImageClickListener imageClickListener = new ImageDelegate.ImageClickListener() {
        @Override
        public void onClick(ImageItem imageItem) {
            launchImageController(imageItem.getImageUrl());
        }
    };

    @Override
    protected void onCreate() {
        itemComponent = DaggerItemComponent
                .builder()
                .rootComponent(((RootController) getParentController()).getRootComponent())
                .itemModule(new ItemModule())
                .build();
        itemComponent.inject(this);
        getItemsUseCase.execute(new GetItemsSubscriber());
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.items_controller, container, false);
    }

    @Override
    protected void onViewCreated(@NonNull View v) {
        setupUI();
    }

    @Override
    protected void onDestroyView(View view) {
        super.onDestroyView(view);
        scrollEventObservable.unsubscribe();
        swipeRefreshSubscription.unsubscribe();
    }

    private void setupUI(){
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        //swipeRefreshLayout.setRefreshing(true);
        sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        setupAdapter();
        setupFabNavigation();
        sglm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        //rvItems.addItemDecoration(new SpanItemDecorator(12, 12));
        rvItems.setLayoutManager(sglm);
        rvItems.setAdapter(itemAdapter);

        swipeRefreshSubscription = RxSwipeRefreshLayout.refreshes(swipeRefreshLayout).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                getItemsUseCase.execute(new GetItemsSubscriber());
            }
        });

        scrollEventObservable = RxRecyclerView.scrollEvents(rvItems).subscribe(new Action1<RecyclerViewScrollEvent>() {
            @Override
            public void call(RecyclerViewScrollEvent recyclerViewScrollEvent) {
                swipeRefreshLayout.setRefreshing(sglm.findFirstVisibleItemPositions(null).length == 0);
            }
        });
    }

    private void setupAdapter() {
        itemAdapter = new ItemAdapter(getActivity(), null, checkboxClickListener, imageClickListener);
    }

    private void setupFabNavigation() {
        fab.setMenuListener(new FabSpeedDial.MenuListener() {

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.fab_camera:
                        EasyImage.openCamera(getActivity(), 0);
                        registerForActivityResult(EasyImage.REQ_TAKE_PICTURE);
                        return true;
                    case R.id.fab_image:
                        EasyImage.openGallery(getActivity(), 0);
                        registerForActivityResult(EasyImage.REQ_PICK_PICTURE_FROM_GALLERY);
                        return true;
                    case R.id.fab_drawing:
                        getParentController().getRouter().pushController(RouterTransaction.with(new DrawingController())
                                .pushChangeHandler(new FadeChangeHandler())
                                .popChangeHandler(new FadeChangeHandler()));
                        return true;
                    case R.id.fab_checkbox:
                        launchCheckboxController();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void launchCheckboxController() {
        getRouter().pushController(RouterTransaction.with(new CheckboxController())
                .pushChangeHandler(new FadeChangeHandler(false))
                .popChangeHandler(new FadeChangeHandler()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource imageSource, int i) {

            }

            @Override
            public void onImagePicked(File file, EasyImage.ImageSource imageSource, int i) {
                launchImageController(file);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource imageSource, int i) {

            }
        });
    }

    private void launchImageController(File file) {
        Bundle bundle = new Bundle(1);
        bundle.putSerializable("image", file);
        getParentController().getRouter().pushController(RouterTransaction.with(new ImageController(bundle))
                .pushChangeHandler(new FadeChangeHandler())
                .popChangeHandler(new FadeChangeHandler()));
    }

    private void launchImageController(String url) {
        Bundle bundle = new Bundle(1);
        bundle.putSerializable("url", url);
        getParentController().getRouter().pushController(RouterTransaction.with(new ImageController(bundle))
                .pushChangeHandler(new FadeChangeHandler())
                .popChangeHandler(new FadeChangeHandler()));
    }

    private class GetItemsSubscriber extends Subscriber<List<Item>> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            swipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();
        }

        @Override
        public void onNext(List<Item> items) {
            itemAdapter.setItems(items);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
