package com.davidbneto.votacao.service;

import com.davidbneto.votacao.request.VotoRequest;
import org.springframework.stereotype.Service;

@Service
public interface VotoService {

    void votar(VotoRequest votoRequest);


}
