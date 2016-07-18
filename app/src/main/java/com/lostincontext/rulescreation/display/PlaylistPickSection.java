package com.lostincontext.rulescreation.display;


import android.support.v7.widget.RecyclerView;

import com.lostincontext.R;
import com.lostincontext.commons.list.Section;
import com.lostincontext.data.PlaylistPicker;

import java.util.List;

public class PlaylistPickSection extends Section<PlaylistPicker> {


    public PlaylistPickSection(String title,
                               List<PlaylistPicker> fenceCreators) {
        super(title, fenceCreators, R.id.view_type_playlist_picker);
    }

    @Override protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PlaylistPickViewHolder) holder).setContent(get(position).getPlaylist());

    }
}
