# ⚡ Energy Management System

Sistema de gerenciamento de energia elétrica baseado em **Spring Modulith** com arquitetura de monólito modular.

## 🏗️ Arquitetura

```
br.com.energymng
├── user          → Gerenciamento de usuários
├── station       → Estações de carregamento
├── charge        → Sessões de carregamento
├── payment       → Pagamentos
├── notification  → Notificações
├── telemetry     → Telemetria em tempo real
├── ocppgateway   → Gateway OCPP (protocolo de comunicação)
└── ocppproxy     → Proxy de conexões OCPP
```

## 🔧 Tecnologias

| Tecnologia        | Versão  | Uso                          |
|-------------------|---------|------------------------------|
| Java              | 21      | Linguagem base                |
| Spring Boot       | 3.3.0   | Framework principal           |
| Spring Modulith   | 1.2.0   | Arquitetura modular           |
| PostgreSQL        | latest  | Banco de dados               |
| Flyway            | -       | Migrations de banco          |
| Lombok            | -       | Redução de boilerplate       |
| Testcontainers    | -       | Testes de integração         |

## 🚀 Como executar

### Pré-requisitos
- Java 21+
- Maven 3.9+
- Docker (para PostgreSQL)

### Banco de dados com Docker
```bash
docker run -d \
  --name energymng-db \
  -e POSTGRES_DB=energymng \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:16
```

### Executar a aplicação
```bash
mvn spring-boot:run
```

### Variáveis de ambiente
| Variável   | Padrão      | Descrição          |
|------------|-------------|---------------------|
| DB_HOST    | localhost   | Host do PostgreSQL  |
| DB_PORT    | 5432        | Porta do PostgreSQL |
| DB_NAME    | energymng   | Nome do banco       |
| DB_USER    | postgres    | Usuário             |
| DB_PASS    | postgres    | Senha               |

## 🧪 Testes

```bash
# Todos os testes
mvn test

# Verificação da estrutura modular
mvn test -Dtest=EnergyManagementSystemApplicationTests
```

## 📡 Endpoints

| Módulo       | Base Path              |
|-------------|------------------------|
| Users        | `/api/users`           |
| Stations     | `/api/stations`        |
| Charging     | `/api/charging`        |
| Payments     | `/api/payments`        |
| Notifications| `/api/notifications`   |
| Telemetry    | `/api/telemetry`       |
| OCPP Gateway | `/api/ocpp/gateway`    |
| OCPP Proxy   | `/api/ocpp/proxy`      |

## 📐 Protocolo OCPP

O sistema suporta o protocolo **OCPP 1.6** para comunicação com estações de carregamento:
- `BootNotification` — Registro da estação
- `Heartbeat` — Sinal de vida
- `StartTransaction` / `StopTransaction` — Controle de sessões
- `MeterValues` — Leituras de medidores
- `StatusNotification` — Notificações de estado
