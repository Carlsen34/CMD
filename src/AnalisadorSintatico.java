import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;

public class AnalisadorSintatico {

	static Stack tokenAS = new Stack();
	static Stack errorToken = new Stack();
	static int nivel = 0;

	public static void analisadorSintatico() {
		tokenAS = AnalisadorLexico.token;
		errorToken = AnalisadorLexico.token;
		if (tokenAS.isEmpty())
			Erro.tratarError(0);
		int i = 1;
		if (Simbulo.sprograma.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(),"nomedeprograma",nivel, null);
				i = pegarToken(i); // Ler proximo Token
				if (Simbulo.sponto_virgula.equals(tokenAS.get(i))) {
					i = analisarBloco(i);
					if (Simbulo.sponto.equals(tokenAS.get(i))) {
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
		if (Simbulo.sinicio.equals(tokenAS.get(i))) {
			i = pegarToken(i); // Ler proximo Token
			i = analisaComandoSimples(i);
			while (!Simbulo.sfim.equals(tokenAS.get(i))) {
				if (Simbulo.sponto.equals(tokenAS.get(i))) {
					fimAnalisador(i);
				}
				if (Simbulo.sponto_virgula.equals(tokenAS.get(i))) {
					i = pegarToken(i); // Ler Proximo token
					if (!Simbulo.sfim.equals(tokenAS.get(i))) {
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
		i = pegarToken(i); // ler token seguinte
		if (Simbulo.satribuicao.equals(tokenAS.get(i))) {
			if(AnalisadorSemantico.pesquisa_declvarfunc_tabela(tokenAS.get(i-3).toString(),  "var", nivel, null)){
			AnalisadorSemantico.inserirTabela(tokenAS.get(i-3).toString(), "var", nivel, null);
			}else {
				System.out.println("Erro Semantico : " + tokenAS.get(i-3).toString() );
			}
			i = analisaAtribuicao(i);

		} else {
			AnalisadorSemantico.inserirTabela(tokenAS.get(i-3).toString(), "procedimento", nivel, null);
			i = chamadaProc(i);
		}
		return i;
	}

	private static int chamadaProc(int i) {
		return i;
	}

	private static int analisaAtribuicao(int i) {
		i = pegarToken(i);
		if (Simbulo.snumero.equals(tokenAS.get(i)) || Simbulo.sidentificador.equals(tokenAS.get(i))) {
			if(Simbulo.sidentificador.equals(tokenAS.get(i))){
				//AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(), "funcao", nivel, null);
				AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(), "var", nivel, null);
			}
			i = pegarToken(i);
			while (!Simbulo.sponto_virgula.equals(tokenAS.get(i)) && !Simbulo.sfim.equals(tokenAS.get(i))) {
				i = analiseExpressao(i);
			}
		} else
			Erro.tratarError(i);
		return i;
	}

	private static int analisaSe(int i) {
		i = pegarToken(i);
		i = analiseExpressao(i);
		if (Simbulo.sentao.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			i = analisaComandoSimples(i);
			if (Simbulo.sponto_virgula.equals(tokenAS.get(i)) && Simbulo.ssenao.equals(tokenAS.get(i + 2))) {
				i = pegarToken(i);
			}
			if (Simbulo.ssenao.equals(tokenAS.get(i))) {
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
		if (Simbulo.sfaca.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			i = analisaComandoSimples(i);

		} else
			Erro.tratarError(i);
		return i;
	}

	private static int analiseExpressao(int i) {
		i = analiseExpressaoSimples(i);
		if (Simbulo.smaior.equals(tokenAS.get(i)) || Simbulo.smaiorig.equals(tokenAS.get(i))
				|| Simbulo.smenor.equals(tokenAS.get(i)) || Simbulo.smenorig.equals(tokenAS.get(i))
				|| Simbulo.sdif.equals(tokenAS.get(i)) || Simbulo.sig.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			i = analiseExpressaoSimples(i);
		}

		return i;
	}

	private static int analiseExpressaoSimples(int i) {
		if (Simbulo.smais.equals(tokenAS.get(i)) || Simbulo.smenos.equals(tokenAS.get(i))) {
			i = pegarToken(i);
		}
		i = analiseTermo(i);
		while (Simbulo.smais.equals(tokenAS.get(i)) || Simbulo.smenos.equals(tokenAS.get(i))
				|| Simbulo.sou.equals(tokenAS.get(i))) {

			i = pegarToken(i);
			i = analiseTermo(i);
		}

		return i;
	}

	private static int analiseTermo(int i) {
		i = analiseFator(i);
		while (Simbulo.smult.equals(tokenAS.get(i)) || Simbulo.sdiv.equals(tokenAS.get(i))
				|| Simbulo.sse.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			i = analiseFator(i);
		}

		return i;
	}

	private static int analiseFator(int i) {
		if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
			// Pode ser função
			if(AnalisadorSemantico.pesquisa_declvarfunc_tabela(tokenAS.get(i-1).toString(), "var", nivel, null)) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(), "var", nivel, null);
			}else {
				System.out.println("Erro Semantico + "+ tokenAS.get(i-1));
			}
			i = analisaChamadaFuncao(i);
		} else {
			if (Simbulo.snumero.equals(tokenAS.get(i))) {
				i = pegarToken(i);
			} else {
				if (Simbulo.snao.equals(tokenAS.get(i))) {
					i = pegarToken(i);
					i = analiseFator(i);
				} else {
					if (Simbulo.sabre_parenteses.equals(tokenAS.get(i))) {
						i = pegarToken(i);
						i = analiseExpressao(i);
						if (Simbulo.sfecha_parenteses.equals(tokenAS.get(i))) {
							i = pegarToken(i);
						} else
							Erro.tratarError(i);

					} else {
						if (Simbulo.sverdadeiro.equals(tokenAS.get(i)) || Simbulo.sfalso.equals(tokenAS.get(i))) {
							i = pegarToken(i);
						} else {
							if (Simbulo.smult.equals(tokenAS.get(i)) || Simbulo.sdiv.equals(tokenAS.get(i))
									|| Simbulo.sse.equals(tokenAS.get(i))) {
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
		if (Simbulo.sabre_parenteses.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
				if(AnalisadorSemantico.pesquisa_declvar_tabela(tokenAS.get(i-1).toString(), "var", nivel, null)) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(), "var", nivel, null);
				}else {
					System.out.println("Error Semantico :" + tokenAS.get(i-1));
				}
				i = pegarToken(i);
				if (Simbulo.sfecha_parenteses.equals(tokenAS.get(i))) {
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
		if (Simbulo.sabre_parenteses.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
				if(AnalisadorSemantico.pesquisa_declvar_tabela(tokenAS.get(i-1).toString(), "var", nivel, null)) {
					AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(), "var", nivel, null);
					}else {
						System.out.println("Error Semantico :" + tokenAS.get(i-1));
					}
				i = pegarToken(i);
				if (Simbulo.sfecha_parenteses.equals(tokenAS.get(i))) {
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
		if (Simbulo.sprocedimento.equals(tokenAS.get(i)) || Simbulo.sfuncao.equals(tokenAS.get(i))) {
			//
			// auxrot:= rotulo
			// GERA(´ ´,JMP,rotulo,´ ´) {Salta sub-rotinas}
			// rotulo:= rotulo + 1
			// flag = 1
			//
		}

		while (Simbulo.sprocedimento.equals(tokenAS.get(i)) || Simbulo.sfuncao.equals(tokenAS.get(i))) {
			if (Simbulo.sprocedimento.equals(tokenAS.get(i))) {
				i = analisaDeclaracaoProcedimento(i);
			} else {
				i = analisaDeclaracaoFuncao(i);
			}
			if (Simbulo.sponto_virgula.equals(tokenAS.get(i))) {
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
		if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
			
			if(AnalisadorSemantico.pesquisa_declproc_tabela(tokenAS.get(i-1).toString(), "procedimento", nivel, null)) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(), "procedimento", nivel, null);
				i = pegarToken(i); // ler proximo token
				if (Simbulo.sponto_virgula.equals(tokenAS.get(i))) {
					i = analisarBloco(i);
				} else
					Erro.tratarError(i);
			}else {
				System.out.println("Error Semantico "+ tokenAS.get(i-1));
			}
		
		} else
			Erro.tratarError(i);
		return i;
	}

	private static int analisaDeclaracaoFuncao(int i) {
		i = pegarToken(i); // ler proximo token
		if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
			if(AnalisadorSemantico.pesquisa_declfunc_tabela(tokenAS.get(i-1).toString(),"funcao", nivel, null)) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(),"funcao", nivel, null);
				i = pegarToken(i); // ler proximo token
				if (Simbulo.sdoispontos.equals(tokenAS.get(i))) {
					i = pegarToken(i); // ler proximo token
					if (Simbulo.sinteiro.equals(tokenAS.get(i)) || Simbulo.sbooleano.equals(tokenAS.get(i))) {
						i = pegarToken(i); // ler proximo token
						if (Simbulo.sponto_virgula.equals(tokenAS.get(i))) {
							i = analisarBloco(i);
						}
					} else
						Erro.tratarError(i);
				} else
					Erro.tratarError(i);
			}else
				System.out.println("Error Semantico :" + tokenAS.get(i-1));
		
		} else
			Erro.tratarError(i);

		return i;
	}

	public static int analisaEtVariaveis(int i) {

		if (Simbulo.svar.equals(tokenAS.get(i))) {
			i = pegarToken(i); // Ler Proximo Token
			if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
				while (Simbulo.sidentificador.equals(tokenAS.get(i))) {

					i = analisaVariaveis(i);
					if (Simbulo.sponto_virgula.equals(tokenAS.get(i))) {
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
			if (Simbulo.sidentificador.equals(tokenAS.get(i))) {
				if(AnalisadorSemantico.pesquisar_duplicvar_tabela(tokenAS.get(i-1).toString(), "var", nivel, null)) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i-1).toString(),"var", nivel, null);
				}else {
					System.out.println("Error Semantico : "+ tokenAS.get(i-1).toString());
				}
				i = pegarToken(i);
				if (Simbulo.svirgula.equals(tokenAS.get(i)) || Simbulo.sdoispontos.equals(tokenAS.get(i))) {
					if (Simbulo.svirgula.equals(tokenAS.get(i))) {
						i = pegarToken(i);
						if (Simbulo.sdoispontos.equals(tokenAS.get(i))) {
							Erro.tratarError(i);
						}
					}
				} else
					Erro.tratarError(i);
			} else
				Erro.tratarError(i);
		} while (!Simbulo.sdoispontos.equals(tokenAS.get(i)));

		i = pegarToken(i); // Ler Proximo Token

		i = analisaTipo(i);

		return i;
	}

	public static int analisaTipo(int i) {

		if (Simbulo.sinteiro.equals(tokenAS.get(i)) || Simbulo.sbooleano.equals(tokenAS.get(i))) {

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
