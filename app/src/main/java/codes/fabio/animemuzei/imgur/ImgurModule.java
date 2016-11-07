package codes.fabio.animemuzei.imgur;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static codes.fabio.animemuzei.BuildConfig.IMGUR_KEY;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

@Module public class ImgurModule {

  @Provides @Singleton Gson provideGson() {
    return new GsonBuilder().registerTypeAdapterFactory(ModelsTypeAdapterFactory.create()).create();
  }

  @Provides @Singleton @Sfw List<String> provideAlbumIdsList() {
    return unmodifiableList(asList("veQcQ", "SBhEW", "8QKvH", "5cbaf", "9v8Bs", "bbshg"));
  }

  @Provides @Singleton @Nsfw List<String> provideNsfwAlbumsIdsList() {
    return unmodifiableList(asList("TwZL7", "YOL7i", "Lkdd2"));
  }

  @Provides @Singleton Interceptor provideInterceptor() {
    return new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Client-ID " + IMGUR_KEY)
            .build();

        return chain.proceed(request);
      }
    };
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient(Interceptor interceptor) {
    return new OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build();
  }

  @Provides @Singleton Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
    return new Retrofit.Builder().baseUrl("https://api.imgur.com/3/")
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build();
  }

  @Provides @Singleton ImgurApi provideImgurApi(Retrofit retrofit) {
    return retrofit.create(ImgurApi.class);
  }
}
