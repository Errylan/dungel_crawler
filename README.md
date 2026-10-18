# Dungeon Crawler

Jogo de labirinto em Java com movimentacao por tecla no terminal.

## Executar no Windows

Use o terminal integrado do VS Code ou outro terminal interativo. O Maven Wrapper baixa
o Maven e as bibliotecas do jogo automaticamente na primeira execucao. Na pasta raiz:

```cmd
mvnw.cmd compile
mvnw.cmd exec:java
```

E necessario ter JDK 17 ou superior instalado. Durante a partida, pressione `W`, `A`,
`S` ou `D` para mover imediatamente, sem apertar Enter. Pressione `P` para abrir a pausa.
Nos menus, digite a opcao e pressione Enter.

No IntelliJ IDEA, abra a pasta do projeto como um projeto Maven e aguarde a importacao
das dependencias. Execute `dungeon.jogo` em um terminal integrado para que a captura
imediata de teclas funcione.
