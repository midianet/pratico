package midianet.pratico.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "morador")
@Data
public class Morador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "morador_id")
    private Long id;

    @NotNull
    @Size(min = 5, max = 80)
    @Column(name = "morador_nome", length = 80 , nullable = false)
    private String nome;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(name = "morador_cpf", length = 11, nullable = false)
    private String cpf;

    @NotNull
    @Column(name = "morador_telegram")
    private String telegram;

    @NotNull
    @Column(name = "morador_apto")
    private Integer apto;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "torre_id")
    private Torre torre;

    @Column(name = "morador_ativo")
    private Boolean ativo;

}