import java.util.Stack;

public class AnalisadorLexico {
	static Stack token = new Stack();
	static Stack errorToken = new Stack();

	public static Stack eliminarComentario(Stack caracter) {
		Stack aux = new Stack();

		for (int i = 0; i < caracter.size(); i++) {
			if (caracter.get(i).equals('{')) {
				do {
					i++;
				} while (!caracter.get(i).equals('}'));
			} else {
				aux.add(caracter.get(i));

			}
		}
		return aux;
	}

	public static Stack consumirEspaco(Stack caracter) {
		Stack aux = new Stack();

		for (int i = 0; i < caracter.size(); i++) {
			String dig = caracter.get(i).toString().toLowerCase();
			if (!isEspaco(dig)) { // Verifica se caracter não é um espaço
				aux.add(caracter.get(i));
				if (!isLetra(dig) && !isDigit(dig)) { // Verifica se caracter não é um numero ou uma letra

					if (!isLetra(caracter.get(i + 1).toString()) && !isDigit(caracter.get(i + 1).toString()) // Verifica
																												// "!="
							&& !caracter.get(i + 1).equals(' ')) {
						String dig2 = dig + caracter.get(i + 1).toString();

						if (dig2.equals(":=") || dig2.equals("!=") || dig2.equals(">=") || dig2.equals("<=")) { // Verifica
							aux.pop();
							aux.add(dig2);
							i++; // Controle para não repetir o segundo Simbolo no token seguinte
						}
					}

					if (aux.size() > 1) { // Fix dos Simbolos grudados nos finais das palavras
						Stack aux1 = new Stack();
						aux1.add(aux.pop());

						tratarToken(aux);
						aux.clear();
						tratarToken(aux1);

					}
					if (!aux.empty()) { // Garantir que todos Simbolos tenha um token (Caso realmente exista)

						tratarToken(aux);

						aux.clear();
					}
				}
			} else {
				if (!aux.isEmpty()) { // Garantir que todos Simbolos tenha um token (Caso realmente exista)

					tratarToken(aux);
				}
				aux.clear();
			}
		}

		AnalisadorSintatico.analisadorSintatico();
		return caracter;
	}

	public static void tratarToken(Stack caracter) {
		Simbolo Simbolos = new Simbolo();
		String aux = "";

		for (int i = 0; i < caracter.size(); i++) {
			aux = aux + caracter.get(i);
		}
		verificarToken(aux);

	}

