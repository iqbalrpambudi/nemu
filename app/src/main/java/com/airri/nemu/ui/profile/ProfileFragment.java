package com.airri.nemu.ui.profile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airri.nemu.LoginActivity;
import com.airri.nemu.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.mikhaellopez.circularimageview.CircularImageView;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private TextView tvName, tvEmail;
    private Button btnLogout;
    private CircularImageView imgPhoto;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // inisialisasi
        tvName    = root.findViewById(R.id.tv_name);
        tvEmail   = root.findViewById(R.id.tv_email);
        btnLogout = root.findViewById(R.id.btn_logout);
        imgPhoto  = root.findViewById(R.id.img_photo);

        // menampilkan view model ke layout
        profileViewModel.getName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvName.setText(s);
            }
        });

        profileViewModel.getEmail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvEmail.setText(s);
            }
        });

        profileViewModel.getPhoto().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Glide.with(getActivity()).load(s).into(imgPhoto);
                }
            }
        });

        // memberi click listener
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // menampilkan toast
                Toast toast = Toast.makeText(getActivity(), "Logout berhasil", Toast.LENGTH_SHORT);
                toast.show();

                // finish
                getActivity().finish();

                // Statement program untuk logout/keluar
                AuthUI.getInstance().signOut(getActivity());

                // membuka login
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }
}
