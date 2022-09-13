package http;

import java.net.HttpURLConnection;
import java.util.Map;

public class VRHttpClientConfig {
    private static final String TAG = VRHttpClientConfig.class.getSimpleName();
    public static String EM_TIME_OUT_KEY = "em_timeout";
    public static int EM_DEFAULT_TIMEOUT = 60*1000;

    public static String processUrl(String remoteUrl){
        if (remoteUrl.contains("+")) {
            remoteUrl = remoteUrl.replaceAll("\\+", "%2B");
        }

        if (remoteUrl.contains("#")) {
            remoteUrl = remoteUrl.replaceAll("#", "%23");
        }

        return remoteUrl;
    }

    public static int getTimeout(Map<String,String> headers){
        int timeout = VRHttpClientConfig.EM_DEFAULT_TIMEOUT;

        if(headers != null && headers.get(VRHttpClientConfig.EM_TIME_OUT_KEY) != null){
            timeout = Integer.valueOf(headers.get(VRHttpClientConfig.EM_TIME_OUT_KEY));
            headers.remove(VRHttpClientConfig.EM_TIME_OUT_KEY);
        }

        return timeout;
    }

    static void checkAndProcessSSL(String url, HttpURLConnection conn) {
    }

}
