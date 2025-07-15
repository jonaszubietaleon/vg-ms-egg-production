package pe.edu.vallegrande.eggs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.edu.vallegrande.eggs.model.EggProductionModel;
import pe.edu.vallegrande.eggs.service.EggProductionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EggProductionControllerTest {

    private WebTestClient webTestClient;
    private EggProductionService service;

    @BeforeEach
    void setup() {
        service = mock(EggProductionService.class);
        EggProductionController controller = new EggProductionController(service);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    @DisplayName("GET /egg-production - debería retornar todos los registros")
    void getAll() {
        EggProductionModel model = new EggProductionModel();
        model.setId(1);
        model.setEstado("A");

        when(service.getAll()).thenReturn(Flux.just(model));

        webTestClient.get().uri("/egg-production")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].estado").isEqualTo("A");

        verify(service).getAll();
    }

    @Test
    @DisplayName("GET /egg-production/active - debería retornar solo registros activos")
    void getAllActive() {
        EggProductionModel model = new EggProductionModel();
        model.setId(2);
        model.setEstado("A");

        when(service.getAllActive()).thenReturn(Flux.just(model));

        webTestClient.get().uri("/egg-production/active")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].estado").isEqualTo("A");

        verify(service).getAllActive();
    }

    @Test
    @DisplayName("GET /egg-production/{id} - debería retornar un registro por ID")
    void getById() {
        EggProductionModel model = new EggProductionModel();
        model.setId(5);

        when(service.getById(5)).thenReturn(Mono.just(model));

        webTestClient.get().uri("/egg-production/5")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(5);

        verify(service).getById(5);
    }

    @Test
    @DisplayName("POST /egg-production - debería crear un nuevo registro")
    void create() {
        EggProductionModel model = new EggProductionModel();
        model.setId(3);
        model.setEstado("A");

        when(service.create(any())).thenReturn(Mono.just(model));

        webTestClient.post().uri("/egg-production")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(model)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(3);

        verify(service).create(any());
    }

    @Test
    @DisplayName("PUT /egg-production/{id} - debería actualizar un registro existente")
    void update() {
        EggProductionModel model = new EggProductionModel();
        model.setId(4);

        when(service.update(eq(4), any())).thenReturn(Mono.just(model));

        webTestClient.put().uri("/egg-production/4")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(model)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(4);

        verify(service).update(eq(4), any());
    }

    @Test
    @DisplayName("DELETE /egg-production/{id} - debería eliminar un registro")
    void delete() {
        when(service.delete(10)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/egg-production/10")
                .exchange()
                .expectStatus().isOk();

        verify(service).delete(10);
    }

    @Test
    @DisplayName("PUT /egg-production/activate/{id} - debería activar un registro")
    void activate() {
        when(service.activate(7)).thenReturn(Mono.empty());

        webTestClient.put().uri("/egg-production/activate/7")
                .exchange()
                .expectStatus().isNoContent();

        verify(service).activate(7);
    }

    @Test
    @DisplayName("PUT /egg-production/inactivate/{id} - debería inactivar un registro")
    void inactivate() {
        when(service.inactivate(8)).thenReturn(Mono.empty());

        webTestClient.put().uri("/egg-production/inactivate/8")
                .exchange()
                .expectStatus().isNoContent();

        verify(service).inactivate(8);
    }
}
