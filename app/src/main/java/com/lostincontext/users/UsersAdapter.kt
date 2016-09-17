package com.lostincontext.users

import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lostincontext.R
import com.lostincontext.commons.list.EmptyListCallback
import com.lostincontext.commons.list.StatefulAdapter
import com.lostincontext.commons.list.StatefulAdapter.ContentState.EMPTY
import com.lostincontext.commons.list.ViewHolder
import com.lostincontext.data.user.User


class UsersAdapter(private val itemCallback: UserViewHolder.Callback,
                   private val emptyListCallback: EmptyListCallback) : StatefulAdapter(EMPTY) {


    init {
        setHasStableIds(true)
    }

    private var users: List<User> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, @IdRes viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            R.id.view_type_standard -> return UserViewHolder.create(layoutInflater,
                                                                    parent,
                                                                    itemCallback)

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
                val viewHolder = holder as UserViewHolder
                viewHolder.bindTo(users[position])
            }

            R.id.view_type_load_more,
            R.id.view_type_loading,
            R.id.view_type_error, //todo
            R.id.view_type_empty -> {
            }

            else -> throw IllegalStateException("the adapter is in an invalid state")
        }

    }

    override fun getContentItemsCount(): Int = users.size

    fun setUsers(users: List<User>) {
        this.users = users
        if (users.size == 0) setCurrentState(ContentState.EMPTY)
        else setCurrentState(ContentState.CONTENT, true)
    }
}