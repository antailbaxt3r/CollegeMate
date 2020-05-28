package com.antailbaxt3r.collegemate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.antailbaxt3r.collegemate.R;
import com.antailbaxt3r.collegemate.databinding.ActivityLogInBinding;
import com.antailbaxt3r.collegemate.models.TokenResponseModel;
import com.antailbaxt3r.collegemate.retrofit.RetrofitClient;
import com.antailbaxt3r.collegemate.utils.SharedPrefs;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth auth;
    private SharedPrefs prefs;
    private ActivityLogInBinding binding;

    private static final int GOOGLE_SIGN_IN_REQUEST_CODE = 707;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        prefs = new SharedPrefs(this);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(user != null) Log.i("AUTH", "Logged In");
                else Log.i("AUTH", "NOT Logged In");
            }
        };

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))//you can also use R.string.default_web_client_id
            .requestEmail()
            .build();

        googleApiClient = new GoogleApiClient.Builder(this)
            .enableAutoManage(this,this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();

        binding.loginGoogleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, GOOGLE_SIGN_IN_REQUEST_CODE);
            }
        });


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN_IN_REQUEST_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            Log.i("id", account.getIdToken());
            // you can store user data to SharedPreference
            Call<TokenResponseModel> call = RetrofitClient.getClient().getToken(account.getIdToken());
            call.enqueue(new Callback<TokenResponseModel>() {
                @Override
                public void onResponse(Call<TokenResponseModel> call, Response<TokenResponseModel> response) {
                    if(response.isSuccessful() && response.code() == 200){
                        TokenResponseModel tokenModel = response.body();
                        if(tokenModel.getSuccess()) {
                            prefs.saveEmail(account.getEmail());
                            prefs.saveName(account.getDisplayName());
                            prefs.saveToken(tokenModel.getAuthToken());
                            prefs.saveNewUser(tokenModel.getNewUser());
                            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                            firebaseAuthWithGoogle(credential);
                        }else{
                            Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TokenResponseModel> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    Log.e("Login", t.getMessage());
                    t.printStackTrace();
                }
            });

        }else{
            // Google Sign In failed, update UI appropriately
            Log.e("LOGIN ATTEMPT", "Login Unsuccessful. "+result.getStatus());
            Log.e("LOGIN ATTEMPT", "Login Unsuccessful. "+result.getSignInAccount());
            Log.e("LOGIN ATTEMPT", "Login Unsuccessful. "+result.toString());

            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(AuthCredential credential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("Login Attempt", "signInWithCredential:onComplete:" + task.isSuccessful());
                    if(task.isSuccessful()){

                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        if(prefs.getNewUser()){
                            goToOnboarding();
                        }else {
                            gotoHome();
                        }
                    }else{
                        Log.w("Login Attempt", "signInWithCredential" + task.getException().getMessage());
                        task.getException().printStackTrace();
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    }

                }
            });
    }

    private void gotoHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void goToOnboarding() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authStateListener != null){
            FirebaseAuth.getInstance().signOut();
        }
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }
}