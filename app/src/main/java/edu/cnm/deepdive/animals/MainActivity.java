package edu.cnm.deepdive.animals;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.cnm.deepdive.animals.model.Animal;
import edu.cnm.deepdive.animals.service.WebServiceProxy;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    Log.d(getClass().getName(), response.body().toString());
                } else {
                    Log.e(getClass().getName(), response.message());
                }
            } catch (IOException e) {
                Log.e(getClass().getName(), e.getMessage(), e);
            }

        }
    }
}