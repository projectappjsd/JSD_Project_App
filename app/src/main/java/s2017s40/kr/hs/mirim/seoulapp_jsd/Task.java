package s2017s40.kr.hs.mirim.seoulapp_jsd;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Task  extends AsyncTask<String, Void, String> {
    public static String ip ="10.96.124.213:8087"; //자신의 IP번호
    String sendMsg, receiveMsg;
    String serverip = "http://"+ip+"/Android_Conn/list.jsp"; // 연결할 jsp주소

    Task(String sendmsg){
        this.sendMsg = sendmsg;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;
            URL url = new URL(serverip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            if(sendMsg.equals("vision_write")){
                sendMsg = "vision_write="+strings[0]+"&type="+strings[1];
            }else if(sendMsg.equals("vision_list")){
                sendMsg = "&type="+strings[0];
            }

            osw.write(sendMsg);
            osw.flush();
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {
                Log.e("통신 결과", conn.getResponseCode()+"에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;
    }
}