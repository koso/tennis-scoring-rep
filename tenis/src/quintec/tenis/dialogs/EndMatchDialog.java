package quintec.tenis.dialogs;

import quintec.tenis.R;
import quintec.tenis.activities.CounterActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class EndMatchDialog extends DialogFragment{
	
	public static EndMatchDialog createDialog(){
		return new EndMatchDialog();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.dialog_match_ended)
		       .setPositiveButton("finish", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CounterActivity activity = (CounterActivity) getActivity();
				activity.finishMatch();
			}
		})
		    .setNegativeButton(R.string.undo, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CounterActivity activity = (CounterActivity) getActivity();
				activity.undo();
				
			}
		})
		    /*.setNeutralButton(R.string.edit_match, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO ope
				
			}
		})*/
		.setCancelable(false);
		
		
		
		return builder.create();
	}
}
