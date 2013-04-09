package quintec.tenis.activities;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import quintec.tenis.LocaleActivity;
import quintec.tenis.R;

public class InfoActivity extends LocaleActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.info_activity_layout);
		
		Button backButton = (Button) findViewById(R.id.infoback);
		String linkText = "<a href='http://www.qintec.sk/en'>Qintec s.r.o.</a>";
	    TextView link = (TextView) findViewById(R.id.QintecSRO);
		link.setText(Html.fromHtml(linkText));
		link.setMovementMethod(LinkMovementMethod.getInstance());
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
