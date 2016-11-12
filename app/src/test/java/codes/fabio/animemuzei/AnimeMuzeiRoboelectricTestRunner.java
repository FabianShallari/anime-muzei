package codes.fabio.animemuzei;

import android.os.Build;
import java.lang.reflect.Method;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

public class AnimeMuzeiRoboelectricTestRunner extends RobolectricTestRunner {

  private static final int TEST_SDK_LEVEL = Build.VERSION_CODES.M;

  public AnimeMuzeiRoboelectricTestRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
  }

  @Override public Config getConfig(Method method) {
    final Config defaultConfig = super.getConfig(method);
    return new Config.Implementation(new int[] { TEST_SDK_LEVEL }, defaultConfig.manifest(),
        defaultConfig.qualifiers(), defaultConfig.packageName(), defaultConfig.abiSplit(),
        defaultConfig.resourceDir(), defaultConfig.assetDir(), defaultConfig.buildDir(),
        defaultConfig.shadows(), defaultConfig.instrumentedPackages(), AnimeMuzeiApplication.class,
        defaultConfig.libraries(), BuildConfig.class);
  }
}
