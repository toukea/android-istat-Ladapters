package istat.android.widgets.list.adapters;

import istat.android.widgets.list.adapters.abs.AbsAdapter;
import istat.android.widgets.list.adapters.utils.ViewIdValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;

public abstract class ObjectAdapter<ListObject, viewValue> extends
        AbsAdapter<viewValue> {

    public ObjectAdapter(Context context, int view_layout) {
        super(context, view_layout, new ArrayList<Map<Integer, viewValue>>());
    }

    protected List<ListObject> objectList = new ArrayList<ListObject>();

    public abstract void onAddItem(ViewIdValuePair<viewValue> paire,
                                   ListObject item);

    @SuppressLint("UseSparseArrays")
    public void addItem(int position, ListObject object) {
        insert(createMap(object), position);
        objectList.add(position, object);

    }

    public void addItem(ListObject object) {
        add(createMap(object));
        objectList.add(object);

    }

    protected final ViewIdValuePair<viewValue> createMap(ListObject object) {
        ViewIdValuePair<viewValue> map = new ViewIdValuePair<viewValue>();
        onAddItem(map, object);
        return map;
    }

    public void addItems(List<ListObject> items) {
        for (ListObject object : items)
            addItem(object);
    }

    public void setItems(List<ListObject> items) {
        removeAllItems();
        addItems(items);
    }

    // public void setItem(int index, ListObject object) {
    //
    // objectList.set(index, object);
    // ViewIdValuePair<viewValue> map = new ViewIdValuePair<viewValue>();
    // onAddItem(map, object);
    // listItems.set(index, map);
    // }
    @Override
    public void clear() {
        removeAllItems();
    }

    public void removeAllItems() {
        super.clear();
        objectList = new ArrayList<ListObject>();
    }

    /*
     * public List<ListObject> getObjectList() { return objectList; }
     */
    public List<ListObject> getObjectItems() {
        return objectList;
    }

    public int getObjectItemPosition(ListObject obj) {
        return getObjectItems().indexOf(obj);
    }

    public ListObject getObjectItem(int position) {
        return objectList.get(position);
    }

    public ListObject getObjectItem(Map<Integer, viewValue> map) {
        int index = objectList.indexOf(map);
        return objectList.get(index);
    }

    public void removeItemObject(ListObject object) {
        int index = objectList.indexOf(object);
        objectList.remove(object);
        remove(getItem(index));
        // listItems.remove(index);
    }

    public void removeItemObject(int index) {
        removeItemObject(getObjectItem(index));
    }

    public boolean notifyDataSetChanged(ListObject item) {
        boolean out = updateItem(item);
        notifyDataSetChanged();
        return out;
    }

    public void notifyDataSetChanged(int index, ListObject item) {
        setItem(index, item);
        notifyDataSetChanged();
        // return out;
    }

    public boolean updateItem(ListObject object) {
        boolean out = false;
        int index = objectList.indexOf(object);
        if (index >= 0) {
            setItem(index, object);
            out = true;
        }
        return out;
    }

    public void setItem(int index, ListObject object) {
        if (index >= 0) {
            objectList.set(index, object);
            insert(createMap(object), index);
            // listItems.set(index, createMap(object));
        }
    }

    public void removeItemObject(List<ListObject> object) {
        for (ListObject obj : object) {
            removeItemObject(obj);
        }
    }

}
