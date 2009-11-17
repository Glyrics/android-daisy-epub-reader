package com.ader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class DaisyBrowser extends ListActivity {
	File currentDirectory = new File("/sdcard/");

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GenerateBrowserData();
	}

	boolean isDaisyDirectory(File aFile) {
		if (!aFile.isDirectory())
			return false;

		if (new File(aFile, "ncc.html").exists())
			return true;
		else
			return false;
	}

	@Override
	protected void onListItemClick(android.widget.ListView l,
			android.view.View v, int position, long id) {
		String item = l.getSelectedItem().toString();

		if (isDaisyDirectory(new File(currentDirectory, item))) {
			Intent i = new Intent(this, DaisyReader.class);
			
			i.putExtra("daisyPath", new File(currentDirectory, item).getAbsolutePath()+"/");
			startActivity(i);
			return;
		}

		if (item.equals("Up 1 Level")) {
			currentDirectory = new File(currentDirectory.getParent());
			GenerateBrowserData();
			return;
		}

		File temp = new File(currentDirectory, item);
		if (temp.isDirectory()) {
			currentDirectory = temp;
			GenerateBrowserData();
		}
	}

	void GenerateBrowserData() {
		String[] files = currentDirectory.list();

		if (currentDirectory.getParent().equals("/")) {
			setListAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, files));
			return;
		}

		String[] files2 = new String[files.length + 1];

		for (int i = 0; i < files.length; i++)
			files2[i] = files[i];
		files2[files.length] = "Up 1 Level";
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, files2));
		return;

	}
}
