package edu.cnm.deepdive.animals.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.animals.R;
import edu.cnm.deepdive.animals.databinding.ActivityMainBinding;
import edu.cnm.deepdive.animals.model.Animal;
import edu.cnm.deepdive.animals.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    binding.animalSelector.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Animal animal = (Animal) parent.getItemAtPosition(position);
        Picasso.get().load(animal.getImageUrl()).into(binding.image);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
    setupViewModel(binding);
  }

  private void setupViewModel(ActivityMainBinding binding) {
    MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    getLifecycle().addObserver(viewModel);
    viewModel.getAnimals().observe(this, (animals) -> {
      ArrayAdapter<Animal> adapter = new ArrayAdapter<>(MainActivity.this,
          R.layout.item_animal_spinner, animals);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
      binding.animalSelector.setAdapter(adapter);
    });
    viewModel.getThrowable().observe(this, (throwable) -> {
      Log.e(getClass().getName(), throwable.getMessage(), throwable);
      //noinspection ConstantConditions
      Snackbar.make(binding.getRoot(), throwable.getMessage(),
          BaseTransientBottomBar.LENGTH_INDEFINITE).show();
    });
  }

}