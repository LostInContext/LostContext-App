package com.lostincontext.mainscreen

import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.ViewGroup

import com.lostincontext.R
import com.lostincontext.commons.list.EmptyListCallback
import com.lostincontext.commons.list.StatefulAdapter
import com.lostincontext.commons.list.StatefulAdapter.ContentState.LOADING
import com.lostincontext.commons.list.ViewHolder
import com.lostincontext.data.rules.Rule


class MainScreenAdapter(private val emptyListCallback: EmptyListCallback) : StatefulAdapter(LOADING) {

    private var rules: List<Rule> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, @IdRes viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            R.id.view_type_standard -> return MainScreenViewHolder.create(layoutInflater, parent)

            R.id.view_type_loading -> return StatefulAdapter.buildLoadingViewHolder(layoutInflater,
                                                                                    parent)

            R.id.view_type_error -> return StatefulAdapter.buildErrorViewHolder(layoutInflater,
                                                                                parent,
                                                                                emptyListCallback)

            R.id.view_type_empty -> return StatefulAdapter.buildEmptyViewHolder(layoutInflater,
                                                                                parent)

            else -> throw IllegalStateException("the adapter is in an invalid state")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
        if (rules.size == 0)
            currentState = StatefulAdapter.ContentState.EMPTY
        else
            setCurrentState(StatefulAdapter.ContentState.CONTENT, true)
    }

}
