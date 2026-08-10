# Racha-Conta — P1 corrigida

Implementação baseada no código original, no Diagrama de Classes oficial e no roteiro da P1.

## Modelo implementado
- Usuario (superclasse)
- Morador
- AdministradorCasa
- Casa
- Despesa
- Divida
- Pagamento
- RelatorioFinanceiro

## Arquitetura
Controller → Service → Repository em memória.

## Regras principais
- Valor Individual = Valor Total / Quantidade de Moradores.
- Despesa gera automaticamente as dívidas dos demais moradores ativos.
- Pagamento confirmado quita a dívida correspondente.
- Relatório apresenta saldo pendente e "quem deve para quem".

## Rotas principais
- `/home`
- `/dashboard`
- `/casa`
- `/moradores`
- `/administradores`
- `/despesas`
- `/dividas`
- `/pagamentos`
- `/relatorio`
- `/api/v1`

## Observação de teste
O Maven não pôde ser executado neste ambiente porque o Maven Wrapper depende de download externo e o acesso ao repositório Maven não estava disponível. Execute localmente `mvnw.cmd test` no Windows ou `./mvnw test` no Linux/macOS.
