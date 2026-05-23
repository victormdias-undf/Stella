# 🎮 Stella - Game Project

Um jogo 2D desenvolvido em Java com sistema de colisão, medo e inimigos.

---

## 📁 Estrutura do Projeto

O projeto foi reorganizado em **pacotes** (packages) para melhor organização:

```
src/
└── com/stella/
    ├── core/
    │   ├── App.java              → Classe principal que inicia o jogo
    │   └── GamePanel.java        → Painel principal, renderização e loop do jogo
    │
    ├── player/
    │   ├── Player.java           → O jogador, movimento e detecção de inimigos
    │   └── KeyHandler.java       → Detecta entrada do teclado (WASD)
    │
    ├── world/
    │   ├── Tile.java             → Representa um bloco do mapa
    │   └── TileManager.java      → Carrega e desenha o mapa
    │
    ├── entities/
    │   ├── Entity.java           → Classe base para entidades (posição, colisão)
    │   ├── superObject.java      → Classe base para objetos do mundo (inimigos, itens)
    │   └── Enemy.java            → Inimigo do jogo
    │
    ├── physics/
    │   └── CollisionChecker.java → Verifica colisões com o mapa
    │
    └── assets/
        └── AssetSetter.java      → Inicializa objetos do jogo (inimigos, itens)
```

---

## 🔧 Como Compilar e Rodar

### Opção 1: Terminal (Recomendado)

```bash
# Compilar
javac -d bin src/com/stella/core/App.java

# Rodar
java -cp bin com.stella.core.App
```

### Opção 2: VS Code

Se estiver usando VS Code com extensão Java:
- Clique em **Run** (▶️) ao lado da classe `App.java`

---

## 🎯 Como Funciona

### 📍 Movimentação
- **W** = Cima
- **A** = Esquerda
- **S** = Baixo
- **D** = Direita

### 👻 Sistema de Medo
O jogador sente medo quando um inimigo está próximo:
- **< 20px**: Barra de medo 100%
- **20-75px**: Barra de medo 75%
- **75-150px**: Barra de medo 50%
- **150-300px**: Barra de medo 20%

### 💥 Colisões
O sistema de colisão verifica se o jogador pode se mover em uma direção analisando os tiles:
- **Tile 67** = Parede (tem colisão)
- Outros tiles = Passáveis

---

## 📝 Comentários no Código

**Cada arquivo tem comentários claros e diretos**:
- ✅ Explicam o propósito de cada classe
- ✅ Descrevem o que cada método faz
- ✅ Esclarecem variáveis importantes
- ✅ Linguagem simples, não técnica

---

## 🔄 Ordem de Inicialização (Importante!)

1. **TileManager** - Carrega o mapa e tiles
2. **CollisionChecker** - Verifica colisões
3. **Player** - O jogador
4. **AssetSetter** - Coloca inimigos no mundo

Esta ordem é crítica para evitar null pointers!

---

## 🛠️ Detalhes Técnicos

- **Linguagem**: Java 17+
- **Resolução**: 1440x864px (20x12 tiles na tela)
- **Mundo**: 50x50 tiles
- **FPS**: 60 (16ms por frame)
- **Câmera**: Centralizada no jogador

---

## 📌 Próximas Melhorias Possíveis

- [ ] Animações do jogador
- [ ] Mais tipos de inimigos
- [ ] Sistema de itens
- [ ] Efeitos de som
- [ ] Menu de pausa
- [ ] Diferentes níveis

---

**Desenvolvido com ❤️ em Java**
