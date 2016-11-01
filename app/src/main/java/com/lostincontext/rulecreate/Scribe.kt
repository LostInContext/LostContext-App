package com.lostincontext.rulecreate

import android.content.Context
import android.content.res.Resources
import android.text.TextUtils
import com.lostincontext.R
import com.lostincontext.data.rules.FenceNamer
import com.lostincontext.data.rulesV2.AtomicCondition
import com.lostincontext.data.rulesV2.Condition

class Scribe(val context: Context) {

    val resources: Resources = context.resources
    val theme: Resources.Theme = context.theme

    fun describeCondition(condition: Condition): CharSequence {
        val description = resources.getString(R.string.when_x)
        return TextUtils.expandTemplate(description,
                                        describeAtomicConditions(condition.atomics))

    }

    fun describeAtomicConditions(conditions: List<AtomicCondition>): CharSequence {
        val namer = FenceNamer(context)

        val template = resources.getString(R.string.x_and_y)

        when (conditions.size) {
            0 -> return ""
            1 -> return conditions[0].fence.name(namer)
            else ->
                return conditions.fold("" as CharSequence) { s, atomic ->
                    if (s.isEmpty()) atomic.fence.name(namer)
                    else TextUtils.expandTemplate(template,
                                                  s,
                                                  atomic.fence.name(namer))
                }
        }


    }

}