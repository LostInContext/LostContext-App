package com.lostincontext.data.rulesV2

import com.lostincontext.data.playlist.Playlist


data class Rule(val conditions: List<Condition>,
                val playlist: Playlist,
                val key: String)