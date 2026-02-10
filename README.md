# Gerenciador de Produtos

Sistema de gerenciamento de produtos e matérias-primas desenvolvido com Spring Boot.

## 🗄️ Banco de Dados

Este projeto utiliza **PostgreSQL 18** como banco de dados relacional.

## 📦 Dependências

### Framework e Core
- **Spring Boot 4.0.2** - Framework principal
- **Java 21** - Versão do Java
- **Maven** - Gerenciador de dependências

### Passo 1: Criar o arquivo .env

Copie o arquivo .env.example para .env e substitua as variáveis:

- **DATABASE_URL**: URL de conexão com o PostgreSQL
  - `localhost:5432` - Host e porta do PostgreSQL (padrão: 5432)
  - `gerenciador_produtos` - Nome do banco de dados criado
  
- **DATABASE_USERNAME**: Usuário do PostgreSQL

- **DATABASE_PASSWORD**: Senha definida durante a instalação do PostgreSQL

- **ALLOWED_ORIGINS**: URLs permitidas para CORS (separadas por vírgula)
  - Adicione aqui os endereços do seu frontend



## Migrações de Banco de Dados

Migrações disponíveis:
- `V1__Create_part_and_product_tables.sql` - Criação das tabelas iniciais
- `V2__Create_product_material_table_migration_to_new_table.sql` - Migração para nova estrutura

## Endpoints da API

A API fornece endpoints para gerenciar:
- Produtos (`/products`)
- Matérias-primas (`/raw-materials`)

