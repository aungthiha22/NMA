package com.rebook.nma.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rebook.nma.Config;
import com.rebook.nma.R;
import com.rebook.nma.adapter.MarkAdapter;
import com.rebook.nma.util.TinyDB;
import com.rebook.nma.widget.ZawgyiTextView;
import com.squareup.okhttp.OkHttpClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 4/28/2019.
 */

public class ProfileFragment extends Fragment{

    @BindView(R.id.profile_name)ZawgyiTextView txtProfileName;
    @BindView(R.id.profile_email)ZawgyiTextView txtProfileEmail;
    @BindView(R.id.current_course_cardView)CardView cardViewCurrentCourse;
    @BindView(R.id.history_course_cardView)CardView cardViewHistoryCourse;
    @BindView(R.id.assignment_cardView)CardView cardViewAssignment;
    @BindView(R.id.attendance_cardView)CardView cardViewAttendance;
    @BindView(R.id.announcement_cardView)CardView cardViewAnnouncement;
    @BindView(R.id.mark_cardView)CardView cardViewMark;

    OkHttpClient okHttpClient;
    TinyDB tinyDB ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        okHttpClient = new OkHttpClient();
        tinyDB = new TinyDB(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        ButterKnife.bind(this, view);
        txtProfileName.setText(tinyDB.getString(Config.STORE_NAME));
        txtProfileEmail.setText(tinyDB.getString(Config.STORE_EMAIL));

        cardViewCurrentCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CurrentCourseActivity.class);
                intent.putExtra(CurrentCourseActivity.PROFILE_INTENT,"CurrentCourse");
                startActivity(intent);

            }
        });
        cardViewHistoryCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CurrentCourseActivity.class);
                intent.putExtra(CurrentCourseActivity.PROFILE_INTENT,"HistoryCourse");
                startActivity(intent);
            }
        });
        cardViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AttendanceActivity.class);
                intent.putExtra(AttendanceActivity.ATTENDANCE_INTENT,"attendance");
                startActivity(intent);
            }
        });
        cardViewAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AssignmentActivity.class);
                intent.putExtra(AssignmentActivity.ASSIGNMENT_INTENT,"assignment");
                startActivity(intent);
            }
        });
        cardViewMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MarkActivity.class);
                intent.putExtra(AnnouncementActivity.MARK_INTENT,"mark");
                startActivity(intent);
            }
        });
        cardViewAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AnnouncementActivity.class);
                intent.putExtra(AnnouncementActivity.MARK_INTENT,"announcement");
                startActivity(intent);
            }
        });


        return view;
    }
}
