package pe.edu.vallegrande.eggs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.eggs.model.EggProductionModel;
import pe.edu.vallegrande.eggs.repository.EggProductionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EggProductionService {

    private final EggProductionRepository repository;

    public Flux<EggProductionModel> getAll() {
        return repository.findAllByOrderByIdAsc();
    }

    public Mono<EggProductionModel> getById(Integer id) {
        return repository.findById(id);
    }

    public Mono<EggProductionModel> create(EggProductionModel model) {
        model.setId(null); // Nos aseguramos de que sea nuevo
        return repository.save(model);
    }

    public Mono<EggProductionModel> update(Integer id, EggProductionModel model) {
        return repository.findById(id)
                .flatMap(existing -> {
                    model.setId(id); // Aseguramos que el ID no cambie
                    return repository.save(model);
                });
    }

    public Mono<Void> delete(Integer id) {
        return repository.findById(id)
                .flatMap(existing -> repository.deleteById(id));
    }

    public Mono<Void> inactivate(Integer id) {
        return repository.findById(id)
                .flatMap(egg -> {
                    egg.setEstado("I");
                    return repository.save(egg);
                })
                .then();
    }

    public Mono<Void> activate(Integer id) {
        return repository.findById(id)
                .flatMap(egg -> {
                    egg.setEstado("A");
                    return repository.save(egg);
                })
                .then();
    }
    public Flux<EggProductionModel> getAllActive() {
    return repository.findAllByEstadoOrderByIdAsc("A");
}

}
