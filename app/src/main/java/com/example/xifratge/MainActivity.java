package com.example.xifratge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private Button decodeButton;
    private TextView textField;
    private TextView textEncoded;
    private TextView textDecoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textField = findViewById(R.id.editText);
        this.textEncoded = findViewById(R.id.editText);
        this.textDecoded = findViewById(R.id.textView);
        this.decodeButton = findViewById(R.id.button);
        decodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                encryptDecrypt(view);
            }
        });

    }

    public void encryptDecrypt(View view) {

        try {
            String password = this.textField.getText().toString();
            String encodedText;
            String decodedText;
            RSA encodeRsa = new RSA();
            RSA decodeRsa = new RSA();

            encodeRsa.genKeyPair(1024);

            encodeRsa.setContext(getBaseContext());
            encodeRsa.saveToDiskPrivateKey("privateKey");
            encodeRsa.saveToDiskPublicKey("publicKey");

            decodeRsa.setContext(getBaseContext());
            decodeRsa.openFromDiskPrivateKey("privateKey");
            decodeRsa.openFromDiskPublicKey("publicKey");

            encodedText = encodeRsa.Encrypt(password);
            textDecoded.setText(encodedText);

            decodedText = decodeRsa.Decrypt(encodedText);
            textEncoded.setText(decodedText);


        } catch (Exception e) {
            System.out.println("An error ocurred encrypting the password");
        }


    }
}
