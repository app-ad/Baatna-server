package com.application.baatna.util;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.application.baatna.bean.Categories;
import com.application.baatna.bean.Institution;
import com.application.baatna.bean.User;

public class CommonLib {

	public static boolean isFacebookCheckValid = false;
	public static final String ANDROID_CLIENT_ID = "bt_android_client";
	public static final String ANDROID_APP_TYPE = "bt_android";

	public static final int RESPONSE_FAILURE = 200;
	public static final int RESPONSE_SUCCESS = 201;
	public static final int RESPONSE_INVALID_USER = 202;
	public static final int RESPONSE_INVALID_PARAMS = 203;
	public static final int RESPONSE_INVALID_APP_TYPE = 204;
	public static final int RESPONSE_INVALID_CLIENT_ID = 205;
	public static final int RESPONSE_RATED_FAILURE = 206;

	public static final String BAPP_ID = "hello@baatna.com";
	public static final String BAPP_PWD = "hello.baatna";

	public static final int STATUS_DELETED = 0;
	public static final int STATUS_ACTIVE = 1;
	public static final int STATUS_ACCEPTED = 2;
	public static final int STATUS_OFFERED = 3;
	public static final int STATUS_RECEIVED = 4;
	public static final int STATUS_FULLFILLED = 5;

	public static final int MESSAGE_STATUS_INACTIVE = 0;
	public static final int MESSAGE_STATUS_ARCHIVE_FROM = 1;
	public static final int MESSAGE_STATUS_ARCHIVE_TO = 2;
	public static final int MESSAGE_STATUS_ACTIVE = 3;

	public static final int WISH_OFFERED = 1;
	public static final int WISH_OWN = 2;

	public static final int INVALID_COUPON = 0;

	/**
	 * Android API constants
	 */
	public static String projectId = "122380389686";//531855430941";// "792616879007";
	public static String apiKey = "AIzaSyD6BP-UVGQZ_YO-wtKFGiNmPk3e8k6qbbQ";//AIzaSyC1Zbn_ROSWO-l4IJYTDaeyBTEit3fn9FI";// "AIzaSyAJBGj_8aaNykTNaPxdayggDMyxzArE7gM";
	public static final String GCM_SERVER = "gcm.googleapis.com";
	public static final int GCM_PORT = 5235;
	public static final String GCM_ELEMENT_NAME = "gcm";
	public static final String GCM_NAMESPACE = "google:mobile:data";

	public static final boolean BaatnaLog = false;

	public static final int ACTION_ACCEPT_WISH = 1;
	public static final int ACTION_DECLINE_WISH = 2;

	public static final int ACTION_WISH_OFFERED = 3;
	public static final int ACTION_WISH_RECEIVED = 4;

	public static final int ACTION_NEGOTIATION_STARTED = 5;
	public static final int ACTION_NEGOTIATION_ACCEPTED = 6;
	public static final int ACTION_RENEGOTIATION = 7;

	public static final int CURRENT_USER_WISH_ACCEPTED = 1;
	public static final int WISH_ACCEPTED_CURRENT_USER = 2;
	
	public static int SEX_MALE = 1;
	public static int SEX_FEMALE = 2;
	public static int SEX_IDONTKNOW = 3;

	public static JSONObject getResponseString(Object responseJson, String errorMessage, int status) {
		JSONObject responseObject = new JSONObject();
		switch (status) {
		case RESPONSE_SUCCESS:
			try {
				responseObject.put("response", responseJson);
				responseObject.put("status", "success");
				responseObject.put("errorCode", "0");
				responseObject.put("errorMessage", "");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;

		case RESPONSE_INVALID_USER:
			try {
				responseObject.put("response", responseJson);
				responseObject.put("status", "failure");
				responseObject.put("errorCode", RESPONSE_INVALID_USER);
				responseObject.put("errorMessage", errorMessage);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case RESPONSE_INVALID_PARAMS:
			try {
				responseObject.put("response", responseJson);
				responseObject.put("status", "failure");
				responseObject.put("errorCode", RESPONSE_INVALID_PARAMS);
				responseObject.put("errorMessage", errorMessage);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case RESPONSE_INVALID_APP_TYPE:
			try {
				responseObject.put("response", responseJson);
				responseObject.put("status", "failure");
				responseObject.put("errorCode", RESPONSE_INVALID_APP_TYPE);
				responseObject.put("errorMessage", errorMessage);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case RESPONSE_INVALID_CLIENT_ID:
			try {
				responseObject.put("response", responseJson);
				responseObject.put("status", "failure");
				responseObject.put("errorCode", RESPONSE_INVALID_CLIENT_ID);
				responseObject.put("errorMessage", errorMessage);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case RESPONSE_RATED_FAILURE:
			try {
				responseObject.put("response", responseJson);
				responseObject.put("status", "failure");
				responseObject.put("errorCode", RESPONSE_RATED_FAILURE);
				responseObject.put("errorMessage", "ALREADY RATED");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

		return responseObject;
	}

	public static List<Categories> getCategoriesList() {
		List<Categories> finalCategoryList = new ArrayList<Categories>();
		finalCategoryList.add(new Categories(1, "Books and Sports", "Y"));
		finalCategoryList.add(new Categories(2, "Games", "Z"));
		finalCategoryList.add(new Categories(3, "Music", "a"));
		finalCategoryList.add(new Categories(4, "Travel and Holiday", "b"));
		finalCategoryList.add(new Categories(5, "Party", "c"));
		finalCategoryList.add(new Categories(6, "Baking and cooking", "d"));
		finalCategoryList.add(new Categories(7, "Home Improvement ", "e"));
		return finalCategoryList;
	}

	public static List<Institution> getInstitutionsList() {
		List<Institution> finalCategoryList = new ArrayList<Institution>();

		ArrayList<String> branches = new ArrayList<String>();
		branches.add("IT");
		branches.add("COE");
		branches.add("BT");

		// NSIT
		Institution nsit = new Institution();
		nsit.setInstitutionName("NSIT");
		nsit.setBranches(branches);

		// DCE
		Institution dce = new Institution();
		dce.setInstitutionName("DCE");
		dce.setBranches(branches);

		// IIT
		Institution iit = new Institution();
		iit.setInstitutionName("IIT");
		iit.setBranches(branches);

		finalCategoryList.add(nsit);
		finalCategoryList.add(dce);
		finalCategoryList.add(iit);

		return finalCategoryList;
	}

	public static List<String> getBranches() {
		List<String> finalCategoryList = new ArrayList<String>();
		finalCategoryList.add("IT");
		finalCategoryList.add("COE");
		finalCategoryList.add("BT");
		return finalCategoryList;
	}

	public static String getUserName(User user) {

		if (user.getUserName() == null)
			try {
				JSONObject data = new JSONObject(user.getFacebookData());
				if (data.has("name"))
					return String.valueOf(data.get("name")).split(" ")[0];
			} catch (JSONException e) {
				System.out.println("error");
			}
		else
			return user.getUserName().split(" ")[0];
		return "";

	}

}