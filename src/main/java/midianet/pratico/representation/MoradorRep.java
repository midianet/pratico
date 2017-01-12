package midianet.pratico.representation;

import lombok.Data;
import midianet.pratico.domain.Torre;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MoradorRep {

    private Long id;

    @NotNull
    @Size(min = 5, max = 80)
    private String nome;

    @NotNull
    @Size(min = 11, max = 11)
    private String cpf;

    @NotNull
    private String telegram;

    @NotNull
    private Integer apto;

    @NotNull
    private TorreRep torre;

    private Boolean ativo;

}