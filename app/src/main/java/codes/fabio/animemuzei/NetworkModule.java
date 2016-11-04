package codes.fabio.animemuzei;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module class NetworkModule {
  
  @Provides Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
    return new Retrofit.Builder().baseUrl("https://api.imgur.com/3/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();
  }

  @Provides ImgurApi provideImgurApi(Retrofit retrofit) {
    return retrofit.create(ImgurApi.class);
  }
}
