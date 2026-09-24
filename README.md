Sistema de Gerenciamento de Assinaturas (SaaS)
Este projeto visa o desenvolvimento de um sistema web full-stack (Angular + Java/Spring Boot) voltado para o controle financeiro e gerenciamento de assinaturas recorrentes. O objetivo é atuar como uma aproximação de uma plataforma SaaS (Software as a Service) real, garantindo alta segurança e precisão no tráfego de dados sensíveis.

Funcionalidades (Requisitos do Projeto)
O sistema foi construído para contemplar requisitos essenciais de uma plataforma de faturamento recorrente:


Autenticação e Segurança (Stateless):

Sistema completo de gestão de contas permitindo login, logout e criação de novos usuários.

Fluxo seguro de recuperação e alteração de senhas.

Proteção de rotas e controle de acesso baseado em roles (perfis de usuário) utilizando Spring Security e tokens JWT.


Painel do Cliente (Dashboard):

Área dedicada exibindo as assinaturas em posse do usuário e seus respectivos status (Ativo, Pendente, Cancelado).

Visualização clara das datas de renovação e próximo ciclo de faturamento.


Controle Financeiro e Faturamento:

Processamento e validação precisa de dados financeiros através de Bean Validation, garantindo integridade antes da persistência no banco.

Registro detalhado de cada ciclo de cobrança e faturamento recorrente.


Extrato e Histórico de Pagamentos:

Histórico cronológico detalhado de todos os pagamentos realizados pelo usuário.

Geração de recibos/faturas simuladas após cada renovação de ciclo.

🛠️ Tecnologias Utilizadas:

Frontend: Angular (TypeScript, HTML5, CSS3)

Backend: Java com Spring Boot e Spring Security

Banco de Dados: PostgreSQL (Relacional) com mapeamento ORM via Hibernate/JPA

Arquitetura & Qualidade: API RESTful orientada a objetos, JWT (JSON Web Token) e princípios de Clean Code.

📡 Endpoints da API (Estrutura REST):

O frontend consome a API RESTful construída no Spring Boot. Abaixo estão as rotas principais utilizadas para a comunicação cliente-servidor:

Autenticação: /api/auth/login, /api/auth/register, /api/auth/recover-password

Planos: /api/planos (GET para listagem)

Assinaturas: /api/assinaturas (POST para nova assinatura, PUT para upgrade/downgrade, DELETE para cancelamento)

Faturas/Pagamentos: /api/faturas/usuario/{id} (GET para histórico)

⚙️ Como executar o projeto localmente:

1. Clone o repositório:

Bash
git clone https://github.com/njbr13/gerenciamento-assinaturas

2. Configuração do Banco de Dados:

Certifique-se de ter o PostgreSQL instalado e rodando na porta 5432.

Crie um banco de dados chamado assinaturas_db.

As tabelas serão geradas automaticamente pelo Hibernate ao iniciar o backend.

3. Configuração do Backend (Java/Spring Boot):

Bash
cd backend 
mvn clean install 
mvn spring-boot:run

4. Configuração do Frontend (Angular):

Bash
cd frontend 
npm install 
ng serve
