import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;

public class AnalisadorSemantico {
	static List<Simbolo> tabelaSimbolo = new ArrayList<Simbolo>();
	static Stack expressão = new Stack();
	static Stack errorToken = new Stack();
	
	// Metodo para inserir na tabela de simbolo
	// Metodo testado e funcionando
	public static void inserirTabela(String lexema, String tipoLexema, int nivel, String rotulo) {
		Simbolo ts = new Simbolo();
		ts.setLexema(lexema);
		ts.setTipoLexema(tipoLexema);
		ts.setNivel(nivel);
		ts.setRotulo(rotulo);
		ts.setTipo("");
		tabelaSimbolo.add(ts);

	}

	// Metodo para pesquisar a duplicidade de variaveis na tabela
	/*
	 * pesquisar_duplicvar_tabela Retorna true, caso a variavel possa ser declarada
	 * 
	 */

	public static boolean pesquisar_duplicvar_tabela(String lexema, String tipoLexema, int nivel, String rotulo) {
		for (int i = 0; i < tabelaSimbolo.size(); i++) {
			if (tabelaSimbolo.get(i).getLexema().equals(lexema)) {
				if (tabelaSimbolo.get(i).getTipoLexema().equals("var")) { // Verificar se o lexema é uma variavel e está no
																		// mesmo nivel
					if (tabelaSimbolo.get(i).getNivel() == nivel) { // Caso o nivel seja diferente a variavel pode ser
																// declarada novamente
						return false;
					}
				} else {
					if (tabelaSimbolo.get(i).getTipoLexema().equals("nomedeprograma")
							|| tabelaSimbolo.get(i).getTipoLexema().equals("procedimento") // Caso o lexema seja nome do
																						// programa,procedimento ou
																						// funcao
							|| tabelaSimbolo.get(i).getTipoLexema().equals("funcao")) { // Indiferente se o nivel seja
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
		for (int i = 0; i < tabelaSimbolo.size(); i++) {
			if (tabelaSimbolo.get(i).getLexema().equals(lexema)) {
				if (tabelaSimbolo.get(i).getTipoLexema().equals("var")) {
					if (tabelaSimbolo.get(i).getNivel() <= nivel) {
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
			for (int i = 0; i < tabelaSimbolo.size(); i++) {
				if (tabelaSimbolo.get(i).getLexema().equals(lexema)) {
					return true;
				}
			}
		}
		if (tipoLexema.equals("var")) {
			for (int i = 0; i < tabelaSimbolo.size(); i++) {
				if (tabelaSimbolo.get(i).getLexema().equals(lexema)) {
					if (tabelaSimbolo.get(i).getNivel() <= nivel) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Pesquisar se há duplicidade na declaração de um procedimento
	public static boolean pesquisa_declproc_tabela(String lexema, String tipoLexema, int nivel, String rotulo) {

		for (int i = 0; i < tabelaSimbolo.size(); i++) {
			if (tabelaSimbolo.get(i).getLexema().equals(lexema)) {
				if (tipoLexema.equals(tabelaSimbolo.get(i).getTipoLexema()) || tabelaSimbolo.get(i).getTipoLexema().equals("nomedeprograma")) {
					return false;
				}
			}
		}


		return true;
	}

	// Pesquisar se há duplicidade na declaração de uma funcao
	public static boolean pesquisa_declfunc_tabela(String lexema, String tipoLexema, int nivel, String rotulo) {
		for (int i = 0; i < tabelaSimbolo.size(); i++) {
			if (tabelaSimbolo.get(i).getLexema().equals(lexema) && 
					(tabelaSimbolo.get(i).getTipoLexema().equals(tipoLexema) || tabelaSimbolo.get(i).getTipoLexema().equals("nomedeprograma"))) {
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
			for (int i = 0; i < tabelaSimbolo.size(); i++) {
				if (tabelaSimbolo.get(i).getTipo().equals("") || tabelaSimbolo.get(i).getTipo().equals(null)) {
					if (!tabelaSimbolo.get(i).getTipoLexema().equals("nomedeprograma")
							&& !tabelaSimbolo.get(i).getTipoLexema().equals("procedimento")) {
						tabelaSimbolo.get(i).setTipo(tipo);
					}
				}
			}
		}

		if (tipoLexema.equals("funcao")) {
			for (int i = 0; i < tabelaSimbolo.size(); i++) {
				if (tabelaSimbolo.get(i).getTipo().equals("") || tabelaSimbolo.get(i).getTipo().equals(null)) {
					if (!tabelaSimbolo.get(i).getTipoLexema().equals("nomedeprograma")
							&& !tabelaSimbolo.get(i).getTipoLexema().equals("procedimento")) {
						tabelaSimbolo.get(i).setTipo(tipo);
					}
				}
			}
		}
	}

	public static void validar_tipo(String lexema, String tipo) {
		expressão.add(lexema);// adiciona em uma pilha todos os valores da expressão, para poder validar os tipos da expressão
	}

	public static void validar_tipoAUX(int numAux) {
		String aux;
		boolean isBool = false;
		Stack aux1 = new Stack();
		
		
		for (int i = 0; i < expressão.size(); i++) {
			if(expressão.get(i).toString().equals("verdadeiro") || expressão.get(i).toString().equals("falso")) aux = "sbooleano";
			else if(expressão.get(i).toString().equals("smaior")||
			expressão.get(i).toString().equals("smaiorig")||	
			expressão.get(i).toString().equals("smenor")||
			expressão.get(i).toString().equals("smenorig")||
			expressão.get(i).toString().equals("sdif")||
			expressão.get(i).toString().equals("sig")) {
				aux = "isBoolean";
			}
			else aux = retorna_tipo(expressão.get(i).toString());
			if (aux != null) {
				aux1.add(aux);  // insere o tipo dos valores da expressão na pilha aux
			}
		}
		
		
		for (int i = 0; i < aux1.size() - 1; i++) {
			aux = aux1.get(0).toString();
			if(aux.equals("sbooleano") && aux1.get(i).equals("isBoolean")) {
				isBool =true;
			
			}
			if(aux1.get(i).equals("isBoolean")) {
				aux1.remove(i);

			}
	
		}
		
		
		for (int i = 0; i < aux1.size() - 1; i++) {
				if (aux1.get(i) != aux1.get(i + 1) && !isBool) { //Caso os tipos forem diferentes exibir erro 
				JOptionPane.showMessageDialog(null,"Erro Semantico: TIPOS INVALIDOS");
				Erro.tratarError1(numAux, "semantico");
			}
		}
		expressão.clear();
	}

	public static void remover_nivel_tabelaSimbolo(int nivel) {
		for (int i = 0; i < tabelaSimbolo.size(); i++) {
			if (tabelaSimbolo.get(i).getNivel() == nivel) {
				tabelaSimbolo.remove(i);
				i--;
			}

		}

	}


	public static String retorna_tipo(String lexema) {

		if (AnalisadorLexico.isDigit(lexema.substring(0, 1))) {
			return "sinteiro";
		}

		if (AnalisadorLexico.isDigit(lexema)) {
			return "sinteiro";
		}

		for (int i = tabelaSimbolo.size() - 1; i > 0; i--) {
			if (tabelaSimbolo.get(i).getLexema().equals(lexema)) {
				return tabelaSimbolo.get(i).getTipo();
			}
		}

		return null;
	}


}
