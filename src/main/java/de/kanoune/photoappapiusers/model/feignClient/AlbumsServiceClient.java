package de.kanoune.photoappapiusers.model.feignClient;

import de.kanoune.photoappapiusers.model.rest.response.AlbumResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="albums-ws")
public interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albumss")
    List<AlbumResponse> getAlbums(@PathVariable String id);


}
