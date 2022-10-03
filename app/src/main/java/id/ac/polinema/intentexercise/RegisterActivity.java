package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etPass, etConfPass, etHomePage, etAbout;
    private String fullName, email, pass, confPass, homepPage, about;
    private Button okButton;
    private CircleImageView civAvatar;
    private ImageView avatarButton;
    private Uri avatarUri;

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullName = findViewById(R.id.text_fullname);
        etEmail = findViewById(R.id.text_email);
        etPass = findViewById(R.id.text_password);
        etConfPass = findViewById(R.id.text_confirm_password);
        etHomePage = findViewById(R.id.text_homepage);
        etAbout = findViewById(R.id.text_about);
        okButton = findViewById(R.id.button_ok);
        civAvatar = findViewById(R.id.image_profile);
        avatarButton = findViewById(R.id.imageView);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullName = etFullName.getText().toString();
                email = etEmail.getText().toString();
                pass = etPass.getText().toString();
                confPass = etConfPass.getText().toString();
                homepPage = etHomePage.getText().toString();
                about = etAbout.getText().toString();

                if (!Objects.equals(pass, confPass)) {
                    Toast.makeText(RegisterActivity.this,
                            "Konfirmasi password tidak cocok",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                Intent register = new Intent(RegisterActivity.this, ProfileActivity.class);

                register.putExtra("fullname", fullName);
                register.putExtra("email", email);
                register.putExtra("homepage", homepPage);
                register.putExtra("about", about);
                if(!Objects.equals(avatarUri, null)) {
                    register.putExtra("avatar", avatarUri);
                }

                startActivity(register);
            }
        });

        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            Toast.makeText(RegisterActivity.this,
                    "Batal menambahkan gambar",
                    Toast.LENGTH_SHORT).show();

            return;
        } else if(requestCode == GALLERY_REQUEST_CODE) {
            if(!Objects.equals(data, null)) {
                try {
                    avatarUri = data.getData();
                    Bitmap avatarBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), avatarUri);
                    civAvatar.setImageBitmap(avatarBitmap);
                } catch(IOException e) {
                    Toast.makeText(RegisterActivity.this,
                            "Tidak bisa memuat gambar",
                            Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}
