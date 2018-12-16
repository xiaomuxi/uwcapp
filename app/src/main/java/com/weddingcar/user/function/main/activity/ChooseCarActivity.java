package com.weddingcar.user.function.main.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;

import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.base.fragment.BaseActivityFragment;
import com.weddingcar.user.common.base.fragment.BaseLoadingFragment;
import com.weddingcar.user.function.main.fragment.GroupListFragment;
import com.weddingcar.user.function.main.fragment.SelfListFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseCarActivity extends BaseActivity {

    @BindView(R.id.tl_bar)
    TabLayout tl_bar;
    @BindView(R.id.vp_list)
    ViewPager vp_list;

    private List<String> tabIndicators;
    private final String[] indicators = new String[]{"自选婚车", "组合推荐"};
    private ContentPagerAdapter contentAdapter;

    String time;
    int initIndex = 0;

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_choose_car);
        ButterKnife.bind(this);
        time = getIntent().getStringExtra("time");
        initIndex = getIntent().getIntExtra("index", 0);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft(time);
    }

    @Override
    protected void initView() {
        super.initView();

        initViewPager();
        initTab();
    }

    private void initViewPager() {
        FragmentFactory.createFragment(FragmentFactory.TAB_SELF);
        FragmentFactory.createFragment(FragmentFactory.TAB_GROUP);
        vp_list.addOnPageChangeListener(mPageChangeListener);
    }

    private void initTab(){
        tabIndicators = Arrays.asList(indicators);
        tl_bar.setTabMode(TabLayout.MODE_FIXED);
        tl_bar.setTabTextColors(Color.parseColor("#999999"), Color.parseColor("#ff4545"));
        tl_bar.setSelectedTabIndicatorColor(Color.parseColor("#ff4545"));
        ViewCompat.setElevation(tl_bar, 10);
        tl_bar.setupWithViewPager(vp_list);

        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        vp_list.setAdapter(contentAdapter);

        vp_list.setCurrentItem(initIndex);
    }

    public String getDate() {
        return time;
    }

    /**
     * 采用工厂类进行创建Fragment，便于扩展，已经创建的Fragment不再创建
     */
    public static class FragmentFactory {
        public static final int TAB_SELF = 0;
        public static final int TAB_GROUP = 1;

        //记录所有的fragment，防止重复创建
        public static final Map<Integer, BaseActivityFragment> mFragmentMap = new HashMap<>();

        public static BaseActivityFragment createFragment(int index) {
            BaseActivityFragment fragment = mFragmentMap.get(index);
            if (null == fragment) {
                switch (index) {
                    //自选
                    case TAB_SELF:
                        fragment = new SelfListFragment();
                        break;
                    //组合
                    case TAB_GROUP:
                        fragment = new GroupListFragment();
                        break;
                    default:
                        break;
                }
                mFragmentMap.put(index, fragment);
            }
            return fragment;
        }
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public BaseActivityFragment getItem(int position) {
            return FragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }

    private final ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageSelected(int position) {
            BaseActivityFragment fragment = FragmentFactory.createFragment(position);
            if (fragment instanceof BaseLoadingFragment) {
                ((BaseLoadingFragment) fragment).show();
            }
        }
    };
}
