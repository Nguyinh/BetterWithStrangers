package ubiquasif.uqac.betterwithstrangers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_connection);

        // Add Intent to Guest Activity
        findViewById(R.id.goToMainButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstConnectionActivity.this, GuestActivity.class);
                startActivity(intent);
            }
        });
    }
}
