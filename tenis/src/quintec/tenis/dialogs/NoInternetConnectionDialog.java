package quintec.tenis.dialogs;

import quintec.tenis.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class NoInternetConnectionDialog extends DialogFragment{

	public static NoInternetConnectionDialog createDIalog(){
		return new NoInternetConnectionDialog();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.networkError);
		builder.setPositiveButton("Ok", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			       dismiss();
			}
		});
		return builder.create();
	}
}
