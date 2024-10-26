package url.services;


import url.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import url.repository.UrlRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

        public String shortenUrl(String originaUrl){

            String shortUrl = generateShortUrl();
            Url url = new Url();
            url.setOriginalUrl(originaUrl);
            url.setShortUrl(shortUrl);
            url.setExpirationDate(LocalDateTime.now().plusDays(30));
            urlRepository.save(url);
            return shortUrl;

        }

        public Optional<Url> getOriginalUrl(String shortUrl){
            Optional<Url> urOptional = urlRepository.findByShortUrl(shortUrl);
            if (urOptional.isPresent()){
                Url url = urOptional.get();
                if (url.getExpirationDate().isAfter(LocalDateTime.now())){
                    return Optional.of(url);
                }
                else {
                    urlRepository.delete(url);
                }
            }
            return Optional.empty();
        }


        private String generateShortUrl() {
            String characteres = "ABCDEFGHIJKLMNOPQRSTUVYWXZabcdefghijklmnopqrstuvywxz";
            StringBuilder shortUrl = new StringBuilder();
            Random random = new Random();
            int length = 5 + random.nextInt(6);
            for (int i =0; i < length; i++){
                shortUrl.append(characteres.charAt(random.nextInt(characteres.length())));
            }
            return shortUrl.toString();
        }

    }


