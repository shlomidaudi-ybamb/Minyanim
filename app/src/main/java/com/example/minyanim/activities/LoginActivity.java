package com.example.minyanim.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.minyanim.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

public class LoginActivity extends FragmentActivity {

    private static final String TAG = "LoginActivity";
    FirebaseAuth fa;

    EditText etMail, etPassword;

    SharedPreferences userPrefs = getSharedPreferences("user", MODE_PRIVATE);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

    }

    private void initViews() {
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        // set email to previously connected user
        etMail.setText(userPrefs.getString("email", ""));
    }

    @Override
    protected void onStart() {
        super.onStart();
        fa = FirebaseAuth.getInstance();
    }

    public void onClick(View view) {
        String email = etMail.getText().toString();
        String password = etPassword.getText().toString();
        fa.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmailAndPassword:success");
                            saveUser();
                            moveOn();
                        } else {
                            Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                            // ask whether to create new user
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("No such user. Create new user?")
                                    .setPositiveButton("Create", (dialog, which) -> {
                                        // create new user
                                        createUser(email, password);
                                    })
                            .setNegativeButton("No", null);
                            builder.create().show();
                        }
                    }
                });

    }

    private void saveUser() {
        FirebaseUser user = fa.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        userPrefs.edit().putString("email", email).apply();
    }

    private void createUser(String email, String password) {
        fa.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Toast.makeText(LoginActivity.this, "User created!", Toast.LENGTH_SHORT).show();
                    saveUser();
                    showNameDialog();
                });
    }

    private void showNameDialog() {
        FragmentManager fm = getSupportFragmentManager();
        UsernameDialog und = new UsernameDialog(fa, this);
        und.show(fm, "username dialog");
    }

    private void moveOn() {

        // go to the list activity
        Intent minyanimActivity = new Intent(this, MinyanimActivity.class);
        startActivity(minyanimActivity);
    }

    public static class UsernameDialog extends DialogFragment {
        FirebaseAuth fa;
        LoginActivity la;
        public UsernameDialog(FirebaseAuth fa, LoginActivity la) {
            this.fa = fa;
            this.la = la;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater li = requireActivity().getLayoutInflater();

            View view = li.inflate(R.layout.username_dialog_layout, null);
            EditText etUername = view.findViewById(R.id.etUsername);

            builder.setView(view)
                    .setPositiveButton("זהו", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UserProfileChangeRequest.Builder b = new UserProfileChangeRequest.Builder();
                            b.setDisplayName(etUername.getText().toString());
                            FirebaseUser user = fa.getCurrentUser();
                            user.updateProfile(b.build());
                            la.moveOn();
                        }
                    });

            return builder.create();
        }
    }

    // TODO sign out?
}
