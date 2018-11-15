
public class TabelaSimbolos {

	String lexema; //Lexema
	String tipoLexema; // Simbolo ( Se é var, proc, func, programa etc )
	String tipo; // Tipo da Variavel, func
	int nivel; // nivel em relação ao scopo 
	String rotulo; // rotulo para geração de codigos
	
	
	public String getLexema() {
		return lexema;
	}
	public void setLexema(String tokenLexema) {
		this.lexema = tokenLexema;
	}
	public String getTipoLexema() {
		return tipoLexema;
	}
	public void setTipoLexema(String tipoLexema) {
		this.tipoLexema = tipoLexema;
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
