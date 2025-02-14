package neoris.application.usecase;

import neoris.application.ports.out.ClientOutPort;
import neoris.domain.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClientUseCaseTest {

    @Mock
    private ClientOutPort clientOutPort;

    @InjectMocks
    private ClientUseCase clientUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveClient_OkTest() {
        String identifier = "0106258956";
        Client client = new Client(1L, identifier, "Test Name", "Test Address", "123456789", "M", "1990-01-01", "123456", true);

        when(clientOutPort.save(any(Client.class))).thenReturn(Mono.just(client));
        when(clientUseCase.save(client)).thenReturn(Mono.just(client));

        // Act
        Client clientSaved = clientUseCase.save(client).block();

        // Assert
        assertNotNull(clientSaved);
        assertEquals(identifier, clientSaved.getIdentifier());
        verify(clientOutPort).save(client);
    }

    @Test
    void save_ShouldReturnError_WhenRepositoryFails() {
        // Arrange
        Client client = new Client(1L, "0106258956", "Test Name", "Test Address", "123456789", "M", "1990-01-01", "123456", true);
        when(clientOutPort.save(any(Client.class))).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(clientUseCase.save(client))
                .expectErrorMessage("Database Error")
                .verify();

        verify(clientOutPort).save(client);
    }

    @Test
    void update_ShouldReturnUpdatedClient_WhenSuccess() {
        // Arrange
        Client client = new Client(1L, "0106258956", "Test Name", "Test Address", "123456789", "M", "1990-01-01", "123456", true);
        when(clientOutPort.update(client, 1L)).thenReturn(Mono.just(client));

        // Act & Assert
        StepVerifier.create(clientUseCase.update(client, 1L))
                .expectNext(client)
                .verifyComplete();

        verify(clientOutPort).update(client, 1L);
    }

    @Test
    void update_ShouldReturnError_WhenClientNotFound() {
        // Arrange
        Client client = new Client(1L, "0106258956", "Test Name", "Test Address", "123456789", "M", "1990-01-01", "123456", true);
        when(clientOutPort.update(client, 1L)).thenReturn(Mono.error(new RuntimeException("Client not found")));

        // Act & Assert
        StepVerifier.create(clientUseCase.update(client, 1L))
                .expectErrorMessage("Client not found")
                .verify();

        verify(clientOutPort).update(client, 1L);
    }

    @Test
    void delete_ShouldReturnTrue_WhenSuccess() {
        // Arrange
        when(clientOutPort.delete(1L)).thenReturn(Mono.just(true));

        // Act & Assert
        StepVerifier.create(clientUseCase.delete(1L))
                .expectNext(true)
                .verifyComplete();

        verify(clientOutPort).delete(1L);
    }

    @Test
    void delete_ShouldReturnError_WhenClientDoesNotExist() {
        // Arrange
        when(clientOutPort.delete(1L)).thenReturn(Mono.error(new RuntimeException("Client not found")));

        // Act & Assert
        StepVerifier.create(clientUseCase.delete(1L))
                .expectErrorMessage("Client not found")
                .verify();

        verify(clientOutPort).delete(1L);
    }

    @Test
    void findAll_ShouldReturnClientList_WhenSuccess() {
        // Arrange
        Client client = new Client(1L, "0106258956", "Test Name", "Test Address", "123456789", "M", "1990-01-01", "123456", true);
        List<Client> clients = List.of(client);
        when(clientOutPort.findAll()).thenReturn(Mono.just(clients));

        // Act & Assert
        StepVerifier.create(clientUseCase.findAll())
                .expectNext(clients)
                .verifyComplete();

        verify(clientOutPort).findAll();
    }

    @Test
    void findAll_ShouldReturnError_WhenDatabaseFails() {
        // Arrange
        when(clientOutPort.findAll()).thenReturn(Mono.error(new RuntimeException("Database Error")));

        // Act & Assert
        StepVerifier.create(clientUseCase.findAll())
                .expectErrorMessage("Database Error")
                .verify();

        verify(clientOutPort).findAll();
    }

    @Test
    void getById_ShouldReturnClient_WhenSuccess() {
        // Arrange
        Client client = new Client(1L, "0106258956", "Test Name", "Test Address", "123456789", "M", "1990-01-01", "123456", true);
        when(clientOutPort.getById(1L)).thenReturn(Mono.just(client));

        // Act & Assert
        StepVerifier.create(clientUseCase.getById(1L))
                .expectNext(client)
                .verifyComplete();

        verify(clientOutPort).getById(1L);
    }

    @Test
    void getById_ShouldReturnError_WhenClientNotFound() {
        // Arrange
        when(clientOutPort.getById(1L)).thenReturn(Mono.error(new RuntimeException("Client not found")));

        // Act & Assert
        StepVerifier.create(clientUseCase.getById(1L))
                .expectErrorMessage("Client not found")
                .verify();

        verify(clientOutPort).getById(1L);
    }
}
