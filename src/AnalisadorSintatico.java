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
		i = analisaSubrotinas(i);
		i = analisaComandos(i);

		/*
		 * Analisa_subrotinas Analisa_comandos
		 */

		return i;

	}

	private static int analisaComandos(int i) {

		if (Simbulo.sinício.equals(tokenAS.get(i))) {
			i += 2; // Ler proximo Token

			i = analisaComandoSimples(i);

			while (!Simbulo.sfim.equals(tokenAS.get(i))) {
				if (Simbulo.sponto_vírgula.equals(tokenAS.get(i))) {
					i += 2; // Ler Proximo token
					if (!Simbulo.sfim.equals(tokenAS.get(i))) {
						i = analisaComandoSimples(i);
					} else
						System.out.println("ERROR ANALISA COMANDOS 3");

				}
				System.out.println("ERROR ANALISA COMANDOS 2");
			}

		} else
			System.out.println("ERROR ANALISA COMANDOS");

		return i;
	}

	private static int analisaComandoSimples(int i) {

		if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
			i = analisaAtribChProcedimento(i);
		} else {
			if (Simbulo.sse.equals(tokenAS.get(i))) {
				i = analisaSe(i);
			} else {
				if (Simbulo.senquanto.equals(tokenAS.get(i))) {
					i = analisaEnquanto(i);
				} else {

					if (Simbulo.sleia.equals(tokenAS.get(i))) {
						i = analisaLeia(i);
					} else {
						if (Simbulo.sescreva.equals(tokenAS.get(i))) {
							i = analisaEscreva(i);
						} else {

							i = analisaComandos(i);
						}
					}

				}
			}
		}

		return i;
	}

	private static int analisaAtribChProcedimento(int i) {
		i += 2; // ler token seguinte
		if (Simbulo.satribuição.equals(tokenAS.get(i))) {
			i = analisaAtribuicao(i);

		} else {
			i = chamadaProc(i);
		}

		return i;
	}

	private static int chamadaProc(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static int analisaAtribuicao(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static int analisaSe(int i) {

		i += 2;

		if (Simbulo.sentao.equals(tokenAS.get(i))) {
			i += 2;

			i = analisaComandoSimples(i);

			if (Simbulo.ssenao.equals(tokenAS.get(i))) {
				i += 2;

				i = analisaComandoSimples(i);

			}
			System.out.println("ERROR ANALISE SE 2");

		} else
			System.out.println("ERROR ANALISE SE 1");

		return i;
	}

	private static int analisaEnquanto(int i) {

		i += 2;

		i = analiseExpressao(i);

		if (Simbulo.sfaca.equals(tokenAS.get(i))) {
			i += 2;
			i = analisaComandoSimples(i);

		} else
			System.out.println("ERROR Enquanto");

		return i;
	}

	private static int analiseExpressao(int i) {

		i = analiseExpressaoSimples(i);
		if (Simbulo.smaior.equals(tokenAS.get(i)) || Simbulo.smaiorig.equals(tokenAS.get(i)) || Simbulo.smenor.equals(tokenAS.get(i)) 
		|| Simbulo.smenorig.equals(tokenAS.get(i)) || Simbulo.sdif.equals(tokenAS.get(i)) || Simbulo.sig.equals(tokenAS.get(i))) {
			
			i += 2;
				i = analiseExpressaoSimples(i);
			
			
		}else System.out.println("ERROR Analise Expressao");
		
		return i;
	}

	private static int analiseExpressaoSimples(int i) {
		if(Simbulo.smais.equals(tokenAS.get(i)) ||  Simbulo.smenos.equals(tokenAS.get(i))){
			
			
		}else System.out.println("Error Analise Expressão Simples");
		
		return 0;
	}

	private static int analisaLeia(int i) {
		i += 2;
		if (Simbulo.sabre_parênteses.equals(tokenAS.get(i))) {
			i += 2;
			if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
				i += 2;
				if (Simbulo.sfecha_parênteses.equals(tokenAS.get(i))) {
					i += 2;
				}

			} else
				System.out.println("ERROR LEIA2");
		} else
			System.out.println("ERROR LEIA1");

		return i;
	}

	private static int analisaEscreva(int i) {

		i += 2;
		if (Simbulo.sabre_parênteses.equals(tokenAS.get(i))) {
			i += 2;
			if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
				if (Simbulo.sfecha_parênteses.equals(tokenAS.get(i))) {
					i += 2;
				} else
					System.out.println("ERROR ESCREVA3");
			} else
				System.out.println("ERROR ESCREVA2");
		} else
			System.out.println("ERROR ESCREVA1");

		return i;
	}

	private static int analisaSubrotinas(int i) {

		int flag = 0;

		if (Simbulo.sprocedimento.equals(tokenAS.get(i)) || Simbulo.sfuncao.equals(tokenAS.get(i))) {

		} else
			System.out.println("ERROR SUBROTINA");

		while (Simbulo.sprocedimento.equals(tokenAS.get(i)) || Simbulo.sfuncao.equals(tokenAS.get(i))) {

			if (Simbulo.sprocedimento.equals(tokenAS.get(i))) {

				i = analisaDeclaracaoProcedimento(i);

			} else {

				i = analisaDeclaracaoFuncao(i);
			}

			if (Simbulo.sponto_vírgula.equals(tokenAS.get(i))) {

				i += 2; // ler Proximo Token

			} else
				System.out.println("ERROR SUBROTINA 2");

		}

		return i;
	}

	private static int analisaDeclaracaoProcedimento(int i) {
		i += 2; // ler proximo token
		if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
			i += 2; // ler proximo token
			if (Simbulo.sponto_vírgula.equals(tokenAS.get(i))) {
				i = analisarBloco(i);

			}
			System.out.println("ERROR DEC PROC 2");

		}
		System.out.println("ERROR DEC PROC 1");

		return i;
	}

	private static int analisaDeclaracaoFuncao(int i) {
		i += 2; // ler proximo token

		if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
			i += 2; // ler proximo token
			if (Simbulo.sdoispontos.equals(tokenAS.get(i))) {
				i += 2; // ler proximo token

				if (Simbulo.sinteiro.equals(tokenAS.get(i)) || Simbulo.sbooleano.equals(tokenAS.get(i))) {
					i += 2; // ler proximo token
					if (Simbulo.sponto_vírgula.equals(tokenAS.get(i))) {

						i = analisarBloco(i);

					}
					System.out.println("ERROR DECLARACAO FUNC 4");

				}
				System.out.println("ERROR DECLARACAO FUNC 3");

			} else
				System.out.println("ERROR DECLARACAO FUNC");

		} else
			System.out.println("ERROR DECLARACAO FUNC 2");

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
