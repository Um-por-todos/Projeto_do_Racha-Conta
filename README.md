# 🏠 Racha-Conta — Despesas de República

Sistema desenvolvido para gerenciamento e divisão de despesas entre moradores de repúblicas, apartamentos e casas compartilhadas.

O objetivo do projeto é facilitar o controle financeiro coletivo, automatizando cálculos de divisão de contas e oferecendo transparência entre os moradores.

# 📌 Funcionalidades
 * ✅ Cadastro de moradores
 * ✅ Registro de despesas
 * ✅ Divisão automática de contas
 * ✅ Controle de quem pagou
 * ✅ Relatório de balanço financeiro
 * ✅ Controle de dívidas quitadas

# 👥 Perfis do Sistema
>🧑 Morador

Pode:

 * Visualizar despesas;
 * Consultar dívidas;
 * Acompanhar pagamentos;
 * Visualizar balanço financeiro.

>🛠️ Administrador da Casa

Pode:

 * Cadastrar moradores;
 * Registrar despesas;
 * Gerenciar pagamentos;
 * Visualizar relatórios gerais.

# ⚙️ Regra de Negócio

Cada despesa cadastrada é dividida igualmente entre todos os moradores da casa.

Fórmula utilizada:
Valor Individual = Valor Total / Quantidade de Moradores

```bash
Exemplo:
Conta de água: R$ 200,00
Moradores: 5

Cada morador deve:
R$ 40,00
```
# 🧩 Requisitos Funcionais
 * Moradores
 * Cadastro de moradores;
 * Listagem de moradores;
 * Remoção de moradores.
 * Despesas
 * Cadastro de despesas;
 * Informar:
 * valor;
 * tipo;
 * descrição;
 * quem realizou o pagamento;
 * Divisão automática da despesa.
 * Dívidas
 * Geração automática de débitos;
 * Relatório de “quem deve para quem”;
 * Marcação de dívida como paga.
# 🗂️ Estrutura do Projeto
src/
 * ├── ..../
 * ├── ..../
 * ├── ..../
 * ├── ..../
 * ├── ..../
 * ├── ..../
 * └── ..../
 
# 🚀 Tecnologias Utilizadas
 * Backend
....
 * Banco de Dados
....
 * Versionamento
 1. Git
 2. GitHub

# ▶️ Como Executar o Projeto
....

# 📊 Exemplo de Fluxo
1. Administrador cadastra moradores;
2. Uma despesa é registrada;
3. O sistema divide automaticamente o valor;
4. O balanço é atualizado;
5. Moradores podem quitar dívidas.

# 🧪 Possíveis Melhorias Futuras
Integração com PIX;
Dashboard financeiro;
Histórico mensal;
Login e autenticação;
Notificações de cobrança;
Aplicativo mobile.

# 🤝 Desenvolvedores

Projeto desenvolvido por:

Victor
Renan

# 📄 Licença

Este projeto está sob a licença MIT.
