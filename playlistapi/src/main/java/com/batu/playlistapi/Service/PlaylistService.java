package com.batu.playlistapi.Service;


import com.batu.playlistapi.Domain.Playlist;
import com.batu.playlistapi.Repository.PlaylistRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final ObjectMapper objectMapper;

    public PlaylistService(PlaylistRepository playlistRepository, ObjectMapper objectMapper) {
        this.playlistRepository = playlistRepository;
        this.objectMapper = objectMapper;
    }

    public Playlist create(Playlist playlist){

        playlist.setId(UUID.randomUUID().toString());
        return playlistRepository.create(playlist);
    }

    public Optional<Playlist> findById(String id){
        return playlistRepository.findById(id);
    }

    public List<Playlist> findAllByUserId(String userId){
        return playlistRepository.findAllByUserId(userId);
    }

    public void delete(String id){
        playlistRepository.delete(id);
    }

    public Playlist update(String id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if(playlist.isEmpty()) throw new RuntimeException();

        Playlist playlistPatched = applyPatch(patch, playlist.get());

        playlistRepository.update(playlistPatched.getId(), playlistPatched);

        return playlistPatched;
    }
    private Playlist applyPatch(JsonPatch patch, Playlist target) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(target, JsonNode.class));
        return objectMapper.treeToValue(patched, Playlist.class);
    }
}
