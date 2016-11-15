package codes.fabio.animemuzei.remoteservice;

import android.content.Context;
import codes.fabio.animemuzei.R;
import com.google.android.apps.muzei.api.UserCommand;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Singleton;

import static codes.fabio.animemuzei.remoteservice.AnimeMuzeiRemoteSourceService.CUSTOM_COMMAND_ID_SHARE_ARTWORK;
import static com.google.android.apps.muzei.api.MuzeiArtSource.BUILTIN_COMMAND_ID_NEXT_ARTWORK;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

@Module public class RemoteServiceModule {

  @Provides @Singleton List<UserCommand> provideUserCommands(Context context) {
    return unmodifiableList(asList(new UserCommand(BUILTIN_COMMAND_ID_NEXT_ARTWORK),
        new UserCommand(CUSTOM_COMMAND_ID_SHARE_ARTWORK,
            context.getString(R.string.share_artwork_command))));
  }
}
