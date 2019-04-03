package s2017s40.kr.hs.mirim.seoulapp_jsd;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;

    private ImageView FindImage;

    private RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<XmlDTO> myDataList;

    String key="hxHjiOLsl7JaN0OUdYG3%2Fbvt4KQfpvDkFCUo8OAFfzsRAMwHxYs8koZT8FIQxW56b9o5o%2BAGgld95CaXlaT3ng%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FindImage = findViewById(R.id.main_search_image);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
        } else{
            //사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄운다.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},0);
        }
        StrictMode.enableDefaults();

        boolean inAddr = false, initem = false;
        String addr = null;

        try{
            URL url = new URL("http://api.data.go.kr/openapi/heat-wve-shltr-std?"
                    + "&pageNo=1&numOfRows=10&ServiceKey="
                    + key
            ); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");
            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("shltrNm")){ //title 만나면 내용을 받을수 있게 하자
                            inAddr = true;
                        } break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inAddr){ //isTitle이 true일 때 태그의 내용을 저장.
                            addr = parser.getText();
                            inAddr = false;
                        }   break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            //status1.setText(status1.getText()+"주소 : "+ addr +"\n");
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
            //status1.setText("에러가..났습니다...");
            Log.e("error",String.valueOf(e));
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.main_list_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataList = new ArrayList<>();

        myDataList.add(new XmlDTO("안녕","오늘은","누구누구의","생일이야"));
        myDataList.add(new XmlDTO("안녕","오늘은","누구누구의","생일이야"));
        myDataList.add(new XmlDTO("안녕","오늘은","누구누구의","생일이야"));
        myDataList.add(new XmlDTO("안녕","오늘은","누구누구의","생일이야"));

        mAdapter = new MainAdapter(myDataList, new MainAdapter.ClickCallback() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, ShowListActivity.class);
                //intent.putExtra("Date",arr.get(position));
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
    public void fetchTimelineAsync(int page) {
        swipeContainer.setRefreshing(false);
    }
}
