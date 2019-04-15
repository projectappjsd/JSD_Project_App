package s2017s40.kr.hs.mirim.seoulapp_jsd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PlusFragment extends Fragment implements View.OnClickListener{
    View view;
    ImageView Air, Fan, Toilet;
    TextView Cancel, Ok;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plus, container, false);
        Air = view.findViewById(R.id.plus_Air_image);
        Fan = view.findViewById(R.id.plus_Fan_image);
        Toilet = view.findViewById(R.id.plus_Toilet_image);
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.plus_Air_image :
                Air.setImageResource(R.drawable.air_on);
                break;
            case R.id.plus_Fan_image :
                Air.setImageResource(R.drawable.fan_on);
                break;
            case R.id.plus_Toilet_image :
                Air.setImageResource(R.drawable.toilet_on);
                break;
        }
    }

}
