package bluetooth.jsp.com.myviews;

import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bluetooth.jsp.com.myviews.ui.ClockActivity;
import bluetooth.jsp.com.myviews.ui.FaceActivity;
import bluetooth.jsp.com.myviews.ui.HeartBezierActivity;
import bluetooth.jsp.com.myviews.ui.QQRedDotActivity;
import bluetooth.jsp.com.myviews.ui.RadarActivity;
import bluetooth.jsp.com.myviews.ui.RotateViewActivity;
import bluetooth.jsp.com.myviews.ui.SearchViewActivity;
import bluetooth.jsp.com.myviews.ui.SmartisanTimeActivity;
import bluetooth.jsp.com.myviews.view.RotateArrowView;

public class MainActivity extends ListActivity {

    String[] titles = new String[]{
            "我的时钟",
            "锤子时钟",
            "雷达分布图",
            "笑脸ING",
            "Bezier心形",
            "QQ红点拖拽",
            "旋转箭头",
            "搜索动画"
    };

    Class[] classes = new Class[]{
            ClockActivity.class,
            SmartisanTimeActivity.class,
            RadarActivity.class,
            FaceActivity.class,
            HeartBezierActivity.class,
            QQRedDotActivity.class,
            RotateViewActivity.class,
            SearchViewActivity.class
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,titles));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(this,classes[position]));
    }

}
