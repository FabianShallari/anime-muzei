package codes.fabio.animemuzei.remoteservice;

import android.content.Context;
import android.content.Intent;
import codes.fabio.animemuzei.R;
import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import com.google.android.apps.muzei.api.UserCommand;
import java.util.List;
import javax.inject.Inject;

import static codes.fabio.animemuzei.AnimeMuzeiApplication.getApplicationComponent;
import static codes.fabio.animemuzei.Util.shareImageIntent;

public class AnimeMuzeiRemoteSourceService extends RemoteMuzeiArtSource {

  static final int CUSTOM_COMMAND_ID_SHARE_ARTWORK = MAX_CUSTOM_COMMAND_ID - 1;

  public static final String ACTION_RESCHEDULE_ONLY =
      "codes.fabio.animemuzei.remoteservice.AnimeMuzeiRemoteSourceService.ACTION_RESCHEDULE_ONLY";

  private static final String SOURCE_NAME = "AnimeMuzeiArtSource";

  @Inject RemoteServiceDelegate delegate;

  public AnimeMuzeiRemoteSourceService() {
    super(SOURCE_NAME);
  }

  public static void startActionRescheduleOnly(Context context) {
    Intent intent = new Intent(context, AnimeMuzeiRemoteSourceService.class);
    intent.setAction(ACTION_RESCHEDULE_ONLY);
    context.startService(intent);
  }

  @Override public void onCreate() {
    super.onCreate();
    getApplicationComponent(this).inject(this);
    delegate.attachService(this);
  }

  @Override protected void onHandleIntent(Intent intent) {
    String action = intent.getAction();
    if (ACTION_RESCHEDULE_ONLY.equals(action)) {
      delegate.rescheduleOnly();
    } else {
      super.onHandleIntent(intent);
    }
  }

  void setCustomCommands(List<UserCommand> userCommands) {
    setUserCommands(userCommands);
  }

  void setNextUpdate(long nextUpdateUnixEpoch) {
    scheduleUpdate(nextUpdateUnixEpoch);
  }

  void setArtwork(Artwork artwork) {
    publishArtwork(artwork);
  }

  @Override protected void onTryUpdate(int reason) throws RetryException {
    delegate.tryUpdate(reason);
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

  @Override public void onDestroy() {
    delegate.detachService();
    super.onDestroy();
  }
}
