package com.grupoalemao.restaurante.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Esta classe tem a responsabilidade de armazenar os dados pessoais de um
 * cliente do restaurante.
 */
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private static int cod = 0;
    private String nome;
    private String telefone;

    /**
     * Construtor padrão sem parâmetros necessário para o JPA e alguns frameworks de teste.
     */
    public Cliente() {
    }

    /**
     * Construtor da classe Cliente, que faz a validação se os dados passados não
     * são nulos. Caso não sejam, gera um código inteiro para o cliente.
     * @param n   Representa o nome do cliente, cujo tamanho deve ser maior que 3
     *            caracteres
     * @param tel Representa o telefone do cliente, cujo tamanho deve ser maior ou igual a
     *            8 caracteres
     */
    public Cliente(String n, String tel) {
        if (n != null && tel != null) {
            setNome(n);
            setTelefone(tel);
            Cliente.cod = Cliente.cod + 1;
        }
    }

    /**
     * Método que retorna o ID do Cliente.
     * 
     * @return Um número inteiro que é o ID do Cliente
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método que atribui um valor recebido à variável Nome, se este tiver mais que
     * 3 caracteres.
     * @param nome Representa o nome do cliente
     */
    public void setNome(String nome) {
        if (nome.length() > 3) {
            this.nome = nome;
        }
    }

    /**
     * Método que atribui um valor recebido à variável Telefone, se este tiver 8 ou
     * mais caracteres.
     * @param telefone Representa o telefone do cliente
     */
    public void setTelefone(String telefone) {
        if (telefone.length() >= 8) {
            this.telefone = telefone;
        }
    }

    /**
     * Método que retorna o código do Cliente.
     * 
     * @return Um número inteiro que é o código do Cliente
     */
    public int getCod() {
        return cod;
    }

    /**
     * Método que retorna o nome do Cliente.
     * 
     * @return Uma string que é o nome do Cliente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método que retorna o telefone do Cliente.
     * 
     * @return Uma string que é o telefone do Cliente
     */
    public String getTelefone() {
        return telefone;
    }
}
