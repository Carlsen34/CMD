import java.util.ArrayList;
import java.util.List;

public class AnalisadorSemantico {
	static List<TabelaSimbolos> simbolos = new ArrayList<TabelaSimbolos>();
	
	// Metodo para inserir na tabela de simbolo

	public static void inserirTabela(String tokenLexema, String tokenSimbolo, int nivel, String rotulo) {
		TabelaSimbolos ts = new TabelaSimbolos();
		ts.setTokenLexema(tokenLexema);
		ts.setTokenSimbolo(tokenSimbolo);
		ts.setNivel(nivel);
		ts.setRotulo(rotulo);
		ts.setTipo(null);
		simbolos.add(ts);

	}

	// Metodo para pesquisar a duplicidade de variaveis na tabela
	
	public static boolean pesquisar_duplicvar_tabela(String tokenLexema, String tipoLexema, int nivel, String rotulo) {
		for (int i = 0; i < simbolos.size(); i++) {
			if (simbolos.get(i).getTokenLexema().equals(tokenLexema)) {
				if (simbolos.get(i).getTokenSimbolo().equals("var")) {
					if (simbolos.get(i).getNivel() == nivel) {
						return false;
					}
				}else {
					if (simbolos.get(i).getTokenSimbolo().equals("nomedeprograma") || simbolos.get(i).getTokenSimbolo().equals("procedimento") || simbolos.get(i).getTokenSimbolo().equals("funcao")) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	// Pesqusiar se a variavel já esta declarada 

	public static boolean pesquisa_declvar_tabela(String tokenLexema,String tipoLexema, int nivel, String rotulo) {
		for (int i = 0; i < simbolos.size(); i++) {
			if (simbolos.get(i).getTokenLexema().equals(tokenLexema)) {
				if (simbolos.get(i).getTokenSimbolo().equals("var")) {
					if (simbolos.get(i).getNivel() == nivel) {
						return true;
					}
				}
			}
			
		}
		return false;
	}
	
	// Verificar se o identificador é uma função ou uma variavel 
	
	public static boolean pesquisa_declvarfunc_tabela(String tokenLexema,String tipoLexema,int nivel,String rotulo) {
		if(tokenLexema.equals("funcao")) {
			for (int i = 0; i < simbolos.size(); i++) {
				if (simbolos.get(i).getTokenLexema().equals(tokenLexema)) {
					return true;
				}
			}
		}
		if(tokenLexema.equals("var")) {
			for (int i = 0; i < simbolos.size(); i++) {
				if (simbolos.get(i).getTokenLexema().equals(tokenLexema)) {
					if(simbolos.get(i).getNivel() == nivel) {
						return true;
					}
				}
			}
		}
		return false;
	}
	// Pesquisar se há duplicidade na declaração de um procedimento  OBS:Se existir uma variavel com o mesmo nome ?
	public static boolean pesquisa_declproc_tabela(String tokenLexema,String tipoLexema,int nivel,String rotulo) {
		for (int i = 0; i < simbolos.size(); i++) {
			if(simbolos.get(i).getTokenLexema().equals(tokenLexema) ) {
				return false;
			}
		}
		return true;
	}
	
	// Pesquisar se há duplicidade na declaração de uma funcao	OBS:Se existir uma variavel com o mesmo nome ?
	public static boolean pesquisa_declfunc_tabela(String tokenLexema,String tipoLexema,int nivel,String rotulo) {
		for (int i = 0; i < simbolos.size(); i++) {
			if(simbolos.get(i).getTokenLexema().equals(tokenLexema)) {
				return false;
			}
		}
		return true;
	}
	
	public static void coloca_tipo_tabela(String tokenLexema,String tipoLexema,int nivel,String rotulo, String tipo) {
		for (int i = 0; i < simbolos.size(); i++) {
			// Tipos de funcoes e variavies podem ter funcionamentos diferentes
		}
	}
	
	public static void printarTS() {
		for (int i = 0; i < simbolos.size(); i++) {
			System.out.println(simbolos.get(i).getTokenLexema());
			System.out.println(simbolos.get(i).getTokenSimbolo());
			System.out.println(simbolos.get(i).getNivel());
			System.out.println(simbolos.get(i).getRotulo());
			System.out.println(simbolos.get(i).getTipo());

		}
	}

}
