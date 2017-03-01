package time.hanwei.com;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

public class MainActivity extends AppCompatActivity {

//    private TextView timeT;
//    private TextView endTimet;
//    private TextView minute_15;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        timeT = (TextView) findViewById(R.id.tv_time);
//        endTimet = (TextView) findViewById(R.id.tv_time_end);
//        minute_15 = (TextView) findViewById(R.id.minute_15);
//        initData();
//        initDatas();
//    }
//
//    private void initDatas() {
//        //方法一
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd%20HH:mm");
//        Date now = new Date();
//        System.out.println("当前时间：" + sdf.format(now));
//        Date afterDate = new Date(now .getTime() + 300000);
//        System.out.println(sdf.format(afterDate ));
//
//        //方法二
//        Calendar nowTime = Calendar.getInstance();
//        nowTime.add(Calendar.MINUTE,-10);
//        System.out.println(sdf.format(nowTime.getTime()));
//        minute_15.setText(sdf.format(nowTime.getTime()));
//    }
//
//    private int hour;
//    private int day; // 需要更改的天数
//    private int day2; // 需要更改的天数
//    private void initData() {
////        Time time = new Time();
////        time.setToNow();
////        int year = time.year;
////        int month = time.month;
////        int day = time.monthDay;
////        int minute = time.minute;
////        int hour = time.hour;
////        int sec = time.second;
////        timeT.setText(""+year+""+month+""+day);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String str = format.format(new java.util.Date());
//
//        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//        String str1 = format1.format(new java.util.Date());
//
//        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");
//        String str2 = format2.format(new java.util.Date());
//
//        Calendar c = Calendar.getInstance(); // 当时的日期和时间
//        hour = c.get(Calendar.HOUR_OF_DAY) - 1;//往前推一小时
////        timeT.setText("0"+hour+":00:00");
//        day = c.get(Calendar.DAY_OF_MONTH) - 1;//往前推一天
//        day2 = c.get(Calendar.DAY_OF_MONTH) - 30;//往前推一个月
//        timeT.setText(str);//当前的时间
////        endTimet.setText(str1+" 0"+hour+":00:00");
////        endTimet.setText(str2+"-0"+day+" 00:00:00");
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date dBefore = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.DAY_OF_MONTH,1);
//        dBefore = calendar.getTime();
//        Log.e("time","--"+sdf.format(dBefore)+" 00:00:00");
//
//        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
//        Date dBefore0 = new Date();
//        Calendar calendar0 = Calendar.getInstance();
//        calendar0.setTime(new Date());
//        calendar0.add(Calendar.HOUR_OF_DAY,-24);//往前推一小时
////        calendar0.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
//        dBefore0 = calendar0.getTime();
//        endTimet.setText(sdf.format(dBefore)+" 00:00:00");
//
//
//    }

    private ListView listView;
    private PullToRefreshLayout ptrl;
    private boolean isFirstIn=true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        // 此处设置下拉刷新或上拉加载更多监听器
        ptrl.setOnPullListener(new MyPullListener());
        // 设置带gif动画的上拉头与下拉头
        try
        {
            ptrl.setGifRefreshView(new GifDrawable(getResources(), R.drawable.anim));
            ptrl.setGifLoadmoreView(new GifDrawable(getResources(), R.drawable.anim));

        } catch (Resources.NotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        listView = (ListView) ptrl.getPullableView();
        initListView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        // 第一次进入自动刷新
        if (isFirstIn)
        {
            ptrl.autoRefresh();
            isFirstIn = false;
        }
    }

    /**
     * ListView初始化方法
     */
    private void initListView()
    {
        List<String> items = new ArrayList<String>();
        for (int i = 0; i < 30; i++)
        {
            items.add("这里是item " + i);
        }
        MyAdapter adapter = new MyAdapter(this, items);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id)
            {
                Toast.makeText(
                        MainActivity.this,
                        "LongClick on "
                                + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                Toast.makeText(MainActivity.this,
                        " Click on " + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 下拉刷新与上拉加载更多监听器
     */
    public class MyPullListener implements PullToRefreshLayout.OnPullListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 5000);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载更多操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 5000);
        }
    }


}
