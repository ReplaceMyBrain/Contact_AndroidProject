package com.aoslec.contactproject.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.aoslec.contactproject.Fragment.ContactFragment;
import com.aoslec.contactproject.Fragment.FavoriteFragment;
import com.aoslec.contactproject.Fragment.GroupFragment;

public class MainFragmentAdapter extends FragmentStateAdapter {
    public MainFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1:
                return new FavoriteFragment();
            case 2:
                return new GroupFragment();
        }
        return new ContactFragment();
    }

    @Override
    public int getItemCount() { // 갯수
        return 3 ;
    }
}
