package com.application.baatna.resource;

import java.util.ArrayList;
import java.util.Comparator;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.application.baatna.bean.AllMessages;
import com.application.baatna.bean.Location;
import com.application.baatna.bean.Message;
import com.application.baatna.bean.NewsFeed;
import com.application.baatna.bean.Session;
import com.application.baatna.bean.User;
import com.application.baatna.bean.UserCompactMessage;
import com.application.baatna.bean.Wish;
import com.application.baatna.dao.FeedDAO;
import com.application.baatna.dao.MessageDAO;
import com.application.baatna.dao.UserDAO;
import com.application.baatna.dao.WishDAO;
import com.application.baatna.util.CommonLib;
import com.application.baatna.util.JsonUtil;

@Path("/messaging")
public class Messaging {

	final String GOOGLE_SERVER_KEY = "google";

	/**
	 * Create a new wish
	 */
	@Path("/post")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject postWish(@FormParam("client_id") String clientId, @FormParam("app_type") String appType,
			@FormParam("userId") String toUserId, @FormParam("wishId") String wishId,
			@FormParam("message") String message,
			@FormParam("access_token") String accessToken) {

		// null checks, invalid request
		if (clientId == null || appType == null)
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		// check for client_id
		if (!clientId.equals(CommonLib.ANDROID_CLIENT_ID))
			return CommonLib.getResponseString("Invalid client id", "", CommonLib.RESPONSE_INVALID_CLIENT_ID);

		// check for app type
		if (!appType.equals(CommonLib.ANDROID_APP_TYPE))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_APP_TYPE);

		UserDAO userDao = new UserDAO();
		WishDAO wishDao = new WishDAO();
		// access token validity
		int userId = userDao.userActive(accessToken);

		if (userId > 0) {
			MessageDAO messageDao = new MessageDAO();

			Message messageObj = messageDao.addMessage(message, false, "" + System.currentTimeMillis(), userId,
					Integer.parseInt(toUserId), Integer.parseInt(wishId));

			if (messageObj != null) {

				User fromUser = userDao.getUserDetails(messageObj.getFromUserId());
				User toUser = userDao.getUserDetails(messageObj.getToUserId());
				Wish wish = wishDao.getWish(messageObj.getWishId());
				JSONObject messageJson = new JSONObject();
				try {
					messageJson.put("to_user", JsonUtil.getUserJson(toUser));
					messageJson.put("from_user", JsonUtil.getUserJson(fromUser));
					messageJson.put("wish", JsonUtil.getWishJson(wish));
					messageJson.put("message_id", messageObj.getMessageId());
					messageJson.put("from_to", false);
					messageJson.put("message", messageObj.getMessage());
				} catch (JSONException e) {
					e.printStackTrace();
				}

				messageDao.sendPushToNearbyUsers(messageJson, toUser.getUserId());

				JSONObject messageJsonCustom = new JSONObject();
				try {
					messageJsonCustom.put("to_user", JsonUtil.getUserJson(toUser));
					messageJsonCustom.put("from_user", JsonUtil.getUserJson(fromUser));
					messageJsonCustom.put("wish", JsonUtil.getWishJson(wish));
					messageJsonCustom.put("message_id", messageObj.getMessageId());
					messageJsonCustom.put("from_to", true);
					messageJsonCustom.put("message", messageObj.getMessage());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return CommonLib.getResponseString(messageJsonCustom, "", CommonLib.RESPONSE_SUCCESS);
			} else
				return CommonLib.getResponseString("failure", "", CommonLib.RESPONSE_FAILURE);
		} else
			return CommonLib.getResponseString("failure", "", CommonLib.RESPONSE_FAILURE);

	}

	@Path("/view_messages")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public AllMessages viewMessages(@FormParam("access_token") String accessToken,
			@FormParam("to_userId") int toUserId) {

		AllMessages allmessages = null;
		UserDAO userdao = new UserDAO();

		int fromUserId = userdao.userActive(accessToken);

		if (fromUserId > 0 && toUserId > 0) {

			MessageDAO messagedao = new MessageDAO();

			allmessages = messagedao.getAllMessages(fromUserId, toUserId);

		}

		return allmessages;
	}

	@Path("/get")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject getNewsFeed(@FormParam("access_token") String accessToken,
			@FormParam("client_id") String clientId, @FormParam("app_type") String appType) {
		// check for client_id
		if (!clientId.equals(CommonLib.ANDROID_CLIENT_ID))
			return CommonLib.getResponseString("Invalid client id", "", CommonLib.RESPONSE_INVALID_CLIENT_ID);

		// check for app type
		if (!appType.equals(CommonLib.ANDROID_APP_TYPE))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_APP_TYPE);

		UserDAO userDao = new UserDAO();

		// access token validity
		int userId = userDao.userActive(accessToken);

		if (userId > 0) {
			// Iterate over all the wishes
			MessageDAO messageDao = new MessageDAO();
			JSONArray messageArr = new JSONArray();
			JSONObject messageJson = new JSONObject();
			ArrayList<UserCompactMessage> messages = messageDao.getAcceptedUsersForMessages(userId);
			try {
				for (UserCompactMessage message : messages) {
					messageArr.put(JsonUtil.getUserCompatJson(message));
				}
				messageJson.put("messages", messageArr);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return CommonLib.getResponseString(messageJson, "", CommonLib.RESPONSE_SUCCESS);
		}
		return CommonLib.getResponseString("failure", "", CommonLib.RESPONSE_FAILURE);
	}
	
	@Path("/delete")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject deleteWishes(
			@FormParam("access_token") String accessToken,
			@FormParam("wishId") int wishId,
			@FormParam("userId") int otherUserId) {

		UserDAO dao = new UserDAO();
		int userId = dao.userActive(accessToken);
		boolean value = false;

		if (userId > 0) {

			MessageDAO messageDao = new MessageDAO();
			value = messageDao.deleteMessage(wishId, userId);

			if (value)
				return CommonLib.getResponseString(String.valueOf(wishId),
						"success", CommonLib.RESPONSE_SUCCESS);

		}

		return CommonLib.getResponseString("failure", "failure",
				CommonLib.RESPONSE_FAILURE);

	}


}
