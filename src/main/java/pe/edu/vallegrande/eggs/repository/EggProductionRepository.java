package pe.edu.vallegrande.eggs.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.eggs.model.EggProductionModel;
import reactor.core.publisher.Flux;

@Repository
public interface EggProductionRepository extends ReactiveCrudRepository<EggProductionModel, Integer> {
    Flux<EggProductionModel> findAllByOrderByIdAsc();
    Flux<EggProductionModel> findAllByEstadoOrderByIdAsc(String estado);

}
