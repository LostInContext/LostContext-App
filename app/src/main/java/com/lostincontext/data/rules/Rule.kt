package com.lostincontext.data.rules


import com.lostincontext.data.playlist.Playlist

class Rule {

    var name: String? = null
    var fenceVM: FenceVM? = null
    var playlist: Playlist? = null

    constructor() {
    }

    constructor(name: String, fenceVM: FenceVM, playlist: Playlist) {
        this.name = name
        this.fenceVM = fenceVM
        this.playlist = playlist
    }
}
