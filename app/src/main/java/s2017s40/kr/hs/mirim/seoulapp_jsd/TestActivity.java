package s2017s40.kr.hs.mirim.seoulapp_jsd;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class TestActivity extends AppCompatActivity {

    String key="2Wtg732VOXaynAnVHFZ9iSO1GeFrBxzQvCLXNXWCBnDJ7O3%2BLOGBMbiUMv5RhimVH2r3Uq6K%2FF24rJeTNETjrQ%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
        } else{
            //사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄운다.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},0);
        }
        StrictMode.enableDefaults();

        TextView status1 = (TextView)findViewById(R.id.result); //파싱된 결과확인!

        boolean inAddr = false, initem = false;
        String addr = null;

        try{
            URL url = new URL("http://api.data.go.kr/openapi/heat-wve-shltr-std?"
                    + "&ServiceKey="
                    + key
            ); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("shltrNm")){ //title 만나면 내용을 받을수 있게 하자
                            inAddr = true;
                        }else if(parser.getName().equals("shltrNm")){
                            inAddr = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inAddr){ //isTitle이 true일 때 태그의 내용을 저장.
                            addr = parser.getText();
                            inAddr = false;
                        }   break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            status1.setText(status1.getText()+"주소 : "+ addr +"\n");
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
            status1.setText("에러가..났습니다...");
            Log.e("error",String.valueOf(e));
        }
    }
}
