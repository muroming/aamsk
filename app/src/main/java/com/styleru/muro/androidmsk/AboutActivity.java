package com.styleru.muro.androidmsk;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    private EditText mMessageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout mainContainer = findViewById(R.id.main_container);
        TextView disclaimer = new TextView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.media);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        disclaimer.setLayoutParams(params);
        disclaimer.setText(getString(R.string.disclaimer));

        mainContainer.addView(disclaimer);

        mMessageText = findViewById(R.id.et_message);
        ImageButton mVk = findViewById(R.id.ib_gplus);
        ImageButton mInstagram = findViewById(R.id.ib_inst);
        ImageButton mTelegram = findViewById(R.id.ib_tel);

        mVk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.vk)));
                if (webIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntent);
                }
            }
        });

        mInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.instagram)));
                if (webIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntent);
                }
            }
        });

        mTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getString(R.string.telegram), Toast.LENGTH_SHORT).show();
            }
        });

        TextView mSendMessage = findViewById(R.id.tv_send_message);
        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }


    public void sendMessage() {
        String message = mMessageText.getText().toString();
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse(String.format("mailto:%s", getString(R.string.email))))
                .putExtra(Intent.EXTRA_SUBJECT, "Hello")
                .putExtra(Intent.EXTRA_TEXT, message);

        if (mailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mailIntent);
        } else {
            Toast.makeText(this, "Whoops", Toast.LENGTH_SHORT).show();
        }
    }
}
