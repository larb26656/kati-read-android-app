package com.example.errortime.kati_read;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;

import java.util.ArrayList;

/**
 * Created by Error Time on 10/18/2017.
 */

public class NotificationAdapter extends BaseAdapter{
        private Context context;
        private ArrayList<String> notification_date_detail,notification_detail_detail;

    public NotificationAdapter(Context context, ArrayList<String> notification_date_detail, ArrayList<String> notification_detail_detail) {
        this.context = context;
        this.notification_date_detail = notification_date_detail;
        this.notification_detail_detail = notification_detail_detail;

    }

    @Override
    public int getCount() {
        return notification_date_detail.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  view1 = layoutInflater.inflate(R.layout.notification_listview, viewGroup, false);

        TextView notification_date_text = (TextView) view1.findViewById(R.id.notification_date_text);
        TextView notification_detail_text = (TextView) view1.findViewById(R.id.notification_detail_text);

        notification_date_text.setText(notification_date_detail.get(i));
        notification_detail_text.setText(notification_detail_detail.get(i));
        return view1;
    }
}
