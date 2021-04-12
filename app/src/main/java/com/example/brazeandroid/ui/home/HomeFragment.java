package com.example.brazeandroid.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.appboy.support.StringUtils;

import com.example.brazeandroid.R;

import com.appboy.Appboy;
import com.appboy.AppboyUser;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button changeUserButton;
    private EditText mUserIdEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        changeUserButton = root.findViewById(R.id.button);
        mUserIdEditText = root.findViewById(R.id.editText);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        changeUserButton.setOnClickListener(view -> {
            String userId = mUserIdEditText.getText().toString();
            if (!StringUtils.isNullOrBlank(userId)) {
                Appboy.getInstance(getContext()).changeUser(userId);
                Toast.makeText(getContext(), "Set userId to: " + userId, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please enter a userId.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}