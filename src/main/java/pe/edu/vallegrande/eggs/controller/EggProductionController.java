package pe.edu.vallegrande.eggs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.eggs.model.EggProductionModel;
import pe.edu.vallegrande.eggs.service.EggProductionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin("*")
@RestController
@RequestMapping("/egg-production")
@RequiredArgsConstructor
public class EggProductionController {

    private final EggProductionService service;

    //Obtener todos (A e I)
    @GetMapping
    public Flux<EggProductionModel> getAll() {
        return service.getAll();
    }

    //Obtener solo activos
    @GetMapping("/active")
    public Flux<EggProductionModel> getAllActive() {
        return service.getAllActive();
    }

    //Obtener por ID
    @GetMapping("/{id}")
    public Mono<EggProductionModel> getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    //Crear nuevo registro
    @PostMapping
    public Mono<EggProductionModel> create(@RequestBody EggProductionModel model) {
        return service.create(model);
    }

    //Actualizar registro
    @PutMapping("/{id}")
    public Mono<EggProductionModel> update(@PathVariable Integer id, @RequestBody EggProductionModel model) {
        return service.update(id, model);
    }

    //Eliminar físicamente (opcional, si quieres borrar de verdad)
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    //Inactivar
    @PutMapping("/inactivate/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> inactivate(@PathVariable Integer id) {
        return service.inactivate(id);
    }

    //Activar
    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> activate(@PathVariable Integer id) {
        return service.activate(id);
    }
}