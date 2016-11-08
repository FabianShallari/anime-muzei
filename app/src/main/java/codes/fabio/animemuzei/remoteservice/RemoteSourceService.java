package codes.fabio.animemuzei.remoteservice;

import android.content.Context;
import android.content.Intent;
import codes.fabio.animemuzei.R;
import codes.fabio.animemuzei.SharedPrefsHelper;
import codes.fabio.animemuzei.imgur.ImgurImageRemoteDataSource;
import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import com.google.android.apps.muzei.api.UserCommand;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

import static codes.fabio.animemuzei.AnimeMuzeiApplication.getApplicationComponent;
import static codes.fabio.animemuzei.Util.shareImageIntent;
import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RemoteSourceService extends RemoteMuzeiArtSource {

  static final int CUSTOM_COMMAND_ID_SHARE_ARTWORK = MAX_CUSTOM_COMMAND_ID - 1;

  private static final String ACTION_RESCHEDULE_ONLY =
      "codes.fabio.animemuzei.remoteservice.RemoteSourceService.ACTION_RESCHEDULE_ONLY";
  private static final String SOURCE_NAME = "AnimeMuzeiArtSource";

  @Inject ImgurImageRemoteDataSource imgurImageRemoteDataSource;
  @Inject List<UserCommand> userCommands;
  @Inject SharedPrefsHelper sharedPrefsHelper;

  public RemoteSourceService() {
    super(SOURCE_NAME);
  }

  public static void startActionRescheduleOnly(Context context) {
    Intent intent = new Intent(context, RemoteSourceService.class);
    intent.setAction(ACTION_RESCHEDULE_ONLY);
    context.startService(intent);
  }

  @Override public void onCreate() {
    super.onCreate();
    injectDependencies();
    setUserCommands(userCommands);
  }

  private void injectDependencies() {
    getApplicationComponent(this).inject(this);
  }

  @Override protected void onHandleIntent(Intent intent) {
    String action = intent.getAction();
    if (ACTION_RESCHEDULE_ONLY.equals(action)) {
      rescheduleOnly();
    } else {
      super.onHandleIntent(intent);
    }
  }

  private void rescheduleOnly() {
    Timber.d("rescheduleOnly: updateTimeInMinutes: %s",
        String.valueOf(MILLISECONDS.toMinutes(sharedPrefsHelper.getUpdateIntervalMillis())));

    scheduleUpdate(currentTimeMillis() + sharedPrefsHelper.getUpdateIntervalMillis());
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
    Timber.d("onTryUpdate: updateTimeInMinutes: %s",
        String.valueOf(MILLISECONDS.toMinutes(sharedPrefsHelper.getUpdateIntervalMillis())));

    scheduleUpdate(currentTimeMillis() + sharedPrefsHelper.getUpdateIntervalMillis());
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
