package midianet.pratico.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ambiente")
@Data
public class Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name =  "ambiente_id")
    private Integer id;

    @NotNull
    @Size(min = 4 , max = 20)
    @Column(name = "ambiente_nome")
    private String nome;

    public Ambiente(){}

    public Ambiente(final Integer id , final String nome){
        this.id = id;
        this.nome = nome;
    }

}