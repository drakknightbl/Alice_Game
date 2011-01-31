package com.alu.alice_game.server;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;



public class HttpRequest {
    

        /**
        * Post request (upload files)
        * @param sUrl
        * @param params Form data
        * @param files
        * @return
        */
        public HttpData post(String sUrl, List<NameValuePair>nameValuePairs) {
                HttpData ret = new HttpData();
                HttpThread ht = new HttpThread(sUrl, nameValuePairs);
                ht.run();
                return ret;
        }
        
        class HttpThread extends Thread {
        	String sUrl;
        	List<NameValuePair> params;
        	
        	public HttpThread(String sUrl, List<NameValuePair>nameValuePairs) {
                    this.sUrl = sUrl;
                    this.params = nameValuePairs;
        	}
        	
        	public void run() {
                    try {
                        HttpClient hc = new DefaultHttpClient();
                        HttpPost hp = new HttpPost(this.sUrl);
                        Log.i("HttpRequest", "send : " + this.sUrl);
                        hp.setEntity(new UrlEncodedFormEntity(this.params));
                        HttpResponse hr = hc.execute(hp);
                        
                    } catch (ClientProtocolException e) {
                        Log.e("HttpRequest", "ClientProtocolException");
                    } catch (IOException e) {
                        Log.e("HttpRequest", "IOException" + e.getMessage());

                    }

                }
        }
        
}
