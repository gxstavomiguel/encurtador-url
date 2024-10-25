package repository;

import com.desafio.url.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository <Url, Long> {

    Optional<Url> findByShortUrl(String shortUrl);

}
