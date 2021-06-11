package com.aoslec.contactproject.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.aoslec.contactproject.Fragment.ContactFragment;
import com.aoslec.contactproject.Fragment.FavoriteFragment;
import com.aoslec.contactproject.Fragment.GroupFragment;
import com.aoslec.contactproject.Fragment.LoginFragment;
import com.aoslec.contactproject.Fragment.SignupFragment;

public class LoginFragmentAdapter extends FragmentStateAdapter {

    public LoginFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1:
                return new SignupFragment();
        }
        return new LoginFragment();
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
