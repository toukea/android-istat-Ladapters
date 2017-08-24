package istat.android.widgets.list.adapters.abs;

import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Toukea tatsi (Istat)
 * 
 */
class AbsAdapterViewBinderViewValueSeter {
	AbsAdapter<?> adapter;

	private AbsAdapterViewBinderViewValueSeter(AbsAdapter<?> adapter) {
		this.adapter = adapter;
	}

	public static AbsAdapterViewBinderViewValueSeter getInstance(
			AbsAdapter<?> adapter) {

		return new AbsAdapterViewBinderViewValueSeter(adapter);
	}

	public boolean bindView(View tmpView, Object item, String textRepresentation) {
		if (tmpView instanceof TextView) {
			((TextView) tmpView).setText(textRepresentation);
			return true;
		}
		if (tmpView instanceof EditText) {
			((EditText) tmpView).setText(textRepresentation);
			return true;
		}
		if (tmpView instanceof ImageView) {
			adapter.setViewImage((ImageView) tmpView, textRepresentation);
			return true;
		}
		if (tmpView instanceof WebView) {
			bindWebView(tmpView, item, textRepresentation);
			return true;
		}
		if (tmpView instanceof ListView) {
			bindListView(tmpView, item, textRepresentation);
			return true;
		}
		if (tmpView instanceof CheckBox) {
			bindCheckBox(tmpView, item, textRepresentation);
			return true;
		}

		return false;
	}

	private void bindWebView(View tmpView, Object item,
			String textRepresentation) {
		WebView view = (WebView) tmpView;
		view.loadUrl(textRepresentation);
	}

	private void bindCheckBox(View tmpView, Object item,
			String textRepresentation) {
		CheckBox view = (CheckBox) tmpView;
		if (textRepresentation.equalsIgnoreCase("true")
				|| textRepresentation.equalsIgnoreCase("false")) {

			view.setChecked(Boolean.valueOf(textRepresentation));
		} else {
			view.setText(textRepresentation);
		}
	}

	private void bindListView(View tmpView, Object item,
			String textRepresentation) {
		ListView view = (ListView) tmpView;
		view.setAdapter((ListAdapter) item);
	}
}
