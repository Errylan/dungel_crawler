public enum Dificuldade {

    FACIL(1),

    MEDIO(2),

    DIFICIL(3);

    private final int nivel;

    Dificuldade(int nivel) {

        this.nivel = nivel;

    }

    public int getNivel() {

        return nivel;

    }

}
