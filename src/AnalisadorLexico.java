import java.util.Stack;

public class AnalisadorLexico {
	static Stack token = new Stack();
	static Stack errorToken = new Stack();
	static Stack tokenAux = new Stack();

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
							i++; // Controle para não repetir o segundo simbulo no token seguinte
						}
					}

					if (aux.size() > 1) { // Fix dos simbulos grudados nos finais das palavras
						Stack aux1 = new Stack();
						aux1.add(aux.pop());

						tratarToken(aux);
						aux.clear();
						tratarToken(aux1);

					}
					if (!aux.empty()) { // Garantir que todos simbulos tenha um token (Caso realmente exista)

						tratarToken(aux);

						aux.clear();
					}
				}
			} else {
				if (!aux.isEmpty()) { // Garantir que todos simbulos tenha um token (Caso realmente exista)

					tratarToken(aux);
				}
				aux.clear();
			}
		}

		AnalisadorSintatico.analisadorSintatico();
		System.out.println("Tokens Invalidos " + errorToken);
		return caracter;
	}

	public static void tratarToken(Stack caracter) {
		Simbulo simbulos = new Simbulo();
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
			errorToken.add(palavra);
		}

		if (isDigit(palavra.substring(0, 1))) {
			for (int i = 0; i < palavra.length(); i++) {
				String aux = palavra.charAt(i) + "";
				if (isLetra(aux)) {
					errorToken.add(palavra);
					palavra = "";
				}

			}

			token.add(palavra);
			token.add(Simbulo.snumero);
			identificador = false;

		}

		if (palavra.equals("programa")) {
			token.add(palavra);
			token.add(Simbulo.sprograma);
			identificador = false;
		}

		if (palavra.equals("fim")) {
			token.add(palavra);
			token.add(Simbulo.sfim);

			identificador = false;

		}
		if (palavra.equals("procedimento")) {
			token.add(palavra);
			token.add(Simbulo.sprocedimento);

			identificador = false;

		}

		if (palavra.equals("funcao")) {
			token.add(palavra);
			token.add(Simbulo.sfuncao);

			identificador = false;

		}

		if (palavra.equals("se")) {
			token.add(palavra);
			token.add(Simbulo.sse);

			identificador = false;
		}

		if (palavra.equals("entao")) {
			token.add(palavra);
			token.add(Simbulo.sentao);

			identificador = false;
		}

		if (palavra.equals("senao")) {
			token.add(palavra);
			token.add(Simbulo.ssenao);

			identificador = false;
		}

		if (palavra.equals("enquanto")) {
			token.add(palavra);
			token.add(Simbulo.senquanto);

			identificador = false;
		}

		if (palavra.equals("faca")) {
			token.add(palavra);
			token.add(Simbulo.sfaca);

			identificador = false;
		}

		if (palavra.equals("inicio")) {
			token.add(palavra);
			token.add(Simbulo.sinicio);
			identificador = false;
		}

		if (palavra.equals("escreva")) {
			token.add(palavra);
			token.add(Simbulo.sescreva);

			identificador = false;
		}

		if (palavra.equals("leia")) {
			token.add(palavra);
			token.add(Simbulo.sleia);

			identificador = false;
		}

		if (palavra.equals("var")) {
			token.add(palavra);
			token.add(Simbulo.svar);
			identificador = false;
		}

		if (palavra.equals("inteiro")) {
			token.add(palavra);
			token.add(Simbulo.sinteiro);

			identificador = false;
		}

		if (palavra.equals("booleano")) {
			token.add(palavra);
			token.add(Simbulo.sbooleano);

			identificador = false;
		}

		if (palavra.equals("verdadeiro")) {
			token.add(palavra);
			token.add(Simbulo.sverdadeiro);

			identificador = false;
		}

		if (palavra.equals("falso")) {
			token.add(palavra);
			token.add(Simbulo.sfalso);

			identificador = false;
		}

		if (palavra.equals("div")) {
			token.add(palavra);
			token.add(Simbulo.sdiv);

			identificador = false;
		}

		if (palavra.equals("e")) {
			token.add(palavra);
			token.add(Simbulo.se);

			identificador = false;
		}

		if (palavra.equals("ou")) {
			token.add(palavra);
			token.add(Simbulo.sou);

			identificador = false;
		}

		if (palavra.equals("nao")) {
			token.add(palavra);
			token.add(Simbulo.snao);

			identificador = false;
		}

		if (palavra.equals(".")) {
			token.add(palavra);
			token.add(Simbulo.sponto);

			identificador = false;
		}
		if (palavra.equals(";")) {
			token.add(palavra);
			token.add(Simbulo.sponto_virgula);

			identificador = false;
		}

		if (palavra.equals(",")) {
			token.add(palavra);
			token.add(Simbulo.svirgula);

			identificador = false;
		}

		if (palavra.equals("(")) {
			token.add(palavra);
			token.add(Simbulo.sabre_parenteses);

			identificador = false;
		}
		if (palavra.equals(")")) {
			token.add(palavra);
			token.add(Simbulo.sfecha_parenteses);

			identificador = false;
		}
		if (palavra.equals(">")) {
			token.add(palavra);
			token.add(Simbulo.smaior);

			identificador = false;
		}
		if (palavra.equals(">=")) {
			token.add(palavra);
			token.add(Simbulo.smaiorig);
		}

		if (palavra.equals(":=")) {
			token.add(palavra);
			token.add(Simbulo.satribuicao);

			identificador = false;
		}

		if (palavra.equals(":")) {
			token.add(palavra);
			token.add(Simbulo.sdoispontos);

			identificador = false;
		}

		if (palavra.equals("=")) {
			token.add(palavra);
			token.add(Simbulo.sig);

			identificador = false;
		}

		if (palavra.equals("<")) {
			token.add(palavra);
			token.add(Simbulo.smenor);

			identificador = false;
		}
		if (palavra.equals("<=")) {
			token.add(palavra);
			token.add(Simbulo.smenorig);

			identificador = false;
		}
		if (palavra.equals("!=")) {
			token.add(palavra);
			token.add(Simbulo.sdif);

			identificador = false;
		}
		if (palavra.equals("+")) {
			token.add(palavra);
			token.add(Simbulo.smais);

			identificador = false;
		}
		if (palavra.equals("-")) {
			token.add(palavra);
			token.add(Simbulo.smenos);

			identificador = false;
		}

		if (palavra.equals("*")) {
			token.add(palavra);
			token.add(Simbulo.smult);

			identificador = false;
		}

		if (identificador == true) {

			if (isIdentificador(palavra)) {
				token.add(palavra);
				token.add(Simbulo.sidentificador);

				identificador = false;
			} else {
				error = true;
			}
		}

		if (error)
			errorToken.add(palavra);
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

	public static boolean isSimbulo(String aux) {
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
