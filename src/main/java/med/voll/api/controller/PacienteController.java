package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadatrar(@RequestBody @Valid DadosCadastroPaciente infos){
        repository.save(new Paciente(infos));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualiza(@RequestBody @Valid DadosAtualizaPaciente infos){
        var paciente = repository.getReferenceById(infos.id());
        paciente.atualizarInformacoes(infos);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void desativa(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.inativa();
    }
}
