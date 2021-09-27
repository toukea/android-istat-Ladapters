package istat.android.widgets.list.adapters;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import istat.android.widgets.list.adapters.abs.AbsAdapter;

public class SimpleCharSequenceAdapter extends AbsAdapter<CharSequence> {

    public SimpleCharSequenceAdapter(Context context, int textViewResourceId,
                                     List<Map<Integer, CharSequence>> objects) {
        super(context, textViewResourceId, objects);
    }

    public SimpleCharSequenceAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId, new ArrayList<Map<Integer, CharSequence>>());

    }

}
