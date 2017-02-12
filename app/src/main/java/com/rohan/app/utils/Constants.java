/*
 * Copyright (C) 2015 Naman Dwivedi
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package com.rohan.app.utils;

import android.support.v4.content.LocalBroadcastManager;

public class Constants {

    public static final String NAVIGATE_LIBRARY = "navigate_library";
    public static final String NAVIGATE_PLAYLIST = "navigate_playlist";
    public static final String NAVIGATE_QUEUE = "navigate_queue";
    public static final String NAVIGATE_ALBUM = "navigate_album";
    public static final String NAVIGATE_ARTIST = "navigate_artist";
    public static final String NAVIGATE_NOWPLAYING = "navigate_nowplaying";

    public static final String NAVIGATE_PLAYLIST_RECENT = "navigate_playlist_recent";
    public static final String NAVIGATE_PLAYLIST_LASTADDED = "navigate_playlist_lastadded";
    public static final String NAVIGATE_PLAYLIST_TOPTRACKS = "navigate_playlist_toptracks";
    public static final String NAVIGATE_PLAYLIST_USERCREATED = "navigate_playlist";
    public static final String PLAYLIST_FOREGROUND_COLOR = "foreground_color";
    public static final String PLAYLIST_NAME = "playlist_name";

    public static final String ALBUM_ID = "album_id";
    public static final String ARTIST_ID = "artist_id";
    public static final String PLAYLIST_ID = "playlist_id";

    public static final String FRAGMENT_ID = "fragment_id";
    public static final String NOWPLAYING_FRAGMENT_ID = "nowplaying_fragment_id";

    public static final String WITH_ANIMATIONS = "with_animations";

    public static final String TIMBER1 = "timber1";
    public static final String TIMBER2 = "timber2";
    public static final String TIMBER3 = "timber3";
    public static final String TIMBER4 = "timber4";
    public static final String TIMBER5 = "timber5";

    public static final String NAVIGATE_SETTINGS = "navigate_settings";
    public static final String NAVIGATE_SEARCH = "navigate_search";

    public static final String SETTINGS_STYLE_SELECTOR_NOWPLAYING = "style_selector_nowplaying";
    public static final String SETTINGS_STYLE_SELECTOR_ARTIST = "style_selector_artist";
    public static final String SETTINGS_STYLE_SELECTOR_ALBUM = "style_selector_album";
    public static final String SETTINGS_STYLE_SELECTOR_WHAT = "style_selector_what";

    public static final String SETTINGS_STYLE_SELECTOR = "settings_style_selector";

    //CHANGES
    //Broadcast elements.
    private LocalBroadcastManager mLocalBroadcastManager;
    public static final String UPDATE_UI_BROADCAST = "com.jams.music.player.NEW_SONG_UPDATE_UI";

    //Update UI broadcast flags.
    public static final String SHOW_AUDIOBOOK_TOAST = "AudiobookToast";
    public static final String UPDATE_SEEKBAR_DURATION = "UpdateSeekbarDuration";
    public static final String UPDATE_PAGER_POSTIION = "UpdatePagerPosition";
    public static final String UPDATE_PLAYBACK_CONTROLS = "UpdatePlabackControls";
    public static final String SERVICE_STOPPING = "ServiceStopping";
    public static final String SHOW_STREAMING_BAR = "ShowStreamingBar";
    public static final String HIDE_STREAMING_BAR = "HideStreamingBar";
    public static final String UPDATE_BUFFERING_PROGRESS = "UpdateBufferingProgress";
    public static final String INIT_PAGER = "InitPager";
    public static final String NEW_QUEUE_ORDER = "NewQueueOrder";
    public static final String UPDATE_EQ_FRAGMENT = "UpdateEQFragment";

    //Contants for identifying each fragment/activity.
    public static final String FRAGMENT_IDS = "FragmentId";
    public static final int ARTISTS_FRAGMENT = 0;
    public static final int ALBUM_ARTISTS_FRAGMENT = 1;
    public static final int ALBUMS_FRAGMENT = 2;
    public static final int SONGS_FRAGMENT = 3;
    public static final int PLAYLISTS_FRAGMENT = 4;
    public static final int GENRES_FRAGMENT = 5;
    public static final int FOLDERS_FRAGMENT = 6;
    public static final int ARTISTS_FLIPPED_FRAGMENT = 7;
    public static final int ARTISTS_FLIPPED_SONGS_FRAGMENT = 8;
    public static final int ALBUM_ARTISTS_FLIPPED_FRAGMENT = 9;
    public static final int ALBUM_ARTISTS_FLIPPED_SONGS_FRAGMENT = 10;
    public static final int ALBUMS_FLIPPED_FRAGMENT = 11;
    public static final int GENRES_FLIPPED_FRAGMENT = 12;
    public static final int GENRES_FLIPPED_SONGS_FRAGMENT = 13;

    //Constants for identifying playback routes.
    public static final int PLAY_ALL_SONGS = 0;
    public static final int PLAY_ALL_BY_ARTIST = 1;
    public static final int PLAY_ALL_BY_ALBUM_ARTIST = 2;
    public static final int PLAY_ALL_BY_ALBUM = 3;
    public static final int PLAY_ALL_IN_PLAYLIST = 4;
    public static final int PLAY_ALL_IN_GENRE = 5;
    public static final int PLAY_ALL_IN_FOLDER = 6;

    //Device orientation constants.
    public static final int ORIENTATION_PORTRAIT = 0;
    public static final int ORIENTATION_LANDSCAPE = 1;

    //Device screen size/orientation identifiers.
    public static final String REGULAR = "regular";
    public static final String SMALL_TABLET = "small_tablet";
    public static final String LARGE_TABLET = "large_tablet";
    public static final String XLARGE_TABLET = "xlarge_tablet";
    public static final int REGULAR_SCREEN_PORTRAIT = 0;
    public static final int REGULAR_SCREEN_LANDSCAPE = 1;
    public static final int SMALL_TABLET_PORTRAIT = 2;
    public static final int SMALL_TABLET_LANDSCAPE = 3;
    public static final int LARGE_TABLET_PORTRAIT = 4;
    public static final int LARGE_TABLET_LANDSCAPE = 5;
    public static final int XLARGE_TABLET_PORTRAIT = 6;
    public static final int XLARGE_TABLET_LANDSCAPE = 7;

    //Miscellaneous flags/identifiers.
    public static final String SONG_ID = "SongId";
    public static final String SONG_TITLE = "SongTitle";
    public static final String SONG_ALBUM = "SongAlbum";
    public static final String SONG_ARTIST = "SongArtist";
    public static final String ALBUM_ART = "AlbumArt";
    public static final String CURRENT_THEME = "CurrentTheme";
    public static final int DARK_THEME = 0;
    public static final int LIGHT_THEME = 1;

    //SharedPreferences keys.
    public static final String CROSSFADE_ENABLED = "CrossfadeEnabled";
    public static final String CROSSFADE_DURATION = "CrossfadeDuration";
    public static final String REPEAT_MODE = "RepeatMode";
    public static final String MUSIC_PLAYING = "MusicPlaying";
    public static final String SERVICE_RUNNING = "ServiceRunning";
    public static final String CURRENT_LIBRARY = "CurrentLibrary";
    public static final String CURRENT_LIBRARY_POSITION = "CurrentLibraryPosition";
    public static final String SHUFFLE_ON = "ShuffleOn";
    public static final String FIRST_RUN = "FirstRun";
    public static final String STARTUP_BROWSER = "StartupBrowser";
    public static final String SHOW_LOCKSCREEN_CONTROLS = "ShowLockscreenControls";
    public static final String ARTISTS_LAYOUT = "ArtistsLayout";
    public static final String ALBUM_ARTISTS_LAYOUT = "AlbumArtistsLayout";
    public static final String ALBUMS_LAYOUT = "AlbumsLayout";
    public static final String PLAYLISTS_LAYOUT = "PlaylistsLayout";
    public static final String GENRES_LAYOUT = "GenresLayout";
    public static final String FOLDERS_LAYOUT = "FoldersLayout";

    //Repeat mode constants.
    public static final int REPEAT_OFF = 0;
    public static final int REPEAT_PLAYLIST = 1;
    public static final int REPEAT_SONG = 2;
    public static final int A_B_REPEAT = 3;
}
