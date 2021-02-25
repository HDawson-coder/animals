package edu.cnm.deepdive.animals.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import edu.cnm.deepdive.animals.model.Animal;
import edu.cnm.deepdive.animals.service.AnimalRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

  private final AnimalRepository repository;
  private final MutableLiveData<List<Animal>> animals;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  public MainViewModel(@NonNull Application application) {
    super(application);
    repository = new AnimalRepository(application);
    animals = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    load();
  }

  public LiveData<List<Animal>> getAnimals() {
    return animals;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  private void load() {
    pending.add(
        repository
            .getAll()
            .subscribe(
                animals::postValue,
                throwable::postValue
            )
    );
  }
  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {
    pending.clear();
  }
}
