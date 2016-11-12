package codes.fabio.animemuzei.settings;

import android.view.View;
import android.widget.TextView;
import codes.fabio.animemuzei.R;

class UpdateIntervalViewHolder {

  private TextView updateIntervalText;

  UpdateIntervalViewHolder(View convertView) {
    updateIntervalText = (TextView) convertView.findViewById(R.id.updateIntervalText);
  }

  void bind(UpdateInterval updateInterval) {
    updateIntervalText.setText(updateInterval.format(updateIntervalText.getContext()));
  }
}
