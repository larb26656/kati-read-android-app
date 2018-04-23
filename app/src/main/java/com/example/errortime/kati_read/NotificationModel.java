package com.example.errortime.kati_read;


public class NotificationModel  {
    public String Notification_detail_thai;
    public String Notification_detail_english;
    public String Notification_date;
    public String Notification_date_with_format;
    public String Token;
    public String Notification_visible_status;
    public String Token_Notification_visible_status;

    public NotificationModel() {
    }

    public NotificationModel(String notification_detail_thai, String notification_detail_english, String notification_date, String notification_date_with_format, String token, String notification_visible_status, String token_Notification_visible_status) {
        Notification_detail_thai = notification_detail_thai;
        Notification_detail_english = notification_detail_english;
        Notification_date = notification_date;
        Notification_date_with_format = notification_date_with_format;
        Token = token;
        Notification_visible_status = notification_visible_status;
        Token_Notification_visible_status = token_Notification_visible_status;
    }

}
