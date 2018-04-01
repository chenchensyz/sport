package com.hbasesoft.manager.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.hbasesoft.framework.manager.wechat.guangjia.util.MyX509TrustManager;
import com.hbasesoft.manager.vo.Token;
import com.hbasesoft.manager.vo.WeixinGroupNew;
import com.hbasesoft.manager.vo.WeixinUserInfo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

/**
 * 类名: CommonUtil </br>
 * 描述: 通用工具类 </br>
 * 开发人员： souvc </br>
 * 创建时间： 2015-11-27 </br>
 * 发布版本：V1.0 </br>
 */
public class CommonUtil {
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

	// 凭证获取（GET）
	public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	 public final static String WE_URl="/root/log/";

//	public final static String WE_URl = "E:\\weixinimage\\";

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("连接超时：{}", ce);
		} catch (Exception e) {
			log.error("https请求异常：{}", e);
		}
		return jsonObject;
	}

	/**
	 * 获取接口访问凭证
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static Token getToken(String appid, String appsecret) {
		Token token = null;
		String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		// 发起GET请求获取凭证
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				token = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return token;
	}

	/**
	 * URL编码（utf-8）
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据内容类型判断文件扩展名
	 * 
	 * @param contentType
	 *            内容类型
	 * @return
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType))
			fileExt = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileExt = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileExt = ".amr";
		else if ("video/mp4".equals(contentType))
			fileExt = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileExt = ".mp4";
		return fileExt;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param openId
	 *            用户标识
	 * @return WeixinUserInfo
	 */
	public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
		WeixinUserInfo weixinUserInfo = null;
		// 拼接请求地址
		String requestUrl = " https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				// 用户的标识
				weixinUserInfo.setOpenId(jsonObject.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
				// 用户关注时间
				weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
				// 昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// 用户的语言，简体中文为zh_CN
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				// 用户头像
				weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// 分组id
				weixinUserInfo.setGroupId(jsonObject.getString("groupid"));
				// 备注
				weixinUserInfo.setRemark(jsonObject.getString("remark"));
				// 标签
				weixinUserInfo.setTagList(jsonObject.getString("tagid_list"));
			} catch (Exception e) {
				if (0 == weixinUserInfo.getSubscribe()) {
					log.error("用户{}已取消关注", weixinUserInfo.getOpenId());
				} else {
					int errorCode = jsonObject.getInt("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
				}
			}
		}
		return weixinUserInfo;
	}

	/**
	 * 修改备注
	 * 
	 * @param accessToken
	 * @param openId
	 * @param remark
	 * @return
	 */
	public static JSONObject updateRemark(String accessToken, String openId, String remark) {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject j = new JSONObject();
		try {
			j.put("openid", openId);
			j.put("remark", remark);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", j.toString());
		System.out.println("返回结果：" + jsonObject);

		return jsonObject;
	}

	/**
	 * 获取用户分组
	 * 
	 * @param accessToken
	 * @return
	 */
	public static List<WeixinGroupNew> getGroup(String accessToken) {
		List<WeixinGroupNew> groups = new ArrayList<WeixinGroupNew>();
		String requestUrl = " https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		// JSONArray jsonArray = new
		// JSONArray(jsonObject.get("groups").toString());
		net.sf.json.JSONArray jsonArray = jsonObject.getJSONArray("groups");
		for (Object object : jsonArray) {
			JSONObject json2 = JSONObject.fromObject(object);
			WeixinGroupNew g2 = new WeixinGroupNew();
			g2.setGroupId(json2.getString("id"));
			g2.setGroupName(json2.getString("name"));
			g2.setCount(json2.getString("count"));
			groups.add(g2);
			System.out.println(g2.getGroupName());
		}
		return groups;
	}

	/**
	 * 获取所有的关注用户
	 * 
	 * @param accessToken
	 * @return
	 */
	public static List<String> getOpenids(String accessToken) {
		List<String> openIds = new ArrayList<String>();
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		JSONObject json1 = JSONObject.fromObject(jsonObject.get("data").toString());
		JSONArray json2 = new JSONArray(json1.get("openid").toString());
		for (int i = 0; i < json2.length(); i++) {
			openIds.add(json2.getString(i));
		}
		return openIds;
	}

	/**
	 * 批量获取用户
	 * 
	 * @param accessToken
	 * @param openIds
	 * @return
	 */
	public static List<JSONObject> getUsers(String accessToken, List<String> openIds) {
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token" + accessToken;
		List<JSONObject> users = Lists.newArrayList();
		JSONObject group = new JSONObject();
		for (String openId : openIds) {
			JSONObject j = new JSONObject();
			j.put("openid", openId);
			users.add(j);
		}
		group.put("user_list", users);
		System.out.println(group);
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", group.toString());
		System.out.println(jsonObject);
		return users;
	}

	/**
	 * 创建分组
	 * 
	 * @param accessToken
	 * @param newGroupName
	 */
	public static void createGroup(String accessToken, String newGroupName) {
		// 请求路径
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=" + accessToken;
		// 创建分组
		JSONObject j = new JSONObject();
		JSONObject group = new JSONObject();
		try {
			j.put("name", newGroupName);
			group.put("group", j);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", group.toString());
		System.out.println("返回结果：" + jsonObject);
	}

	/**
	 * 删除分组
	 */
	public static void deleteGroup(String accessToken, String groupId) {
		// 请求路径
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=" + accessToken;
		JSONObject j = new JSONObject();
		JSONObject group = new JSONObject();
		try {
			j.put("id", groupId);
			group.put("group", j);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", group.toString());
		System.out.println("返回结果：" + jsonObject);
	}

	/**
	 * 修改分组名
	 * 
	 * @param groupId
	 * @param newGroupName
	 * @return 如 {"errcode": 0, "errmsg": "ok"}
	 */
	public static JSONObject updateGroup(String accessToken, String groupId, String newGroupName) {
		String url = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=" + accessToken;
		JSONObject j = new JSONObject();
		JSONObject group = new JSONObject();
		try {
			j.put("id", groupId);
			j.put("name", newGroupName);
			group.put("group", j);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String returnJson = MyHttpUtils.getReturnJson(url, group.toString());//
		System.out.println("WeixinManager.createGroup()" + returnJson);
		JSONObject json;
		try {
			json = JSONObject.fromObject(returnJson);
		} catch (JSONException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 将用户移动到指定分组
	 */
	public static JSONObject moveGroup(String accessToken, String openid, String to_groupid) {
		String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=" + accessToken;// 请求路径
		// 封装请求数据
		JSONObject j = new JSONObject();
		JSONObject group = new JSONObject();
		try {
			j.put("openid", openid);
			j.put("to_groupid", to_groupid);
			group.put("group", j);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", group.toString());
		System.out.println("返回结果：" + jsonObject);

		return jsonObject;

	}

	/**
	 * 黑名单列表
	 */
	public static List<String> getBlacklist(String accessToken, String begin_openid) {
		String url = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=" + accessToken;
		List<String> openIds = new ArrayList<String>();
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "GET", begin_openid);
		JSONObject json1 = JSONObject.fromObject(jsonObject.get("data").toString());
		JSONArray json2 = new JSONArray(json1.get("openid").toString());
		for (int i = 0; i < json2.length(); i++) {
			openIds.add(json2.getString(i));
		}
		return openIds;
	}

	/**
	 * 加入黑名单
	 */
	public static JSONObject joinBlacklist(String accessToken, String[] openid_list) {
		String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=" + accessToken;
		JSONObject j = new JSONObject();
		try {
			j.put("openid_list", openid_list);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject json = JSONObject.fromObject(j);
		JSONObject httpsRequest = CommonUtil.httpsRequest(url, "POST", json.toString());
		System.out.println(httpsRequest);
		return json;
	}

	/**
	 * 移出黑名单
	 */
	public static JSONObject exitBlacklist(String accessToken, String[] openid_list) {
		String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=" + accessToken;
		JSONObject j = new JSONObject();
		try {
			j.put("openid_list", openid_list);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject json = JSONObject.fromObject(j);
		JSONObject httpsRequest = CommonUtil.httpsRequest(url, "POST", json.toString());
		System.out.println(httpsRequest);
		return json;
	}

	/**
	 * 获取标签信息
	 * 
	 * @param accessToken
	 * @return
	 */
	public static List<WeixinGroupNew> getTags(String accessToken) {
		List<WeixinGroupNew> groups = new ArrayList<WeixinGroupNew>();
		String requestUrl = " https://api.weixin.qq.com/cgi-bin/tags/get?access_token=" + accessToken;
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		// JSONArray jsonArray = new
		// JSONArray(jsonObject.get("groups").toString());
		if (jsonObject.toString().indexOf("tags") > 0) {
			net.sf.json.JSONArray jsonArray = jsonObject.getJSONArray("tags");
			for (Object object : jsonArray) {
				JSONObject json2 = JSONObject.fromObject(object);
				WeixinGroupNew g2 = new WeixinGroupNew();
				g2.setGroupId(json2.getString("id"));
				g2.setGroupName(json2.getString("name"));
				g2.setCount(json2.getString("count"));
				groups.add(g2);
			}
		}
		return groups;
	}

	/**
	 * 修改标签名
	 * 
	 * @param accessToken
	 * @param TagId
	 * @param TagName
	 * @return
	 */
	public static JSONObject updateTag(String accessToken, String TagId, String TagName) {
		String url = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=" + accessToken;
		JSONObject j = new JSONObject();
		JSONObject group = new JSONObject();
		try {
			j.put("id", TagId);
			j.put("name", TagName);
			group.put("tag", j);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String returnJson = MyHttpUtils.getReturnJson(url, group.toString());//
		System.out.println("WeixinManager.updateTag()" + returnJson);
		JSONObject json;
		try {
			json = JSONObject.fromObject(returnJson);
		} catch (JSONException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 删除标签
	 */
	public static void deleteTag(String accessToken, String tagId) {
		// 请求路径
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=" + accessToken;
		JSONObject j = new JSONObject();
		JSONObject tag = new JSONObject();
		try {
			j.put("id", tagId);
			tag.put("tag", j);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", tag.toString());
		System.out.println("返回结果：" + jsonObject);
	}

	/**
	 * 获取标签下粉丝列表
	 * 
	 * @param args
	 */
	public static List<String> getTasgOpenids(String accessToken, String tagId) {
		List<String> openIds = new ArrayList<String>();
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=" + accessToken;
		JSONObject j = new JSONObject();
		try {
			j.put("tagid", tagId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject json = JSONObject.fromObject(j);
		JSONObject httpsRequest = CommonUtil.httpsRequest(requestUrl, "POST", json.toString());
		if (new Integer(httpsRequest.get("count").toString()) != 0) {
			JSONObject json1 = JSONObject.fromObject(httpsRequest.get("data").toString());
			JSONArray json2 = new JSONArray(json1.get("openid").toString());
			for (int i = 0; i < json2.length(); i++) {
				openIds.add(json2.getString(i));
			}
		}
		return openIds;
	}

	/**
	 * 批量为用户打标签
	 * 
	 * @param args
	 */
	public static JSONObject joinTag(String accessToken, String[] openids, String tagId) {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=" + accessToken;// 请求路径
		// 封装请求数据
		JSONObject j = new JSONObject();
		j.put("openid_list", openids);
		j.put("tagid", tagId);
		JSONObject httpsRequest = CommonUtil.httpsRequest(requestUrl, "POST", j.toString());
		System.out.println("返回结果：" + httpsRequest);
		return httpsRequest;
	}

	/**
	 * 批量为用户取消标签
	 * 
	 * @param accessToken
	 * @param openids
	 * @param tagId
	 * @return
	 */
	public static JSONObject cancelTag(String accessToken, String[] openids, String tagId) {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=" + accessToken;// 请求路径
		// 封装请求数据
		JSONObject j = new JSONObject();
		j.put("openid_list", openids);
		j.put("tagid", tagId);
		JSONObject httpsRequest = CommonUtil.httpsRequest(requestUrl, "POST", j.toString());
		System.out.println("返回结果：" + httpsRequest);
		return httpsRequest;
	}

	/**
	 * 将content中图片url替换为微信url
	 * 
	 * @param accessToken
	 * @param content
	 *            图文内容
	 * @param index
	 *            从第几个字符开始替换
	 * @return
	 * @throws Exception
	 */
	public static String getUrl(String accessToken, String content, int index) throws Exception {

		int srcStart = content.indexOf("src=\"http", index); // 获取src出现的位置
		int srcEnd = content.indexOf("\"", srcStart + 5);
		;
		srcStart = srcStart + 5;
		String src = content.substring(srcStart, srcEnd); // 获取图片路径
		if (src.indexOf(".jpg") == -1 && src.indexOf(".png") == -1) {
			return getUrl(accessToken, content, srcEnd);
		}
		src = src.substring(src.indexOf("http"),
				src.indexOf(".jpg") != -1 ? src.indexOf(".jpg") + 4 : src.indexOf(".png") + 4);

		File file = new File(WE_URl);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			System.out.println("//不存在");
			file.mkdir();
		} else {
			System.out.println("//目录存在");
		}

		File canonicalPath = downLoadFromUrl(src,
				WE_URl + System.currentTimeMillis() + src.substring(src.lastIndexOf("."), src.length()));

		// 执行上传图片方法
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + accessToken;
		String scptjg = postFile(url, canonicalPath.getCanonicalPath());
		JSONObject scptjgJson = JSONObject.fromObject(scptjg);
		String newPath = scptjgJson.getString("url");
		// 替换字符串中该图片路径
		content = content.replace(src, newPath);
		// 查看字符串下方是否还有img标签
		int sfhyImg = content.indexOf("<img", srcEnd);
		if (sfhyImg == -1) {
			return content;
		} else {
			return getUrl(accessToken, content, srcEnd);
		}
	}

	// 上传图片素材/上传图文消息内的图片获取URL
	// url - 路径
	// filePath 图片绝对路径
	public static String postFile(String url, String filePath) {
		File file = new File(filePath);
		if (!file.exists())
			return null;
		String result = "";
		try {
			URL url1 = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Cache-Control", "no-cache");
			String boundary = "-----------------------------" + System.currentTimeMillis();
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			OutputStream output = conn.getOutputStream();
			output.write(("--" + boundary + "\r\n").getBytes());
			output.write(
					String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", file.getName())
							.getBytes());
			output.write("Content-Type: image/jpeg \r\n\r\n".getBytes());
			byte[] data = new byte[1024];
			int len = 0;
			FileInputStream input = new FileInputStream(file);
			while ((len = input.read(data)) > -1) {
				output.write(data, 0, len);
			}
			output.write(("\r\n--" + boundary + "\r\n\r\n").getBytes());
			output.flush();
			output.close();
			input.close();
			InputStream resp = conn.getInputStream();
			StringBuffer sb = new StringBuffer();
			while ((len = resp.read(data)) > -1)
				sb.append(new String(data, 0, len, "utf-8"));
			resp.close();
			result = sb.toString();
		} catch (IOException e) {
			log.info("postFile数据传输失败", e);
		}
		return result;
	}

	public static File downLoadFromUrl(String srcUrl, String saveFilePath) {
		boolean flag = false;
		File sf = null;
		long start = System.currentTimeMillis();
		log.info("开始", start);
		try {

			OkHttpClient ok = new OkHttpClient();
			Request req = new Request.Builder().url(srcUrl).build();
			Response res = ok.newCall(req).execute();
			ResponseBody resbody = res.body();
			InputStream is = resbody.byteStream();
			String contentType = resbody.contentType().toString();
			System.out.println(contentType);
			// 数据缓冲
			byte[] bs = new byte[6144];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			sf = new File(saveFilePath);

			OutputStream os = new FileOutputStream(sf);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();

			is.close();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		long end = System.currentTimeMillis();
		return sf;
	}

	/**
	 * 判断微信图片是否下载失败
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static boolean downLoadFail(InputStream is) throws IOException {
		Gson gson = new Gson();
		StringBuffer buffer = new StringBuffer();
		InputStreamReader inputStreamReader = new InputStreamReader(is, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		// 释放资源
		bufferedReader.close();
		inputStreamReader.close();
		if (buffer != null) {
			String json = buffer.toString();

			if (StringUtils.isNotBlank(json)) {
				Map<String, String> result = gson.fromJson(json, Map.class);
				if (result != null) {
					if (result.get("errcode") != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void main(String args[]) throws IOException {
		// 获取接口访问凭证
		// String accessToken = CommonUtil.getToken("wxc55d77a3fd2f5786",
		// "4de3e10bbc77b7e057fd3b63378404b3").getAccessToken();
		String accessToken = "VplU4nmAVvdg_Sr5SmLUIyQmj_BEVd05d1NC5hqSc1D1OLPjvud1nv0f_O2BERyFEOXxKmfIpjDS88xhmZcH8EburKdyNUs9N89E81eXLJgseQL5HhyCw6LLYCUfzD1vHYIjAEAOFP";

		// getBlacklist(accessToken,"0");
		/**
		 * 获取用户信息
		 */
		// WeixinUserInfo user =
		// getUserInfo(accessToken,"oJoysxP6tIHtLC9uv2AO3HPSEN18");
		// System.out.println("OpenID：" + user.getOpenId());
		// System.out.println("关注状态：" + user.getSubscribe());
		// System.out.println("关注时间：" + user.getSubscribeTime());
		// System.out.println("昵称：" + user.getNickname());
		// System.out.println("性别：" + user.getSex());
		// System.out.println("国家：" + user.getCountry());
		// System.out.println("省份：" + user.getProvince());
		// System.out.println("城市：" + user.getCity());
		// System.out.println("语言：" + user.getLanguage());
		// System.out.println("头像：" + user.getHeadImgUrl());
		// System.out.println("分组：" + user.getGroupId());
		// System.out.println("标签：" + user.getTagList());
		/**
		 * 获取openid
		 */
		// List<String> openids = getOpenids(accessToken);
		// System.out.println(openids.size());

		/** 分组列表 */
		// getGroup(accessToken);

		// createGroup(accessToken,"测试");

		// updateGroup(accessToken,"101","测试");

		// moveGroup(accessToken, "", "101");

		// deleteGroup(accessToken,"104");

		/** 黑名单 */
		// joinBlacklist(accessToken,new
		// String[]{"oJoysxCNHfpj8JSLdY7q_7KwUpJo"});

		// exitBlacklist(accessToken,new
		// String[]{"oJoysxCNHfpj8JSLdY7q_7KwUpJo"});

		/** 备注 */
		// updateRemark(accessToken,"oJoysxCM3OEE-hlFnPr8ZRVOepLI","");

		/** 标签列表 */
		// getTags(accessToken);

		// getTasgOpenids(accessToken, "100");

		// joinTag(accessToken, new String[]{"asfasdf","adgsdfd"}, "100");

		// deleteGroup(accessToken,"107");
		// getUsers(accessToken,openids);

		String url = "http://news.ncnews.com.cn/ncxw/bwzg_rd/201711/W020171110025792099004.jpg";
		downLoadFromUrl(url, "E:\\weixinimage\\" + System.currentTimeMillis() + ".jpg");
		// downLoadFromUrl(url, WE_URl+System.currentTimeMillis()+".jpg");
	}
}