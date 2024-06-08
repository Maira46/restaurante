package com.grupoalemao.restaurante.controllers;

import com.grupoalemao.restaurante.models.Cliente;
import com.grupoalemao.restaurante.models.RequisicaoReserva;
import com.grupoalemao.restaurante.repositories.ClienteRepository;
import com.grupoalemao.restaurante.repositories.RequisicaoReservaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class RequisicaoReservaController {

    @Autowired
    private RequisicaoReservaRepository requisicaoReservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Retorna a lista de todas as reservas.
     * @return uma lista de todas as reservas.
     */
    @GetMapping
    public List<RequisicaoReserva> getAllReservas() {
        return requisicaoReservaRepository.findAll();
    }

    /**
     * Retorna uma reserva específica com base no ID fornecido.
     * @param id o ID da reserva a ser retornada.
     * @return a reserva com o ID fornecido ou uma resposta de não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequisicaoReserva> getReservaById(@PathVariable Integer id) {
        return requisicaoReservaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria uma nova reserva.
     * @param requisicaoReserva o objeto RequisicaoReserva a ser criado.
     * @return a reserva criada ou uma mensagem de erro se o cliente não for encontrado.
     */
    @PostMapping
    public ResponseEntity<?> createReserva(@RequestBody RequisicaoReserva requisicaoReserva) {
        Integer clienteId = requisicaoReserva.getCliente().getId();
        if (clienteId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do cliente não pode ser nulo");
        }

        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);

        if (!clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }

        requisicaoReserva.setCliente(clienteOptional.get());
        RequisicaoReserva savedReserva = requisicaoReservaRepository.save(requisicaoReserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReserva);
    }

    /**
     * Atualiza uma reserva existente com base no ID fornecido.
     * @param id o ID da reserva a ser atualizada.
     * @param reservaDetails o objeto RequisicaoReserva com os detalhes atualizados.
     * @return a reserva atualizada ou uma resposta de não encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RequisicaoReserva> updateReserva(@PathVariable Integer id, @RequestBody RequisicaoReserva reservaDetails) {
        return requisicaoReservaRepository.findById(id)
                .map(reserva -> {
                    reserva.setDataReserva(reservaDetails.getDataReserva());
                    reserva.setAtiva(reservaDetails.isAtiva());
                    reserva.setPessoas(reservaDetails.getPessoas());
                    reserva.setCliente(reservaDetails.getCliente());
                    reserva.setMesa(reservaDetails.getMesa());
                    RequisicaoReserva updatedReserva = requisicaoReservaRepository.save(reserva);
                    return ResponseEntity.ok(updatedReserva);
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deleta uma reserva com base no ID fornecido.
     * @param id o ID da reserva a ser deletada.
     * @return uma resposta OK se a reserva for deletada ou uma resposta de não encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserva(@PathVariable Integer id) {
        return requisicaoReservaRepository.findById(id)
                .map(reserva -> {
                    requisicaoReservaRepository.delete(reserva);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
