import java.util.ArrayList;
import java.util.List;

public class AnalisadorSemantico {
	static List<TabelaSimbolos> simbolos = new ArrayList<TabelaSimbolos>();

	// Metodo para inserir na tabela de simbolo
	//Metodo testado e funcionando
	public static void inserirTabela(String lexema, String tipoLexema, String nivel, String rotulo) {
		TabelaSimbolos ts = new TabelaSimbolos();
		ts.setLexema(lexema);
		ts.setTipoLexema(tipoLexema);
		ts.setNivel(nivel);
		ts.setRotulo(rotulo);
		ts.setTipo("");
		simbolos.add(ts);

	}

	// Metodo para pesquisar a duplicidade de variaveis na tabela
	/*
	 *  pesquisar_duplicvar_tabela
	 *  Retorna true, caso a variavel possa ser declarada
	 * 
	 */

	public static boolean pesquisar_duplicvar_tabela(String lexema, String tipoLexema, String nivel, String rotulo) {
		for (int i = 0; i < simbolos.size(); i++) {
			if (simbolos.get(i).getLexema().equals(lexema)) {
				if (simbolos.get(i).getTipoLexema().equals("var")) { // Verificar se o lexema é uma variavel e está no mesmo nivel
					if (simbolos.get(i).getNivel().equals(nivel)) {		//  Caso o nivel seja diferente a variavel pode ser declarada novamente
						return false;
					}
				} else {
					if (simbolos.get(i).getTipoLexema().equals("nomedeprograma")            
							|| simbolos.get(i).getTipoLexema().equals("procedimento")   // Caso o lexema seja nome do programa,procedimento ou funcao
							|| simbolos.get(i).getTipoLexema().equals("funcao")) {     //  Indiferente se o nivel seja diferente 
						return false;												   //  A variavel não pode ser declarada 							
					}
				}
			}
		}
		return true;
	}

	
	
	
	
	
	
	// Pesqusiar se a variavel já esta declarada
	//Metodo testado e funcionando
	public static boolean pesquisa_declvar_tabela(String lexema, String tipoLexema, String nivel, String rotulo) {
		for (int i = 0; i < simbolos.size(); i++) {
			if (simbolos.get(i).getLexema().equals(lexema)) {
				if (simbolos.get(i).getTipoLexema().equals("var")) {
					if (simbolos.get(i).getNivel().equals(nivel) ) {
						return true;
					}
				}
			}

		}
		return false;
	}
	

	// Verificar se o identificador é uma função ou uma variavel

	public static boolean pesquisa_declvarfunc_tabela(String lexema, String tipoLexema, String nivel, String rotulo) {
		if (tipoLexema.equals("funcao")) {
			for (int i = 0; i < simbolos.size(); i++) {
				if (simbolos.get(i).getLexema().equals(lexema)) {
					return true;
				}
			}
		}
		if (tipoLexema.equals("var")) {
			for (int i = 0; i < simbolos.size(); i++) {
				if (simbolos.get(i).getLexema().equals(lexema)) {
					if (simbolos.get(i).getNivel().equals(nivel)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	
	
	
	
	

	// Pesquisar se há duplicidade na declaração de um procedimento 
	public static boolean pesquisa_declproc_tabela(String lexema, String tipoLexema, String nivel, String rotulo) {
		for (int i = 0; i < simbolos.size(); i++) {
			if (simbolos.get(i).getLexema().equals(lexema)) {
				return false;
			}
		}
		return true;
	}

	
	
	
	
	
	// Pesquisar se há duplicidade na declaração de uma funcao
	public static boolean pesquisa_declfunc_tabela(String lexema, String tipoLexema, String nivel, String rotulo) {
		for (int i = 0; i < simbolos.size(); i++) {
			if (simbolos.get(i).getLexema().equals(lexema)) {
				return false;
			}
		}
		return true;
	}

	
	/*
	 * Colocar o tipo, nas variaves ou funcao
	 * 
	 * Caso seja uma variavel, verificar se ela ta no mesmo nivel 
	 * 
	 * 
	 */
	
	public static void coloca_tipo_tabela(String tokenLexema, String tipoLexema, String nivel, String rotulo,String tipo) {
			
		if(tipoLexema.equals("var")) {
			for(int i = 0; i<simbolos.size();i++) {
				if(simbolos.get(i).getLexema().equals(tokenLexema) &&  simbolos.get(i).getNivel().equals(nivel)) {
					simbolos.get(i).setTipo(tipo);
				}
			}
		}
		
		
		if(tipoLexema.equals("funcao")) {
				for(int i = 0; i<simbolos.size();i++) {
				if(simbolos.get(i).getLexema().equals(tokenLexema)) {
					simbolos.get(i).setTipo(tipo);
				}
			}
		}


	}
	
	
	public static void remover_nivel_simbolos(String nivel) {
		for(int i = 0; i<simbolos.size();i++) {
			if(simbolos.get(i).getNivel().equals(nivel)) {
				simbolos.remove(i);
			}
			
		}
	}
	
	
	
	

	public static void printarTS() {
		for (int i = 0; i < simbolos.size(); i++) {
			System.out.println("Lexema : " + simbolos.get(i).getLexema());
			System.out.println("Tipo Identificador : " + simbolos.get(i).getTipoLexema());
			System.out.println("Nivel : " + simbolos.get(i).getNivel());
			System.out.println("Rotulo : " + simbolos.get(i).getRotulo());
			System.out.println("Tipo : " + simbolos.get(i).getTipo());

		}
	}

}
