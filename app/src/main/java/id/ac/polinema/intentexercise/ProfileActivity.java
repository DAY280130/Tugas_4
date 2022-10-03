package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvFullName, tvEmail, tvHomePage, tvAbout;
    private CircleImageView civAvatar;
    private String fullName, email, homePage, about;
    private Button goHomePage;
    private Intent register;
    private Uri avatarUri;

    private static final String TAG = RegisterActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvFullName = findViewById(R.id.label_fullname);
        tvEmail = findViewById(R.id.label_email);
        tvHomePage = findViewById(R.id.label_homepage);
        tvAbout = findViewById(R.id.label_about);
        goHomePage = findViewById(R.id.button_homepage);
        civAvatar = findViewById(R.id.image_profile);
        register = getIntent();

        fullName = register.getStringExtra("fullname");
        tvFullName.setText(fullName);

        email = register.getStringExtra("email");
        tvEmail.setText(email);

        homePage = register.getStringExtra("homepage");
        tvHomePage.setText(homePage);

        about = register.getStringExtra("about");
        tvAbout.setText(about);

        if(register.hasExtra("avatar")) {
            avatarUri = register.getParcelableExtra("avatar");
            try {
                Bitmap avatarBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), avatarUri);
                civAvatar.setImageBitmap(avatarBitmap);
            } catch(IOException e) {
                Toast.makeText(ProfileActivity.this,
                        "Tidak bisa memuat gambar",
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }
        }

        goHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Objects.equals(homePage, "")) {
                    Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + homePage));
                    startActivity(implicit);
                } else {
                    Toast.makeText(ProfileActivity.this,
                            "URL homepage belum diset",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
