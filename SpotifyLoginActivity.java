package com.sodastudio.jun.spotify_demo;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

public class SpotifyLoginActivity extends AppCompatActivity {
//I tried setting up my login page to access my api tht i set up to try stream music directly from the Api but i was unsuccessful
// i used online assistance to help me achieve this but ultimately it did not happen
//i am creating a button that will allow me to have access to music on my external device instead.

    @SuppressWarnings("SpellCheckingInspection")
    public static final String CLIENT_ID = "5de6930c8a744270851a5064c7ff6333";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String REDIRECT_URI = "http://localhost:8888/callback";

    private static final String TAG = "Spotify " + SpotifyLoginActivity.class.getSimpleName();

    /**
     * Request code that will be passed together with authentication result to the onAuthenticationResult
     */
    private static final int REQUEST_CODE = 1337;

    public static final String AUTH_TOKEN = "AUTH_TOKEN";

    //Initialization

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_login);

        Button mLoginButton = (Button)findViewById(R.id.b_tton);
        System.out.println("Login is now working");
        mLoginButton.setOnClickListener(mListener);

    }

    //Authentication


    private void openLoginWindow() {

        // TODO
        Intent intent = new Intent(SpotifyLoginActivity.this, Activity2.class);
        startActivity(intent);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN,REDIRECT_URI);

        builder.setScopes(new String[]{"user-read-private", "streaming", "user-top-read", "user-read-recently-played"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }


    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE)
        {
            final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:

                    Intent intent = new Intent(SpotifyLoginActivity.this,
                            MainActivity.class);

                    intent.putExtra(AUTH_TOKEN, response.getAccessToken());

                    startActivity(intent);

                    destroy();

                    break;

                // Auth flow returned an error
                case ERROR:
                    Log.e(TAG,"Auth error: " + response.getError());
                    break;

                // Most likely auth flow was cancelled
                default:
                    Log.d(TAG,"Auth result: " + response.getType());
            }
        }
    }

    View.OnClickListener mListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.b_tton:
                    openLoginWindow();
                    break;
            }
        }
    };

    public void destroy(){
        SpotifyLoginActivity.this.finish();

    }

}