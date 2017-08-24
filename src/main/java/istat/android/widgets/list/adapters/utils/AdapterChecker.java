package istat.android.widgets.list.adapters.utils;

import istat.android.widgets.list.adapters.ObjectAdapter;
import istat.android.widgets.list.adapters.abs.AbsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.SimpleAdapter.ViewBinder;

public class AdapterChecker<ListObject> {

	boolean checkerEnable = false;
	protected boolean finalised = false;
	List<Boolean> checkedState = new ArrayList<Boolean>();
	AbsAdapter<?> mAdapter;
	int checkerId = 0;
	List<Integer> desapearedViewId = new ArrayList<Integer>();

	public AdapterChecker(AbsAdapter<?> adapter) {
		this.mAdapter = adapter;
		this.mAdapter.setViewBinder(mBinder);
	}

	private ViewBinder mBinder = new ViewBinder() {

		@Override
		public boolean setViewValue(View view, Object data, String text) {
			// TODO Auto-generated method stub
			if (data == null || text == null || text.equals("")) {
//				if (view.getId() == checkerId)
//					{
//					view.setVisibility(View.VISIBLE);
//					((CheckBox) view).setChecked(false);
//					}
//				else
				view.setVisibility(View.GONE);
				return true;
			} else
				view.setVisibility(View.VISIBLE);

			if (view.getId() == checkerId) {
				if (checkerEnable) {
					view.setVisibility(View.VISIBLE);
//					if(!TextUtils.isEmpty(text))
//						((CheckBox) view).setChecked(false);
//					else
					((CheckBox) view).setChecked(isChecked(Integer
							.valueOf(text)));
					setCheckerClicListener(view, Integer.valueOf(text));
				} else {
					view.setVisibility(View.GONE);
				}

				return true;
			}
			if (desapearedViewId.contains(view.getId())) {
				if (checkerEnable) {
					view.setVisibility(View.GONE);
				} else {
					view.setVisibility(View.VISIBLE);
				}

			}

			return false;
		}

	};

	public void setCheckerEnable(boolean checkerEnable) {
		this.checkerEnable = checkerEnable;
		mAdapter.notifyDataSetChanged();
	}

	public boolean isChecked(int index) {
		if (checkedState.size() > 0 && index < checkedState.size())
			return checkedState.get(index);
		else
			return false;
	}

	public boolean isCheckerEnable() {
		return checkerEnable;
	}

	void setCheckerClicListener(View view, final int index) {
		final CheckBox check = (CheckBox) view;
		check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				while (checkedState.size() <= index + 1) {
					checkedState.add(false);
				}
				checkedState.set(index, check.isChecked());
			}
		});
	}

	public void unCheckAll() {
		checkAll(false);
	}

	public void checkAll() {
		checkAll(false);
	}

	private void checkAll(boolean state) {
		for (int i = 0; i < checkedState.size(); i++)
			checkedState.set(i, state);
		mAdapter.notifyDataSetChanged();
	}

	public void setChecked(int position, boolean checked) {
		while (checkedState.size() <= position + 1) {
			checkedState.add(false);
		}
		checkedState.set(position, checked);
		mAdapter.notifyDataSetChanged();

	}

	public AdapterChecker<ListObject> setCheckerViewId(int checkerViewId) {
		this.checkerId = checkerViewId;
		return this;
	}

	public AdapterChecker<ListObject> addDesapearOnCheckModeViewById(
			int desapearedId) {
		this.desapearedViewId.add(desapearedId);
		return this;
	}
	public AdapterChecker<ListObject> setDesapearOnCheckModeViewById(
			Integer... desapearedId) {
		this.desapearedViewId=Arrays.asList(desapearedId);
		return this;
	}

	public AdapterChecker<ListObject> removeDesapearOnCheckModeViewById(
			int desapearedId) {
		this.desapearedViewId.add(desapearedId);
		return this;
	}

	@SuppressWarnings("unchecked")
	public List<ListObject> getCheckedItems() {
		ObjectAdapter<?, ?> adapter = null;
		if (mAdapter instanceof ObjectAdapter<?, ?>) {
			adapter = (ObjectAdapter<?, ?>) mAdapter;
		} else
			return new ArrayList<ListObject>();
		List<ListObject> record = new ArrayList<ListObject>();
		for (int i = 0; i < adapter.getObjectItems().size(); i++) {
			if (i < checkedState.size()) {
				if (checkedState.get(i))
					record.add((ListObject) adapter.getObjectItems().get(i));
			}
		}
		return record;
	}
}
