package istat.android.widgets.list.adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;

public abstract class SimpleObjectAdapter<ObjectClass> extends
		ObjectAdapter<ObjectClass, String> {

	public SimpleObjectAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		
	}

	public SimpleObjectAdapter(Context context, int textViewResourceId,
                               List<Map<Integer, String>> objects) {
		super(context, textViewResourceId);
		addAll(objects);
		
	}

	public SimpleObjectAdapter(Context context, int textViewResourceId,
                               ObjectClass... objects) {
		super(context, textViewResourceId);
		for (ObjectClass item : objects)
			addItem(item);
		
	}

}
