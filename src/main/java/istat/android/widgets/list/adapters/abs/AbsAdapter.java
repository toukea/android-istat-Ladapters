package istat.android.widgets.list.adapters.abs;

import istat.android.base.image.ImageLoader;
import istat.android.base.image.ImageLoader.ImageLoadListener;
import istat.android.base.image.ImageLoader.PhotoToLoad;
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
 * 
 * @author Toukea tatsi (Istat)
 * 
 * @param <viewValue>
 */
public abstract class AbsAdapter<viewValue> extends
		ArrayAdapter<Map<Integer, viewValue>> {

	ImageLoader imageLoader;
	protected LayoutInflater inflater;
	protected int baseLayoutRessource = 0;

	// protected List<Map<Integer,viewValue>> listItems=new
	// ArrayList<Map<Integer,viewValue>>();

	public AbsAdapter(Context context, int textViewResourceId,
                      List<Map<Integer, viewValue>> objects) {
		super(context, textViewResourceId, objects);
		// listItems=objects;
		baseLayoutRessource = textViewResourceId;
		imageLoader = new ImageLoader(context);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader.setLoadListener(imageLoadListener);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rootView = null;
		if (convertView != null)
			rootView = convertView;
		else {
			rootView = inflater.inflate(baseLayoutRessource, parent, false);
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
		imageLoader.DisplayImage(path, v);
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
			// TODO Auto-generated method stub
			return viewBinderManager
					.bindView(tmpView, item, textRepresentation);
		}

	};
	private ImageLoadListener imageLoadListener = new ImageLoadListener() {

		@Override
		public boolean onLoadFinish(PhotoToLoad phLoad, Bitmap bitmap) {
			// TODO Auto-generated method stub
			notifyDataSetChanged();
			return false;
		}

		@Override
		public boolean onLoad(PhotoToLoad phLoad) {
			// TODO Auto-generated method stub

			return false;
		}
	};
	private ViewBinder viewBinder = defaultViewBinder;
}