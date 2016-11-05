package codes.fabio.animemuzei;

import android.content.res.Resources;
import com.google.android.apps.muzei.api.UserCommand;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Singleton;

import static codes.fabio.animemuzei.AnimeMuzeiRemoteSource.CUSTOM_COMMAND_ID_SHARE_ARTWORK;
import static com.google.android.apps.muzei.api.MuzeiArtSource.BUILTIN_COMMAND_ID_NEXT_ARTWORK;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

@Module class AnimeMuzeiModule {

  private final Resources resources;

  AnimeMuzeiModule(Resources resources) {
    this.resources = resources;
  }

  @Provides @Singleton List<UserCommand> provideUserCommands() {
    return unmodifiableList(asList(new UserCommand(BUILTIN_COMMAND_ID_NEXT_ARTWORK),
        new UserCommand(CUSTOM_COMMAND_ID_SHARE_ARTWORK,
            resources.getString(R.string.share_artwork_command))));
  }
}
