package notificationupdater;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Stream;

public class HttpGetter {

    private String url;
    private HttpsURLConnection connection;

    public HttpGetter(String url) {
        this.url = url;
    }

    public Stream<String> getContent() throws Exception {
        HttpClient client = new HttpClient(new SslContextFactory.Server());
        client.start();
        ContentResponse response = client.newRequest(url)
                .method(HttpMethod.GET)
                .agent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:93.0) Gecko/20100101 Firefox/93.0")
                .send();
        client.stop();
        return Stream.of(response.getContentAsString().split("\n"));
    }
}
