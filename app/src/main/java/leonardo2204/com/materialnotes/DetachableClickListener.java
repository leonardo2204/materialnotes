package leonardo2204.com.materialnotes;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.ViewTreeObserver;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;

/**
 * Created by Leonardo on 03/07/16.
 */
@TargetApi(JELLY_BEAN_MR2)
public final class DetachableClickListener implements DialogInterface.OnClickListener {

    private DialogInterface.OnClickListener delegate;

    private DetachableClickListener(DialogInterface.OnClickListener delegate) {
        this.delegate = delegate;
    }

    public static DetachableClickListener wrap(DialogInterface.OnClickListener delegate) {
        return new DetachableClickListener(delegate);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (delegate != null) {
            delegate.onClick(dialogInterface, i);
        }
    }

    public void clearOnDetach(Dialog dialog) {
        dialog.getWindow().getDecorView().getViewTreeObserver()
                .addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
                    @Override
                    public void onWindowAttached() {

                    }

                    @Override
                    public void onWindowDetached() {
                        delegate = null;
                    }
                });
    }

}
