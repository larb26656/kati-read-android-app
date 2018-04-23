package com.example.errortime.kati_read;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Error Time on 10/18/2017.
 */

public class NotificationAdapter extends BaseAdapter{
        private Context context;
        private String[] notification_date, notification_detail;

    public NotificationAdapter(Context context, String[] notification_date_detail, String[] notification_detail_detail) {
        this.context = context;
        this.notification_date = notification_date_detail;
        this.notification_detail = notification_detail_detail;
    }

    @Override
    public int getCount() {
        return notification_date.length;
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

        notification_date_text.setText(notification_date[i]);
        notification_detail_text.setText(notification_detail[i]);
        return view1;
    }
}
