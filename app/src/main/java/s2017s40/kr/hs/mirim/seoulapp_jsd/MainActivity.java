package s2017s40.kr.hs.mirim.seoulapp_jsd;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
    //오픈 API 키
    String key="hxHjiOLsl7JaN0OUdYG3%2Fbvt4KQfpvDkFCUo8OAFfzsRAMwHxYs8koZT8FIQxW56b9o5o%2BAGgld95CaXlaT3ng%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //GPS 이용 위한 준비
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        FindImage = findViewById(R.id.main_search_image);

        //권한 설정
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
        } else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},0);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
        StrictMode.enableDefaults();

        //fragment 띄울 버튼 이벤트 설정
        FindImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //파싱에 사용할 변수 정의
        boolean inShltrNm = false, inLegaldongNm = false, inLatitude = false, inHardness = false,inRdnmadr = false, initem = false;
        double indistance = 0.0;
        String shltrNm = null, legaldongNm = null, latitude = null ,hardness = null, rdnmadr = null;

        mRecyclerView = (RecyclerView) findViewById(R.id.main_list_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataList = new ArrayList<>();

        //GPS 위치 정보
       /* Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);//가장 최근의 위치 정보 가지고 옴
        String provider = location.getProvider();//GPS
        double g_longitude = location.getLongitude();//경도
        double g_latitude = location.getLatitude();//위도
        double g_altitude = location.getAltitude();//고도
        //갱신 위한 설정(위치 정보 시간, 거리에 따라 갱신)
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000,
                1,
                gpsLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1000,
                1,
                gpsLocationListener);*/
        double g_longitude =  127.025716;//경도
        double g_latitude = 37.638043;//위도

        //오픈 API 파싱
        try{
            URL url = new URL("http://api.data.go.kr/openapi/heat-wve-shltr-std?" + "&ServiceKey=" + key);

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("shltrNm")){//쉼터명
                            inShltrNm = true;
                        }
                        if(parser.getName().equals("rdnmadr")){//소재지도로명주소
                            inRdnmadr = true;
                        }
                        if(parser.getName().equals("legaldongNm")){//법정동명
                            inLegaldongNm = true;
                        }
                        if(parser.getName().equals("latitude")){//위도
                            inLatitude = true;
                        }
                        if(parser.getName().equals("hardness")){;//경도
                            inHardness = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inShltrNm){ //isTitle이 true일 때 태그의 내용을 저장.
                            shltrNm = parser.getText();
                            inShltrNm = false;
                        }
                        if(inLegaldongNm){
                            legaldongNm = parser.getText();
                            CheckLocal(legaldongNm);
                            inLegaldongNm = false;
                        }
                        if(inRdnmadr){
                            String arr[] = parser.getText().split(",");
                            rdnmadr = arr[0];
                            inRdnmadr = false;
                        }
                        if(inLatitude){
                            latitude = parser.getText();
                        }
                        if(inHardness){
                            hardness = parser.getText();
                        }
                        if(inLatitude && inHardness){
                            indistance = computeDistance(new Coords(g_latitude,g_longitude), new Coords(Double.parseDouble(latitude), Double.parseDouble(hardness)));
                            inLatitude = false;
                            inHardness = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            myDataList.add(new XmlDTO(shltrNm, rdnmadr, Double.toString(indistance)));
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
            Log.e("파싱 에러","파싱 에러.");
            Log.e("error",String.valueOf(e));
        }

        //어뎁터 설정 및 방향 설정
        mAdapter = new MainAdapter(myDataList, new MainAdapter.ClickCallback() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, ShowListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                finish();
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    //swipe 함수
    public void fetchTimelineAsync(int page) {
        swipeContainer.setRefreshing(false);
    }

    //지역 구분 함수
    public String CheckLocal(String str){
        String arr[] = str.split(" ");
        if(arr[0].equals("서울특별시")){
            return "서울특별시";
        }
        return "";
    }

    //거리 계산 함수
    public double computeDistance(Coords startCoords, Coords destCoords){
        double startLatRads = degreesToRadians(startCoords.getLatitude());
        double startLongRads = degreesToRadians(startCoords.getLongitude());
        double destLatRads = degreesToRadians(destCoords.getLatitude());
        double destLongRads = degreesToRadians(destCoords.getLongitude());
        double Radius = 6371; // 지구의 반경 (km)
        double distance = Math.acos(Math.sin(startLatRads) * Math.sin(destLatRads) + Math.cos(startLatRads) * Math.cos(destLatRads) * Math.cos(startLongRads - destLongRads)) * Radius;
        return distance;
    }
    public double degreesToRadians(double degrees) {
        double radians = (degrees * Math.PI)/180;
        return radians;
    }

    //갱신되는 위치 정보 함수
   /* final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            double g_longitude = location.getLongitude();
            double g_latitude = location.getLatitude();
            double g_altitude = location.getAltitude();

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };*/

}
