package gr.ntua.ece.sevle.com.toolbarplays;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentWithUnlockedToolbar extends Fragment implements ToolbarManipulation
{
	public static final String TAG = FragmentWithUnlockedToolbar.class.getSimpleName();
	private static final String KEY_TITLE = "DummyTitle";
	private ToolbarManipulation mCallback;

	public static FragmentWithUnlockedToolbar newInstance(String title) {
		FragmentWithUnlockedToolbar f = new FragmentWithUnlockedToolbar();
		Bundle args = new Bundle();
		args.putString(KEY_TITLE, title);
		f.setArguments(args);
		return (f);
	}

	//Dummy interface to register onDetach
	private static ToolbarManipulation sDummyCallbacks = new ToolbarManipulation() {
		@Override
		public void collapseToolbar() {
		}
		@Override
		public void expandToolbar() {
		}

		@Override
		public void setTitle(String s) {
		}
	};

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (ToolbarManipulation) context;

		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString()
					+ " must implement eventFragmentCallbacks");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		// Reset the active callback interface to the dummy implementation.
		mCallback = sDummyCallbacks;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.green_fragment, container, false);
		return v;
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onResume() {
		super.onResume();
		mCallback.setTitle("Unlocked");
		mCallback.expandToolbar();
	}


	@Override
	public void collapseToolbar() {
	}

	@Override
	public void expandToolbar() {
	}

	@Override
	public void setTitle(String s) {
	}
}
