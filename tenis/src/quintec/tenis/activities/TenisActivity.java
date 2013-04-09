package quintec.tenis.activities;

import java.util.List;

import quintec.tenis.LocaleActivity;
import quintec.tenis.Match;
import quintec.tenis.R;
import quintec.tenis.Score;
import quintec.tenis.database.LocalDbQuery;
import quintec.tenis.database.Qmatch;
import quintec.tenis.dialogs.NoInternetConnectionDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TenisActivity extends LocaleActivity {
	
	public static final String PLAYER_ONE_TEAM_1_NAME_BUNDLE = "PLAYER_ONE_TEAM_1_NAME_BUNDLE";
	public static final String PLAYER_TWO_TEAM_1_NAME_BUNDLE = "PLAYER_TWO_TEAM_1_NAME_BUNDLE";
	public static final String PLAYER_ONE_TEAM_2_NAME_BUNDLE = "PLAYER_ONE_TEAM_2_NAME_BUNDLE";
	public static final String PLAYER_TWO_TEAM_2_NAME_BUNDLE = "PLAYER_TWO_TEAM_2_NAME_BUNDLE";
	public static final String MATCH_IS_SINGLE_BUNDLE = "MATCH_IS_SINGLE_BUNDLE";
	public static final String MATCH_ID_BUNDLE = "MATCH_ID";
	public static final String GAME_TYPE_BUNDLE = "GAME_TYPE_BUNDLE";
	
	private Match matchh;
	private Button nextButton;
	private LinearLayout refresh, loading, noData;
	private ListView matchListView;
	private int selectedItem = -1;
	private List<Match> matchList;
	
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_list_activity);
        referenceViews();
        setClickListeners();
        if (selectedItem == -1) {
			nextButton.setEnabled(false);
		}
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	new LoadMatches().execute();
    	/*if (isOnline()) {
        	new LoadMatches().execute();
		}
        else {
			setEmptyList();
			showNoInternetDialog();
		}*/
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.default_menu, menu);
		return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.InfoMenuItem:
			Intent infoIntent = new Intent(getApplicationContext(), InfoActivity.class);
			startActivity(infoIntent);
			return true;
			
        case R.id.languageMenuItem:
			Intent settingsIntent = new Intent(getApplicationContext(), LanguageActivity.class);
			startActivity(settingsIntent);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
    }
    
    private void referenceViews() {
    	matchListView = (ListView) findViewById(R.id.matcheslistView);
        nextButton = (Button) findViewById(R.id.matchListNextButton);
        refresh = (LinearLayout) findViewById(R.id.matchListRefresh);
        loading = (LinearLayout) findViewById(R.id.matcheslistLoadingHolder);
        noData = (LinearLayout) findViewById(R.id.matcheslistnoData);
    }
    
    private void setListAdapter(List<Match> matchList) {
    	this.matchList = matchList;
    	matchListView.setAdapter(new MatchesAdapter(getApplicationContext()));
    }
    
    private void setClickListeners(){
        nextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i;
				Score pausedScore = LocalDbQuery.getPausedScore(getApplicationContext(), matchh.getId());
//				if (pausedScore != null) {
//					i= new Intent(getApplicationContext(), CounterActivity.class);
//					i.putExtra(GameProfileActivity.GAME_PROFILE_EXTRASS_KEY, pausedScore.getGameProfile());
//				}
//				else {
					i= new Intent(getApplicationContext(), GameProfileActivity.class);
				
				i.putExtra(PLAYER_ONE_TEAM_1_NAME_BUNDLE, matchh.getPlayer_one_team_one());
				i.putExtra(PLAYER_ONE_TEAM_2_NAME_BUNDLE, matchh.getPlayer_one_team_two());
				if (!matchh.isSingle()) {
					i.putExtra(PLAYER_TWO_TEAM_1_NAME_BUNDLE, matchh.getPlayer_two_team_one());
					i.putExtra(PLAYER_TWO_TEAM_2_NAME_BUNDLE, matchh.getPlayer_two_team_two());
					i.putExtra(MATCH_IS_SINGLE_BUNDLE, false);
				}
				else {
					i.putExtra(MATCH_IS_SINGLE_BUNDLE, true);
				}
				i.putExtra(MATCH_ID_BUNDLE, matchh.getId());
				startActivity(i);
			}
		});
        
        refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new LoadMatches().execute();
			}
		});
    }
    
    private void showNoInternetDialog(){
    	FragmentManager fm = getSupportFragmentManager();
    	DialogFragment dialog = NoInternetConnectionDialog.createDIalog();
    	dialog.show(fm, "NoInternetDIalog");
    }
    
    private class MatchesAdapter extends ArrayAdapter<Match> {

		public MatchesAdapter(Context context) {
			super(context, 0);
			
		}
		
		@Override
		public int getCount() {
			return matchList.size();
		}
		
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.listitem_matches, null);
            }
            
            final Match match = matchList.get(position);
            String player1Name = match.getPlayer_one_team_one();
            String player2Name = match.getPlayer_one_team_two();
            if (!match.isSingle()) {
				player1Name = player1Name +  "  " + playerSeparator 
						      + "  " + match.getPlayer_two_team_one();
				player2Name = player2Name +  "  " + playerSeparator
					      + "  " + match.getPlayer_two_team_two();
			}
		    
		    final TextView firstNameTV = (TextView) v.findViewById(R.id.matches_first_team_player1Name);
		    final TextView secondName = (TextView) v.findViewById(R.id.matches_second_team_player1Name);
		    final ImageView checkBox = (ImageView) v.findViewById(R.id.matches_CheckBox);
		    final ImageView stateIMG = (ImageView) v.findViewById(R.id.matches_status);
		    
		    v.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					matchh = match;
					selectedItem = position;
					nextButton.setEnabled(true);
					notifyDataSetChanged();
				}
			});
		    
		    if (selectedItem == position) {
		    	checkBox.setBackgroundDrawable(getResources().getDrawable(R.drawable.icn38_form_radio1_focused));
		    	firstNameTV.setTextColor(getResources().getColor(R.color.color_white));
		    	secondName.setTextColor(getResources().getColor(R.color.color_white));
		    	firstNameTV.setTextAppearance(getApplicationContext(), R.style.TextStyleBold);
		    	secondName.setTextAppearance(getApplicationContext(), R.style.TextStyleBold);
			}
			else {
				checkBox.setBackgroundDrawable(getResources().getDrawable(R.drawable.icn38_form_radio0_focused));
				firstNameTV.setTextColor(getResources().getColor(R.color.color_light_grey_a));
		    	secondName.setTextColor(getResources().getColor(R.color.color_light_grey_b));
		    	firstNameTV.setTextAppearance(getApplicationContext(), R.style.TextStyleRegular);
		    	secondName.setTextAppearance(getApplicationContext(), R.style.TextStyleRegular);
			}
		    firstNameTV.setText(player1Name);
		    secondName.setText(player2Name);
		    int state = match.getState();
		    if (state == Match.STATE.INTERUPTED) {
			    stateIMG.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_st_paused_activity));
			}
		    else {
		    	 stateIMG.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_st_planned_activity));
			}
			return v;
		}
    }
    
    private class LoadMatches extends AsyncTask<Void , Void, LoaderData>{

    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		matchListView.setVisibility(View.GONE);
    		loading.setVisibility(View.VISIBLE);
    		noData.setVisibility(View.GONE);
    	}
    	
		@Override
		protected LoaderData doInBackground(Void... params) {
			LoaderData loaderData= new LoaderData();
			if (isOnline()) {
				loaderData.loaderResult = LoaderResult.OK;
				loaderData.matchList = Qmatch.getList(getApplicationContext());
			}
	        else {
	        	loaderData.loaderResult = LoaderResult.NO_INTERNET_CONNECTION;
	        	loaderData.matchList = null;
			}
			return loaderData;
		}
		
		@Override
		protected void onPostExecute(LoaderData result) {
			super.onPostExecute(result);
			if (result.loaderResult == LoaderResult.OK) {
				if (result != null && result.matchList.size() != 0) {
					setListAdapter(result.matchList);
					matchListView.setVisibility(View.VISIBLE);
		    		loading.setVisibility(View.GONE);
		    		noData.setVisibility(View.GONE);
				}
				else {
					noData.setVisibility(View.VISIBLE);
					matchListView.setVisibility(View.GONE);
		    		loading.setVisibility(View.GONE);
				}
			}
			else {
				showNoInternetDialog();
			}
		}
    	
    }
    
    private class LoaderData {
    	List<Match> matchList;
    	LoaderResult loaderResult;
    }
    
    private enum LoaderResult {
    	NO_INTERNET_CONNECTION,
    	OK;
    }
}