package show.me.the.money.pricecomparison;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import show.me.the.money.pricecomparison.fragment.PriceFragment;
import show.me.the.money.pricecomparison.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout = null;
    ViewPager viewPager = null;
    Fragment[] _arrayFragment = {new PriceFragment(), new SettingFragment()};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adding Toolbar to the activity

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("시세"));
        tabLayout.addTab(tabLayout.newTab().setText("위젯설정"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), _arrayFragment);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    class TabPagerAdapter extends FragmentStatePagerAdapter {
        // Count number of tabs
        Fragment [] arrayFragment;

        public TabPagerAdapter(FragmentManager fm, Fragment[]array) {
            super(fm);
            this.arrayFragment = array;
        }

        @Override
        public Fragment getItem(int position) {
            return arrayFragment[position];
        }

        @Override
        public int getCount() {
            return arrayFragment.length;
        }
    }

}
