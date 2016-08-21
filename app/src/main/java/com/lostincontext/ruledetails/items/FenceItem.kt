package com.lostincontext.ruledetails.items


import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.lostincontext.R
import com.lostincontext.data.GridBottomSheetItem
import com.lostincontext.data.rules.FenceVM
import com.lostincontext.data.rules.NotFenceVM
import com.lostincontext.ruledetails.items.FenceItem.Link.*

class FenceItem {

    val fenceVM: FenceVM

    val name: String
    @DrawableRes val drawableRes: Int
    var link: Link


    enum class Link constructor(@StringRes val resourceId: Int) {
        AND(R.string.and),
        OR(R.string.or),
        AND_NOT(R.string.and_not),
        OR_NOT(R.string.or_not),
        WHEN(R.string.`when`)
    }


    constructor(fenceVM: FenceVM,
                name: String,
                drawableRes: Int,
                isFirstItem: Boolean) {
        this.fenceVM = fenceVM
        this.name = name
        this.drawableRes = drawableRes
        if (isFirstItem) link = WHEN
        else link = AND
    }

    /**
     * clones an object but replaces its fenceVM & link attributes
     */
    constructor(item: FenceItem,
                fenceVM: FenceVM,
                link: Link) {
        this.fenceVM = fenceVM
        this.name = item.name
        this.drawableRes = item.drawableRes
        this.link = link

    }

    fun isWhen() = link == WHEN

    companion object {

        /**
         * If the item link is not of the 'NOT' variety, this just returns the item,
         * Otherwise, it returns a new item without the not but with a wrapping NotFenceVM
         */
        fun wrapNot(item: FenceItem): FenceItem {
            when (item.link) {
                AND, OR, WHEN -> return item

                FenceItem.Link.AND_NOT -> return FenceItem(item,
                                                           NotFenceVM(item.fenceVM),
                                                           AND)
                FenceItem.Link.OR_NOT -> return FenceItem(item,
                                                          NotFenceVM(item.fenceVM),
                                                          OR)
            }
        }

        fun createFromPick(pick: GridBottomSheetItem,
                           fenceVM: FenceVM,
                           isFirstItem: Boolean): FenceItem {
            return FenceItem(fenceVM,
                             pick.name,
                             pick.drawableRes,
                             isFirstItem)
        }
    }

}
