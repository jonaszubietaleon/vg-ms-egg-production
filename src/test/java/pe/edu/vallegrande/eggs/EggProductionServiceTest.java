package pe.edu.vallegrande.eggs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.vallegrande.eggs.model.EggProductionModel;
import pe.edu.vallegrande.eggs.repository.EggProductionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EggProductionServiceTest {

    private EggProductionRepository repository;
    private EggProductionService service;

    @BeforeEach
    void setUp() {
        repository = mock(EggProductionRepository.class);
        service = new EggProductionService(repository);
    }

    @Test
    @DisplayName("Debería retornar todos los registros ordenados por ID")
    void getAll() {
        EggProductionModel model = new EggProductionModel();
        model.setId(1);
        when(repository.findAllByOrderByIdAsc()).thenReturn(Flux.just(model));

        StepVerifier.create(service.getAll())
                .assertNext(result -> assertThat(result.getId()).isEqualTo(1))
                .verifyComplete();

        verify(repository).findAllByOrderByIdAsc();
    }

    @Test
    @DisplayName("Debería obtener registro por ID")
    void getById() {
        EggProductionModel model = new EggProductionModel();
        model.setId(1);
        when(repository.findById(1)).thenReturn(Mono.just(model));

        StepVerifier.create(service.getById(1))
                .assertNext(result -> assertThat(result.getId()).isEqualTo(1))
                .verifyComplete();

        verify(repository).findById(1);
    }

    @Test
    @DisplayName("Debería crear un nuevo registro")
    void create() {
        EggProductionModel input = new EggProductionModel();
        input.setId(999); // Se espera que se sobrescriba
        when(repository.save(any())).thenAnswer(inv -> {
            EggProductionModel saved = inv.getArgument(0);
            saved.setId(1);
            return Mono.just(saved);
        });

        StepVerifier.create(service.create(input))
                .assertNext(result -> {
                    assertThat(result.getId()).isEqualTo(1);
                })
                .verifyComplete();

        verify(repository).save(any());
    }

    @Test
    @DisplayName("Debería actualizar un registro existente")
    void update() {
        EggProductionModel existing = new EggProductionModel();
        existing.setId(1);
        existing.setEstado("A");

        when(repository.findById(1)).thenReturn(Mono.just(existing));
        when(repository.save(any())).thenReturn(Mono.just(existing));

        StepVerifier.create(service.update(1, existing))
                .assertNext(result -> assertThat(result.getId()).isEqualTo(1))
                .verifyComplete();

        verify(repository).findById(1);
        verify(repository).save(any());
    }

    @Test
    @DisplayName("Debería eliminar un registro por ID")
    void delete() {
        EggProductionModel model = new EggProductionModel();
        model.setId(1);
        when(repository.findById(1)).thenReturn(Mono.just(model));
        when(repository.deleteById(1)).thenReturn(Mono.empty());

        StepVerifier.create(service.delete(1))
                .verifyComplete();

        verify(repository).findById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("Debería inactivar un registro")
    void inactivate() {
        EggProductionModel model = new EggProductionModel();
        model.setId(1);
        model.setEstado("A");

        when(repository.findById(1)).thenReturn(Mono.just(model));
        when(repository.save(any())).thenReturn(Mono.just(model));

        StepVerifier.create(service.inactivate(1))
                .verifyComplete();

        verify(repository).findById(1);
        verify(repository).save(any());
    }

    @Test
    @DisplayName("Debería activar un registro")
    void activate() {
        EggProductionModel model = new EggProductionModel();
        model.setId(1);
        model.setEstado("I");

        when(repository.findById(1)).thenReturn(Mono.just(model));
        when(repository.save(any())).thenReturn(Mono.just(model));

        StepVerifier.create(service.activate(1))
                .verifyComplete();

        verify(repository).findById(1);
        verify(repository).save(any());
    }

    @Test
    @DisplayName("Debería obtener solo los registros activos")
    void getAllActive() {
        EggProductionModel model = new EggProductionModel();
        model.setId(1);
        model.setEstado("A");

        when(repository.findAllByEstadoOrderByIdAsc("A")).thenReturn(Flux.just(model));

        StepVerifier.create(service.getAllActive())
                .assertNext(result -> assertThat(result.getEstado()).isEqualTo("A"))
                .verifyComplete();

        verify(repository).findAllByEstadoOrderByIdAsc("A");
    }
}