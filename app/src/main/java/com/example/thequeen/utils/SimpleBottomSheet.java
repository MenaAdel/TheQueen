package com.example.thequeen.utils;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SimpleBottomSheet extends BottomSheetDialogFragment {

	private View customView;
	private SheetCreatedListener sheetCreatedListener;
	private Integer customViewLayout;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (customView == null)
			if (customViewLayout != null)
				customView = inflater.inflate(customViewLayout, null, false);
		return customView;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (sheetCreatedListener != null)
			sheetCreatedListener.onSheetViewCreated(view);

	}

	public SimpleBottomSheet setView(View customView) {
		this.customView = customView;
		return this;
	}

	public SimpleBottomSheet setView(@LayoutRes int customViewLayout) {
		this.customViewLayout = customViewLayout;
		return this;
	}

	public SimpleBottomSheet setSheetCreatedListener(SheetCreatedListener sheetCreatedListener) {
		this.sheetCreatedListener = sheetCreatedListener;
		return this;
	}

	public interface SheetCreatedListener {
		void onSheetViewCreated(View view);
	}
}
