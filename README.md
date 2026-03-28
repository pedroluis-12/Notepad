# 📝 Notepad App - Android Study Project

[**Português**](#-notepad-app---projeto-de-estudo-android) | [**English**](#-notepad-app---android-study-project-1)

---

## 🇧🇷 Notepad App - Projeto de Estudo Android

Este é um projeto de demonstração desenvolvido para fins de estudo, focado na implementação de boas práticas de desenvolvimento Android moderno. O objetivo principal é explorar conceitos de **Arquitetura Clean**, **Cache Local**, **Injeção de Dependência** e **Paralelismo**.

### 🚀 Tecnologias e Conceitos
- **Arquitetura Clean (MVVM)**: Separação clara de responsabilidades entre as camadas de apresentação, domínio e dados.
- **Single Activity Pattern**: O projeto utiliza uma única Activity (`MainActivity`) que gerencia a navegação entre múltiplos Fragments via **Jetpack Navigation Component**.
- **Injeção de Dependência (Koin)**: Gerenciamento eficiente de dependências para facilitar a testabilidade e o desacoplamento.
- **Cache Local (DataStore)**: Persistência de dados assíncrona e segura, substituindo o antigo SharedPreferences.
- **Paralelismo e Reatividade**: Uso intensivo de **Kotlin Coroutines** para operações assíncronas e **StateFlow** para emissão de estados de UI reativos.
- **Testes Unitários e Instrumentados**: Cobertura de lógica de negócio e fluxos de UI utilizando **MockK**, **Turbine** e **Espresso**.

### 📂 Descrição das Classes Principais

#### 🏛️ Camada de Apresentação (Presentation)
- **`MainActivity`**: Activity única que serve como host para o gráfico de navegação do aplicativo.
- **`ListFragment` / `DetailFragment`**: Responsáveis por renderizar a UI e observar as mudanças de estado emitidas pelas ViewModels usando `repeatOnLifecycle`.
- **`ListViewModel` / `DetailViewModel`**: Gerenciam o estado da tela, processam eventos da UI e interagem com os UseCases. Utilizam `StateFlow` para expor dados.
- **`ListNoteAdapter`**: Gerencia a exibição da lista de notas no RecyclerView.

#### 🎯 Camada de Domínio (Domain)
- **`ListUseCase` / `DetailUseCase`**: Contêm as regras de negócio da aplicação (ex: validação de campos vazios antes de salvar).
- **`NotepadModel`**: Entidade de dados que representa uma nota no sistema.

#### 💾 Camada de Dados (Data)
- **`ListRepository` / `DetailRepository`**: Interfaces que definem como os dados devem ser acessados, permitindo que a camada de domínio ignore a origem dos dados.
- **`PreferencesData`**: Implementação do **DataStore**, lidando com a persistência real dos dados em disco de forma assíncrona.

#### ⚙️ Infraestrutura e DI
- **`AppModule`**: Configurações de injeção de dependência do Koin.
- **`NotepadApplication`**: Ponto de entrada do app onde o Koin é inicializado.

---

## 🇺🇸 Notepad App - Android Study Project

This is a demonstration project developed for study purposes, focusing on the implementation of modern Android development best practices. The main goal is to explore concepts of **Clean Architecture**, **Local Caching**, **Dependency Injection**, and **Parallelism**.

### 🚀 Technologies and Concepts
- **Clean Architecture (MVVM)**: Clear separation of concerns between presentation, domain, and data layers.
- **Single Activity Pattern**: The project uses a single Activity (`MainActivity`) that manages navigation between multiple Fragments via the **Jetpack Navigation Component**.
- **Dependency Injection (Koin)**: Efficient dependency management to facilitate testability and decoupling.
- **Local Cache (DataStore)**: Asynchronous and safe data persistence, replacing the legacy SharedPreferences.
- **Parallelism and Reactivity**: Intensive use of **Kotlin Coroutines** for asynchronous operations and **StateFlow** for reactive UI state emission.
- **Unit and Instrumented Testing**: Business logic and UI flow coverage using **MockK**, **Turbine**, and **Espresso**.

### 📂 Main Classes Description

#### 🏛️ Presentation Layer
- **`MainActivity`**: Single activity serving as the host for the application's navigation graph.
- **`ListFragment` / `DetailFragment`**: Responsible for rendering the UI and observing state changes emitted by the ViewModels using `repeatOnLifecycle`.
- **`ListViewModel` / `DetailViewModel`**: Manage screen state, process UI events, and interact with UseCases. They use `StateFlow` to expose data.
- **`ListNoteAdapter`**: Manages the display of the notes list in the RecyclerView.

#### 🎯 Domain Layer
- **`ListUseCase` / `DetailUseCase`**: Contain the application business rules (e.g., empty field validation before saving).
- **`NotepadModel`**: Data entity representing a note in the system.

#### 💾 Data Layer
- **`ListRepository` / `DetailRepository`**: Interfaces defining how data should be accessed, allowing the domain layer to ignore the data source.
- **`PreferencesData`**: **DataStore** implementation, handling the actual asynchronous data persistence to disk.

#### ⚙️ Infrastructure and DI
- **`AppModule`**: Koin dependency injection configurations.
- **`NotepadApplication`**: App entry point where Koin is initialized.
