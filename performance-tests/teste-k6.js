import http from 'k6/http';
import { check } from 'k6';

const BASE_URL = 'http://localhost:8080/api/v1';

export const options = {
  vus: 50,
  iterations: 1000,
};

export function setup() {
  const params = { headers: { 'Content-Type': 'application/json' } };

  const pauta = http.post(`${BASE_URL}/pautas`, JSON.stringify({
    titulo: 'Pauta teste k6',
    descricao: 'Criada para teste de performance',
  }), params);
  const pautaId = pauta.json('id');

  const sessao = http.post(`${BASE_URL}/sessoes`, JSON.stringify({
    pautaId: pautaId,
    duracaoEmMinutos: 999,
  }), params);
  const sessaoId = sessao.json('id');

  console.log(`sessaoId criado: ${sessaoId}`);
  return { sessaoId };
}

export default function (data) {
  const payload = JSON.stringify({
    sessaoId: data.sessaoId,
    associadoId: `associado-${__VU}-${__ITER}`,
    voto: __ITER % 2 === 0 ? 'SIM' : 'NAO',
  });
  const params = { headers: { 'Content-Type': 'application/json' } };

  const res = http.post(`${BASE_URL}/votos`, payload, params);
  check(res, { 'status é 201': (r) => r.status === 201 });
}