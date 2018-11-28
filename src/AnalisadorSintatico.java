import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;

public class AnalisadorSintatico {

	static Stack tokenAS = new Stack();
	static Stack errorToken = new Stack();
	static int nivel = 0;
	static String tipo;
	static int rotulo = 1;
	static int allocAux1 = 0;
	static int allocAux3 = 0;
	static int countAlloc = 0;
	static int nivelAux = 0;
	static List<AllocDTO> allocAux = new ArrayList<AllocDTO>();


	public static void analisadorSintatico() {
		String aux1 ="";
		String aux2 ="";
		tokenAS = AnalisadorLexico.token;
		errorToken = AnalisadorLexico.token;
		if (tokenAS.isEmpty())
			Erro.tratarError(0);
		int i = 1;
		if (Simbolo.sprograma.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i - 1).toString(), "nomedeprograma", nivel, "");
				GeradorCodigo.exibir_codigo_objeto("", "START", "", "");
				i = pegarToken(i); // Ler proximo Token
				if (Simbolo.sponto_virgula.equals(tokenAS.get(i))) {
					i = analisarBloco(i);
					if (Simbolo.sponto.equals(tokenAS.get(i))) {
						for(int a =0 ; a< allocAux.size();a++) {
							if(allocAux.get(a).getNivel() == nivel) {
								 aux1 = allocAux.get(a).getParam1();
								 aux2 = allocAux.get(a).getParam2();
							}
						}
						if(!aux1.equals(""))GeradorCodigo.exibir_codigo_objeto("", "DALLOC", aux1, aux2);
						GeradorCodigo.exibir_codigo_objeto("", "HLT", "", "");
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
		System.out.println(GeradorCodigo.programaObjeto);
		//System.exit(0);

	System.out.println("Press Enter to continue...");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Vm();
		
	
	}

	private static int analisaComandoSimples(int i) {
		AnalisadorSemantico.validar_tipoAUX();
		String aux = "";
		int aux1 = 0;
		if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
			aux = tokenAS.get(i - 1).toString();
			i = analisaAtribChProcedimento(i);
			aux1 = GeradorCodigo.returnIndex(aux);
			if (!(aux1 == -1))
				GeradorCodigo.exibir_codigo_objeto("", "STR", Integer.toString(aux1), "");

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
		tipo = AnalisadorSemantico.retorna_tipo(tokenAS.get(i - 1).toString());
		AnalisadorSemantico.validar_tipo(tokenAS.get(i - 1).toString(), tipo);
		int index = 0;
		String rotuloAux = "";
		i = pegarToken(i); // ler token seguinte
		if (Simbolo.satribuicao.equals(tokenAS.get(i))) {
			if (AnalisadorSemantico.pesquisa_declvarfunc_tabela(tokenAS.get(i - 3).toString(), "var", nivel, "")) {

			} else {
				AnalisadorSemantico.errorToken.add(tokenAS.get(i - 3).toString());
			}
			i = analisaAtribuicao(i);
			if (!AnalisadorSemantico.pesquisa_declfunc_tabela(tokenAS.get(i - 3).toString(), "funcao", nivel, "")) {
				rotuloAux = GeradorCodigo.returnRotulo(tokenAS.get(i - 3).toString());
				GeradorCodigo.exibir_codigo_objeto("", "CALL", rotuloAux, "");
			}

		} else {
			i = chamadaProc(i);
		}
		return i;
	}

	private static int chamadaProc(int i) {
		String rotuloAux = "";
		if (AnalisadorSemantico.pesquisa_declproc_tabela(tokenAS.get(i - 3).toString(), "procedimento", 0, null)) {
			AnalisadorSemantico.errorToken.add(tokenAS.get(i - 3).toString());
		} else {
			rotuloAux = GeradorCodigo.returnRotulo(tokenAS.get(i - 3).toString());
			GeradorCodigo.exibir_codigo_objeto("", "CALL", rotuloAux, "");
		}

		return i;
	}

	private static int analisaAtribuicao(int i) {
		i = pegarToken(i);
		i = analiseExpressao(i);
		return i;
	}

	private static int analisaSe(int i) {
		int auxrot = rotulo;
		int auxrot2 = rotulo;
		i = pegarToken(i);
		i = analiseExpressao(i);
		if (Simbolo.sentao.equals(tokenAS.get(i))) {
			GeradorCodigo.exibir_codigo_objeto("", "JMPF", "L" + Integer.toString(auxrot), "");
			rotulo++;
			i = pegarToken(i);
			i = analisaComandoSimples(i);
			auxrot2 = rotulo;
			GeradorCodigo.exibir_codigo_objeto("", "JMP", "L" + Integer.toString(auxrot2), "");
			rotulo++;
			if (Simbolo.sponto_virgula.equals(tokenAS.get(i)) && Simbolo.ssenao.equals(tokenAS.get(i + 2))) {
				i = pegarToken(i);
			}
			if (Simbolo.ssenao.equals(tokenAS.get(i))) {
				GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(auxrot), "NULL", "", "");
				i = pegarToken(i);
				i = analisaComandoSimples(i);

			}else
			GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(auxrot), "NULL", "", "");

		} else
			Erro.tratarError(i);

		GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(auxrot2), "NULL", "", "");
		return i;
	}

	private static int analisaEnquanto(int i) {
		int auxrot1, auxrot2;
		auxrot1 = rotulo;
		GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(rotulo), "NULL", "", "");
		rotulo++;

		i = pegarToken(i);
		i = analiseExpressao(i);
		if (Simbolo.sfaca.equals(tokenAS.get(i))) {
			auxrot2 = rotulo;
			GeradorCodigo.exibir_codigo_objeto("", "JMPF", "L" + Integer.toString(rotulo), "");
			rotulo++;
			i = pegarToken(i);
			i = analisaComandoSimples(i);
			GeradorCodigo.exibir_codigo_objeto("", "JMP", "L" + Integer.toString(auxrot1), "");
			GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(auxrot2), "NULL", "", "");

		} else
			Erro.tratarError(i);
		return i;
	}

	private static int analiseExpressao(int i) {
		i = analiseExpressaoSimples(i);
		String aux = "";
		if (Simbolo.smaior.equals(tokenAS.get(i)) || Simbolo.smaiorig.equals(tokenAS.get(i))
				|| Simbolo.smenor.equals(tokenAS.get(i)) || Simbolo.smenorig.equals(tokenAS.get(i))
				|| Simbolo.sdif.equals(tokenAS.get(i)) || Simbolo.sig.equals(tokenAS.get(i))) {

			if (Simbolo.smaior.equals(tokenAS.get(i)))
				aux = "CMA";
			if (Simbolo.smaiorig.equals(tokenAS.get(i)))
				aux = "CMAQ";
			if (Simbolo.smenor.equals(tokenAS.get(i)))
				aux = "CME";
			if (Simbolo.smenorig.equals(tokenAS.get(i)))
				aux = "CMEQ";
			if (Simbolo.sdif.equals(tokenAS.get(i)))
				aux = "CDIF";
			if (Simbolo.sig.equals(tokenAS.get(i)))
				aux = "CEQ";

			i = pegarToken(i);
			i = analiseExpressaoSimples(i);

			GeradorCodigo.exibir_codigo_objeto("", aux, "", "");
		}

		return i;
	}

	private static int analiseExpressaoSimples(int i) {
		String aux = "";
		if (Simbolo.smais.equals(tokenAS.get(i)) || Simbolo.smenos.equals(tokenAS.get(i))) {
			if (Simbolo.smais.equals(tokenAS.get(i)))
				aux = "ADD";
			if (Simbolo.smenos.equals(tokenAS.get(i)))
				aux = "SUB";
			i = pegarToken(i);

		}
		i = analiseTermo(i);
		if (aux != "")
			GeradorCodigo.exibir_codigo_objeto("", aux, "", "");

		while (Simbolo.smais.equals(tokenAS.get(i)) || Simbolo.smenos.equals(tokenAS.get(i))
				|| Simbolo.sou.equals(tokenAS.get(i))) {
			if (Simbolo.smais.equals(tokenAS.get(i)))
				aux = "ADD";
			if (Simbolo.smenos.equals(tokenAS.get(i)))
				aux = "SUB";
			if (Simbolo.sou.equals(tokenAS.get(i)))
				aux = "OR";

			i = pegarToken(i);
			i = analiseTermo(i);
			if (aux != "")
				GeradorCodigo.exibir_codigo_objeto("", aux, "", "");

		}

		return i;
	}

	private static int analiseTermo(int i) {
		i = analiseFator(i);
		String aux = "";
		while (Simbolo.smult.equals(tokenAS.get(i)) || Simbolo.sdiv.equals(tokenAS.get(i))
				|| Simbolo.sse.equals(tokenAS.get(i))) {
			if (Simbolo.smult.equals(tokenAS.get(i)))
				aux = "MULT";
			if (Simbolo.sdiv.equals(tokenAS.get(i)))
				aux = "DIVI";
			if (Simbolo.sse.equals(tokenAS.get(i)))
				aux = "AND";

			i = pegarToken(i);

			i = analiseFator(i);

			GeradorCodigo.exibir_codigo_objeto("", aux, "", "");

		}

		return i;
	}

	private static int analiseFator(int i) {
		tipo = AnalisadorSemantico.retorna_tipo(tokenAS.get(i - 1).toString());
		AnalisadorSemantico.validar_tipo(tokenAS.get(i - 1).toString(), tipo);
		int aux;
		if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
			// Pode ser função
			if (AnalisadorSemantico.pesquisa_declvarfunc_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
				aux = GeradorCodigo.returnIndex(tokenAS.get(i - 1).toString());
				if (aux != -1)
					GeradorCodigo.exibir_codigo_objeto("", "LDV", Integer.toString(aux), "");
			} else {
				AnalisadorSemantico.errorToken.add(tokenAS.get(i - 1).toString());

			}
			i = analisaChamadaFuncao(i);
		} else {
			if (Simbolo.snumero.equals(tokenAS.get(i))) {
				GeradorCodigo.exibir_codigo_objeto("", "LDC", tokenAS.get(i - 1).toString(), "");
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
							AnalisadorSemantico.validar_tipoAUX();
							if (Simbolo.sverdadeiro.equals(tokenAS.get(i)))
								GeradorCodigo.exibir_codigo_objeto("", "LDC", "1", "");
							else
								GeradorCodigo.exibir_codigo_objeto("", "LDC", "0", "");
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
		int aux = 0;
		if (Simbolo.sabre_parenteses.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
				if (AnalisadorSemantico.pesquisa_declvar_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
					GeradorCodigo.exibir_codigo_objeto("", "RD", "", "");
					aux = GeradorCodigo.returnIndex(tokenAS.get(i - 1).toString());
					GeradorCodigo.exibir_codigo_objeto("", "STR", Integer.toString(aux), "");

				} else {
					AnalisadorSemantico.errorToken.add(tokenAS.get(i - 1).toString());

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
		int auxIndex = 0;
		String rotuloAux = "";
		if (Simbolo.sabre_parenteses.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
				if (AnalisadorSemantico.pesquisa_declvar_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
					auxIndex = GeradorCodigo.returnIndex(tokenAS.get(i - 1).toString());
					GeradorCodigo.exibir_codigo_objeto("", "LDV", Integer.toString(auxIndex), "");
					GeradorCodigo.exibir_codigo_objeto("", "PRN", "", "");
				} else {
					if (AnalisadorSemantico.pesquisa_declfunc_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
						rotuloAux = GeradorCodigo.returnRotulo(tokenAS.get(i - 1).toString());
						GeradorCodigo.exibir_codigo_objeto("", "CALL", rotuloAux, "");
						GeradorCodigo.exibir_codigo_objeto("", "PRN", "", "");
					} else
						AnalisadorSemantico.errorToken.add(tokenAS.get(i - 1).toString());

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
		int auxrot = rotulo;
		;
		if (Simbolo.sprocedimento.equals(tokenAS.get(i)) || Simbolo.sfuncao.equals(tokenAS.get(i))) {
			GeradorCodigo.exibir_codigo_objeto("", "JMP", "L" + Integer.toString(auxrot), "");
			rotulo++;
			flag = 1;

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
			GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(auxrot), "NULL", "", "");

		}

		return i;
	}

	private static int analisaDeclaracaoProcedimento(int i) {
		String aux1 ="";
		String aux2 ="";
		i = pegarToken(i); // ler proximo token
		if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
			if (AnalisadorSemantico.pesquisa_declproc_tabela(tokenAS.get(i - 1).toString(), "procedimento", nivel,
					"")) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i - 1).toString(), "procedimento", nivel,
						"L" + Integer.toString(rotulo));
				GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(rotulo), "NULL", "", "");
				nivel++;
				rotulo++;
				i = pegarToken(i); // ler proximo token
				if (Simbolo.sponto_virgula.equals(tokenAS.get(i))) {
					i = analisarBloco(i);
				} else
					Erro.tratarError(i);
			} else {
				AnalisadorSemantico.errorToken.add(tokenAS.get(i - 1).toString());

			}

		} else
			Erro.tratarError(i);
		AnalisadorSemantico.remover_nivel_simbolos(nivel);

		for(int a =0 ; a< allocAux.size();a++) {
			if(allocAux.get(a).getNivel() == nivel) {
				 aux1 = allocAux.get(a).getParam1();
				 aux2 = allocAux.get(a).getParam2();
			}
		}
		if(!aux1.equals(""))GeradorCodigo.exibir_codigo_objeto("", "DALLOC", aux1, aux2);
		nivel--;

		GeradorCodigo.exibir_codigo_objeto("", "RETURN", "", "");

		return i;
	}

	private static int analisaDeclaracaoFuncao(int i) {
		i = pegarToken(i); // ler proximo token
		if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
			if (AnalisadorSemantico.pesquisa_declfunc_tabela(tokenAS.get(i - 1).toString(), "funcao", nivel, "")) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i - 1).toString(), "funcao", nivel,
						"L" + Integer.toString(rotulo));
				GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(rotulo), "NULL", "", "");
				nivel++;
				rotulo++;
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
				AnalisadorSemantico.errorToken.add(tokenAS.get(i - 1).toString());

		} else
			Erro.tratarError(i);

		AnalisadorSemantico.remover_nivel_simbolos(nivel);
		nivel--;
		String aux1;
		String aux2;

		aux1 = GeradorCodigo.auxDalloc.pop().toString();
		aux2 = GeradorCodigo.auxDalloc.pop().toString();

		GeradorCodigo.exibir_codigo_objeto("", "RETURNF", aux2, aux1);

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
		int allocAux2 = 0;

		do {
			if (Simbolo.sidentificador.equals(tokenAS.get(i))) {
				GeradorCodigo.pilhaVar.add(tokenAS.get(i - 1));

				if (AnalisadorSemantico.pesquisar_duplicvar_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
					AnalisadorSemantico.inserirTabela(tokenAS.get(i - 1).toString(), "var", nivel, "");
					allocAux2++;
				} else {
					AnalisadorSemantico.errorToken.add(tokenAS.get(i - 1).toString());

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
		countAlloc++;
		AllocDTO alloc = new AllocDTO();
		alloc.setParam1(Integer.toString(allocAux1));
		alloc.setParam2(Integer.toString(allocAux2));
		alloc.setNivel(nivel);
		allocAux.add(alloc);

		//allocAux 
		GeradorCodigo.exibir_codigo_objeto("", "ALLOC", Integer.toString(allocAux1+allocAux3), Integer.toString(allocAux2));
		allocAux1 = allocAux1 + allocAux2;

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
