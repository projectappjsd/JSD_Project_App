package s2017s40.kr.hs.mirim.seoulapp_jsd;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;
    private ImageView FindImage;
    private RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<XmlDTO> list = null;
    final String TAG = "MainActivity";
    RecyclerView recyclerView;
    private FragmentManager fragmentManager = getSupportFragmentManager();

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
        ) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 0);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        StrictMode.enableDefaults();

        //fragment 띄울 버튼 이벤트 설정
        FindImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlusFragment bottomSheetDialog = PlusFragment.getInstance();
                bottomSheetDialog.show(getSupportFragmentManager(),"bottomSheet");
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
        } else {
            //사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄운다.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 0);
        }
        StrictMode.enableDefaults();

        mRecyclerView = (RecyclerView) findViewById(R.id.main_list_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        list = new ArrayList<>();

        String sendmsg = "vision_write";
        String result = "값"; //자신이 보내고싶은 값을 보내시면됩니다
        try{
            String rst = new Task(sendmsg).execute(result,"vision_write").get();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
