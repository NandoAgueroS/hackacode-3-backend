# Hackacode 3 - Backend

Backend del proyecto ganador de la Hackathon **"Hackacode"** de TodoCode (3ra edición).

## 🏆 Certificado

![Certificado Hackacode](docs/certificado.jpg)

[Ver presentacion - Grupo N° 7 - Init Coding](https://youtu.be/Nr6f0MuI_rM?t=736)

---

## Tabla de Contenidos

- [Descripcion](#descripcion)
- [Tecnologias](#tecnologias)
- [Requisitos](#requisitos)
- [Instalacion](#instalacion)
- [Configuracion](#configuracion)
- [Arquitectura](#arquitectura)
- [Seguridad](#seguridad)
- [Modelos de Datos](#modelos-de-datos)
- [Endpoints API](#endpoints-api)
- [Documentacion API](#documentacion-api)
- [Despliegue](#despliegue)

## Descripcion

Sistema de gestion para clinica medica que permite administrar pacientes, medicos, consultas, servicios medicos y pagos. Construido con arquitectura RESTful siguiendo el patron Controller-Service-Repository.

## Tecnologias

| Tecnologia | Version | Uso |
|------------|---------|-----|
| Java | 21 | Lenguaje principal |
| Spring Boot | 3.4.2 | Framework principal |
| Spring Security | 6.x | Autenticacion y autorizacion |
| Spring Data JPA | 3.x | Acceso a datos |
| PostgreSQL | 15+ | Base de datos |
| Maven | 3.9+ | Gestion de dependencias |
| MapStruct | 1.6.3 | Mapeo objeto-objeto |
| Lombok | 1.18.x | Reduccion de boilerplate |
| JWT | 4.5.0 | Autenticacion stateless |
| Docker | Latest | Containerizacion |
| Swagger/OpenAPI | 2.8.4 | Documentacion API |

## Requisitos

- JDK 21
- Maven 3.9+ (o usar el wrapper incluido)
- PostgreSQL 15+ (o Docker)
- Docker y Docker Compose (opcional)

## Instalacion

### Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd hackacode-3-backend
```

### Ejecutar usando Docker

```bash
docker-compose up -d
```

Esto iniciara tanto la aplicacion como PostgreSQL.

## Configuracion

### Variables de Entorno

| Variable | Descripcion |
|----------|-------------|
| `SPRING_DATASOURCE_URL` | URL de conexion a PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | Usuario de base de datos |
| `SPRING_DATASOURCE_PASSWORD` | Contrasena de base de datos |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | Estrategia de esquema |
| `JWT_PRIVATE_KEY` | Clave secreta para JWT (min 256 bits) |
| `JWT_USER_GENERATOR` | Generador de usuarios JWT |
| `JWT_EXPIRATION` | Tiempo de expiracion del token (ms) |
| `CORS_ALLOWED_ORIGINS` | Orígenes CORS permitidos |
| `SERVER_PORT` | Puerto del servidor |

## Arquitectura

```
src/main/java/com/init_coding/hackacode_3_backend/
├── config/           # Configuraciones (Security, CORS, Filtros JWT)
├── controller/        # Controladores REST (7 controladores)
├── dto/              # Data Transfer Objects
│   ├── request/     # DTOs de entrada
│   └── response/    # DTOs de salida
├── exception/        # Manejo de excepciones custom
├── mapper/           # Mapeadores MapStruct
├── model/            # Entidades JPA
├── repository/       # Repositorios JPA
├── service/          # Logica de negocio
│   └── impl/         # Implementaciones de servicio
└── util/             # Utilidades (JWT Utils)
```

### Patrón de Arquitectura

```
Cliente -> Controller -> Service -> Repository -> Base de Datos
                |
                v
              DTO (Request/Response)
                |
                v
             Mapper (MapStruct)
```

## Seguridad

### Autenticacion JWT

El sistema utiliza JSON Web Tokens para autenticacion stateless.

**Headers requeridos:**
```
Authorization: Bearer <token_jwt>
```

### Roles y Permisos

| Rol | Descripcion | Permisos |
|-----|-------------|----------|
| `ADMIN` | Administrador del sistema | Acceso total |
| `DIRECTOR` | Director de la clinica | CRUD completo + reportes de ganancias |
| `RECEPCIONISTA` | Personal de recepcion | CRUD de pacientes, medicos, consultas, servicios |

### Endpoints Publicos

- `/swagger-ui/**` - Documentacion Swagger

## Modelos de Datos

### Entidades Principales

```
Persona (Abstracta)
├── Paciente          - Datos personales y si tiene obra social
└── Medico            - Sueldo, especialidad y disponibilidad
Consulta              - Citas medicas (paciente, medico, servicio, fecha, estado)
Especialidad          - Especialidades medicas
Disponibilidad        - Horarios de medicos
ServicioMedico (Abstracta)
├── ServicioIndividual - Servicios individuales con precio
└── PaqueteServicios   - Conjuntos de servicios
Pago                  - Pagos de consultas
DetallePago           - Estados de pago y vencimientos
User                  - Usuarios del sistema
Role                  - Roles (ADMIN, DIRECTOR, RECEPCIONISTA)
Permission            - Permisos individuales
```

### Eliminacion Logica

Todas las entidades soportan eliminacion logica mediante el campo `activo`, permitiendo reactivacion posterior.

## Endpoints API

### Autenticacion

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | `/api/auth/sign-up` | Registrar usuario |
| POST | `/api/auth/log-in` | Iniciar sesion |
| GET | `/api/auth/verificar-token` | Verificar token JWT |

### Pacientes

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/api/pacientes` | Listar pacientes |
| GET | `/api/pacientes/{id}` | Obtener por ID |
| GET | `/api/pacientes/dni/{dni}` | Obtener por DNI |
| POST | `/api/pacientes` | Crear paciente |
| PUT | `/api/pacientes/{id}` | Actualizar |
| DELETE | `/api/pacientes/{id}` | Desactivar |
| PATCH | `/api/pacientes/{id}/reactivar` | Reactivar |

### Medicos

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/api/medicos` | Listar medicos |
| GET | `/api/medicos/{id}` | Obtener por ID |
| GET | `/api/medicos/turnos-disponibles` | Turnos disponibles |
| POST | `/api/medicos` | Crear medico |
| PUT | `/api/medicos/{id}` | Actualizar |
| DELETE | `/api/medicos/{id}` | Desactivar |

### Especialidades

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/api/especialidades` | Listar |
| POST | `/api/especialidades` | Crear |
| PUT | `/api/especialidades/{id}` | Actualizar |
| DELETE | `/api/especialidades/{id}` | Desactivar |

### Consultas

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/api/consultas` | Listar |
| GET | `/api/consultas/cantidad` | Cantidad por mes |
| POST | `/api/consultas` | Crear consulta |
| PUT | `/api/consultas/{id}` | Actualizar |
| DELETE | `/api/consultas/{id}` | Desactivar |

### Servicios

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/api/servicios/individuales` | Listar servicios |
| POST | `/api/servicios/individuales` | Crear |
| GET | `/api/servicios/paquetes` | Listar paquetes |
| POST | `/api/servicios/paquetes` | Crear paquete |

### Pagos

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/api/pagos/ganancias` | Ganancias totales |
| GET | `/api/pagos/ganancias/servicio/{codigo}` | Ganancias por servicio |

## Documentacion API

Swagger UI disponible en: `http://localhost:8080/swagger-ui.html`

## Despliegue

Establecer las variables de entorno necesarias y ejecutar:

```bash
docker-compose up -d
```

Esto iniciara la aplicacion y PostgreSQL.
