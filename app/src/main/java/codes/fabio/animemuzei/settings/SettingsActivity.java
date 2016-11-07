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

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;
import static codes.fabio.animemuzei.AnimeMuzeiApplication.getApplicationComponent;
import static codes.fabio.animemuzei.Util.getTypeFace;

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

    injectDependencies();
    findViews();
    setAnimeMuzeiText();
    setMadeWithLoveText();
    setUpNsfwCheckBox();
    setUpSpinner();
  }

  private void setAnimeMuzeiText() {
    animeMuzeiTextView.setTypeface(getTypeFace(this, "Alegreya-BlackItalic.ttf"));
    animeMuzeiTextView.setText(getString(R.string.app_name).toLowerCase());
  }

  private void injectDependencies() {
    getApplicationComponent(this).inject(this);
  }

  private void findViews() {
    animeMuzeiTextView = ((TextView) findViewById(R.id.appName));
    madeWithLoveTextView = ((TextView) findViewById(R.id.madeWithLove));
    nsfwCheckBox = ((CheckBox) findViewById(R.id.nsfw));
    updateIntervalSpinner = ((Spinner) findViewById(R.id.updateInterval));
  }

  private void setUpNsfwCheckBox() {

    nsfwCheckBox.setChecked(sharedPrefsHelper.isNsfwEnabled());
    nsfwCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        sharedPrefsHelper.setNsfwEnabled(isChecked);
      }
    });
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

  private void setUpSpinner() {
    updateIntervalSpinner.setAdapter(updateIntervalSpinnerAdapter);

    long updateIntervalMillis = sharedPrefsHelper.getUpdateIntervalMillis();

    for (int position = 0; position < updateIntervalSpinner.getAdapter().getCount(); position++) {
      UpdateInterval updateInterval =
          ((UpdateInterval) updateIntervalSpinner.getAdapter().getItem(position));
      if (updateInterval.toMillis() == updateIntervalMillis) {
        updateIntervalSpinner.setSelection(position);
      }
    }

    updateIntervalSpinner.setOnItemSelectedListener(new OnItemSelectedAdapter() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        UpdateInterval updateInterval = ((UpdateInterval) parent.getAdapter().getItem(position));
        sharedPrefsHelper.setUpdateIntervalMillis(updateInterval.toMillis());
      }
    });
  }

  @Override protected void onDestroy() {
    updateIntervalSpinner.setAdapter(null);
    updateIntervalSpinner.setOnItemSelectedListener(null);
    nsfwCheckBox.setOnCheckedChangeListener(null);
    super.onDestroy();
  }
}
