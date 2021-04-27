package com.example.brazeandroid.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appboy.configuration.AppboyConfig;
import com.appboy.support.StringUtils;

import com.example.brazeandroid.R;

import com.appboy.Appboy;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button changeUserButton;
    private EditText mUserIdEditText;
    private Button logCustomEventButton;
    private EditText customEventText;
    private Button contentCardButton;
    private Button changeConfigButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        changeUserButton = root.findViewById(R.id.button);
        logCustomEventButton = root.findViewById(R.id.eventButton);
        mUserIdEditText = root.findViewById(R.id.editText);
        customEventText = root.findViewById(R.id.attributeText);
        changeConfigButton = root.findViewById(R.id.configureButton);
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
        logCustomEventButton.setOnClickListener(view -> {
            String event = customEventText.getText().toString();
            if (!StringUtils.isNullOrBlank(event)) {
                Appboy.getInstance(getContext()).logCustomEvent(event);
                Toast.makeText(getContext(), "Logging custom event: " + event, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please enter a custom event.", Toast.LENGTH_SHORT).show();
            }
        });
        changeConfigButton.setOnClickListener(view -> {
            Boolean isAppboyDisabled = Appboy.isSdkDisabled();
            if (!isAppboyDisabled) {
                Appboy.disableSdk(getContext());
            }
            String apiKey = customEventText.getText().toString();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AppboyConfig appboyConfig = new AppboyConfig.Builder()
                            .setApiKey(apiKey)
                            .setCustomEndpoint("sondheim.braze.com")
                            .build();
                    Appboy.configure(getContext(), appboyConfig);
                    Appboy.enableSdk(getContext());
                }
            }, 3000);
        });
    }
}