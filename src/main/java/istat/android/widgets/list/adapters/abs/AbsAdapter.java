package istat.android.widgets.list.adapters.abs;

import istat.android.base.utils.ImageLoader;
import istat.android.widgets.list.adapters.utils.ViewNotSupportedException;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter.ViewBinder;

/**
 * @param <viewValue>
 * @author Toukea tatsi (Istat)
 */
public abstract class AbsAdapter<viewValue> extends
        ArrayAdapter<Map<Integer, viewValue>> {

    ImageLoader imageLoader;
    protected LayoutInflater inflater;
    protected int baseLayoutResource = 0;

    // protected List<Map<Integer,viewValue>> listItems=new
    // ArrayList<Map<Integer,viewValue>>();

    public AbsAdapter(Context context, int textViewResourceId,
                      List<Map<Integer, viewValue>> objects) {
        super(context, textViewResourceId, objects);
        // listItems=objects;

        baseLayoutResource = textViewResourceId;
        imageLoader = new ImageLoader(context);
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader.setLoadListener(imageLoadListener);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = null;
        if (convertView != null)
            rootView = convertView;
        else {
            rootView = inflater.inflate(baseLayoutResource, parent, false);
        }
        Map<Integer, ?> item = getItem(position);

        for (int i : item.keySet()) {
            View tmpView = rootView.findViewById(i);
            if (tmpView != null) {
                if (viewBinder.setViewValue(tmpView, item.get(i), item.get(i)
                        .toString()))
                    ;
                else if (defaultViewBinder.setViewValue(tmpView, item.get(i),
                        item.get(i).toString()))
                    ;
                else
                    throw new ViewNotSupportedException(
                            tmpView.getClass().toString()
                                    + " is not a view that can be bound by AbsAdapter");

            }
        }

        return rootView;
    }

    void setViewImage(final ImageView v, final String path) {
        imageLoader.displayImage(path, v);
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.viewBinder = viewBinder;
    }

    public void setDefaultViewBinder() {
        viewBinder = defaultViewBinder;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static ArrayAdapter<String> getArrayAdapter(Context context,
                                                       List<String> list, int choice) {

        return new ArrayAdapter<String>(context, choice, list);

    }

    public static ArrayAdapter<String> getArrayAdapter(Context context,
                                                       String array[], int choice) {

        return new ArrayAdapter<String>(context, choice, array);

    }

    private ViewBinder defaultViewBinder = new ViewBinder() {
        private AbsAdapterViewBinderViewValueSeter viewBinderManager = AbsAdapterViewBinderViewValueSeter
                .getInstance(AbsAdapter.this);

        @Override
        public boolean setViewValue(View tmpView, Object item,
                                    String textRepresentation) {

            return viewBinderManager
                    .bindView(tmpView, item, textRepresentation);
        }

    };
    private ImageLoader.ImageLoadListener imageLoadListener = new ImageLoader.ImageLoadListener() {

        @Override
        public boolean onLoadFinish(ImageLoader.PhotoToLoad phLoad, Bitmap bitmap) {

            notifyDataSetChanged();
            return false;
        }

        @Override
        public boolean onLoad(ImageLoader.PhotoToLoad phLoad) {


            return false;
        }
    };
    private ViewBinder viewBinder = defaultViewBinder;
}
