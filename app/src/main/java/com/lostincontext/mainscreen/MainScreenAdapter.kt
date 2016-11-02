package com.lostincontext.mainscreen

import android.support.annotation.IdRes
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lostincontext.R
import com.lostincontext.commons.list.EmptyListCallback
import com.lostincontext.commons.list.StatefulAdapter
import com.lostincontext.commons.list.StatefulAdapter.ContentState.CONTENT
import com.lostincontext.commons.list.StatefulAdapter.ContentState.LOADING
import com.lostincontext.commons.list.ViewHolder
import com.lostincontext.data.rule.Rule


class MainScreenAdapter(private val emptyListCallback: EmptyListCallback,
                        private val callback: MainScreenViewHolder.Callback)
    : StatefulAdapter(LOADING) {

    private var rules: List<Rule> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, @IdRes viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            R.id.view_type_standard -> return MainScreenViewHolder.create(layoutInflater,
                                                                          parent,
                                                                          callback)

            R.id.view_type_loading -> return buildLoadingViewHolder(layoutInflater,
                                                                    parent)

            R.id.view_type_error -> return buildErrorViewHolder(layoutInflater,
                                                                parent,
                                                                emptyListCallback)

            R.id.view_type_empty -> return buildEmptyViewHolder(layoutInflater,
                                                                parent)

            else -> throw IllegalStateException("the adapter is in an invalid state")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        when (holder.itemViewType) {
            R.id.view_type_standard -> {
                val viewHolder = holder as MainScreenViewHolder
                viewHolder.bindTo(rules[position])
            }

            R.id.view_type_loading,
            R.id.view_type_error, //todo
            R.id.view_type_empty -> {
            }

            else -> throw IllegalStateException("the adapter is in an invalid state")
        }

    }

    override fun getContentItemsCount(): Int = rules.size

    fun setRules(rules: List<Rule>) {
        this.rules = rules
        if (rules.isEmpty())
            setCurrentState(StatefulAdapter.ContentState.EMPTY)
        else
            setCurrentState(StatefulAdapter.ContentState.CONTENT, true)
    }

    /*
        fun setRules(newRules: List<Rule>) {

        if (newRules.isEmpty()) {
            rules = newRules
            setCurrentState(StatefulAdapter.ContentState.EMPTY)
            return
        }

        currentState = CONTENT

        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int,
                                         newItemPosition: Int) = rules[oldItemPosition].key == newRules[newItemPosition].key


            override fun getOldListSize() = rules.size

            override fun getNewListSize() = newRules.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = true

        })

        this.rules = newRules
        diff.dispatchUpdatesTo(this)
    }
     */

}
