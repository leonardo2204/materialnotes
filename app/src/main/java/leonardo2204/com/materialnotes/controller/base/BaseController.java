package leonardo2204.com.materialnotes.controller.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by leonardo on 6/29/16.
 */

public abstract class BaseController extends Controller {

    private boolean created = false;
    private Unbinder unbinder;

    protected abstract View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container);

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        if (!created) {
            created = true;
            onCreate();
        }

        View view = inflateView(inflater, container);
        unbinder = ButterKnife.bind(this, view);
        onViewCreated(view);

        return view;
    }

    protected void onCreate() {
    }

    protected void onViewCreated(@NonNull View v) {
    }

    @CallSuper
    @Override
    protected void onDestroyView(View view) {
        super.onDestroyView(view);
        unbinder.unbind();
    }
}
