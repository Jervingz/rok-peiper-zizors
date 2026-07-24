# 🎮 Rok, Peiper, Zizors

![Versión](https://img.shields.io/badge/versión-1.0.0--MVP-blue)
![Estado](https://img.shields.io/badge/estado-Prototipo%20funcional-green)
![Plataforma](https://img.shields.io/badge/plataforma-Android-orange)

## 📱 Descripción del problema que resuelve

¿Te ha pasado que estás esperando el bus, haciendo una fila o en un descanso corto y no sabes qué hacer para matar el aburrimiento?

**Rok, Peiper, Zizors** nace para resolver esos momentos de tiempo muerto donde necesitas una actividad rápida, simple y sin compromiso. Esta aplicación ofrece partidas de piedra, papel o tijera contra la máquina en menos de 20 segundos, sin necesidad de conexión y con una curva de aprendizaje nula.

## 🎯 Objetivo de la aplicación

Permitir que un usuario complete una partida completa (selección, resultado y reinicio) con una interfaz táctil clara.

## 📖 Historias de usuario del MVP

| ID | Título | Descripción |
|----|--------|-------------|
| HU-01 | Jugar partida rápida | Como jugador casual, quiero seleccionar Rok, Peiper o Zizors con un toque |
| HU-02 | Ver resultado | Como jugador casual, quiero ver quién gana después de elegir mi jugada |
| HU-03 | Jugar nuevamente | Como jugador casual, quiero reiniciar la partida con un botón |
| HU-04 | Ver estadísticas | Como usuario competitivo, quiero ver un contador de victorias |
| HU-05 | Retroalimentación visual | Como jugador casual, quiero ver animaciones al elegir una jugada |

## 🛠 Tecnología utilizada

- Android Studio  
- Kotlin  
- Room (Base de datos local SQLite)  
- ViewModel  
- LiveData  
- RecyclerView  
- WorkManager (Notificaciones)  
- OpenGL ES  
- SharedPreferences  

---

## 🗃️ Base de datos (ROOM)

La aplicación usa **Room** como base de datos local para gestionar usuarios.

### Componentes:

- **Entity:** Usuario  
- **DAO:** UserDao  
- **Database:** AppDatabase  

### Operaciones implementadas:

- Insertar usuarios  
- Listar usuarios  
- Actualizar usuarios  
- Eliminar usuarios  
- Login con consulta a base de datos  

### Flujo de datos:

```text
UI (Activities)
   ↓
ViewModel
   ↓
DAO (Room)
   ↓
SQLite Database
