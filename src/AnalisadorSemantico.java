import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;

public class AnalisadorSemantico {
	static List<TabelaSimbolos> simbolos = new ArrayList<TabelaSimbolos>();
	static Stack expressão = new Stack();
	static Stack errorToken = new Stack();
	
	// Metodo para inserir na tabela de simbolo
	// Metodo testado e funcionando
	public static void inserirTabela(String lexema, String tipoLexema, int nivel, String rotulo) {
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
	 * pesquisar_duplicvar_tabela Retorna true, caso a variavel possa ser declarada
	 * 
	 */

	public static boolean pesquisar_duplicvar_tabela(String lexema, String tipoLexema, int nivel, String rotulo) {
		for (int i = 0; i < simbolos.size(); i++) {
			if (simbolos.get(i).getLexema().equals(lexema)) {
				if (simbolos.get(i).getTipoLexema().equals("var")) { // Verificar se o lexema é uma variavel e está no
																		// mesmo nivel
					if (simbolos.get(i).getNivel() == nivel) { // Caso o nivel seja diferente a variavel pode ser
																// declarada novamente
						return false;
					}
				} else {
					if (simbolos.get(i).getTipoLexema().equals("nomedeprograma")
							|| simbolos.get(i).getTipoLexema().equals("procedimento") // Caso o lexema seja nome do
																						// programa,procedimento ou
																						// funcao
							|| simbolos.get(i).getTipoLexema().equals("funcao")) { // Indiferente se o nivel seja
																					// diferente
						return false; // A variavel não pode ser declarada
					}
				}
			}
		}
		return true;
	}

	// Pesqusiar se a variavel já esta declarada
	// Metodo testado e funcionando
	public static boolean pesquisa_declvar_tabela(String lexema, String tipoLexema, int nivel, String rotulo) {
		for (int i = 0; i < simbolos.size(); i++) {
			if (simbolos.get(i).getLexema().equals(lexema)) {
				if (simbolos.get(i).getTipoLexema().equals("var")) {
					if (simbolos.get(i).getNivel() <= nivel) {
						return true;
					}
				}
			}

		}
		return false;
	}

	// Verificar se o identificador é uma função ou uma variavel

	public static boolean pesquisa_declvarfunc_tabela(String lexema, String tipoLexema, int nivel, String rotulo) {
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
					if (simbolos.get(i).getNivel() <= nivel) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Pesquisar se há duplicidade na declaração de um procedimento
	public static boolean pesquisa_declproc_tabela(String lexema, String tipoLexema, int nivel, String rotulo) {

		for (int i = 0; i < simbolos.size(); i++) {
			if (simbolos.get(i).getLexema().equals(lexema)) {
				if (tipoLexema.equals(simbolos.get(i).getTipoLexema())) {
					return false;
				}
			}
		}

		// if(pesquisa_declfunc_tabela(lexema, "funcao", nivel, rotulo)) return false;

		return true;
	}

	// Pesquisar se há duplicidade na declaração de uma funcao
	public static boolean pesquisa_declfunc_tabela(String lexema, String tipoLexema, int nivel, String rotulo) {
		for (int i = 0; i < simbolos.size(); i++) {
			if (simbolos.get(i).getLexema().equals(lexema) && simbolos.get(i).getTipoLexema().equals(tipoLexema)) {
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

	public static void coloca_tipo_tabela(String tokenLexema, String tipoLexema, int nivel, String rotulo,
			String tipo) {

		if (tipoLexema.equals("var")) {
			for (int i = 0; i < simbolos.size(); i++) {
				if (simbolos.get(i).getTipo().equals("") || simbolos.get(i).getTipo().equals(null)) {
					if (!simbolos.get(i).getTipoLexema().equals("nomedeprograma")
							&& !simbolos.get(i).getTipoLexema().equals("procedimento")) {
						simbolos.get(i).setTipo(tipo);
					}
				}
			}
		}

		if (tipoLexema.equals("funcao")) {
			for (int i = 0; i < simbolos.size(); i++) {
				if (simbolos.get(i).getTipo().equals("") || simbolos.get(i).getTipo().equals(null)) {
					if (!simbolos.get(i).getTipoLexema().equals("nomedeprograma")
							&& !simbolos.get(i).getTipoLexema().equals("procedimento")) {
						simbolos.get(i).setTipo(tipo);
					}
				}
			}
		}
	}

	public static void validar_tipo(String lexema, String tipo) {
		expressão.add(lexema);
	}

	public static void validar_tipoAUX(int numAux) {
		String aux;
		Stack aux1 = new Stack();
		for (int i = 0; i < expressão.size(); i++) {
			if(expressão.get(i).toString().equals("verdadeiro") || expressão.get(i).toString().equals("falso")) aux = "sbooleano";
			else aux = retorna_tipo(expressão.get(i).toString());
			if (aux != null) {
				aux1.add(aux);
			}
		}

		for (int i = 0; i < aux1.size() - 1; i++) {
			if (aux1.get(i) != aux1.get(i + 1)) {
				JOptionPane.showMessageDialog(null,"Erro Semantico: TIPOS INVALIDOS");
				Erro.tratarError1(numAux, "semantico");
			}
		}
		expressão.clear();
	}

	public static void remover_nivel_simbolos(int nivel) {
		for (int i = 0; i < simbolos.size(); i++) {
			if (simbolos.get(i).getNivel() == nivel) {
				simbolos.remove(i);
				i--;
			}

		}

	}

	public static void printarTS() {
		for (int i = 0; i < simbolos.size(); i++) {
//			System.out.println("Lexema : " + simbolos.get(i).getLexema());
//			System.out.println("Tipo Identificador : " + simbolos.get(i).getTipoLexema());
//			System.out.println("Nivel : " + simbolos.get(i).getNivel());
//			System.out.println("Rotulo : " + simbolos.get(i).getRotulo());
//			System.out.println("Tipo : " + simbolos.get(i).getTipo());
//			System.out.println("\n");

		}
	}

	public static String retorna_tipo(String lexema) {

		if (AnalisadorLexico.isDigit(lexema.substring(0, 1))) {
			return "sinteiro";
		}

		if (AnalisadorLexico.isDigit(lexema)) {
			return "sinteiro";
		}

		for (int i = simbolos.size() - 1; i > 0; i--) {
			if (simbolos.get(i).getLexema().equals(lexema)) {
				return simbolos.get(i).getTipo();
			}
		}

		return null;
	}

}
