package blankthings.soundthing.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iosif on 6/14/17.
 */

public class SoundCloudApiService {

    private SoundCloudEndpoints endpoints;
    private Retrofit retrofit;


    SoundCloudApiService() {
        final String baseUrl = SoundCloudEndpoints.BASE_URL;

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        endpoints = retrofit.create(SoundCloudEndpoints.class);
    }


    SoundCloudEndpoints make() {
        return endpoints;
    }



}
