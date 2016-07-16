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

import java.util.ArrayList;
import java.util.List;

public class SecondFrag extends Fragment {

    private ViewPager mViewpager;
    private List<ImageView> imageViewList = new ArrayList<>(); // Viewpager的数据
    private String[] imageDescriptions;//图片信息描述

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_second, null);
        mViewpager = (ViewPager) view.findViewById(R.id.viewpager);

        imageDescriptions = new String[]{"美女一", "美女二", "美女三", "美女四", "美女五"};
        int[] imageResIDs = {R.mipmap.first, R.mipmap.second, R.mipmap.third, R.mipmap.four, R.mipmap.five};//图片资源id
        for (int i = 0; i < imageResIDs.length; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setBackgroundResource(imageResIDs[i]);
            imageViewList.add(iv);
        }
        mViewpager.setAdapter(new MyAdapter());
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
