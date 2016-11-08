package codes.fabio.animemuzei.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import codes.fabio.animemuzei.R;
import codes.fabio.animemuzei.SharedPrefsHelper;
import javax.inject.Inject;
import timber.log.Timber;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;
import static codes.fabio.animemuzei.AnimeMuzeiApplication.getApplicationComponent;
import static codes.fabio.animemuzei.Util.getTypeFace;
import static codes.fabio.animemuzei.remoteservice.RemoteSourceService.startActionRescheduleOnly;

public class SettingsActivity extends AppCompatActivity {

  @Inject UpdateIntervalSpinnerAdapter updateIntervalSpinnerAdapter;
  @Inject SharedPrefsHelper sharedPrefsHelper;

  TextView animeMuzeiTextView;
  TextView madeWithLoveTextView;
  CheckBox nsfwCheckBox;
  Spinner updateIntervalSpinner;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_anime_muzei_settings);

    // inject dependencies
    getApplicationComponent(this).inject(this);

    // get views
    findViews();

    // set anime muzei typeface and text
    animeMuzeiTextView.setText(getString(R.string.app_name).toLowerCase());
    animeMuzeiTextView.setTypeface(getTypeFace(this, "Alegreya-BlackItalic.ttf"));

    // set Made with love text with ImageSpan
    setMadeWithLoveText();

    nsfwCheckBox.setChecked(sharedPrefsHelper.isNsfwEnabled());
    nsfwCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Timber.d("onCheckedChanged: %s", isChecked);
        sharedPrefsHelper.setNsfwEnabled(isChecked);
      }
    });

    // set adapter on update interval spinner
    updateIntervalSpinner.setAdapter(updateIntervalSpinnerAdapter);

    // set default selection on spinner's adapter based on shared preferences
    setUpdateIntervalSelection(sharedPrefsHelper.getUpdateIntervalMillis());

    // set spinner's listener to update shared preferences
    updateIntervalSpinner.setOnItemSelectedListener(new OnItemSelectedAdapter() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        UpdateInterval updateInterval = ((UpdateInterval) parent.getAdapter().getItem(position));
        if (!updateInterval.equalsMillis(sharedPrefsHelper.getUpdateIntervalMillis())) {
          // If update interval has changed, reschedule
          sharedPrefsHelper.setUpdateIntervalMillis(updateInterval.toMillis());
          startActionRescheduleOnly(SettingsActivity.this);
        }
      }
    });

  }

  private void findViews() {
    animeMuzeiTextView = ((TextView) findViewById(R.id.appName));
    madeWithLoveTextView = ((TextView) findViewById(R.id.madeWithLove));
    nsfwCheckBox = ((CheckBox) findViewById(R.id.nsfw));
    updateIntervalSpinner = ((Spinner) findViewById(R.id.updateInterval));
  }

  private void setMadeWithLoveText() {
    String madeWithLove = getString(R.string.made_with_love_in_tirana);
    String love = "love";

    SpannableString spannableString = new SpannableString(madeWithLove);

    ImageSpan heart = new ImageSpan(this, R.drawable.ic_heart, ImageSpan.ALIGN_BASELINE);
    spannableString.setSpan(heart, madeWithLove.indexOf(love),
        madeWithLove.indexOf(love) + love.length(), SPAN_INCLUSIVE_EXCLUSIVE);

    madeWithLoveTextView.setText(spannableString);
  }

  private void setUpdateIntervalSelection(long updateIntervalMillis) {
    for (int position = 0; position < updateIntervalSpinner.getAdapter().getCount(); position++) {
      UpdateInterval updateInterval =
          ((UpdateInterval) updateIntervalSpinner.getAdapter().getItem(position));

      if (updateInterval.equalsMillis(updateIntervalMillis)) {
        updateIntervalSpinner.setSelection(position);
        break;
      }
    }
  }

  @Override protected void onDestroy() {
    updateIntervalSpinner.setAdapter(null);
    updateIntervalSpinner.setOnItemSelectedListener(null);
    nsfwCheckBox.setOnCheckedChangeListener(null);
    super.onDestroy();
  }
}
