package de.kanoune.photoappapiusers.model.feignClient;

import de.kanoune.photoappapiusers.model.rest.response.AlbumResponse;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;


@FeignClient(name="albums-ws", fallbackFactory = AlbumsFallbackFactory.class)
//@FeignClient(name="albums-ws")
public interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albums")
    List<AlbumResponse> getAlbums(@PathVariable String id);
}

@Component
class AlbumsFallbackFactory implements FallbackFactory<AlbumsServiceClient> {


    @Override
    public AlbumsServiceClient create(Throwable cause) {
        return new AlbumsServiceClientFallback(cause);
    }
}

@Slf4j
class AlbumsServiceClientFallback implements AlbumsServiceClient {

    private final Throwable cause;

    AlbumsServiceClientFallback(Throwable throwable) {
        this.cause = throwable;
    }

    @Override
    public List<AlbumResponse> getAlbums(String id) {

        if(cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("404 error took place when getAlbums was called with userId: "
            + id + ". Error message: " + cause.getLocalizedMessage());
        } else {
            log.error("Other error took place: " + cause.getLocalizedMessage());
        }
        return new ArrayList<>();
    }
}



