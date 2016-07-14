package leonardo2204.com.materialnotes.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
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

    private void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setTitle(null);
            toolbar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
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
