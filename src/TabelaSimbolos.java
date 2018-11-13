
public class TabelaSimbolos {

	String tokenLexema; //Lexema
	String tokenSimbolo; // Simbolo ( Se é var, proc, func, programa etc )
	String tipo; // Tipo da Variavel
	int nivel; // nivel em relação ao scopo 
	String rotulo; // rotulo para geração de codigos
	
	
	public String getTokenLexema() {
		return tokenLexema;
	}
	public void setTokenLexema(String tokenLexema) {
		this.tokenLexema = tokenLexema;
	}
	public String getTokenSimbolo() {
		return tokenSimbolo;
	}
	public void setTokenSimbolo(String tokenSimbolo) {
		this.tokenSimbolo = tokenSimbolo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public String getRotulo() {
		return rotulo;
	}
	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}
	

	
	
}
