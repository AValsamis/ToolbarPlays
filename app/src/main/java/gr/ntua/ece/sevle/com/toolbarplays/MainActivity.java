package gr.ntua.ece.sevle.com.toolbarplays;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ToolbarManipulation{
        protected CollapsingToolbarLayout collapsingToolbarLayout;
        protected Toolbar toolbar;
        protected TextView toolbarCollapsedTitle;
        protected AppBarLayout appbar;
        final FragmentManager fm = getSupportFragmentManager();
        protected ActionBarDrawerToggle toggle;
        protected  CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        toolbarCollapsedTitle = (TextView) findViewById(R.id.toolbar_collapsed_title);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appbar = (AppBarLayout) findViewById(R.id.appBarLayout);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            switchToFragmentWithUnlockedToolbar();
        }
    }

    @Override
    protected void onPostCreate (Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            switchToFragmentWithUnlockedToolbar();
        } else if (id == R.id.nav_gallery) {
            switchToFragmentWithLockedToolbar();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

        public void switchToFragmentWithUnlockedToolbar(){
            FragmentWithUnlockedToolbar currentFragment = (FragmentWithUnlockedToolbar) fm.findFragmentByTag(FragmentWithUnlockedToolbar.TAG);
            if(currentFragment == null){
                currentFragment = FragmentWithUnlockedToolbar.newInstance(FragmentWithUnlockedToolbar.TAG);
                fm.beginTransaction().replace(R.id.frame_container, currentFragment, FragmentWithUnlockedToolbar.TAG).commit();

            }
        }

        public void switchToFragmentWithLockedToolbar(){
            FragmentWithLockedToolbar currentFragment = (FragmentWithLockedToolbar) fm.findFragmentByTag(FragmentWithLockedToolbar.TAG);
            if(currentFragment == null){
                currentFragment = FragmentWithLockedToolbar.newInstance(FragmentWithLockedToolbar.TAG);
                fm.beginTransaction().replace(R.id.frame_container, currentFragment, FragmentWithLockedToolbar.TAG).commit();
            }
        }
    @Override
    public void collapseToolbar(){
        AppBarLayout.Behavior behavior;
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        behavior = (AppBarLayout.Behavior) params.getBehavior();
        if(behavior!=null) {
            behavior.setTopAndBottomOffset(0);
            behavior.onNestedPreScroll(coordinatorLayout, appbar, null, 0, 1, new int[2]);
        }
        //appbar.setExpanded(true); currently bugged, above code is a workaround
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)appbar.getLayoutParams();
        lp.height = (int) getResources().getDimension(R.dimen.toolbar_height);
        toolbarCollapsedTitle.setVisibility(View.VISIBLE);

    }

    @Override
    public void expandToolbar() {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)appbar.getLayoutParams();
        lp.height = (int) getResources().getDimension(R.dimen.nav_header_height);
        toolbarCollapsedTitle.setVisibility(View.GONE);
    }

    @Override
    public void setTitle(String s) {
        collapsingToolbarLayout.setTitle(s);
        toolbarCollapsedTitle.setText(s);
        collapsingToolbarLayout.invalidate();
        toolbarCollapsedTitle.invalidate();
    }
}