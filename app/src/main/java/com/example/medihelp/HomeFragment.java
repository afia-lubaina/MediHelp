package com.example.medihelp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class HomeFragment extends Fragment {

    boolean isBookmarked = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnDoctorContact = view.findViewById(R.id.btnDoctorContact);
        ConstraintLayout lytDiagnoseMe = view.findViewById(R.id.lytDiagnoseMe);
        ConstraintLayout lytFindaDoc = view.findViewById(R.id.lytFindaDoc);
        ImageButton btnBookmark = view.findViewById(R.id.btnBookmark);
        ConstraintLayout btnBookmarkBack = view.findViewById(R.id.btnBookmarkBack);


        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isBookmarked) {
                    btnBookmark.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white));
                    btnBookmarkBack.setBackgroundResource(R.drawable.round_border_solid);
                } else {
                    btnBookmark.setColorFilter(ContextCompat.getColor(requireContext(), R.color.lavender));
                    btnBookmarkBack.setBackgroundResource(R.drawable.round_border_trans);
                }
                isBookmarked=!isBookmarked;
            }
        });

        btnDoctorContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialPhoneNumber("+8801714268748");
//                Toast.makeText(view.getContext(),"Contacting",Toast.LENGTH_SHORT).show();
            }
        });

        lytDiagnoseMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new DiagnoseFragment());
            }
        });

        lytFindaDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SearchFragment());
            }
        });

        return view;
    }

    private void replaceFragment(Fragment destinationFragment) {

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,destinationFragment)
                .commit();
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}