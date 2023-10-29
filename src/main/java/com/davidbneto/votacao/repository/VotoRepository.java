package com.davidbneto.votacao.repository;

import com.davidbneto.votacao.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    Optional<Voto> findByCpf(String cpf);
}
