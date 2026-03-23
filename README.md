# Agenda App - Aplicativo de Notas

Aplicativo Android de criação de notas internas, desenvolvido em Kotlin com Jetpack Compose e Material Design 3.

## Credenciais de Login

| Campo | Valor |
|-------|-------|
| Usuário | `root` |
| Senha | `root` |

## Funcionalidades

- **Login local** — autenticação simples em memória
- **Criar nota** — com título, conteúdo e cor personalizada
- **Editar nota** — toque em qualquer nota para editar
- **Excluir nota** — pressione e segure a nota ou use o ícone de lixeira no editor
- **Buscar notas** — filtro em tempo real por título e conteúdo
- **Cores para notas** — 9 opções de cores (estilo Google Keep)
- **Modo escuro** — alternável nas configurações
- **Grid estilo Keep** — exibição em grade escalonada (StaggeredGrid)

## Arquitetura

O projeto segue o padrão **MVVM (Model-View-ViewModel)**:

```
app/src/main/java/com/agenda/app/
├── MainActivity.kt                  # Activity principal
├── data/
│   └── NotesRepository.kt          # Repositório (armazenamento em memória)
├── model/
│   └── Note.kt                     # Modelo de dados (Note, NoteColor)
├── navigation/
│   ├── Screen.kt                   # Definição de rotas
│   └── NavGraph.kt                 # Grafo de navegação
├── ui/
│   ├── components/
│   │   ├── NoteCard.kt             # Card de nota reutilizável
│   │   └── ColorPicker.kt          # Seletor de cores
│   ├── screens/
│   │   ├── LoginScreen.kt          # Tela de login
│   │   ├── NotesListScreen.kt      # Listagem de notas
│   │   ├── NoteEditorScreen.kt     # Criação/edição de nota
│   │   └── SettingsScreen.kt       # Configurações
│   └── theme/
│       └── Theme.kt                # Tema Material 3 (claro/escuro)
└── viewmodel/
    ├── AuthViewModel.kt            # ViewModel de autenticação
    ├── NotesViewModel.kt           # ViewModel das notas
    └── SettingsViewModel.kt        # ViewModel de configurações
```

## Tecnologias

- **Kotlin** — linguagem principal
- **Jetpack Compose** — UI declarativa
- **Material Design 3** — sistema de design
- **Navigation Compose** — navegação entre telas
- **ViewModel** — gerenciamento de estado (MVVM)
- **StateFlow** — fluxo de dados reativo
- **Armazenamento em memória** — sem banco de dados externo

## Como Rodar o Projeto

### Pré-requisitos

1. **Android Studio** Hedgehog (2023.1.1) ou superior
2. **JDK 17** instalado
3. **Android SDK 34** instalado

### Passo a passo

1. **Abra o Android Studio**

2. **Crie um novo projeto vazio:**
   - File → New → New Project → Empty Activity
   - Name: `AgendaApp`
   - Package: `com.agenda.app`
   - Language: Kotlin
   - Minimum SDK: API 26

3. **Substitua os arquivos** do projeto criado pelos arquivos fornecidos,
   respeitando a estrutura de pastas acima.

4. **Sincronize o Gradle:**
   - File → Sync Project with Gradle Files

5. **Execute o app:**
   - Selecione um emulador ou dispositivo físico
   - Clique em Run (▶) ou pressione Shift+F10

### Alternativa (via linha de comando)

```bash
# Clone/copie os arquivos para uma pasta
cd agenda-app

# Build do projeto
./gradlew assembleDebug

# Instale no dispositivo conectado
./gradlew installDebug
```

## Fluxo de Telas

```
Login → Lista de Notas → Criar/Editar Nota
                        → Configurações
```

1. **Login** — Digite `root` / `root` e toque em "Entrar"
2. **Lista de Notas** — Veja suas notas em grade, busque, crie (+) ou edite (toque)
3. **Editor** — Escreva título e conteúdo, escolha uma cor; salva automaticamente ao voltar
4. **Configurações** — Ative/desative o modo escuro

## Licença

Projeto de exemplo para fins educacionais.
