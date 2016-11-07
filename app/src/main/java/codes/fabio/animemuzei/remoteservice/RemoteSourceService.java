package codes.fabio.animemuzei.remoteservice;

import codes.fabio.animemuzei.R;
import codes.fabio.animemuzei.SharedPrefsHelper;
import codes.fabio.animemuzei.imgur.ImgurImageRemoteDataSource;
import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import com.google.android.apps.muzei.api.UserCommand;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import timber.log.Timber;

import static codes.fabio.animemuzei.AnimeMuzeiApplication.getApplicationComponent;
import static codes.fabio.animemuzei.Util.shareImageIntent;

public class RemoteSourceService extends RemoteMuzeiArtSource {

  private static final String SOURCE_NAME = "AnimeMuzeiArtSource";
  static final int CUSTOM_COMMAND_ID_SHARE_ARTWORK = MAX_CUSTOM_COMMAND_ID - 1;

  @Inject ImgurImageRemoteDataSource imgurImageRemoteDataSource;
  @Inject List<UserCommand> userCommands;
  @Inject SharedPrefsHelper sharedPrefsHelper;

  public RemoteSourceService() {
    super(SOURCE_NAME);
  }

  @Override public void onCreate() {
    super.onCreate();
    injectDependencies();
    setUserCommands(userCommands);
  }

  private void injectDependencies() {
    getApplicationComponent(this).inject(this);
  }

  @Override protected void onTryUpdate(int reason) throws RetryException {
    try {
      Artwork artwork = imgurImageRemoteDataSource.nextImage().toBlocking().first();
      publishArtwork(artwork);
      Timber.d("published Artwork from Uri: %s", artwork.getViewIntent().getData());
    } catch (Throwable e) {
      Timber.e(e, "onTryUpdate failed. Throwing RetryException");
      throw new RetryException();
    }

    Timber.d("onTryUpdate: nsfw: %s", sharedPrefsHelper.isNsfwEnabled());
    Timber.d("onTryUpdate: updateTimeInMinutes: %s", String.valueOf(
        TimeUnit.MILLISECONDS.toMinutes(sharedPrefsHelper.getUpdateIntervalMillis())));
    scheduleUpdate(System.currentTimeMillis() + sharedPrefsHelper.getUpdateIntervalMillis());
  }

  @Override protected void onCustomCommand(int id) {
    switch (id) {
      case CUSTOM_COMMAND_ID_SHARE_ARTWORK:
        startActivity(shareImageIntent(getResources().getString(R.string.share_artwork_message,
            getCurrentArtwork().getImageUri())));
        break;
      default:
        super.onCustomCommand(id);
    }
  }
}
