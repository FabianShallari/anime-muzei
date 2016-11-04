package codes.fabio.animemuzei.imgur;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by fabian on 11/4/16.
 */

@GsonTypeAdapterFactory abstract class ModelsTypeAdapterFactory implements TypeAdapterFactory {

  public static TypeAdapterFactory create() {
    return new AutoValueGson_ModelsTypeAdapterFactory();
  }
}
