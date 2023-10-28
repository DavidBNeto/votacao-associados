package com.davidbneto.votacao.repository;

import com.davidbneto.votacao.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta, Long> {

    int countById(Long id);

}
