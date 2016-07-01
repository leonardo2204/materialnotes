package leonardo2204.com.materialnotes;

import java.util.ArrayList;
import java.util.List;

import leonardo2204.com.materialnotes.model.CheckboxItem;
import leonardo2204.com.materialnotes.model.CheckboxList;
import leonardo2204.com.materialnotes.model.ImageItem;
import leonardo2204.com.materialnotes.model.Item;

/**
 * Created by leonardo on 6/30/16.
 */

public class MockClass {

    private MockClass() {
        throw new AssertionError();
    }

    public static List<Item> mockList() {
        List<Item> items = new ArrayList<>(11);
        CheckboxList list = new CheckboxList();

        CheckboxItem item1 = new CheckboxItem(true, "bladsa");
        list.add(item1);
        CheckboxItem item2 = new CheckboxItem(true, "ghgfhgf");
        list.add(item2);
        CheckboxItem item3 = new CheckboxItem(false, "4343423");
        list.add(item3);
        items.add(list);
        CheckboxItem item4 = new CheckboxItem(true, "sdfds sdfdsf");
        //items.add(item4);
        CheckboxItem item5 = new CheckboxItem(false, "tgfd43345");
        //items.add(item5);
        ImageItem iitem1 = new ImageItem("http://cdn2-www.dogtime.com/assets/uploads/gallery/30-impossibly-cute-puppies/impossibly-cute-puppy-8.jpg");
        items.add(iitem1);
        ImageItem iitem2 = new ImageItem("http://www.planwallpaper.com/static/images/puppies.jpg");
        items.add(iitem2);
        ImageItem iitem3 = new ImageItem("http://cdn1-www.dogtime.com/assets/uploads/gallery/30-impossibly-cute-puppies/impossibly-cute-puppy-2.jpg");
        items.add(iitem3);
        ImageItem iitem4 = new ImageItem("http://images.nationalgeographic.com/wpf/media-live/photos/000/347/cache/golden-labrador-puppy_34708_600x450.jpg");
        items.add(iitem4);
        ImageItem iitem5 = new ImageItem("http://r.ddmcdn.com/w_603/s_f/o_1/cx_0/cy_15/cw_603/ch_402/APL/uploads/2014/06/lab-too-cute-puppies-02-625x450-1.jpg");
        items.add(iitem5);
        ImageItem iitem6 = new ImageItem("https://s-media-cache-ak0.pinimg.com/736x/5e/82/e6/5e82e68b741b5a6384bb799cc8e03fe9.jpg");
        items.add(iitem6);

        return items;
    }

}
