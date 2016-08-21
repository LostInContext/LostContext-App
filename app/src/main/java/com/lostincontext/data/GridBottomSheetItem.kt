package com.lostincontext.data


import android.support.annotation.DrawableRes

import com.lostincontext.ruledetails.RuleDetailsPresenter

data class GridBottomSheetItem(val name: String,
                               @DrawableRes val drawableRes: Int,
                               val picker: RuleDetailsPresenter.Picker)
