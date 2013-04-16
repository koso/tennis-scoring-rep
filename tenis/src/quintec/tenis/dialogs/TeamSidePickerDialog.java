package quintec.tenis.dialogs;

import quintec.tenis.LocaleActivity;
import quintec.tenis.R;
import quintec.tenis.activities.CounterActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

@SuppressLint("ValidFragment")
public class TeamSidePickerDialog extends DialogFragment {

	private String pl1Name, pl2Name, pl12Name = null, pl22Name= null;
	
	public TeamSidePickerDialog(String pl1Name, String pl2Name) {
		this.pl1Name = pl1Name;
		this.pl2Name = pl2Name;
	}
	
	public TeamSidePickerDialog(String pl1Name, String pl2Name, String pl12Name, String pl22Name) {
		this.pl1Name = pl1Name;
		this.pl2Name = pl2Name;
		this.pl12Name = pl12Name;
		this.pl22Name = pl22Name;
	}

	public static TeamSidePickerDialog createDialog(String pl1Name, String pl2Name){
		return new TeamSidePickerDialog(pl1Name, pl2Name);
	}
	
	public static TeamSidePickerDialog createDialog(String pl1Name, String pl2Name, String pl12Name,String pl22Name){
		return new TeamSidePickerDialog(pl1Name, pl2Name,pl12Name,pl22Name);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String pl1;
		String pl2;
		if (pl12Name == null || pl22Name == null) {
			pl1 = pl1Name;
			pl2 = pl2Name;
		}
		else {
			pl1 = pl1Name + "  " + LocaleActivity.playerSeparator + "  " + 
		              pl12Name;
			pl2 = pl2Name+ "  " + LocaleActivity.playerSeparator + "  " + 
		              pl22Name;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.teamSideDialog_title)
		       .setMessage(R.string.teamSideDialog_message)
		       .setPositiveButton(pl1, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CounterActivity activity = (CounterActivity) getActivity();
				activity.setSidesAtStart(CounterActivity.TeamSides.NORMAL);
			}
		})
		    .setNegativeButton(pl2, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CounterActivity activity = (CounterActivity) getActivity();
				activity.setSidesAtStart(CounterActivity.TeamSides.REVERSE);
			}
		})
		.setCancelable(false);
		
		return builder.create();
	}
}
