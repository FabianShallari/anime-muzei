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
import android.widget.Toast;
import codes.fabio.animemuzei.R;
import javax.inject.Inject;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;
import static codes.fabio.animemuzei.AnimeMuzeiApplication.getApplicationComponent;

public class SettingsActivity extends AppCompatActivity {

  @Inject UpdateIntervalSpinnerAdapter updateIntervalSpinnerAdapter;

  TextView madeWithLoveTextView;
  CheckBox nsfwCheckBox;
  Spinner updateIntervalSpinner;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_anime_muzei_settings);

    findViews();
    injectDependencies();
    setMadeWithLoveText();
    setUpNsfwCheckBox();
    setUpSpinner();
  }

  private void injectDependencies() {
    getApplicationComponent(this).inject(this);
  }

  private void findViews() {
    madeWithLoveTextView = ((TextView) findViewById(R.id.madeWithLove));
    nsfwCheckBox = ((CheckBox) findViewById(R.id.nsfw));
    updateIntervalSpinner = ((Spinner) findViewById(R.id.updateInterval));
  }

  private void setUpNsfwCheckBox() {
    nsfwCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(SettingsActivity.this, "isChecked " + isChecked, Toast.LENGTH_SHORT).show();
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
    updateIntervalSpinner.setOnItemSelectedListener(new OnItemSelectedAdapter() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(SettingsActivity.this,
            "Selected: " + updateIntervalSpinner.getAdapter().getItem(position), Toast.LENGTH_SHORT)
            .show();
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
