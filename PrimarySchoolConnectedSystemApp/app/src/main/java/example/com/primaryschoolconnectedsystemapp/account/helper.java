package example.com.primaryschoolconnectedsystemapp.account;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import example.com.primaryschoolconnectedsystemapp.R;

public class helper {
    public static User user = new User();

    public static void putSharedPreferencesString(Context context, String key, String value) {
        String last = getSharedPreferencesString(context, key);
        if (last != null && last.equals(value)) {
            return;
        }

        if (key != null && value != null) {
            final SharedPreferences prefs = context
                    .getSharedPreferences(context.getResources().getString(R.string.prefs_users), Context.MODE_PRIVATE);
            prefs.edit()
                    .putString(key, value)
                    .commit();
            //.apply();
        }
    }

    public static String getSharedPreferencesString(Context context, String key) {
        if (key != null) {
            final SharedPreferences prefs = context
                    .getSharedPreferences(context.getResources().getString(R.string.prefs_users), Context.MODE_PRIVATE);
            return prefs.getString(key, "");
        }
        return "";
    }

    public static int getStatusCode(String res, String key) {
        try {
            String json = new String(res.getBytes(), "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject != null && jsonObject.has(key)) {
                return Integer.parseInt((String) jsonObject.get(key));
            }
        } catch (Exception e) {
        }
        return -1;
    }

    public static String getResValue(String res, String key) {
        try {
            String json = new String(res.getBytes(), "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject != null && jsonObject.has(key)) {
                return (String) jsonObject.get(key);
            }
        } catch (Exception e) {
        }
        return "";
    }
}
