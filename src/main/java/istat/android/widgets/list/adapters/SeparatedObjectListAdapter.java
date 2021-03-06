package istat.android.widgets.list.adapters;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

public class SeparatedObjectListAdapter<item> extends BaseAdapter {

	public final Map<item, Adapter> sections = new LinkedHashMap<item, Adapter>();
	public final SimpleObjectAdapter<item> headers;
	public final static int TYPE_SECTION_HEADER = 0;
	public final List<Adapter> listAdapter = new ArrayList<Adapter>();

	public SeparatedObjectListAdapter(
			SimpleObjectAdapter<item> headerAdapter) {
		headers = headerAdapter;
	}

	public void addSection(item section, Adapter adapter) {
		this.headers.addItem(section);
		this.sections.put(section, adapter);
		this.listAdapter.add(adapter);
	}

	public Object getItem(int position) {
		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// check if position inside this section
			if (position == 0)
				return section;
			if (position < size)
				return adapter.getItem(position - 1);

			// otherwise jump into next section
			position -= size;
		}
		return null;
	}

	public int getCount() {
		// total together all sections, plus one for each section header
		int total = 0;
		for (Adapter adapter : this.sections.values())
			total += adapter.getCount() + 1;
		return total;
	}

	public int getViewTypeCount() {
		// assume that headers count as one, then total all sections
		int total = 1;
		for (Adapter adapter : this.sections.values())
			total += adapter.getViewTypeCount();
		return total;
	}

	public int getItemViewType(int position) {
		int type = 1;
		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// check if position inside this section
			if (position == 0)
				return TYPE_SECTION_HEADER;
			if (position < size)
				return type + adapter.getItemViewType(position - 1);

			// otherwise jump into next section
			position -= size;
			type += adapter.getViewTypeCount();
		}
		return -1;
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isEnabled(int position) {
		return (getItemViewType(position) != TYPE_SECTION_HEADER);
	}

	public Section removeSection(int position) {
		Section sectionToRemove = getSection(position);
		item selectedHeaderObject = this.headers.getObjectItem(position);
		this.headers.removeItemObject(position);
		this.listAdapter.remove(position);
		this.sections.remove(selectedHeaderObject);
		return sectionToRemove;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int sectionnum = 0;
		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			if (adapter == null)
				continue;
			int size = adapter.getCount() + 1;

			// check if position inside this section
			if (position == 0) {
				return headers.getView(sectionnum, convertView, parent);
			}

			if (position < size) {
				return adapter.getView(position - 1, convertView, parent);
			}

			// otherwise jump into next section
			position -= size;
			sectionnum++;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public Section getSection(int position) {
		int index = 0;
		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int tmp = index + adapter.getCount();
			index++;
			if (tmp >= position) {
				return new Section(adapter, position - index);
			}
			index = index + adapter.getCount();

		}
		Section section = null;
		return section;
	}

	public static class Section {
		Adapter adapter;
		int position;

		public Section(Adapter adapter, int position) {
			this.adapter = adapter;
			this.position = position;
		}

		public Adapter getAdapter() {
			return adapter;
		}

		public int getPosition() {
			return position;
		}

	}
}