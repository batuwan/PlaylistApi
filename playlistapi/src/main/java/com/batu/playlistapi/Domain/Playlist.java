package com.batu.playlistapi.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Playlist {

    /*
    - playlist içerisinde
      id (UUID generate edilebilir örnek projedeki gibi)
      name,
      description,
      followersCount(int)
      tracks (list of track olacak)
      trackCount(int),
      userId
     */
    private String id;
    private String name;
    private String description;
    private int followersCount;
    private List<Track> tracks;
    @NonNull
    private String userId;


    public Playlist(@NonNull String userId) {
        this.id = UUID.randomUUID().toString();
        this.name = "New Playlist";
        this.description = "Description";
        this.followersCount = 0;
        this.tracks = new ArrayList<Track>();
        this.userId = userId;
    }

    //Empty list, with no followers.
    public Playlist(String name, String description, String userId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.followersCount = 0;
        this.tracks = new ArrayList<Track>();
        this.userId = userId;
    }

    //AllArgsConstructor
    public Playlist(String name, String description, int followersCount, List<Track> tracks, String userId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.followersCount = followersCount;
        this.tracks = tracks;
        this.userId = userId;
    }
}
