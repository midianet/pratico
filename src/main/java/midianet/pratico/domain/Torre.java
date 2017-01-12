package midianet.pratico.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "torre")
@Data
public class Torre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "torre_id")
    private Integer id;

    @NotNull
    @Size(min = 4 , max = 20)
    @Column(name = "torre_nome")
    private String nome;

    public Torre(){}

    public Torre(final Integer id , final String nome){
        this.id = id;
        this.nome = nome;
    }

}