package show.me.the.money.pricecomparison.network;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import show.me.the.money.pricecomparison.Common;
import show.me.the.money.pricecomparison.fragment.PriceFragment;
import show.me.the.money.pricecomparison.listener.ConnectionListener;

/**
 * Created by KOITT on 2018-01-16.
 */

public class HttpConnection extends AsyncTask {

    ConnectionListener _listener;

    public void request(String url,
                        String params,
                        Common.HTTP_TYPE type,
                        ConnectionListener listener,
                        String identifier)
    {
        _listener = listener;
        execute(url, params, type, identifier);
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
        String identifier = objects[3].toString();

        if(identifier.equals(PriceFragment.IDENTIFIER_USD_TO_KRW)){
            try{
                String decoding = null;
                try{
                    decoding = URLDecoder.decode(Common.CHECK_KRW_URL, "utf-8");
                    Log.d(Common.TAG, "decoding : " + decoding);
                }catch (Exception e){
                    e.printStackTrace();
                }

                UnsupportedMimeTypeException mimeType = new UnsupportedMimeTypeException("Hey this is Mime",  "application/json", Common.CHECK_KRW_URL);
                String mime = mimeType.getMimeType();

                Document doc = Jsoup.connect(Common.CHECK_KRW_URL).ignoreContentType(true).get();


//                Document document = Jsoup.connect(decoding).get();
                Map<String,String> result = new HashMap<>();

//                Elements elements = doc.select(".gcw_inputFsCw1k6p3");
                Elements elements = doc.select("input");
//                Elements e = elements.attr("id","gcw_inputFsCw1k6p3");

                for(Element e : elements){
//                     Element eee = e.getElementById("\'gcw_valFsCw1k6p35\'");
                    if(e.id().contains("gcw_valFsCw1k6p35")){
                        Log.d(Common.TAG, e.toString());
                        String resultStr =  e.attr("rate").replace("\'","");
                        resultStr = resultStr.replace("'\\","");
                        resultStr = resultStr.replace("\\","");
                        _listener.onSuccess(resultStr, identifier);
                        return null;
                    }
                }
                _listener.onFail("fail_krw", "환율정보 가져오기 실패", identifier);
                return null;
            }catch (IOException e){
                e.printStackTrace();
                _listener.onFail("-1","환율 정보 파싱 실패", identifier);
                return null;
            }
        }else{
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
                _listener.onSuccess(res, identifier);
                Log.d("lee - ", "response success: " + res);
                return res;
            }else{
                _listener.onFail("-1","서버 연결에 실패하였습니다.", identifier);
                return null;
            }
        }

    }
}
