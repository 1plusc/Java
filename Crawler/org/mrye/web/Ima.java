/**
 * 
 */
package org.mrye.web;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mrye.log.MRYE_Log;

/**
 * @author Alpha
 * 
 */
public class Ima {
	private static String referer = "";
	private static String cookie = "";
	private static String conection = "keep-alive";
	private MRYE_Log ml = null;

	public void get_Content_From_WebPage(String url, int when) {
		when = -1;
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		Header[] resp = null;
		Get_Url gu = new Get_Url();
		gu._Call_(url,1);//首次进行存入
		if (when != -1) {// 如果不是第一个种子链接的话，传入之前的Cookies值对
			// method = init_request(method, when);
		} else {
			method.setRequestHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0 MRYE");
			method.setRequestHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			method.setRequestHeader("Referer", url);
		}

		try {
			client.executeMethod(method);
			resp = method.getResponseHeaders();
			store_resp(resp);

			String body = method.getResponseBodyAsString();
			String character = matchCharset(body);
			byte[] b = method.getResponseBody();
			String tempbody = new String(b, character);
			gu.reslove_Content(url,tempbody);
		} catch (HttpException e) {
			ml = new MRYE_Log();
			ml.record(e.toString(), 1);
		} catch (IOException e) {
			ml = new MRYE_Log();
			ml.record(e.toString(), 1);
		}

	}

	/**
	 * 存储响应头信息
	 * 
	 * @param resp
	 */
	private void store_resp(Header[] resp) {
		// TODO Auto-generated method stub

	}

	/**
	 * 智能识别网页编码格式
	 * 
	 * @param body
	 * @return
	 */
	private static String matchCharset(String body) {
		Document doc = Jsoup.parse(body);
		Elements ele = doc.getElementsByTag("meta ");
		for (Element e : ele) {
			if (e.attr("content").toLowerCase().contains("utf-8")
					|| e.attr("content").toLowerCase().contains("utf8")) {
				return "utf-8";
			} else if (e.attr("content").toLowerCase().contains("gb2312")) {
				return "gb2312";
			} else if (e.attr("content").toLowerCase().contains("gbk")) {
				return "gbk";
			} else if (e.attr("content").toLowerCase().contains("iso859-1")) {
				return "ISO8859-1";
			} else if (e.attr("charset").toLowerCase().contains("utf8")) {
				return "utf8";
			} else if (e.attr("charset").toLowerCase().contains("gb2312")) {
				return "gb2312";
			} else if (e.attr("charset").toLowerCase().contains("gbk")) {
				return "gbk";
			} else if (e.attr("charset").toLowerCase().contains("iso859-1")) {
				return "ISO8859-1";
			} else {
				return "utf-8";
			}
		}
		return "utf-8";
	}
}
