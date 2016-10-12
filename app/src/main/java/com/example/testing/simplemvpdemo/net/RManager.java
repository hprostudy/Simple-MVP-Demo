package com.example.testing.simplemvpdemo.net;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by H on 16/8/8.
 */
public enum RManager {

    INSTANCE;

    RManager() {
        sOkHttpClient = getClient();
        sRetrofit = new Retrofit.Builder().baseUrl(UrlManager.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(sOkHttpClient)
                .build();
    }

    public Retrofit sRetrofit;
    private RManager sRManager;
    private OkHttpClient sOkHttpClient;

    public Retrofit getRetrofit() {
        return sRetrofit;
    }

    public OkHttpClient getOkHttpClient() {
        return sOkHttpClient;
    }

    /**
     * 请求Header拦截器
     */
    public class HeadersInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            Headers headers = request.headers();
            if (request.headers() == null) {
                headers = new Headers.Builder().build();
            }

            Headers proxyHeaders = headers.newBuilder().set("Cookie",
                    CookieBuilder.INSTANCE.addCookieHeader(headers.get("Cookie")).toString())
                    .build();

            Request proxyRequest = request.newBuilder().headers(proxyHeaders).build();
            return chain.proceed(proxyRequest);
        }
    }

    /**
     * 请求log拦截器
     */
    public class LoggerInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

//            long t1 = System.nanoTime();
//            Logger.i(String.format("Sending request %s on %s%n%s",
//                    request.url(), chain.connection(), request.headers()));

            okhttp3.Response response = chain.proceed(request);


//            long t2 = System.nanoTime();
//            Logger.i(String.format("Received response for %s in %.1fms%n",
//                    response.request().url(), (t2 - t1) / 1e6d));

//            Request original = chain.request();

            return response;
        }

    }

    private OkHttpClient getClient() {

        // log用拦截器
        LoggerInterceptor logging = new LoggerInterceptor();

        //        // 如果使用到HTTPS，我们需要创建SSLSocketFactory，并设置到client
        //        SSLSocketFactory sslSocketFactory = null;
        //
        //        try {
        //            // 这里直接创建一个不做证书串验证的TrustManager
        //            final TrustManager[] trustAllCerts = new TrustManager[]{
        //                    new X509TrustManager() {
        //                        @Override
        //                        public void checkClientTrusted(X509Certificate[] chain, String authType)
        //                                throws CertificateException {
        //                        }
        //
        //                        @Override
        //                        public void checkServerTrusted(X509Certificate[] chain, String authType)
        //                                throws CertificateException {
        //                        }
        //
        //                        @Override
        //                        public X509Certificate[] getAcceptedIssuers() {
        //                            return new X509Certificate[]{};
        //                        }
        //                    }
        //            };
        //
        //            // Install the all-trusting trust manager
        //            final SSLContext sslContext = SSLContext.getInstance("SSL");
        //            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        //            // Create an ssl socket factory with our all-trusting manager
        //            sslSocketFactory = sslContext.getSocketFactory();
        //        } catch (Exception e) {
        //            Logger.e(e.getMessage());
        //        }

        return new OkHttpClient.Builder()
                // HeadInterceptor实现了Interceptor，用来往Request Header添加一些业务相关数据，如APP版本，token信息
//                .addInterceptor(new HeadersInterceptor())
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request originalRequest = chain.request();
//                        Request request;
//                        String method = originalRequest.method();
//                        Headers headers = originalRequest.headers();
//                        HttpUrl modifiedUrl = originalRequest.url().newBuilder()
//                                // Provide your custom parameter here
//                                .addQueryParameter("appversion", DeviceUtil.getVersionName() + "")
//                                .addQueryParameter("devicetype", "2")
//                                .addQueryParameter("devid", DeviceUtil.getImei())
//                                .addQueryParameter("sysversion", DeviceUtil.getSystemVersion())
//                                .build();
//                        request = originalRequest.newBuilder().url(modifiedUrl).build();
//                        return chain.proceed(request);
//                    }
//                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        //请求定制：添加请求头
                        Request.Builder requestBuilder = original.newBuilder();
//                                    .header("APPVERSION", ConstantInfo.DEFAULT_VERSION_NAME);

                        //请求体定制：统一添加token参数
                        if (original.body() instanceof FormBody) {
                            FormBody.Builder newFormBody = new FormBody.Builder();
                            FormBody oldFormBody = (FormBody) original.body();
                            for (int i = 0; i < oldFormBody.size(); i++) {
                                newFormBody.addEncoded(oldFormBody.encodedName(i),
                                        oldFormBody.encodedValue(i));
                            }
                            newFormBody
//                                        .add("registrationid",
//                                                PreferenceUtils.getString(MyApplication.mContext,
//                                                        "registrationid", ""))
//                                        .add("apptoken",
//                                                PreferenceUtils.getString(MyApplication.mContext,
//                                                        "apptoken", ""))
//                                    .add("appversion", DeviceUtil.getVersionName() + "")
//                                    .add("devicetype", "2")
//                                    .add("devid", DeviceUtil.getImei())
                                    .add("sysversion", "1");
                            requestBuilder.method(original.method(), newFormBody.build());
                        }
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(logging)
//                // 连接超时时间设置
                .connectTimeout(30, TimeUnit.SECONDS)
//                // 读取超时时间设置
                .readTimeout(30, TimeUnit.SECONDS)
//                .sslSocketFactory(sslSocketFactory)
//                // 信任所有主机名
////                .hostnameVerifier((hostname, session) -> true)
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        return true;
//                    }
//                })
                // 这里我们使用host name作为cookie保存的key
//                .cookieJar(new CookieJar() {
//                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
//
//                    @Override
//                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                        cookieStore.put(HttpUrl.parse(url.host()), cookies);
//                    }
//
//                    @Override
//                    public List<Cookie> loadForRequest(HttpUrl url) {
//                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse(url.host()));
//                        return cookies != null ? cookies : new ArrayList();
//                    }
//                })
                .build();
    }

}
