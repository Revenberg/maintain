package com.javainuse.step;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.javainuse.objects.DataObject;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader implements ItemReader<DataObject> {
    // one instance, reuse
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
	private static long counter = 0;
	@Override
	public DataObject read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        HttpClientExample obj = new HttpClientExample();
        if (list.isEmpty()) {
                try {
                obj.sendGet();
            } finally {
                obj.close();
            }
        }

        if (!list.isEmpty()) {
		   String element = list.get(0);
		   list.remove(0);			
		   return new DataObject(element);
	   } 
	   return null;
   }

private void close() throws IOException {
        httpClient.close();
    }

    private void sendGet() throws Exception {

        Reader.counter++;
        HttpGet request = new HttpGet("http://40.122.30.210:8090/rest/v1/vers?page=" + Reader.counter + "&size=1");

        // add request headers
        request.addHeader("Accept", "application/json");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }

        }
    }
}