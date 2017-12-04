package ubiquasif.uqac.betterwithstrangers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Calendar;

import ubiquasif.uqac.betterwithstrangers.Models.Notification;

public class FirstConnectionActivity extends AppCompatActivity {

    private static final int REQ_SIGN_IN = 1;

    private FirebaseAuth mAuth;
    private boolean beforeSignIn;

    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_connection);

        mAuth = FirebaseAuth.getInstance();
        beforeSignIn = true;
        database = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (beforeSignIn) {
            if (mAuth.getCurrentUser() != null) {
                proceed();
            } else {
                signIn();
                addWelcomeNotification();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                proceed();
            } else {
                IdpResponse response = IdpResponse.fromResultIntent(data);

                if (response == null) {
                    Toast.makeText(this, R.string.sign_in_cancelled, Toast.LENGTH_SHORT).show();
                } else if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, R.string.no_connection, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.sign_in_error, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void signIn() {
        beforeSignIn = false;
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false, true)
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()
                ))
                .build();


        startActivityForResult(signInIntent, REQ_SIGN_IN);
    }

    public void retry(View v) {
        signIn();
    }

    private void proceed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void addWelcomeNotification(){

        Notification notif = new Notification(
                FirebaseAuth.getInstance().getUid(),
                "Bienvenue lalalalalalala !!!!",
                Calendar.getInstance().getTime()
        );

        database.collection("notifications")
                .add(notif)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("myTag", "Error ajout notification Welcome");
                    }
                });
    }
}
