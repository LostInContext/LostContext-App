package com.lostincontext.data.playlist

import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
data class Author(val id: Long, val name: String) {
    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(Author::class.java)
    }
}