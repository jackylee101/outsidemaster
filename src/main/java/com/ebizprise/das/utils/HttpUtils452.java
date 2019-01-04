package com.ebizprise.das.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLInitializationException;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 基于HttpClient实现的Http请求工具
 * 
 * @author 大漠知秋
 * @description 支持POST和GET请求,支持SSL
 * @description HttpClient 4.5.2
 * @description fastjson 1.2.31
 * 
 *              <dependency> <groupId>org.apache.httpcomponents</groupId>
 *              <artifactId>httpclient</artifactId> <version>4.5.2</version>
 *              </dependency>
 * 
 *              <dependency> <groupId>com.alibaba</groupId>
 *              <artifactId>fastjson</artifactId> <version>1.2.31</version>
 *              </dependency>
 */
public class HttpUtils452 {
	private static final Log logger = LogFactory.getLog(HttpUtils452.class);

	/** 连接池 */
	private static PoolingHttpClientConnectionManager connManager;

	/** 编码 */
	private static final String ENCODING = "UTF-8";

	/** 出错返回结果 */
	// private static final String RESULT = "-1";

	/**
	 * 初始化连接池管理器,配置SSL
	 */
	static {
		if (connManager == null) {

			try {
				// 创建ssl安全访问连接
				// 获取创建ssl上下文对象
				// SSLContext sslContext = getSSLContext(true, null, null);
				SSLContextBuilder builder = new SSLContextBuilder();
				builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
				// 注册
				Registry<ConnectionSocketFactory> registry = RegistryBuilder
						.<ConnectionSocketFactory> create()
						.register("http", PlainConnectionSocketFactory.INSTANCE)
						.register("https",
								new SSLConnectionSocketFactory(builder.build()))
						.build();

				// ssl注册到连接池
				connManager = new PoolingHttpClientConnectionManager(registry);
				connManager.setMaxTotal(1000); // 连接池最大连接数
				connManager.setDefaultMaxPerRoute(20); // 每个路由最大连接数

			} catch (SSLInitializationException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			} // catch (CertificateException e) {
				// e.printStackTrace();
				// } catch (FileNotFoundException e) {
				// e.printStackTrace();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }

		}
	}

