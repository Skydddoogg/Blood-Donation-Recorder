package com.nawinc27.mac.blooddonorfrontend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nawinc27.mac.blooddonorfrontend.form.Form;
import com.nawinc27.mac.blooddonorfrontend.utility.Extensions;
import com.nawinc27.mac.blooddonorfrontend.utility.SessionManager;

import java.util.ArrayList;

public class FormFragment extends Fragment {
    private Button hospitalName, submitForm;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference requestRef;
    private DatabaseReference formRef;
    private SessionManager session;
    private CheckBox q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12
            , q13, q14, q15, q16, q17, q18, q19, q20, q21, q22
            , q23, q24, q25, q26, q27, q28, q29, q30, q31;
    private EditText ansQ2, q32;
    private static String DEFAULT_FORM_APPROVE = "unproved";
    private static String DEFAULT_FORM_QUESTION33 = "";
    private Bundle bundle;
    private ImageView backButton;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private HistoryFragment historyFragment;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        session = new SessionManager(getContext());
        hospitalName = getView().findViewById(R.id.spinner1);
        submitForm = getView().findViewById(R.id.form_submit);
        backButton = getView().findViewById(R.id.back_btn_form);

        initQuestion();
        initBackBtn();

        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkForm()){
                    sendForm();
                }
            }
        });

        setHospital(hospitalName);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFragmentBackstack();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new HistoryFragment())
                        .commit();
            }
        });


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container , false);
    }

    public void setHospital(final Button button){
        if(getArguments() != null){
            bundle = getArguments();
            button.setText(bundle.getString("hospitalname"));
            button.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        }
    }

    public void changeFontFamily(){
        q1.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q2.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q3.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q4.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q5.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q6.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q7.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q8.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q9.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q10.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q11.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q12.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q13.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q14.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q15.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q16.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q17.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q18.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q19.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q20.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q21.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q22.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q23.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q24.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q25.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q26.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q27.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q28.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q29.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q30.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q31.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));
        q32.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.prompt_light));

    }

    public void initQuestion(){

        try {
            q1 = getView().findViewById(R.id.q1);
            q2 = getView().findViewById(R.id.q2);
            q3 = getView().findViewById(R.id.q3);
            q4 = getView().findViewById(R.id.q4);
            q5 = getView().findViewById(R.id.q5);
            q6 = getView().findViewById(R.id.q6);
            q7 = getView().findViewById(R.id.q7);
            q8 = getView().findViewById(R.id.q8);
            q9 = getView().findViewById(R.id.q9);
            q10 = getView().findViewById(R.id.q10);
            q11 = getView().findViewById(R.id.q11);
            q12 = getView().findViewById(R.id.q12);
            q13 = getView().findViewById(R.id.q13);
            q14 = getView().findViewById(R.id.q14);
            q15 = getView().findViewById(R.id.q15);
            q16 = getView().findViewById(R.id.q16);
            q17 = getView().findViewById(R.id.q17);
            q18 = getView().findViewById(R.id.q18);
            q19 = getView().findViewById(R.id.q19);
            q20 = getView().findViewById(R.id.q20);
            q21 = getView().findViewById(R.id.q21);
            q22 = getView().findViewById(R.id.q22);
            q23 = getView().findViewById(R.id.q23);
            q24 = getView().findViewById(R.id.q24);
            q25 = getView().findViewById(R.id.q25);
            q26 = getView().findViewById(R.id.q26);
            q27 = getView().findViewById(R.id.q27);
            q28 = getView().findViewById(R.id.q28);
            q29 = getView().findViewById(R.id.q29);
            q30 = getView().findViewById(R.id.q30);
            q31 = getView().findViewById(R.id.q31);
            q32 = getView().findViewById(R.id.q32);

            ansQ2 = getView().findViewById(R.id.ans_q2);
            changeFontFamily();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void initBackBtn(){
        ImageView backBtn = getActivity().findViewById(R.id.back_btn_form);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Extensions.goTo(getActivity(), new HistoryFragment());
            }
        });
    }


    public boolean checkForm(){
        boolean result = false;
        try {
            if(q2.isChecked()){
                if(ansQ2.getText().toString().isEmpty()){
                    result = false;
                    Toast.makeText(getActivity(), "กรุณากรอก 'ระยะเวลาที่จะคลอดบุตร'", Toast.LENGTH_SHORT).show();
                }else {
                    result = true;
                }
            }else{
                result = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public int getAnswerQuestion2(){
        int result = 0;
        try {
            if(q2.isChecked()){
                result = Integer.parseInt(ansQ2.getText().toString());
            }else{
                result = 0;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public void sendForm(){
        try {
            Long timeStamp = System.currentTimeMillis();
            String formTimeStamp  = Long.toString(timeStamp);
            Form formBuffer = new Form(
                    q1.isChecked(),
                    getAnswerQuestion2(),
                    q3.isChecked(),
                    q4.isChecked(),
                    q5.isChecked(),
                    q6.isChecked(),
                    q7.isChecked(),
                    q8.isChecked(),
                    q9.isChecked(),
                    q10.isChecked(),
                    q11.isChecked(),
                    q12.isChecked(),
                    q13.isChecked(),
                    q14.isChecked(),
                    q15.isChecked(),
                    q16.isChecked(),
                    q17.isChecked(),
                    q18.isChecked(),
                    q19.isChecked(),
                    q20.isChecked(),
                    q21.isChecked(),
                    q22.isChecked(),
                    q23.isChecked(),
                    q24.isChecked(),
                    q25.isChecked(),
                    q26.isChecked(),
                    q27.isChecked(),
                    q28.isChecked(),
                    q29.isChecked(),
                    q30.isChecked(),
                    q31.isChecked(),
                    q32.getText().toString() + "",
                    DEFAULT_FORM_QUESTION33,
                    formTimeStamp,
                    DEFAULT_FORM_APPROVE);
            formRef = database.getReference("officer/form/" + bundle.getString("hospitalid") + "/" + session.getUsername() + "/");
            formRef.child("form").setValue(formBuffer);

            Toast.makeText(getActivity(), "ส่งแบบฟอร์มสำเร็จ", Toast.LENGTH_SHORT).show();

            passBundle();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clearFragmentBackstack(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void passBundle(){
        try {
            clearFragmentBackstack();

            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            historyFragment = new HistoryFragment();
            historyFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.main_view, historyFragment);
            fragmentTransaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
