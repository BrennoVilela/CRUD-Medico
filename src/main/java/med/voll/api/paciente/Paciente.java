package med.voll.api.paciente;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.endereco.Endereco;

@Table(name = "pacientes")
@Entity(name = "paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    @Embedded
    private Endereco endereco;
    private boolean ativo;

    public Paciente(DadosCadastroPaciente infos) {
        this.ativo = true;
        this.nome = infos.nome();
        this.email = infos.email();
        this.telefone = infos.telefone();
        this.endereco = new Endereco(infos.endereco());
    }

    public void atualizarInformacoes(DadosAtualizaPaciente infos) {
        if (infos.nome() != null) {
            this.nome = infos.nome();
        }
        if (infos.telefone() != null) {
            this.telefone = infos.telefone();
        }
        if (infos.endereco() != null) {
            this.endereco.atualizaInformacoes(infos.endereco());
        }
    }

    public void inativa() {
        this.ativo = false;
    }
}
