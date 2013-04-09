package quintec.tenis.dialogs;

import quintec.tenis.R;
import quintec.tenis.activities.CounterActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class EndMatchOnBackPressedDIalog extends DialogFragment {

	public static EndMatchOnBackPressedDIalog createDialog(){
		return new EndMatchOnBackPressedDIalog();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(" Pause match?")
		       .setPositiveButton(R.string.Yes, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CounterActivity activity = (CounterActivity) getActivity();
				activity.pauseMatchMenu();
			}
		})
		    .setNegativeButton(R.string.No, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		return builder.create();
	}
}
