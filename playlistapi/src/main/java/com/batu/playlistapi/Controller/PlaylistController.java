package com.batu.playlistapi.Controller;


import com.batu.playlistapi.Domain.Playlist;
import com.batu.playlistapi.Service.PlaylistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;


    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<Playlist> create(@RequestBody Playlist playlist){
        Playlist newPlaylist = playlistService.create(playlist);
        return ResponseEntity.created(URI.create(newPlaylist.getId())).build();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Optional<Playlist>> findById(@PathVariable ("id") String id) {
        Optional<Playlist> playlist = playlistService.findById(id);
        return ResponseEntity.ok().body(playlist);
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> findAllByUserId(@RequestParam String userId) {
        List<Playlist> playlists = playlistService.findAllByUserId(userId);
        return ResponseEntity.ok().body(playlists);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable ("id") String id) {
        playlistService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping (path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Playlist> updateItem(@PathVariable ("id") String playlistId, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {

        Playlist playlist = null;
        try {
            playlist = playlistService.update(playlistId, patch);
        } catch (JsonPatchException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(playlist);

    }

}
