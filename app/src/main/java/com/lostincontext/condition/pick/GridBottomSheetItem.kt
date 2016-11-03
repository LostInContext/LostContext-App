package com.lostincontext.condition.pick


import android.databinding.ObservableBoolean
import android.support.annotation.DrawableRes
import com.lostincontext.condition.ConditionPresenter


data class GridBottomSheetItem(val name: String,
                               @DrawableRes val drawableRes: Int,
                               val picker: ConditionPresenter.Picker,
                               var isPicked: ObservableBoolean = ObservableBoolean(false))
