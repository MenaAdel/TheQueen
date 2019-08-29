package com.example.thequeen.utils

import android.content.Context
import android.databinding.ViewDataBinding
import android.graphics.Point
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.thequeen.R

class CustomBottomSheet<UIModel, Binding : ViewDataBinding> : BottomSheetDialogFragment() {
    lateinit var adapter: SimpleBindingRecyclerAdapter<UIModel, Binding>
    private var customView: View? = null
    lateinit var items: List<UIModel>
    private var sheetCreatedListener: SimpleBottomSheet.SheetCreatedListener? = null
    private var customViewLayout: Int? = null
    private var customItemLayout: Int? = null
    private var variableId: Int? = null
    private var itemCLickedListener: ((UIModel) -> (Unit))? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (customView == null)
            if (customViewLayout != null)
                customView = inflater.inflate(customViewLayout!!, null, false)

        return customView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showsDialog = true
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setSheetHeight(view)

        initRecycleView(view)

        super.onViewCreated(view, savedInstanceState)
        if (sheetCreatedListener != null)
            sheetCreatedListener!!.onSheetViewCreated(view)
    }

    fun setView(customView: View): CustomBottomSheet<UIModel, Binding> {
        this.customView = customView
        return this
    }

    fun setView(@LayoutRes customViewLayout: Int): CustomBottomSheet<UIModel, Binding> {
        this.customViewLayout = customViewLayout
        return this
    }

    fun setSheetCreatedListener(sheetCreatedListener: SimpleBottomSheet.SheetCreatedListener): CustomBottomSheet<UIModel, Binding> {
        this.sheetCreatedListener = sheetCreatedListener
        return this
    }

    private fun setSheetHeight(layout: View) {

        val wm = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        resizeFragment(layout, ViewGroup.LayoutParams.MATCH_PARENT, ((0.5 * size.y).toInt()))
    }

    private fun resizeFragment(fragment: View?, newWidth: Int, newHeight: Int) {
        if (fragment != null) {
            val p = ConstraintLayout.LayoutParams(newWidth, newHeight)
            fragment.layoutParams = p
            fragment.requestLayout()


        }
    }

    fun setItemId(@LayoutRes customItemLayout: Int): CustomBottomSheet<UIModel, Binding> {
        this.customItemLayout = customItemLayout
        return this
    }

    fun provideBindingVariableId(variableId: Int) {
        this.variableId = variableId
    }

    private fun initRecycleView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.country_rv)
        adapter = SimpleBindingRecyclerAdapter(customItemLayout!!, variableId!!)
        adapter.setItems(items)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        adapter.setPositionListener { item, position ->
            itemCLickedListener?.invoke(item)
        }

        val dividerItemDecoration = DividerItemDecorator(ContextCompat.getDrawable(activity!!, R.drawable.custom_divider))
        recyclerView.addItemDecoration(dividerItemDecoration)

    }

    fun itemClickListener(listener: ((UIModel) -> (Unit))) {
        this.itemCLickedListener = listener
    }
}