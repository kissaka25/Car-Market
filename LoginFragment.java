package Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import Database.RemoteDatabase;
import com.ken.cars.R;
import com.ken.cars.UserActivity;

public class LoginFragment extends Fragment {

    Button btnLook, btnLogin;
    EditText etLoginEmail, etLoginPassword;
    CheckBox checkSave;
    TextView tvForgot;

    FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnLook = (Button) view.findViewById(R.id.btnLook);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);

        etLoginEmail = (EditText) view.findViewById(R.id.etLoginEmail);
        etLoginPassword = (EditText) view.findViewById(R.id.etLoginPassword);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String userEmail = sharedPref.getString("email", "");
        String userPass = sharedPref.getString("pass", "");
        etLoginEmail.setText(userEmail);
        etLoginPassword.setText(userPass);
        checkSave = (CheckBox) view.findViewById(R.id.checkSave);
        tvForgot = (TextView) view.findViewById(R.id.tvForgot);

        progressDialog = new ProgressDialog(getContext());

        btnLook.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        btnLook.setBackgroundResource(R.mipmap.look);
                        break;
                    case MotionEvent.ACTION_UP:
                        etLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        btnLook.setBackgroundResource(R.mipmap.nolook);
                        etLoginPassword.setSelection(etLoginPassword.length());
                        break;

                }
                return true;
            }
        });

        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLoginEmail.getText().toString().trim();
                String pass = etLoginPassword.getText().toString().trim();
                userLogin(email, pass);
            }
        });

    }

    private void resetPass() {
        final Dialog passwordReset = new Dialog(getContext());
        passwordReset.setContentView(R.layout.password_reset);
        passwordReset.setTitle("Submit a password reset request");
        final EditText emailEdit = (EditText) passwordReset.findViewById(R.id.forgotEmail);
        emailEdit.setText(etLoginEmail.getText());
        Button btnDergo = (Button) passwordReset.findViewById(R.id.btnReset);
        Button cancelReset = (Button) passwordReset.findViewById(R.id.btnCancelReset);
        btnDergo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailEdit.getText().toString().trim().isEmpty()) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(emailEdit.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Snackbar.make(getView(), "The password reset request was sent to E-mail!", Snackbar.LENGTH_LONG).show();
                                passwordReset.dismiss();
                            } else {
                                Snackbar.make(getView(), "Password reset failed! Please try again.", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Snackbar.make(v, "Please enter your email!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        cancelReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordReset.dismiss();
            }
        });
        passwordReset.show();
    }

    private void userLogin(final String email, final String pass) {
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        try {

            RemoteDatabase.loginUser(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (firebaseUser.isEmailVerified()) {

                            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            editor = sharedPref.edit();
                            if (checkSave.isChecked()) {
                                editor.putString("email", email);
                                editor.putString("pass", pass);
                                editor.putString("userID", firebaseUser.getUid());
                                editor.commit();
                            } else {
                                editor.remove("email");
                                editor.remove("pass");
                                editor.remove("userID");
                                etLoginEmail.setText("");
                                etLoginPassword.setText("");
                            }
                            progressDialog.hide();
                            startActivity(new Intent(getContext(), UserActivity.class));
                            Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_LONG).show();
                        } else {
                            progressDialog.hide();
                            final Dialog verifyDialog = new Dialog(getActivity());
                            verifyDialog.setContentView(R.layout.dialog_for_verification);
                            verifyDialog.setTitle("Email unverified!");
                            Button btnVerify = (Button) verifyDialog.findViewById(R.id.btnVerification);
                            btnVerify.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Verifivation email was sent, please check.", Toast.LENGTH_LONG).show();
                                                verifyDialog.dismiss();

                                            } else {
                                                Toast.makeText(getContext(), "Verification email delivery failed!", Toast.LENGTH_LONG).show();
                                                verifyDialog.dismiss();
                                            }
                                        }
                                    });
                                }
                            });
                            verifyDialog.show();
                            firebaseAuth.signOut();
                        }
                    } else {
                        progressDialog.hide();
                        String errori =null;
                        try {
                            errori = ((FirebaseAuthException) task.getException()).getErrorCode();
                            switch (errori) {
                                case "ERROR_INVALID_CREDENTIAL":
                                    Snackbar.make(getView(), "Invalid credential", Snackbar.LENGTH_LONG).show();
                                    break;
                                case "ERROR_INVALID_EMAIL":
                                    Snackbar.make(getView(), "Invalid email address", Snackbar.LENGTH_LONG).show();
                                    break;
                                case "ERROR_WRONG_PASSWORD":
                                    Snackbar.make(getView(), "Wrong password", Snackbar.LENGTH_LONG).show();
                                    break;
                                case "ERROR_USER_DISABLED":
                                    Snackbar.make(getView(), "User account disabled by administrator", Snackbar.LENGTH_LONG).show();
                                    break;
                                case "ERROR_USER_NOT_FOUND":
                                    Snackbar.make(getView(), "Account not found", Snackbar.LENGTH_LONG).show();
                                    break;
                            }
                        } catch (Exception e) {

                        }
                    }
                }
            });

        } catch (Exception e) {
            Log.e("signInFailed", e.getMessage());
        }
    }
}