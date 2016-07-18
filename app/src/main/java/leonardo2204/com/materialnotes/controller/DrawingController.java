package leonardo2204.com.materialnotes.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import leonardo2204.com.materialnotes.DetachableClickListener;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.controller.base.BaseController;
import leonardo2204.com.materialnotes.view.DrawingView;

/**
 * Created by leonardo on 6/30/16.
 */

public class DrawingController extends BaseController {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawing_view)
    DrawingView drawingView;
    @BindView(R.id.drawing_options_bottom)
    NestedScrollView drawingBottomSheet;
    @BindView(R.id.drawing_options_thick)
    ImageButton thickButton;
    @BindDrawable(R.drawable.ic_brush)
    Drawable brush;

    private BottomSheetBehavior bottomSheetBehavior;
    private boolean canUndo = false;

    public DrawingController() {
        setHasOptionsMenu(true);
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.drawing_controller, container, false);
    }

    @Override
    protected void onViewCreated(@NonNull View v) {
        super.onViewCreated(v);
        setupToolbar();
        setupBottomSheet();
        drawingView.setOnDrawLine(new DrawingView.OnDrawLine() {
            @Override
            public void hasLineOnScreen(boolean hasLine) {
                canUndo = hasLine;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.controller_drawing_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_paint_save:
                Bitmap bmp = drawingView.getDrawingBitmap();
                getRouter().popCurrentController();
                return true;
            case R.id.menu_paint_clear:
                confirmClearDialog();
                return true;
            case R.id.menu_paint_undo:
                drawingView.undoDrawing();
                //item.setEnabled(drawingView.canUndo());
                return true;
            case R.id.menu_paint_redo:
                drawingView.redoDrawing();
                return true;
            case android.R.id.home:
                getRouter().popCurrentController();
                return true;
            default:
                return false;
        }
    }

    @OnClick({R.id.drawing_options_thick, R.id.drawing_options_brush, R.id.drawing_options_paint})
    protected void onDrawingOptions() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setTitle(null);
            toolbar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    private void setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(drawingBottomSheet);
        bottomSheetBehavior.setPeekHeight(brush.getIntrinsicHeight() + 6);
    }

    private void confirmClearDialog() {
        DetachableClickListener positiveClickListener = DetachableClickListener.wrap(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                drawingView.clearCanvas();
            }
        });

        DetachableClickListener negativeClickListener = DetachableClickListener.wrap(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage("Do you really want to clear the canvas ?")
                .setPositiveButton("Remover", positiveClickListener)
                .setNegativeButton("Cancelar", negativeClickListener).show();

        positiveClickListener.clearOnDetach(dialog);
        negativeClickListener.clearOnDetach(dialog);
    }

}
