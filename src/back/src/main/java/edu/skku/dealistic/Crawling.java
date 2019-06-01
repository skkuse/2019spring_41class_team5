package edu.skku.dealistic;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Crawling {

    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";
    private String result;

    // SSL 우회 등록
    public static void setSSL() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultHostnameVerifier(
                new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }
        );
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    public void main(String[] args) {
        try {
            // 1. URL 선언
            String connUrl = "https://derveljunit.tistory.com/280";

            // 2. SSL 체크
            if(connUrl.contains("https://")){
                Crawling.setSSL();
            }

            // 3. HTML 가져오기
            Connection conn = Jsoup
                    .connect(connUrl)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .userAgent(USER_AGENT)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true);

            Document doc = conn.get();
            result = doc.toString();

        } catch (IOException e) {
            // Exp : Connection Fail
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping("/3")
    public @ResponseBody String root_test() throws Exception{
        return "Hello Root3(/)";
    }

    @RequestMapping("/demo3")
    public @ResponseBody String demo_test() throws Exception{
        return result;
    }

}