	/**
	 * 获取客户端连接对象
	 * 
	 * @param timeOut
	 *            超时时间
	 * @return
	 */
	private static CloseableHttpClient getHttpClient(Integer timeOut) {

		// 配置请求参数
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(timeOut)
				.setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
		// 配置超时回调机制
		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {
				if (executionCount >= 3) {// 如果已经重试了3次，就放弃
					return false;
				}
				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
					return false;
				}
				if (exception instanceof InterruptedIOException) {// 超时
					return true;
				}
				if (exception instanceof UnknownHostException) {// 目标服务器不可达
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
					return false;
				}
				if (exception instanceof SSLException) {// ssl握手异常
					return false;
				}
				HttpClientContext clientContext = HttpClientContext
						.adapt(context);
				HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};

		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connManager)
				.setDefaultRequestConfig(requestConfig)
				.setRetryHandler(retryHandler).build();

		return httpClient;

	}

	/**
	 * 获取SSL上下文对象,用来构建SSL Socket连接
	 * 
	 * @param isDeceive
	 *            是否绕过SSL
	 * @param creFile
	 *            整数文件,isDeceive为true 可传null
	 * @param crePwd
	 *            整数密码,isDeceive为true 可传null, 空字符为没有密码
	 * @return SSL上下文对象
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws CertificateException
	 */
	// private static SSLContext getSSLContext(boolean isDeceive, File creFile,
	// String crePwd) throws KeyManagementException,
	// NoSuchAlgorithmException, KeyStoreException, CertificateException,
	// FileNotFoundException, IOException {
	//
	// SSLContext sslContext = null;
	//
	// if (isDeceive) {
	// sslContext = SSLContext.getInstance("SSLv3");
	// // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
	// X509TrustManager x509TrustManager = new X509TrustManager() {
	// @Override
	// public X509Certificate[] getAcceptedIssuers() {
	// return null;
	// }
	//
	// @Override
	// public void checkServerTrusted(X509Certificate[] chain,
	// String authType) throws CertificateException {
	// }
	//
	// @Override
	// public void checkClientTrusted(X509Certificate[] chain,
	// String authType) throws CertificateException {
	// }
	// };
	// sslContext
	// .init(null, new TrustManager[] { x509TrustManager }, null);
	// } else {
	// if (null != creFile && creFile.length() > 0) {
	// if (null != crePwd) {
	// KeyStore keyStore = KeyStore.getInstance(KeyStore
	// .getDefaultType());
	// keyStore.load(new FileInputStream(creFile),
	// crePwd.toCharArray());
	// sslContext = SSLContexts
	// .custom()
	// .loadTrustMaterial(keyStore,
	// new TrustSelfSignedStrategy()).build();
	// } else {
	// throw new SSLHandshakeException("整数密码为空");
	// }
	// }
	// }
	//
	// return sslContext;
	//
	// }

	/**
	 * post请求,支持SSL
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求头信息
	 * @param params
	 *            请求参数
	 * @param timeOut
	 *            超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
	 * @param isStream
	 *            是否以流的方式获取响应信息
	 * @return 响应信息
	 * @throws UnsupportedEncodingException
	 */
	public static String doPost(String url, Map<String, Object> headers,
			Map<String, Object> params, Integer timeOut, boolean isStream)
			throws UnsupportedEncodingException {

		// 创建post请求
		HttpPost httpPost = new HttpPost(url);

		// 添加请求头信息
		if (null != headers) {
			for (Map.Entry<String, Object> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue().toString());
			}
		}

		// 添加请求参数信息
		if (null != params) {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					covertParams2NVPS(params), ENCODING);
			formEntity
					.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
			httpPost.setEntity(formEntity);
		}

		return getResult(httpPost, timeOut, isStream);

	}

	/**
	 * post请求,支持SSL
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求头信息
	 * @param params
	 *            请求参数
	 * @param timeOut
	 *            超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
	 * @param isStream
	 *            是否以流的方式获取响应信息
	 * @return 响应信息
	 * @throws UnsupportedEncodingException
	 */
	public static String doPost(String url, JSONObject headers,
			JSONObject params, Integer timeOut, boolean isStream)
			throws UnsupportedEncodingException {

		// 创建post请求
		HttpPost httpPost = new HttpPost(url);

		// 添加请求头信息
		if (null != headers) {
			for (Map.Entry<String, Object> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue().toString());
			}
		}

		// 添加请求参数信息
		if (null != params) {
			httpPost.setEntity(new UrlEncodedFormEntity(
					covertParams2NVPS(params), ENCODING));
		}

		return getResult(httpPost, timeOut, isStream);

	}

	/**
	 * post请求,支持SSL
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param timeOut
	 *            超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
	 * @return 响应信息
	 * @throws UnsupportedEncodingException
	 */
	public static String doPost(String url, Map<String, Object> params,
			Integer timeOut) throws UnsupportedEncodingException {

		// 创建post请求
		HttpPost httpPost = new HttpPost(url);

		// 添加请求参数信息
		if (null != params) {
			httpPost.setEntity(new UrlEncodedFormEntity(
					covertParams2NVPS(params), ENCODING));
		}

		return getResult(httpPost, timeOut, false);

	}

	/**
	 * post请求,支持SSL
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param timeOut
	 *            超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
	 * @return 响应信息
	 * @throws UnsupportedEncodingException
	 */
	public static String doPost(String url, JSONObject params, Integer timeOut)
			throws UnsupportedEncodingException {

		// 创建post请求
		HttpPost httpPost = new HttpPost(url);

		// 添加请求参数信息
		if (null != params) {
			httpPost.setEntity(new UrlEncodedFormEntity(
					covertParams2NVPS(params), ENCODING));
		}

		return getResult(httpPost, timeOut, false);

	}

	/**
	 * get请求,支持SSL
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求头信息
	 * @param params
	 *            请求参数
	 * @param timeOut
	 *            超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
	 * @param isStream
	 *            是否以流的方式获取响应信息
	 * @return 响应信息
	 * @throws URISyntaxException
	 */
	public static String doGet(String url, Map<String, Object> headers,
			Map<String, Object> params, Integer timeOut, boolean isStream)
			throws URISyntaxException {

		// 构建url
		URIBuilder uriBuilder = new URIBuilder(url);
		// 添加请求参数信息
		if (null != params) {
			uriBuilder.setParameters(covertParams2NVPS(params));
		}

		// 创建post请求
		HttpGet httpGet = new HttpGet(uriBuilder.build());

		// 添加请求头信息
		if (null != headers) {
			for (Map.Entry<String, Object> entry : headers.entrySet()) {
				httpGet.addHeader(entry.getKey(), entry.getValue().toString());
			}
		}

		return getResult(httpGet, timeOut, isStream);

	}

	/**
	 * get请求,支持SSL
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param timeOut
	 *            超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
	 * @return 响应信息
	 * @throws URISyntaxException
	 */
	public static String doGet(String url, Map<String, Object> params,
			Integer timeOut) throws URISyntaxException {

		// 构建url
		URIBuilder uriBuilder = new URIBuilder(url);
		// 添加请求参数信息
		if (null != params) {
			uriBuilder.setParameters(covertParams2NVPS(params));
		}

		// 创建post请求
		HttpGet httpGet = new HttpGet(uriBuilder.build());

		return getResult(httpGet, timeOut, false);

	}

	/**
	 * get请求,支持SSL
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求头信息
	 * @param params
	 *            请求参数
	 * @param timeOut
	 *            超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
	 * @param isStream
	 *            是否以流的方式获取响应信息
	 * @return 响应信息
	 * @throws URISyntaxException
	 */
	public static String doGet(String url, JSONObject headers,
			JSONObject params, Integer timeOut, boolean isStream)
			throws URISyntaxException {

		// 构建url
		URIBuilder uriBuilder = new URIBuilder(url);
		// 添加请求参数信息
		if (null != params) {
			uriBuilder.setParameters(covertParams2NVPS(params));
		}

		// 创建post请求
		HttpGet httpGet = new HttpGet(uriBuilder.build());

		// 添加请求头信息
		if (null != headers) {
			for (Map.Entry<String, Object> entry : headers.entrySet()) {
				httpGet.addHeader(entry.getKey(), entry.getValue().toString());
			}
		}

		return getResult(httpGet, timeOut, isStream);

	}

	/**
	 * get请求,支持SSL
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param timeOut
	 *            超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
	 * @return 响应信息
	 * @throws URISyntaxException
	 */
	public static String doGet(String url, JSONObject params, Integer timeOut)
			throws URISyntaxException {

		// 构建url
		URIBuilder uriBuilder = new URIBuilder(url);
		// 添加请求参数信息
		if (null != params) {
			uriBuilder.setParameters(covertParams2NVPS(params));
		}

		// 创建post请求
		HttpGet httpGet = new HttpGet(uriBuilder.build());

		return getResult(httpGet, timeOut, false);

	}

	private static String getResult(HttpRequestBase httpRequest,
			Integer timeOut, boolean isStream) {

		CloseableHttpResponse response = null;
		JSONObject jSONObject = new JSONObject();
		String error = "";
		try {
			// 获取连接客户端
			CloseableHttpClient httpClient = getHttpClient(timeOut);
			// 发起请求
			response = httpClient.execute(httpRequest);

			int respCode = response.getStatusLine().getStatusCode();
			// 如果是重定向
			if (302 == respCode) {
				String locationUrl = response.getLastHeader("Location")
						.getValue();
				return getResult(new HttpPost(locationUrl), timeOut, isStream);
			}
			// 正确响应
			if (200 == respCode) {
				// 获得响应实体
				HttpEntity entity = response.getEntity();
				// 如果是以流的形式获取
				if (isStream) {
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(entity.getContent(), ENCODING));
					String len = "";
					while ((len = br.readLine()) != null) {
						sb.append(len);
					}
					jSONObject = JSONObject.parseObject(sb.toString());
				} else {
					String jsonString = EntityUtils.toString(entity, ENCODING);
					if (jsonString.length() < 1) {
						jSONObject.put("success", false);
					} else {
						jSONObject = JSONObject.parseObject(jsonString);
					}
				}

			} else if (403 == respCode) {
				error = "403:錢用光了";
			} else if (404 == respCode) {
				error = "404:";
			} else if (503 == respCode) {
				error = "503:網站掛掉了";
			}
		} catch (HttpHostConnectException e) {
			error = "连接失敗!!!";
			e.printStackTrace();
		} catch (ConnectionPoolTimeoutException e) {
			error = "从连接池获取连接超时!!!";
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			error = "响应超时";
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			error = "请求超时";
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			error = "http协议错误";
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			error = "不支持的字符编码";
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			error = "不支持的请求操作";
			e.printStackTrace();
		} catch (ParseException e) {
			error = "解析错误";
			e.printStackTrace();
		} catch (IOException e) {
			error = "IO错误";
			e.printStackTrace();
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					error = "关闭响应连接出错";
					e.printStackTrace();
				}
			}
			if (!"".equals(error)) {
				jSONObject.put("success", false);
				jSONObject.put("error", error);
				logger.error(error);
			}
			return jSONObject.toString();
		}

	}

	/**
	 * Map转换成NameValuePair List集合
	 * 
	 * @param params
	 *            map
	 * @return NameValuePair List集合
	 */
	public static List<NameValuePair> covertParams2NVPS(
			Map<String, Object> params) {

		List<NameValuePair> paramList = new LinkedList<>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			paramList.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue().toString()));
		}

		return paramList;

	}

	public static void main(String[] args) throws Exception {

		/*
		 * JSONObject jsonObject1 = new JSONObject(); jsonObject1.put("kkk",
		 * "djsklfj"); JSONObject jsonObject2 = new JSONObject();
		 * jsonObject2.put("sds", "324324");
		 */

		System.out.println(doPost("https://kyfw.12306.cn/otn/login/init", null,
				null, 6000, false));

	}

}
