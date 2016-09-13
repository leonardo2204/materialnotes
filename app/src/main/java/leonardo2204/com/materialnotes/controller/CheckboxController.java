package leonardo2204.com.materialnotes.controller;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import leonardo2204.com.materialnotes.R;
import leonardo2204.com.materialnotes.adapter.CheckboxItemsAdapter;
import leonardo2204.com.materialnotes.controller.base.BaseController;
import leonardo2204.com.materialnotes.custom.EndDrawableEditText;
import leonardo2204.com.materialnotes.di.component.CheckboxComponent;
import leonardo2204.com.materialnotes.di.component.DaggerCheckboxComponent;
import leonardo2204.com.materialnotes.model.CheckboxItem;
import leonardo2204.com.materialnotes.model.Checkboxes;

/**
 * Created by leonardo on 7/1/16.
 */

public class CheckboxController extends BaseController implements EndDrawableEditText.OnDrawableClickListener, CheckboxItemsAdapter.OnStartDragListener {

    @BindView(R.id.new_item)
    EndDrawableEditText newItemEditText;
    @BindView(R.id.rv_checks)
    RecyclerView rv_checks;
    @BindView(R.id.root_layout_checkbox)
    CoordinatorLayout coordinatorLayout;
    private CheckboxItemsAdapter adapter;
    private ItemTouchHelper itemTouchHelper;
    private Checkboxes checkboxNote = new Checkboxes();
    private List<CheckboxItem> items = new ArrayList<>();

    @Override
    protected void onCreate() {
        CheckboxComponent component = DaggerCheckboxComponent.builder()
                .rootComponent(((RootController) getParentController()).getRootComponent())
                .build();

        component.inject(this);
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.checkboxes_controller, container, false);
    }

    @Override
    protected void onViewCreated(@NonNull View v) {
        Drawable ds = newItemEditText.getCompoundDrawables()[2];
        ds.setColorFilter(ContextCompat.getColor(getActivity(), R.color.md_black_1000), PorterDuff.Mode.SRC_ATOP);
        newItemEditText.setOnDrawableClickListener(this);
        setupRecyclerView();
    }

    @Override
    public void onEndDrawableClick() {
        String checkboxText = newItemEditText.getText().toString();
        if (!TextUtils.isEmpty(checkboxText)) {
            CheckboxItem item = new CheckboxItem(true, checkboxText);
            createCheckbox(item);
            newItemEditText.getText().clear();
        } else {
            Snackbar.make(coordinatorLayout, "Check nao pode ser nulo", Snackbar.LENGTH_LONG).show();
        }
    }

    private void setupRecyclerView() {
        adapter = new CheckboxItemsAdapter(this);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv_checks.setLayoutManager(llm);
        rv_checks.setHasFixedSize(true);
        rv_checks.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rv_checks);
    }

    private void createCheckbox(CheckboxItem item) {
        adapter.addItem(item);
    }

    @OnClick(R.id.check_add_note)
    protected void onAddNote() {
        saveNote();
    }

    private void saveNote() {
//        try {
//            realm.beginTransaction();
//            Checkboxes cbs = realm.createObject(Checkboxes.class);
//            cbs.getItems().addAll(items);
//            realm.commitTransaction();
//            getRouter().popCurrentController();
//        } catch (Exception e) {
//            realm.cancelTransaction();
//            Snackbar.make(getView(), "Erro ao criar checkbox", Snackbar.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
