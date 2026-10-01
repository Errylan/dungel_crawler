package dungeon;

public enum Dificuldade {
    FACIL(1, 5, 5, 0.20),
    MEDIO(2, 8, 8, 0.10),
    DIFICIL(3, 12, 12, 0.02);

    private final int nivel;
    private final int largura;
    private final int altura;
    private final double chanceRemoverParede;

    Dificuldade(int nivel, int largura, int altura, double chanceRemoverParede) {
        this.nivel = nivel;
        this.largura = largura;
        this.altura = altura;
        this.chanceRemoverParede = chanceRemoverParede;
    }

    public int getNivel() {
        return nivel;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public double getChanceRemoverParede() {
        return chanceRemoverParede;
    }
}
