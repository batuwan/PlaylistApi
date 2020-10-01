package com.batu.playlistapi.Repository;


import com.batu.playlistapi.Domain.Playlist;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.couchbase.client.java.query.QueryOptions.queryOptions;

/*
Endpointler:

  -[x] playlist create endpoint (userId  alarak playlist kaydetsin)

  -[x] playlist findById endpoint (playlistId alarak playlist dönsün)

  -[x] playlist findAllByUserId endpoint (verilen userId’ye ait tüm playlistleri dönsün )

  -[x] playlist delete endpoint  (playlistId üzerinden playlisti silsin)

  -[x] add track to playlist endpoint  (playlistId üzerinden playliste track eklesin)

  -[x] remove track to playlist endpoint  (playlistId üzerinden playlistten track cıkartsın)

 */


@Repository
public class PlaylistRepository {
    private final Cluster couchbaseCluster;
    private final Collection playlistCollection;

    public PlaylistRepository(Cluster couchbaseCluster, Collection playlistCollection) {
        this.couchbaseCluster = couchbaseCluster;
        this.playlistCollection = playlistCollection;
    }

    public Playlist create(Playlist playlist){
        playlistCollection.insert(playlist.getId(), playlist);
        return playlist;
    }

    public Optional<Playlist> findById(String id) {

        try {
            GetResult getResult = playlistCollection.get(id);
            Playlist playlist = getResult.contentAs(Playlist.class);
            return Optional.of(playlist);

        } catch (DocumentNotFoundException exception) {
            return Optional.empty();
        }

    }
    public List<Playlist> findAllByUserId(String userId) {
        String statement = "Select * from playlist where userId = $userId";
        QueryResult query = couchbaseCluster.query(statement , queryOptions().parameters(JsonArray.from(userId)));
        return query.rowsAs(Playlist.class);
    }
    public void delete(String id){
        playlistCollection.remove(id);
    }
    public void update(String id, Playlist playlist){
        playlistCollection.replace(id, playlist);
    }
}
