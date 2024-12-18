package br.com.gerenciamento.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void testFindByStatusAtivo() {
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setNome("Bianca");
        alunoAtivo.setMatricula("12345");
        alunoAtivo.setCurso(Curso.CIENCIA_DA_COMPUTACAO);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setTurno(Turno.MATUTINO);
        alunoRepository.save(alunoAtivo);

        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo();
        assertFalse(alunosAtivos.isEmpty());

        // Verificando se o aluno ativo está na lista comparando atributos
        assertTrue(alunosAtivos.stream().anyMatch(a ->
            a.getNome().equals(alunoAtivo.getNome()) &&
            a.getMatricula().equals(alunoAtivo.getMatricula()) &&
            a.getStatus().equals(alunoAtivo.getStatus()) &&
            a.getTurno().equals(alunoAtivo.getTurno())
        ));
    }

    @Test
    public void testFindByStatusInativo() {
        Aluno alunoInativo = new Aluno();
        alunoInativo.setNome("Theo");
        alunoInativo.setMatricula("67890");
        alunoInativo.setCurso(Curso.VETERINARIA);
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setTurno(Turno.NOTURNO);
        alunoRepository.save(alunoInativo);

        List<Aluno> alunosInativos = alunoRepository.findByStatusInativo();
        assertFalse(alunosInativos.isEmpty());

        // Verificando se o aluno inativo está na lista comparando atributos
        assertTrue(alunosInativos.stream().anyMatch(a ->
            a.getNome().equals(alunoInativo.getNome()) &&
            a.getMatricula().equals(alunoInativo.getMatricula()) &&
            a.getStatus().equals(alunoInativo.getStatus()) &&
            a.getTurno().equals(alunoInativo.getTurno())
        ));
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setNome("Bianca");
        alunoAtivo.setMatricula("111111");
        alunoAtivo.setCurso(Curso.CIENCIA_DA_COMPUTACAO);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setTurno(Turno.MATUTINO);
        alunoRepository.save(alunoAtivo);

        List<Aluno> alunosEncontrados = alunoRepository.findByNomeContainingIgnoreCase("bianca");
        assertFalse(alunosEncontrados.isEmpty());
        assertTrue(alunosEncontrados.stream().anyMatch(a -> a.getNome().equalsIgnoreCase("Bianca")));
    }

    @Test
    public void testFindByNomeContainingIgnoreCase_NoResults() {
        List<Aluno> alunosEncontrados = alunoRepository.findByNomeContainingIgnoreCase("Carlos");
        assertTrue(alunosEncontrados.isEmpty());
    }
}
