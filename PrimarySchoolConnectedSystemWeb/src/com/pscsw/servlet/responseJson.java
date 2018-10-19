package com.pscsw.servlet;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class responseJson {
	public static int STATUS_ERR = 0;// 账号密码错误
	public static int STATUS_OK = 200;
	public static int STATUS_DUP = 100;// 账号重复

	JSONObject res = new JSONObject();

	public responseJson() {
	}

	public responseJson(String key, int code) throws JSONException {
		res.put(key, String.valueOf(code));
	}

	public void add(String key, int code) throws JSONException {
		res.put(key, String.valueOf(code));
	}

	public void add(String key, String value) throws JSONException {
		res.put(key, value);
	}

	public void addArray(String array, String key, String value) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put(key, value);
		if (res.has(array)) {
			JSONArray arr = res.getJSONArray(array);
			arr.put(obj);
		} else {
			JSONArray arr = new JSONArray();
			arr.put(obj);
			res.put(array, arr);
		}
	}

	public void addArray(String array, Map<String, String> map) throws JSONException {
		JSONObject obj = new JSONObject();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			obj.put(entry.getKey(), entry.getValue());
		}
		if (res.has(array)) {
			JSONArray arr = res.getJSONArray(array);
			arr.put(obj);
		} else {
			JSONArray arr = new JSONArray();
			arr.put(obj);
			res.put(array, arr);
		}
	}

	public String response() {
		return res.toString();
	}
}
