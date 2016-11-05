package codes.fabio.animemuzei;

import android.content.Intent;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

class Util {
  static Intent shareImageIntent(String message) {
    Intent shareImageIntent = new Intent(Intent.ACTION_SEND);
    shareImageIntent.putExtra(Intent.EXTRA_TEXT, message);
    shareImageIntent.setType("text/plain");
    return Intent.createChooser(shareImageIntent, "Share Image Via")
        .addFlags(FLAG_ACTIVITY_NEW_TASK);
  }
}
