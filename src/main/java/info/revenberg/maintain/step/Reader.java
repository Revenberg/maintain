package info.revenberg.maintain.step;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

public class Reader implements ItemReader<String> {
    // one instance, reuse
    private CloseableHttpClient httpClient = null;
    private static long counter = 0;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (httpClient == null) {
            ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    // Honor 'keep-alive' header
                    HeaderElementIterator it = new BasicHeaderElementIterator(
                            response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                    while (it.hasNext()) {
                        HeaderElement he = it.nextElement();
                        String param = he.getName();
                        String value = he.getValue();
                        if (value != null && param.equalsIgnoreCase("timeout")) {
                            try {
                                return Long.parseLong(value) * 1000;
                            } catch (NumberFormatException ignore) {
                            }
                        }
                    }
                    // keep alive for 30 seconds
                    return 30 * 1000;
                }

            };

            this.httpClient = HttpClients.custom().setConnectionManagerShared(true).setKeepAliveStrategy(myStrategy)
                    .build();
        }
        String rc = "";
        try {
            rc = this.sendGet();
        } finally {
            this.close();
        }
        return rc;
    }

    private void close() throws IOException {
        httpClient.close();
    }

    private String sendGet() throws Exception {

        Reader.counter++;
        HttpGet request = new HttpGet("http://40.122.30.210:8090/rest/v1/vers?page=" + Reader.counter + "&size=1");

        // add request headers
        request.addHeader("Accept", "application/json");

        CloseableHttpResponse response = httpClient.execute(request);
        try {
            // Get HttpResponse Status
            HttpEntity entity = response.getEntity();
            
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return result;
            }
        } finally {
            response.close();
        }
        return "error";
    }
}