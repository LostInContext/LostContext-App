package com.lostincontext.data.rulesV2

import com.google.android.gms.awareness.fence.AwarenessFence
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rules.FenceBuilder
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
data class Rule(val conditions: List<Condition>,
                val playlist: Playlist,
                val key: String) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(Rule::class.java)
    }


    fun buildAwarenessFence(builder: FenceBuilder): AwarenessFence? {
        if (!validateFence()) return null

        val fences = conditions.map { it.buildAwarenessFence(builder) }
        return AwarenessFence.or(fences)
    }

    fun validateFence(): Boolean {
        if (conditions.isEmpty()) return false
        conditions.forEach { if (!it.validate()) return false }
        return true
    }

}