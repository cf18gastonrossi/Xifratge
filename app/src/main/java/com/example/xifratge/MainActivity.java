package com.example.xifratge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.Calendar;

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
            writeToFile(createXML(password, encodedText) ,getApplicationContext());

        } catch (Exception e) {
            System.out.println("An error ocurred encrypting the password");
        }
    }

        public String createXML(String userText, String userTextCrypt) throws
        IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("password.xml"));
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
            int id = (lines - 3) / 5;
            String xml =
                    "\t<data " + "id=\"" + id + "\">\n" +
                            "\t\t<textField>" + userText + "</textField>\n" +
                            "\t\t<cipher_text>" + userTextCrypt + "</cipher_text>\n" +
                            "\t\t<date_time>" + Calendar.getInstance().getTime() + "</date_time>" +
                            "\t</data>\n";

            return xml;
        }
        catch (Exception e) {

            String xml =
                    "\t<data id=\"1\">\n" +
                            "\t\t<textField>" + userText + "</textField>\n" +
                            "\t\t<cipher_text>" + userTextCrypt + "</cipher_text>\n" +
                            "\t\t<date_time>" + Calendar.getInstance().getTime() + "</date_time>" +
                            "\t</data>\n";

            return xml;
        }

        }

    private void writeToFile(String data, Context context) {
        try
        {
            String filename= "password.xml";
            FileWriter fw = new FileWriter(filename,true); //t
            RandomAccessFile raf = new RandomAccessFile("password.xml", "rw");
            long length = raf.length();
            raf.setLength(length - 1);
            raf.close();
            fw.write(data + "</content_file>");
            fw.close();
        }
        catch(IOException ioe) {

            try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("password.xml", Context.MODE_PRIVATE));
            outputStreamWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<content_file>\n" + data +"</content_file>");
            outputStreamWriter.close();
            }
            catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            }
        }

    }


}
