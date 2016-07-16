package lxy.github.viewpager.nest.carouse.picture;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewpager;
    private HashMap<Integer, Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        fragments = new HashMap<>(3);
        fragments.put(0, new FirstFrag());
        fragments.put(1, new SecondFrag());
        fragments.put(2, new ThirdFrag());

        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mViewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
