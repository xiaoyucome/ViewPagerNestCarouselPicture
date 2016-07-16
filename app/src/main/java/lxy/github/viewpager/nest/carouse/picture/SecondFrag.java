package lxy.github.viewpager.nest.carouse.picture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SecondFrag extends Fragment {

    private ViewPager mViewpager;
    private LinearLayout llPointGroup;
    private TextView tvImageDescription;
    private String[] imageDescriptions;//图片信息描述
    private List<ImageView> imageViewList = new ArrayList<>(); // Viewpager的数据
    private int previousPosition = 0; // 前一个被选中的position --> 记录位置

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_second, null);
        mViewpager = (ViewPager) view.findViewById(R.id.viewpager);
        tvImageDescription = (TextView) view.findViewById(R.id.tv_image_description);
        llPointGroup = (LinearLayout) view.findViewById(R.id.ll_point_group);

        imageDescriptions = new String[]{"美女一", "美女二", "美女三", "美女四", "美女五"};
        int[] imageResIDs = {R.mipmap.first, R.mipmap.second, R.mipmap.third, R.mipmap.four, R.mipmap.five};//图片资源id
        for (int i = 0; i < imageResIDs.length; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setBackgroundResource(imageResIDs[i]);
            imageViewList.add(iv);

            // 每循环一次需要向LinearLayout中添加一个点的view对象
            View point = new View(getActivity());
            point.setBackgroundResource(R.drawable.point_bg);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(5, 5);
            if (i != 0) {
                // 当前不是第一个点, 需要设置左边距
                params.leftMargin = 5;
            }
            point.setEnabled(false);
            point.setLayoutParams(params);
            llPointGroup.addView(point);
        }
        mViewpager.setAdapter(new MyAdapter());

        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int newPosition = position;
                tvImageDescription.setText(imageDescriptions[newPosition]);
                llPointGroup.getChildAt(previousPosition).setEnabled(false);
                llPointGroup.getChildAt(newPosition).setEnabled(true);
                previousPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        llPointGroup.getChildAt(previousPosition).setEnabled(true);
        tvImageDescription.setText(imageDescriptions[previousPosition]);
        return view;
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViewList.get(position);
            mViewpager.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mViewpager.removeView(imageViewList.get(position));
        }
    }
}
