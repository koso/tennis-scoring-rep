package quintec.tenis.dialogs;

import quintec.tenis.R;
import quintec.tenis.math.CalculateBallsChange.BallsChange;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

@SuppressLint("ValidFragment")
public class BallsChangeDialog extends DialogFragment{
	
	BallsChange mode;

	@SuppressLint("ValidFragment")
	public static BallsChangeDialog createDIalog(BallsChange mode){
		return new BallsChangeDialog(mode);
	}
	
	public BallsChangeDialog(BallsChange mode) {
		this.mode = mode;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.ballChangeDialog_title);
		if (mode == BallsChange.CHANGE_BALLS) 
			builder.setMessage(R.string.ballChangeDialog_changeBalls);
		else 
			builder.setMessage(R.string.ballChangeDialog_prepare);
		
		builder.setPositiveButton(R.string._ok, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			      dismiss();
			}
		});
		
		return builder.create();
	}
}
