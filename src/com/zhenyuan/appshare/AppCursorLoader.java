package com.zhenyuan.appshare;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.provider.ContactsContract;

public class AppCursorLoader extends CursorLoader {

	String[] mContactProjection = { ContactsContract.Contacts._ID, // 0
			ContactsContract.Contacts.DISPLAY_NAME // 1
	};

	private Context mContext;

	public AppCursorLoader(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	protected Cursor onLoadInBackground() {
		Cursor cursor = mContext.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, mContactProjection,
				null, null, null);
		return cursor;
	}
}