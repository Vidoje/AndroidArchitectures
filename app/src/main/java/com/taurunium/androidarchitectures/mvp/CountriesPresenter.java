package com.taurunium.androidarchitectures.mvp;

import com.taurunium.androidarchitectures.model.CountriesService;
import com.taurunium.androidarchitectures.model.Country;
import com.taurunium.androidarchitectures.mvc.MVCActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesPresenter {
    private View view;
    private CountriesService service;

    public CountriesPresenter(View view){
        this.view = view;
        service = new CountriesService();

        fetchCountries();
    }

    private void fetchCountries(){
        service.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
                    @Override
                    public void onSuccess(List<Country> countries) {
                        List<String> countryNames = new ArrayList<>();
                        for(Country country : countries){
                            countryNames.add(country.countryName);
                        }
                        view.setValues(countryNames);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError();
                    }
                });
    }

    public void onRefresh(){
        fetchCountries();
    }

    public interface View{
        void setValues(List<String> countries);
        void onError();
    }
}
