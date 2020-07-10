package com.antailbaxt3r.collegemate.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.apiControllers.ProfileCtrl;
import com.antailbaxt3r.collegemate.databinding.DialogProfileBinding;
import com.antailbaxt3r.collegemate.models.User;
import com.antailbaxt3r.collegemate.models.UserGetResponseModel;
import com.antailbaxt3r.collegemate.models.UserUpdateResponseModel;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDialog extends DialogFragment {
    DialogProfileBinding binding;
    SharedPrefs prefs;
    Context context;
    User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        prefs = new SharedPrefs(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogProfileBinding.inflate(inflater,container,false);

        //Initializing Text values
        initializeValues();

        //Setting text listeners
        setTextListeners();

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    void initializeValues(){
        Call<UserGetResponseModel> call = RetrofitClient.getClient().getUser(prefs.getToken());
        call.enqueue(new Callback<UserGetResponseModel>() {
            @Override
            public void onResponse(Call<UserGetResponseModel> call, Response<UserGetResponseModel> response) {
                if(response.isSuccessful()){
                    user = response.body().getUser().get(0);
                    setText();
                    Log.i("UserData",user.toString());
                }
            }

            @Override
            public void onFailure(Call<UserGetResponseModel> call, Throwable t) {
                Log.e("Error",t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void updateProfile(){
        ProfileCtrl.updateUser(user, prefs.getToken(), new Callback<UserUpdateResponseModel>() {
            @Override
            public void onResponse(Call<UserUpdateResponseModel> call, Response<UserUpdateResponseModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Profile updated sucessfully", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserUpdateResponseModel> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

    void setText(){
        binding.email.setText(user.getEmail());
        binding.name.setText(user.getName());
        binding.phone.setText(user.getPhone().toString());
        binding.enrollment.setText(user.getEnrollmentId().toString());
        binding.year.setText(user.getYearOfStudy().toString());
    }

    void setTextListeners(){
        binding.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                user.setName(editable.toString());
            }
        });

        binding.year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                user.setYearOfStudy(editable.toString());
            }
        });

        binding.enrollment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                user.setEnrollmentId(editable.toString());
            }
        });

        binding.phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                user.setPhone(editable.toString());
            }
        });
    }

}
