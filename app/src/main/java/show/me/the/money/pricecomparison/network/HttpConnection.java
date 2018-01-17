package show.me.the.money.pricecomparison.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import show.me.the.money.pricecomparison.Common;
import show.me.the.money.pricecomparison.listener.ConnectionListener;

/**
 * Created by KOITT on 2018-01-16.
 */

public class HttpConnection extends AsyncTask {

    ConnectionListener _listener;
    Common.EXCHANGE _exchange;

    public void request(String url,
                        String params,
                        Common.HTTP_TYPE type,
                        Common.EXCHANGE exchange,
                        ConnectionListener listener)
    {
        _exchange = exchange;
        _listener = listener;
        execute(url, params, type);
    }

    InputStream getRequestGET(String urlString, String params){
        InputStream dataInputStream = null;
        try {

            String makeUrl = urlString + params;
            URL url = new URL(makeUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);

            dataInputStream = urlConnection.getInputStream();

//            HttpsURLConnection cc = (HttpsURLConnection) url.openConnection();
//            //set timeout for reading InputStream
//            cc.setReadTimeout(5000);
//            // set timeout for connection
//            cc.setConnectTimeout(5000);
//            //set HTTP method to GET
//            cc.setRequestMethod("GET");
//            //set it to true as we are connecting for input
//            cc.setDoInput(true);
//
//            //reading HTTP response code
//            int response = cc.getResponseCode();

            //           //if response code is 200 / OK then read Inputstream
//            if (response == HttpURLConnection.HTTP_OK) {
//                DataInputStream = cc.getInputStream();
//            }

        } catch (Exception e) {
            Log.e("lee - ", "Error in GetData", e);
        }
        return dataInputStream;
    }

    InputStream getRequestPOST(String urlString, String params){
        InputStream DataInputStream = null;
        try {

            //Post parameters
//            String PostParam = "first_name=android&amp;last_name=pala";

            //Preparing
            URL url = new URL(urlString);

            HttpURLConnection cc = (HttpURLConnection)url.openConnection();
            //set timeout for reading InputStream
            cc.setReadTimeout(5000);
            // set timeout for connection
            cc.setConnectTimeout(5000);
            //set HTTP method to POST
            cc.setRequestMethod("POST");
            //set it to true as we are connecting for input
            cc.setDoInput(true);
            //opens the communication link
            cc.connect();

            //Writing data (bytes) to the data output stream
            DataOutputStream dos = new DataOutputStream(cc.getOutputStream());
            dos.writeBytes(params);
            //flushes data output stream.
            dos.flush();
            dos.close();

            //Getting HTTP response code
            int response = cc.getResponseCode();

            //if response code is 200 / OK then read Inputstream
            //HttpURLConnection.HTTP_OK is equal to 200
            if(response == HttpURLConnection.HTTP_OK) {
                DataInputStream = cc.getInputStream();
            }

        } catch (Exception e) {
            Log.e("lee - ", "Error in GetData", e);
        }
        return DataInputStream;

    }


    String ConvertStreamToString(InputStream stream) {
        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder response = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            Log.e("lee - ", "Error in ConvertStreamToString", e);
        } catch (Exception e) {
            Log.e("lee - ", "Error in ConvertStreamToString", e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                Log.e("lee - ", "Error in ConvertStreamToString", e);
            } catch (Exception e) {
                Log.e("lee - ", "Error in ConvertStreamToString", e);
            }
        }
        return response.toString();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String url = objects[0].toString();
        String params = objects[1].toString();
        Common.HTTP_TYPE type = (Common.HTTP_TYPE)objects[2];

        InputStream in  = null;
        String res = null;

        if(type == Common.HTTP_TYPE.GET){
            //GET
            in = getRequestGET(url, params);
        }else{
            //POST
            in = getRequestPOST(url, params);
        }

        if (in != null) {
            res = ConvertStreamToString(in);
            _listener.onSuccess(res, _exchange);
            Log.d("lee - ", "response success: " + res);
            return res;
        }else{
            _listener.onFail("-1","서버 연결에 실패하였습니다.");
            return null;
        }
    }
}
