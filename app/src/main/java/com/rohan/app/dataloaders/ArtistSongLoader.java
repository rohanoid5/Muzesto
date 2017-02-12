
package com.rohan.app.dataloaders;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.rohan.app.models.Song;
import com.rohan.app.utils.PreferencesUtility;

import java.util.ArrayList;

public class ArtistSongLoader {

    public static ArrayList<Song> getSongsForArtist(Context context, long artistID) {
        Cursor cursor = makeArtistSongCursor(context, artistID);
        ArrayList songsList = new ArrayList();
        if ((cursor != null) && (cursor.moveToFirst()))
            do {
                long id = cursor.getLong(0);
                String title = cursor.getString(1);
                String artist = cursor.getString(2);
                String album = cursor.getString(3);
                int duration = cursor.getInt(4);
                int trackNumber = cursor.getInt(5);
                long albumId = cursor.getInt(6);
                long artistId = artistID;

                songsList.add(new Song(id, albumId, artistID, title, artist, album, duration, trackNumber));
            }
            while (cursor.moveToNext());
        if (cursor != null)
            cursor.close();
        return songsList;
    }


    public static Cursor makeArtistSongCursor(Context context, long artistID) {
        ContentResolver contentResolver = context.getContentResolver();
        final String artistSongSortOrder = PreferencesUtility.getInstance(context).getArtistSongSortOrder();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String string = "is_music=1 AND title != '' AND artist_id=" + artistID;
        return contentResolver.query(uri, new String[]{"_id", "title", "artist", "album", "duration", "track", "album_id"}, string, null, artistSongSortOrder);
    }

}
