package com.dengjinwen.basetool.library.function.expression;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dengjinwen.basetool.library.R;
import com.dengjinwen.basetool.library.function.expression.widget.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian on 2016/6/24 19:35
 * mabeijianxi@gmail.com
 */
public abstract class BaseInsideFragment extends Fragment {

    private ViewPager vp_expression;
    private ArrayList<ExpressionGridFragment> data;
    private CirclePageIndicator cp_iner_expression;
    private String[][] mPageDate;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.expression_iner_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mountData();
    }

    /**
     * 设置每类表情每页的数据，这里采用二位数组比较方便
     * @param pageDate
     */
    public void setPageDate(String[][] pageDate) {
        this.mPageDate = pageDate;
    }

    public String[][] getmPageDate() {
        return mPageDate;
    }

    /**
     * 数据配置
     */
    private void mountData() {
        data = setupData();
        vp_expression.setAdapter(new ExpressionPagerAdapter(getChildFragmentManager(), data));
//       是否需要CirclePageIndicator，最近使用页只有一页是不需要的
        if (isNeedCirclePageIndicator()) {
            cp_iner_expression.setVisibility(View.VISIBLE);
            cp_iner_expression.setViewPager(vp_expression);
        } else {
            cp_iner_expression.setVisibility(View.GONE);

        }
    }

    private void initView(View v) {
        vp_expression = (ViewPager) v.findViewById(R.id.vp_iner_expression);
        cp_iner_expression = (CirclePageIndicator) v.findViewById(R.id.cp_iner_expression);
    }

    protected abstract boolean isNeedCirclePageIndicator();

    protected ArrayList<ExpressionGridFragment> getData() {
        return data;
    }

    public ViewPager getVp_expression() {
        return vp_expression;
    }

    protected abstract ArrayList<ExpressionGridFragment> setupData();

    private static class ExpressionPagerAdapter extends FragmentStatePagerAdapter {

        private final List<ExpressionGridFragment> fragments;

        public ExpressionPagerAdapter(FragmentManager fm, List<ExpressionGridFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }
    }
}
