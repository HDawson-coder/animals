package edu.cnm.deepdive.animals;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.cnm.deepdive.animals.model.Animal;
import edu.cnm.deepdive.animals.service.WebServiceProxy;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

        private WebView contentView;
        private Spinner animalSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentView = findViewById(R.id.content_view);
        animalSelector = findViewById(R.id.animal_selector);
        setupWebView();
    }
    private void setupWebView() {
        contentView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        }); //zoom in/out controls
        WebSettings settings = contentView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        new Retriever().start();
    }

    private class Retriever extends Thread {

        @Override
        public void run() {
            try {
                Response<List<Animal>> response = WebServiceProxy.getInstance()
                    .getAnimals(BuildConfig.API_KEY)
                    .execute();
                if (response.isSuccessful()) {
                    List<Animal> animals = response.body();
                    Random rng = new Random();
                    //noinspection ConstantConditions
                    String url = animals.get(rng.nextInt(animals.size())).getImageUrl();
                    runOnUiThread(() -> {
                        contentView.loadUrl(url);
                        ArrayAdapter<Animal> adapter = new ArrayAdapter<>(
                            MainActivity.this, android.R.layout.simple_dropdown_item_1line, animals);
                        animalSelector.setAdapter(adapter);
                    });
                } else {
                    Log.e(getClass().getName(), response.message());
                }
            } catch (IOException e) {
                Log.e(getClass().getName(), e.getMessage(), e);
            }

        }
    }
}