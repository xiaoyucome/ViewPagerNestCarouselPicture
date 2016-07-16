package lxy.github.viewpager.nest.carouse.picture;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RollViewPager extends ViewPager {
    private int downX, downY;
    private String[] imageDescripe;
    private int previousPosition = 0;
    private LinearLayout llPointGroup;
    private TextView tvImageDescription;
    private List<ImageView> imageViewList;
    private MyAdapter myAdapter;
    private RunnableTask runnableTask;
    private int currentPosition = 0;

    public RollViewPager(Context context) {
        super(context);
    }

    public RollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                llPointGroup.getChildAt(currentPosition).setEnabled(true);
                llPointGroup.getChildAt(previousPosition).setEnabled(false);
                tvImageDescription.setText(imageDescripe[currentPosition]);
                previousPosition = currentPosition;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            RollViewPager.this.setCurrentItem(currentPosition);
            startRoll();
        }
    };

    class RunnableTask implements Runnable {
        @Override
        public void run() {
            currentPosition = (currentPosition + 1) % imageViewList.size();
            handler.obtainMessage().sendToTarget();
        }
    }

    public void setResource(List<ImageView> imageViewList, String[] imageDescripe, TextView tvImageDescription, LinearLayout llPointGroup) {
        this.llPointGroup = llPointGroup;
        this.imageViewList = imageViewList;
        this.imageDescripe = imageDescripe;
        this.tvImageDescription = tvImageDescription;
        runnableTask = new RunnableTask();
    }

    public void startRoll() {
        if (myAdapter == null) {
            myAdapter = new MyAdapter();
            this.setAdapter(myAdapter);
            llPointGroup.getChildAt(previousPosition).setEnabled(true);
            tvImageDescription.setText(imageDescripe[previousPosition]);
        } else {
            myAdapter.notifyDataSetChanged();
        }

        handler.postDelayed(runnableTask, 3000);
    }

    public class MyAdapter extends PagerAdapter {

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
            ImageView imageView = imageViewList.get(position % imageViewList.size());

            imageView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);//按住图片的时候移出图片的 轮播方法
                            break;
                        case MotionEvent.ACTION_UP:
                            startRoll();
                            break;
                    }
                    return true;
                }
            });

            ((RollViewPager) container).addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((RollViewPager) container).removeView(imageViewList.get(position % imageViewList.size()));
        }
    }
}