	public static void verificarToken(String palavra) {
		Boolean identificador = true;
		Boolean error = false;

		if (palavra.substring(0, 1).equals("_")) {
			if (errorToken.isEmpty()) {
				errorToken = token;
				errorToken.add(palavra);
				Erro.tratarError1(errorToken.size(), "lexico");

			}
		}

		if (isDigit(palavra.substring(0, 1))) {
			for (int i = 0; i < palavra.length(); i++) {
				String aux = palavra.charAt(i) + "";
				if (isLetra(aux)) {
					if (errorToken.isEmpty()) {
						errorToken = token;
						errorToken.add(palavra);
						Erro.tratarError1(errorToken.size(), "lexico");

					}
					palavra = "";
				}

			}

			token.add(palavra);
			token.add(Simbolo.snumero);
			identificador = false;

		}

		if (palavra.equals("programa")) {
			token.add(palavra);
			token.add(Simbolo.sprograma);
			identificador = false;
		}

		if (palavra.equals("fim")) {
			token.add(palavra);
			token.add(Simbolo.sfim);

			identificador = false;

		}
		if (palavra.equals("procedimento")) {
			token.add(palavra);
			token.add(Simbolo.sprocedimento);

			identificador = false;

		}

		if (palavra.equals("funcao")) {
			token.add(palavra);
			token.add(Simbolo.sfuncao);

			identificador = false;

		}

		if (palavra.equals("se")) {
			token.add(palavra);
			token.add(Simbolo.sse);

			identificador = false;
		}

		if (palavra.equals("entao")) {
			token.add(palavra);
			token.add(Simbolo.sentao);

			identificador = false;
		}

		if (palavra.equals("senao")) {
			token.add(palavra);
			token.add(Simbolo.ssenao);

			identificador = false;
		}

		if (palavra.equals("enquanto")) {
			token.add(palavra);
			token.add(Simbolo.senquanto);

			identificador = false;
		}

		if (palavra.equals("faca")) {
			token.add(palavra);
			token.add(Simbolo.sfaca);

			identificador = false;
		}

		if (palavra.equals("inicio")) {
			token.add(palavra);
			token.add(Simbolo.sinicio);
			identificador = false;
		}

		if (palavra.equals("escreva")) {
			token.add(palavra);
			token.add(Simbolo.sescreva);

			identificador = false;
		}

		if (palavra.equals("leia")) {
			token.add(palavra);
			token.add(Simbolo.sleia);

			identificador = false;
		}

		if (palavra.equals("var")) {
			token.add(palavra);
			token.add(Simbolo.svar);
			identificador = false;
		}

		if (palavra.equals("inteiro")) {
			token.add(palavra);
			token.add(Simbolo.sinteiro);

			identificador = false;
		}

		if (palavra.equals("booleano")) {
			token.add(palavra);
			token.add(Simbolo.sbooleano);

			identificador = false;
		}

		if (palavra.equals("verdadeiro")) {
			token.add(palavra);
			token.add(Simbolo.sverdadeiro);

			identificador = false;
		}

		if (palavra.equals("falso")) {
			token.add(palavra);
			token.add(Simbolo.sfalso);

			identificador = false;
		}

		if (palavra.equals("div")) {
			token.add(palavra);
			token.add(Simbolo.sdiv);

			identificador = false;
		}

		if (palavra.equals("e")) {
			token.add(palavra);
			token.add(Simbolo.se);

			identificador = false;
		}

		if (palavra.equals("ou")) {
			token.add(palavra);
			token.add(Simbolo.sou);

			identificador = false;
		}

		if (palavra.equals("nao")) {
			token.add(palavra);
			token.add(Simbolo.snao);

			identificador = false;
		}

		if (palavra.equals(".")) {
			token.add(palavra);
			token.add(Simbolo.sponto);

			identificador = false;
		}
		if (palavra.equals(";")) {
			token.add(palavra);
			token.add(Simbolo.sponto_virgula);

			identificador = false;
		}

		if (palavra.equals(",")) {
			token.add(palavra);
			token.add(Simbolo.svirgula);

			identificador = false;
		}

		if (palavra.equals("(")) {
			token.add(palavra);
			token.add(Simbolo.sabre_parenteses);

			identificador = false;
		}
		if (palavra.equals(")")) {
			token.add(palavra);
			token.add(Simbolo.sfecha_parenteses);

			identificador = false;
		}
		if (palavra.equals(">")) {
			token.add(palavra);
			token.add(Simbolo.smaior);

			identificador = false;
		}
		if (palavra.equals(">=")) {
			token.add(palavra);
			token.add(Simbolo.smaiorig);
			identificador = false;

		}

		if (palavra.equals(":=")) {
			token.add(palavra);
			token.add(Simbolo.satribuicao);

			identificador = false;
		}

		if (palavra.equals(":")) {
			token.add(palavra);
			token.add(Simbolo.sdoispontos);

			identificador = false;
		}

		if (palavra.equals("=")) {
			token.add(palavra);
			token.add(Simbolo.sig);

			identificador = false;
		}

		if (palavra.equals("<")) {
			token.add(palavra);
			token.add(Simbolo.smenor);

			identificador = false;
		}
		if (palavra.equals("<=")) {
			token.add(palavra);
			token.add(Simbolo.smenorig);

			identificador = false;
		}
		if (palavra.equals("!=")) {
			token.add(palavra);
			token.add(Simbolo.sdif);

			identificador = false;
		}
		if (palavra.equals("+")) {
			token.add(palavra);
			token.add(Simbolo.smais);

			identificador = false;
		}
		if (palavra.equals("-")) {
			token.add(palavra);
			token.add(Simbolo.smenos);

			identificador = false;
		}

		if (palavra.equals("*")) {
			token.add(palavra);
			token.add(Simbolo.smult);

			identificador = false;
		}

		if (identificador == true) {

			if (isIdentificador(palavra)) {
				token.add(palavra);
				token.add(Simbolo.sidentificador);

				identificador = false;
			} else {
				error = true;
			}
		}

		if (error) {
			if (errorToken.isEmpty()) {
				errorToken = token;
				errorToken.add(palavra);
				Erro.tratarError1(errorToken.size(), "lexico");

			}
		}
		// else AnalisadorSintatico.analisadorSintatico();
	}

	public static boolean isIdentificador(String palavra) {

		String aux = palavra.substring(0, 1);

		return isLetra(aux);
	}

	public static boolean isDigit(String aux) {
		if (aux.equals("0") || aux.equals("1") || aux.equals("2") || aux.equals("3") || aux.equals("4")
				|| aux.equals("5") || aux.equals("6") || aux.equals("7") || aux.equals("8") || aux.equals("9")) {
			return true;
		}
		return false;

	}

	public static boolean isEspaco(String aux) {
		if (aux.equals(" ") || aux.equals("\n"))
			return true;
		return false;

	}

	
	public static boolean isExpressao(String aux) {
		if (aux.equals("(") || aux.equals(")") || aux.equals("!") || aux.equals("=") || aux.equals("<")
				|| aux.equals(">") || aux.equals(":") || aux.equals(";") || aux.equals(".") || aux.equals(",")
				|| aux.equals("_") || aux.equals("+") || aux.equals("-") || aux.equals("*")) {
			return true;
		}
		return false;
		
	}

	public static boolean isLetra(String aux) {
		if (aux.equals("a") || aux.equals("b") || aux.equals("c") || aux.equals("d") || aux.equals("e")
				|| aux.equals("f") || aux.equals("g") || aux.equals("h") || aux.equals("i") || aux.equals("j")
				|| aux.equals("k") || aux.equals("l") || aux.equals("m") || aux.equals("n") || aux.equals("o")
				|| aux.equals("p") || aux.equals("q") || aux.equals("r") || aux.equals("s") || aux.equals("t")
				|| aux.equals("i") || aux.equals("j") || aux.equals("u") || aux.equals("v") || aux.equals("x")
				|| aux.equals("w") || aux.equals("y") || aux.equals("z") || aux.equals("ã") || aux.equals("á")
				|| aux.equals("é") || aux.equals("i") || aux.equals("ç") || aux.equals("_")) {
			return true;
		}
		return false;

	}

}
