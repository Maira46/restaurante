package com.grupoalemao.restaurante.models;

import javax.persistence.*;

/**
 * Esta classe tem a responsabilidade de armazenar os dados relacionados
 * aos produtos ofertados pelo restaurante.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "produtos")
public abstract class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private double preco;

    /**
     * Construtor da classe Produto.
     *
     * @param nome  Representa o nome do produto.
     * @param preco Representa o preço do produto.
     */
    public Produto(String nome, double preco) {
        if (nome != null && nome.length() > 3) {
            this.nome = nome;
        } else {
            throw new IllegalArgumentException("Nome deve ter mais de 3 caracteres");
        }
        if (preco >= 0) {
            this.preco = preco;
        } else {
            throw new IllegalArgumentException("Preço deve ser maior ou igual a zero");
        }
    }

    /**
     * Construtor padrão sem parâmetros necessário para o JPA.
     */
    public Produto() {
    }

    /**
     * Método que retorna o nome do produto.
     *
     * @return Uma string que é o nome do produto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método que retorna o preço do produto.
     *
     * @return Um número real que é o preço do produto.
     */
    public double getPreco() {
        return preco;
    }

    // Getters and setters for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
