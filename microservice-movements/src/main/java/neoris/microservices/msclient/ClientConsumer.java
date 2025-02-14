package neoris.microservices.msclient;

import neoris.microservices.msclient.domain.Client;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ClientConsumer implements ClientConsumerOutPort {

    private final WebClient webClient;

    public ClientConsumer(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Client> getById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Client.class);  // Puedes cambiar el tipo de respuesta
    }
}
