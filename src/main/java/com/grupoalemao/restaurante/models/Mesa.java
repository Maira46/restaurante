package com.grupoalemao.restaurante.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod;

    @Column(nullable = false)
    private int capacidade;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequisicaoReserva> pessoas = new ArrayList<>();

    @Column(nullable = false)
    private boolean disponivel;

    @OneToOne(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pedido pedido;

    /**
     * Construtor da classe Mesa.
     * @param cod O código da mesa.
     * @param capacidade A capacidade máxima de pessoas que a mesa pode acomodar.
     * @param disponivel Boolean se está disponível ou não.
     * @param cliente Cliente em que esta alocado ou não.
     */
    public Mesa(int cod, int capacidade, boolean disponivel, Cliente cliente) {
        this.cod = cod;
        setCapacidade(capacidade);
        this.disponivel = disponivel;
        this.cliente = cliente;
    }

    public Mesa() {
        // Construtor vazio necessário para o JPA
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public int getCod() {
        return cod;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public String getNome() {
        return "Mesa " + cod;
    }

    public void setCapacidade(int capacidade) {
        if (capacidade == 4 || capacidade == 6 || capacidade == 8) {
            this.capacidade = capacidade;
        } else {
            throw new IllegalArgumentException("A capacidade da mesa deve ser 4, 6 ou 8 pessoas.");
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean mudarStatusMesa(Cliente cliente) {
        boolean estavaDisponivel = estaDisponivel(0);

        if (estavaDisponivel) {
            this.cliente = cliente;
            this.disponivel = false;
        } else {
            this.cliente = null;
            pessoas.clear();
            this.disponivel = true;
        }

        return estavaDisponivel;
    }

    public boolean estaDisponivel(int qtPessoas) {
        int totalPessoas = qtPessoas;
        for (RequisicaoReserva requisicao : pessoas) {
            totalPessoas += requisicao.getPessoas();
        }
        return totalPessoas <= capacidade && cliente == null && disponivel;
    }

    public void liberar() {
        this.disponivel = true;
        this.cliente = null;
        pessoas.clear();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
