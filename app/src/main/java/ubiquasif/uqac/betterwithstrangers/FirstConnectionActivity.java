package ubiquasif.uqac.betterwithstrangers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class FirstConnectionActivity extends AppCompatActivity {

    private static final int REQ_SIGN_IN = 1;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            proceed();
        } else {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SIGN_IN && resultCode != RESULT_OK) {
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

    private void proceed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
