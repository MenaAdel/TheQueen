package com.example.thequeen.features.home.secondStepActivity.signature;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;
import com.example.thequeen.R;
import com.example.thequeen.databinding.ActivitySignatureActivityyBinding;
import com.example.thequeen.font.FontProvider;
import com.example.thequeen.utils.UtilsFunctions;

import java.io.ByteArrayOutputStream;

public class SignatureActivityy extends AppCompatActivity {

    ActivitySignatureActivityyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signature_activityy);
        FontProvider.getInstance().init(this);
        initClicking();
    }


    private void initClicking() {

        binding.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.signatureView.isBitmapEmpty())
                    showWarning();
                else {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("signature", UtilsFunctions.convertBitMapToBase64(UtilsFunctions.screenShot(binding.getRoot()))/*convertBitMapToBase64(binding.signatureView.getSignatureBitmap())*/);
                    returnIntent.putExtra("screenShot2", UtilsFunctions.convertBitMapToBase64(UtilsFunctions.screenShot(binding.getRoot())));
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }

    private void showWarning() {
        Toast.makeText(this, getString(R.string.signature_warning), Toast.LENGTH_LONG).show();
    }

    private String convertBitMapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);

    }
}
