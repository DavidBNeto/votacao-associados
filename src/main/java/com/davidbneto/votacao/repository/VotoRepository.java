package com.davidbneto.votacao.repository;

import com.davidbneto.votacao.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    Optional<Voto> findByCpf(String cpf);
    @Query("SELECT v FROM Voto v WHERE v.pauta = ?1 AND v.hora BETWEEN ?2 AND ?3")
    List<Voto> findByPautaIdWhereHoraCorreta(Long pautaId, LocalDateTime horaInicio, LocalDateTime horaFim);
}
