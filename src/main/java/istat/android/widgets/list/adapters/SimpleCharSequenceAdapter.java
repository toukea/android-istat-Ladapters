package istat.android.widgets.list.adapters;


import istat.android.widgets.list.adapters.abs.AbsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

public class SimpleCharSequenceAdapter extends AbsAdapter<CharSequence> {

    public SimpleCharSequenceAdapter(Context context, int textViewResourceId,
                                     List<Map<Integer, CharSequence>> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
    }

    public SimpleCharSequenceAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId, new ArrayList<Map<Integer, CharSequence>>());

    }

}
