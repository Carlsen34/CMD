import java.util.Stack;

public class AnalisadorSintatico {

	static Stack tokenAS = new Stack();

	public static void analisadorSintatico() {

		tokenAS = AnalisadorLexico.token;

		for (int i = 1; i < tokenAS.size(); i += 2) {
			if (Simbulo.sprograma.equals(tokenAS.get(i))) {
				i += 2; // Ler proximo Token
				if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
					i += 2; // Ler proximo Token
					if (Simbulo.sponto_vírgula.equals(tokenAS.get(i))) {

						i = analisarBloco(i);
						tokenAS.clear();

					} else
						System.out.println("ERROR");

				} else
					System.out.println("ERROR");

			} else
				System.out.println("ERROR");
		}
	}

	public static int analisarBloco(int i) {

		i += 2; // Ler Proximo Token

		i = analisaEtVariáveis(i);

		/*
		 * Analisa_subrotinas Analisa_comandos
		 */

		return i;

	}

	public static int analisaEtVariáveis(int i) {

		if (Simbulo.svar.equals(tokenAS.get(i))) {
			i += 2; // Ler Proximo Token
			if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
				while (Simbulo.sidentificador.equals(tokenAS.get(i))) {

					i = analisaVariaveis(i);
					
					if (Simbulo.sponto_vírgula.equals(tokenAS.get(i))) {
						i += 2; // Ler Proximo Token
					} else
						System.out.println("ERROR ANALISA ET VARIAVEIS 1");

				}

			} else
				System.out.println("ERROR ANALISA ET VARIAVEIS 2");
		} else
			System.out.println("ERROR ANALISA ET VARIAVEIS 3");
		return i;
	}

	public static int analisaVariaveis(int i) {
		do {
			if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
				i += 2; // Ler Proximo Token

				if (Simbulo.svírgula.equals(tokenAS.get(i)) || Simbulo.sdoispontos.equals(tokenAS.get(i))) {

					if (Simbulo.svírgula.equals(tokenAS.get(i))) {
						i += 2; // Ler Proximo Token

					} else
						System.out.println("ERROR ANALISA VARIAVEIS 3");

				} else
					System.out.println("ERROR ANALISA VARIAVEIS 2");

			} else
				System.out.println("ERROR ANALISA VARIAVEIS 1");
		} while (!Simbulo.sdoispontos.equals(tokenAS.get(i)));

		i += 2; // Ler Proximo Token

		i = analisaTipo(i);

		return i;
	}

	public static int analisaTipo(int i) {

		if (Simbulo.sinteiro.equals(tokenAS.get(i)) && Simbulo.sbooleano.equals(tokenAS.get(i))) {

			i += 2; // Ler Proximo Token

		} else
			System.out.println("ERROR ANALISA TIPO");

		return i;
	}

}
