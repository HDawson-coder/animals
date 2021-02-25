package edu.cnm.deepdive.animals.service;

import android.content.Context;
import edu.cnm.deepdive.animals.BuildConfig;
import edu.cnm.deepdive.animals.model.Animal;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class AnimalRepository {

  private final Context context;

  public AnimalRepository(Context context) {
    this.context = context;
  }

  public Single<List<Animal>> getAll() {
    return WebServiceProxy
        .getInstance()
        .getAnimals(BuildConfig.API_KEY)
        .subscribeOn(Schedulers.io());
  }

}
