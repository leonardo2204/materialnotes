package leonardo2204.com.materialnotes.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;

import java.io.File;

import butterknife.BindView;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import leonardo2204.com.materialnotes.MockClass;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.adapter.ItemAdapter;
import leonardo2204.com.materialnotes.controller.base.BaseController;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by leonardo on 6/30/16.
 */

public class ItemsController extends BaseController {

    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    @BindView(R.id.main_fab)
    FabSpeedDial fab;
    @BindView(R.id.overlay_container)
    FrameLayout overlayContainer;

    private ItemAdapter itemAdapter;
    private ItemAdapter.OnItemClickListener onItemClickListener = new ItemAdapter.OnItemClickListener() {
        @Override
        public void onClick() {
            Snackbar.make(getView(),"clicked",Snackbar.LENGTH_SHORT).show();
        }
    };


    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.items_controller, container, false);
    }

    @Override
    protected void onViewCreated(@NonNull View v) {
        setupUI();
    }

    private void setupUI(){
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        setupAdapter();
        setupFabNavigation();
        sglm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        //rvItems.addItemDecoration(new SpanItemDecorator(12, 12));
        rvItems.setLayoutManager(sglm);
        rvItems.setAdapter(itemAdapter);
    }

    private void setupAdapter() {
        itemAdapter = new ItemAdapter(getActivity(), MockClass.mockList());
        itemAdapter.setOnItemClickListener(onItemClickListener);
    }

    private void setupFabNavigation() {
        fab.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

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
                        getParentController().getChildRouters().get(0).pushController(RouterTransaction.with(new DrawingController()));
                        //.pushChangeHandler(new FadeChangeHandler())
                        //.popChangeHandler(new FadeChangeHandler()));
                        return true;
                    case R.id.fab_checkbox:
                        getRouter().pushController(RouterTransaction.with(new CheckboxController())
                                .pushChangeHandler(new FadeChangeHandler(false))
                                .popChangeHandler(new FadeChangeHandler()));
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onMenuClosed() {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource imageSource, int i) {

            }

            @Override
            public void onImagePicked(File file, EasyImage.ImageSource imageSource, int i) {
                Bundle bundle = new Bundle(1);
                bundle.putSerializable("image", file);
                getParentController().getRouter().pushController(RouterTransaction.with(new ImageController(bundle))
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler()));
            }

            @Override
            public void onCanceled(EasyImage.ImageSource imageSource, int i) {

            }
        });
    }
}
