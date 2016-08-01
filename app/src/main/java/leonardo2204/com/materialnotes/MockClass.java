package leonardo2204.com.materialnotes;

import io.realm.Realm;
import leonardo2204.com.materialnotes.model.CheckboxItem;
import leonardo2204.com.materialnotes.model.Checkboxes;
import leonardo2204.com.materialnotes.model.ImageItem;

/**
 * Created by leonardo on 6/30/16.
 */

public class MockClass {

    private MockClass() {
        throw new AssertionError();
    }

    public static void mockList() {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Checkboxes list = realm.createObject(Checkboxes.class);

                CheckboxItem item1 = realm.createObject(CheckboxItem.class);
                item1.setChecked(true);
                item1.setText("blasd");
                list.getItems().add(item1);

                CheckboxItem item2 = realm.createObject(CheckboxItem.class);
                item2.setChecked(true);
                item2.setText("bfdgdgddf");
                list.getItems().add(item2);

                CheckboxItem item3 = realm.createObject(CheckboxItem.class);
                item3.setChecked(false);
                item3.setText("birrlllll");
                list.getItems().add(item3);

                CheckboxItem item4 = new CheckboxItem(true, "sdfds sdfdsf");
                //items.add(item4);
                CheckboxItem item5 = new CheckboxItem(false, "tgfd43345");
                //items.add(item5);

                ImageItem iitem1 = realm.createObject(ImageItem.class);
                iitem1.setImageUrl("http://cdn2-www.dogtime.com/assets/uploads/gallery/30-impossibly-cute-puppies/impossibly-cute-puppy-8.jpg");

                ImageItem iitem2 = realm.createObject(ImageItem.class);
                iitem2.setImageUrl("http://www.planwallpaper.com/static/images/puppies.jpg");

                ImageItem iitem3 = realm.createObject(ImageItem.class);
                iitem3.setImageUrl("http://cdn1-www.dogtime.com/assets/uploads/gallery/30-impossibly-cute-puppies/impossibly-cute-puppy-2.jpg");

                ImageItem iitem4 = realm.createObject(ImageItem.class);
                iitem4.setImageUrl("http://images.nationalgeographic.com/wpf/media-live/photos/000/347/cache/golden-labrador-puppy_34708_600x450.jpg");

                ImageItem iitem5 = realm.createObject(ImageItem.class);
                iitem5.setImageUrl("http://r.ddmcdn.com/w_603/s_f/o_1/cx_0/cy_15/cw_603/ch_402/APL/uploads/2014/06/lab-too-cute-puppies-02-625x450-1.jpg");

                ImageItem iitem6 = realm.createObject(ImageItem.class);
                iitem6.setImageUrl("https://s-media-cache-ak0.pinimg.com/736x/5e/82/e6/5e82e68b741b5a6384bb799cc8e03fe9.jpg");

            }
        });

        realm.close();
    }

}
