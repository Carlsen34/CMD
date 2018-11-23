import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;

public class AnalisadorSintatico {

	static Stack tokenAS = new Stack();
	static Stack errorToken = new Stack();
	static int nivel = 0;
	static 	String tipo;

	public static void analisadorSintatico() {
		tokenAS = AnalisadorLexico.token;
		errorToken = AnalisadorLexico.token;
		if (tokenAS.isEmpty())
			Erro.tratarError(0);
		int i = 1;
		if (Simbolo.sprograma.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i - 1).toString(), "nomedeprograma", nivel, "");
				i = pegarToken(i); // Ler proximo Token
				if (Simbolo.sponto_virgula.equals(tokenAS.get(i))) {
					i = analisarBloco(i);
					if (Simbolo.sponto.equals(tokenAS.get(i))) {
						fimAnalisador(i);
					} else
						Erro.tratarError(i);
				} else
					Erro.tratarError(i);
			} else
				Erro.tratarError(i);
		} else
			Erro.tratarError(i);
	}

	public static int analisarBloco(int i) {
		i = pegarToken(i); // Ler Proximo Token
		i = analisaEtVariaveis(i);
		i = analisaSubrotinas(i);
		i = analisaComandos(i);

		return i;

	}

	private static int analisaComandos(int i) {
		if (Simbolo.sinicio.equals(tokenAS.get(i))) {
			i = pegarToken(i); // Ler proximo Token
			i = analisaComandoSimples(i);
			while (!Simbolo.sfim.equals(tokenAS.get(i))) {
				if (Simbolo.sponto.equals(tokenAS.get(i))) {
					fimAnalisador(i);
				}
				if (Simbolo.sponto_virgula.equals(tokenAS.get(i))) {
					i = pegarToken(i); // Ler Proximo token
					if (!Simbolo.sfim.equals(tokenAS.get(i))) {
						i = analisaComandoSimples(i);
					}
				} else {
					Erro.tratarError(i);
				}
			}
			i = pegarToken(i); // Ler Proximo token

		} else
			Erro.tratarError(i);
		return i;
	}

	private static void fimAnalisador(int i) {
		tokenAS.clear();
		Erro.tratarError(i);
		JOptionPane.showMessageDialog(null, "Codigo Compilado Com Sucesso");
		AnalisadorSemantico.printarTS();
		System.exit(0);
	}

	private static int analisaComandoSimples(int i) {
		AnalisadorSemantico.validar_tipoAUX();

		if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
			i = analisaAtribChProcedimento(i);
		} else {
			if (Simbolo.sse.equals(tokenAS.get(i))) {
				i = analisaSe(i);
			} else {
				if (Simbolo.senquanto.equals(tokenAS.get(i))) {
					i = analisaEnquanto(i);
				} else {
					if (Simbolo.sleia.equals(tokenAS.get(i))) {
						i = analisaLeia(i);
					} else {
						if (Simbolo.sescreva.equals(tokenAS.get(i))) {
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
		tipo = AnalisadorSemantico.retorna_tipo(tokenAS.get(i-1).toString());
		AnalisadorSemantico.validar_tipo(tokenAS.get(i - 1).toString(),tipo);
		i = pegarToken(i); // ler token seguinte
		if (Simbolo.satribuicao.equals(tokenAS.get(i))) {
			if (AnalisadorSemantico.pesquisa_declvarfunc_tabela(tokenAS.get(i - 3).toString(), "var", nivel, "")) {
				// AnalisadorSemantico.inserirTabela(tokenAS.get(i-3).toString(), "var", nivel,
				// "");
			} else {
				System.out.println("Erro Semantico : " + tokenAS.get(i - 3).toString());
			}
			i = analisaAtribuicao(i);

		} else {
			// AnalisadorSemantico.inserirTabela(tokenAS.get(i-3).toString(),
			// "procedimento", nivel, "");
			i = chamadaProc(i);
		}
		return i;
	}

	private static int chamadaProc(int i) {
		System.out.println(tokenAS.get(i - 3));
		if (AnalisadorSemantico.pesquisa_declproc_tabela(tokenAS.get(i - 3).toString(), "procedimento", 0, null)) {
			System.out.println("Erro Semantico: Não existe procedimento declarado");
		} else {
		}
		return i;
	}

	private static int analisaAtribuicao(int i) {
		i = pegarToken(i);
		tipo = AnalisadorSemantico.retorna_tipo(tokenAS.get(i-1).toString());
		AnalisadorSemantico.validar_tipo(tokenAS.get(i - 1).toString(),tipo);
		if (Simbolo.snumero.equals(tokenAS.get(i)) || Simbolo.sidentificador.equals(tokenAS.get(i))) {
			if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
				// AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(), "funcao",
				// nivel, "");
				// AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(), "var", nivel,
				// "");
			}
			i = pegarToken(i);
			while (!Simbolo.sponto_virgula.equals(tokenAS.get(i)) && !Simbolo.sfim.equals(tokenAS.get(i))) {
				i = analiseExpressao(i);
			}
		} else
			Erro.tratarError(i);
		return i;
	}

	private static int analisaSe(int i) {
		i = pegarToken(i);
		i = analiseExpressao(i);
		if (Simbolo.sentao.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			i = analisaComandoSimples(i);
			if (Simbolo.sponto_virgula.equals(tokenAS.get(i)) && Simbolo.ssenao.equals(tokenAS.get(i + 2))) {
				i = pegarToken(i);
			}
			if (Simbolo.ssenao.equals(tokenAS.get(i))) {
				i = pegarToken(i);
				i = analisaComandoSimples(i);

			}

		} else
			Erro.tratarError(i);
		return i;
	}

	private static int analisaEnquanto(int i) {
		i = pegarToken(i);
		i = analiseExpressao(i);
		if (Simbolo.sfaca.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			i = analisaComandoSimples(i);

		} else
			Erro.tratarError(i);
		return i;
	}

	private static int analiseExpressao(int i) {
		i = analiseExpressaoSimples(i);
		if (Simbolo.smaior.equals(tokenAS.get(i)) || Simbolo.smaiorig.equals(tokenAS.get(i))
				|| Simbolo.smenor.equals(tokenAS.get(i)) || Simbolo.smenorig.equals(tokenAS.get(i))
				|| Simbolo.sdif.equals(tokenAS.get(i)) || Simbolo.sig.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			i = analiseExpressaoSimples(i);
		}

		return i;
	}

	private static int analiseExpressaoSimples(int i) {
		if (Simbolo.smais.equals(tokenAS.get(i)) || Simbolo.smenos.equals(tokenAS.get(i))) {
			i = pegarToken(i);
		}
		i = analiseTermo(i);
		while (Simbolo.smais.equals(tokenAS.get(i)) || Simbolo.smenos.equals(tokenAS.get(i))
				|| Simbolo.sou.equals(tokenAS.get(i))) {

			i = pegarToken(i);
			i = analiseTermo(i);
		}

		return i;
	}

	private static int analiseTermo(int i) {
		i = analiseFator(i);
		while (Simbolo.smult.equals(tokenAS.get(i)) || Simbolo.sdiv.equals(tokenAS.get(i))
				|| Simbolo.sse.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			i = analiseFator(i);
		}

		return i;
	}

	private static int analiseFator(int i) {
		tipo = AnalisadorSemantico.retorna_tipo(tokenAS.get(i-1).toString());
		AnalisadorSemantico.validar_tipo(tokenAS.get(i - 1).toString(),tipo);
		if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
			// Pode ser função
			if (AnalisadorSemantico.pesquisa_declvarfunc_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
				// AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(), "var", nivel,
				// "");
			} else {
				System.out.println("Erro Semantico : " + tokenAS.get(i - 1));
			}
			i = analisaChamadaFuncao(i);
		} else {
			if (Simbolo.snumero.equals(tokenAS.get(i))) {
				i = pegarToken(i);
			} else {
				if (Simbolo.snao.equals(tokenAS.get(i))) {
					i = pegarToken(i);
					i = analiseFator(i);
				} else {
					if (Simbolo.sabre_parenteses.equals(tokenAS.get(i))) {
						i = pegarToken(i);
						i = analiseExpressao(i);
						if (Simbolo.sfecha_parenteses.equals(tokenAS.get(i))) {
							i = pegarToken(i);
						} else
							Erro.tratarError(i);

					} else {
						if (Simbolo.sverdadeiro.equals(tokenAS.get(i)) || Simbolo.sfalso.equals(tokenAS.get(i))) {
							i = pegarToken(i);
						} else {
							if (Simbolo.smult.equals(tokenAS.get(i)) || Simbolo.sdiv.equals(tokenAS.get(i))
									|| Simbolo.sse.equals(tokenAS.get(i))) {
								return i;
							} else
								Erro.tratarError(i);
						}
					}
				}
			}
		}

		return i;
	}

	private static int analisaChamadaFuncao(int i) {
		i = pegarToken(i);
		return i;
	}

	private static int analisaLeia(int i) {
		i = pegarToken(i);
		if (Simbolo.sabre_parenteses.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
				if (AnalisadorSemantico.pesquisa_declvar_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
					// AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(), "var", nivel,
					// "");
				} else {
					System.out.println("Erro Semantico :" + tokenAS.get(i - 1));
				}
				i = pegarToken(i);
				if (Simbolo.sfecha_parenteses.equals(tokenAS.get(i))) {
					i = pegarToken(i);
				}

			} else
				Erro.tratarError(i);

		} else
			Erro.tratarError(i);

		return i;
	}

	private static int analisaEscreva(int i) {
		i = pegarToken(i);
		if (Simbolo.sabre_parenteses.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
				if (AnalisadorSemantico.pesquisa_declvar_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
					// AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(), "var", nivel,
					// "");
				} else {
					System.out.println("Erro Semantico :" + tokenAS.get(i - 1));
				}
				i = pegarToken(i);
				if (Simbolo.sfecha_parenteses.equals(tokenAS.get(i))) {
					i = pegarToken(i);
				} else
					Erro.tratarError(i);
			} else
				Erro.tratarError(i);
		} else
			Erro.tratarError(i);
		return i;
	}

	private static int analisaSubrotinas(int i) {
		int flag = 0;
		if (Simbolo.sprocedimento.equals(tokenAS.get(i)) || Simbolo.sfuncao.equals(tokenAS.get(i))) {
			//
			// auxrot:= rotulo
			// GERA(´ ´,JMP,rotulo,´ ´) {Salta sub-rotinas}
			// rotulo:= rotulo + 1
			// flag = 1
			//
		}

		while (Simbolo.sprocedimento.equals(tokenAS.get(i)) || Simbolo.sfuncao.equals(tokenAS.get(i))) {
			if (Simbolo.sprocedimento.equals(tokenAS.get(i))) {
				i = analisaDeclaracaoProcedimento(i);
			} else {
				i = analisaDeclaracaoFuncao(i);
			}
			if (Simbolo.sponto_virgula.equals(tokenAS.get(i))) {
				i = pegarToken(i);
			} else
				Erro.tratarError(i);
		}
		if (flag == 1) {
		}
		return i;
	}

	private static int analisaDeclaracaoProcedimento(int i) {
		i = pegarToken(i); // ler proximo token
		if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
			if (AnalisadorSemantico.pesquisa_declproc_tabela(tokenAS.get(i - 1).toString(), "procedimento", nivel,
					"")) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i - 1).toString(), "procedimento", nivel, "");
				nivel++;
				i = pegarToken(i); // ler proximo token
				if (Simbolo.sponto_virgula.equals(tokenAS.get(i))) {
					i = analisarBloco(i);
				} else
					Erro.tratarError(i);
			} else {
				System.out.println("Erro Semantico " + tokenAS.get(i - 1));
			}

		} else
			Erro.tratarError(i);
		nivel--;
		return i;
	}

	private static int analisaDeclaracaoFuncao(int i) {
		i = pegarToken(i); // ler proximo token
		if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
			if (AnalisadorSemantico.pesquisa_declfunc_tabela(tokenAS.get(i - 1).toString(), "funcao", nivel, "")) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i - 1).toString(), "funcao", nivel, "");
				i = pegarToken(i); // ler proximo token
				if (Simbolo.sdoispontos.equals(tokenAS.get(i))) {
					i = pegarToken(i); // ler proximo token
					if (Simbolo.sinteiro.equals(tokenAS.get(i)) || Simbolo.sbooleano.equals(tokenAS.get(i))) {
						AnalisadorSemantico.coloca_tipo_tabela(null, "funcao", i, null, tokenAS.get(i).toString());
						i = pegarToken(i); // ler proximo token
						if (Simbolo.sponto_virgula.equals(tokenAS.get(i))) {
							i = analisarBloco(i);
						}
					} else
						Erro.tratarError(i);
				} else
					Erro.tratarError(i);
			} else
				System.out.println("Erro Semantico : " + tokenAS.get(i - 1));

		} else
			Erro.tratarError(i);

		return i;
	}

	public static int analisaEtVariaveis(int i) {

		if (Simbolo.svar.equals(tokenAS.get(i))) {
			i = pegarToken(i); // Ler Proximo Token
			if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
				while (Simbolo.sidentificador.equals(tokenAS.get(i))) {

					i = analisaVariaveis(i);
					if (Simbolo.sponto_virgula.equals(tokenAS.get(i))) {
						i = pegarToken(i); // Ler Proximo Token
					} else
						Erro.tratarError(i);
				}
			} else
				Erro.tratarError(i);
		}
		return i;
	}

	public static int analisaVariaveis(int i) {

		do {
			if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
				if (AnalisadorSemantico.pesquisar_duplicvar_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
					AnalisadorSemantico.inserirTabela(tokenAS.get(i - 1).toString(), "var", nivel, "");
				} else {
					System.out.println("Erro Semantico : " + tokenAS.get(i - 1).toString());
				}
				i = pegarToken(i);
				if (Simbolo.svirgula.equals(tokenAS.get(i)) || Simbolo.sdoispontos.equals(tokenAS.get(i))) {
					if (Simbolo.svirgula.equals(tokenAS.get(i))) {
						i = pegarToken(i);
						if (Simbolo.sdoispontos.equals(tokenAS.get(i))) {
							Erro.tratarError(i);
						}
					}
				} else
					Erro.tratarError(i);
			} else
				Erro.tratarError(i);
		} while (!Simbolo.sdoispontos.equals(tokenAS.get(i)));

		i = pegarToken(i); // Ler Proximo Token

		i = analisaTipo(i);

		return i;
	}

	public static int analisaTipo(int i) {

		if (Simbolo.sinteiro.equals(tokenAS.get(i)) || Simbolo.sbooleano.equals(tokenAS.get(i))) {
			AnalisadorSemantico.coloca_tipo_tabela(null, "var", i, null, tokenAS.get(i).toString());
			i = pegarToken(i); // Ler Proximo Token

		} else {
			Erro.tratarError(i);

		}
		return i;
	}

	public static int pegarToken(int i) {
		i += 2;
		return i;
	}
}
