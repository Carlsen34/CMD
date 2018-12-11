import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;

public class AnalisadorSintatico {

	static Stack tokenAS = new Stack();
	static Stack tokenASerror = new Stack();
	static int nivel = 0;
	static String tipo = "";
	static int rotulo = 1;
	static int allocAux1 = 0;
	static int countAlloc = 0;
	static int nivelAux = 0;
	static List<AllocDTO> allocAux = new ArrayList<AllocDTO>();
	static int countDalloc = 0;
	static boolean flgExpressaoBooleana = true;
	static int contadorPegaToken = 0;

	public static void analisadorSintatico1(List<Token> token) {
		try {
		tokenAS.push(token.get(contadorPegaToken).getLexema());
		tokenAS.push(token.get(contadorPegaToken).getSimbolo());
		tokenASerror.push(token.get(contadorPegaToken).getNumLinha());
		tokenASerror.push(token.get(contadorPegaToken).getNumLinha());
		analisadorSintatico();		
		}catch(ArrayIndexOutOfBoundsException exception) {
		}
	}
	
	

	public static void analisadorSintatico() {
		String aux1 = "";
		String aux2 = "";
		//tokenAS = AnalisadorLexico.token;
		if (tokenAS.isEmpty())
			Erro.tratarError1(0, "sintatico");
		int i = 1;
		if (PalavraReservada.sprograma.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			if (PalavraReservada.sidentificador.equals(tokenAS.get(i))) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i - 1).toString(), "nomedeprograma", nivel, "");
				GeradorCodigo.exibir_codigo_objeto("", "START", "", "");
				i = pegarToken(i); // Ler proximo Token
				if (PalavraReservada.sponto_virgula.equals(tokenAS.get(i))) {
					i = analisarBloco(i);
					if (PalavraReservada.sponto.equals(tokenAS.get(i))) {
						for (int a = 0; a < allocAux.size(); a++) {
							if (allocAux.get(a).getNivel() == nivel) {
								aux1 = allocAux.get(a).getParam1();
								aux2 = allocAux.get(a).getParam2();
								int aux3 = Integer.parseInt(aux1) - Integer.parseInt(aux2);
								allocAux.get(a).setParam1(Integer.toString(aux3));
							}
						}
						if (!aux1.equals(""))
							GeradorCodigo.exibir_codigo_objeto("", "DALLOC", aux1, aux2);
						GeradorCodigo.exibir_codigo_objeto("", "HLT", "", "");
						fimAnalisador(i);
					} else
						Erro.tratarError1(i, "sintatico");
				} else
					Erro.tratarError1(i, "sintatico");
			} else
				Erro.tratarError1(i, "sintatico");
		} else
			Erro.tratarError1(i, "sintatico");
	}

	public static int analisarBloco(int i) {
		i = pegarToken(i); // Ler Proximo Token
		i = analisaEtVariaveis(i);
		i = analisaSubrotinas(i);
		i = analisaComandos(i);
		

		return i;

	}

	private static int analisaComandos(int i) {
		if (PalavraReservada.sinicio.equals(tokenAS.get(i))) {
			i = pegarToken(i); // Ler proximo Token
			i = analisaComandoSimples(i);
			while (!PalavraReservada.sfim.equals(tokenAS.get(i))) {
				if (PalavraReservada.sponto.equals(tokenAS.get(i))) {
					fimAnalisador(i);
				}
				if (PalavraReservada.sponto_virgula.equals(tokenAS.get(i))) {
					i = pegarToken(i); // Ler Proximo token
					if (!PalavraReservada.sfim.equals(tokenAS.get(i))) {
						i = analisaComandoSimples(i);
					}
				} else {
					Erro.tratarError1(i, "sintatico");
				}
			}
			i = pegarToken(i); // Ler Proximo token

		} else
			Erro.tratarError1(i, "sintatico");
		return i;
	}

	static void fimAnalisador(int i) {
        if(Erro.FlgError) {
		JOptionPane.showMessageDialog(null, "Codigo Compilado Com Sucesso");
		EscreverProgramaObj.EscreverProgramaObjeto(GeradorCodigo.programaObjeto);
		
		new Vm();
        }else {
        	
        	Limpar.limpar();
        	
        	new CSD();
        	Erro.FlgError = true;
        }

	}

	private static int analisaComandoSimples(int i) {
			AnalisadorSemantico.validar_tipoAUX(i);
		String aux = "";
		int aux1 = 0;
		if (PalavraReservada.sidentificador.equals(tokenAS.get(i))) {
			aux = tokenAS.get(i - 1).toString();
			i = analisaAtribChProcedimento(i);
			aux1 = GeradorCodigo.returnIndex(aux, nivel);
			if (!(aux1 == -1))
				GeradorCodigo.exibir_codigo_objeto("", "STR", Integer.toString(aux1), "");

		} else {
			if (PalavraReservada.sse.equals(tokenAS.get(i))) {
				i = analisaSe(i);
			} else {
				if (PalavraReservada.senquanto.equals(tokenAS.get(i))) {
					i = analisaEnquanto(i);
				} else {
					if (PalavraReservada.sleia.equals(tokenAS.get(i))) {
						i = analisaLeia(i);
					} else {
						if (PalavraReservada.sescreva.equals(tokenAS.get(i))) {
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
		if (PalavraReservada.satribuicao.equals(tokenAS.get(i))) {
			if (AnalisadorSemantico.pesquisa_declvarfunc_tabela(tokenAS.get(i - 3).toString(), "var", nivel, "")) {

			} else {
				JOptionPane.showMessageDialog(null, "Erro Semantico: FUNCAO OU VARIAVEL NÃO DECLARADAS");
				Erro.tratarError1(i - 3, "semantico");
			}
			i = analisaAtribuicao(i);
			
			if (!AnalisadorSemantico.pesquisa_declfunc_tabela(tokenAS.get(i - 3).toString(), "funcao", nivel, "")) {
				rotuloAux = GeradorCodigo.returnRotulo(tokenAS.get(i - 3).toString());
				if(rotuloAux.equals("-1")) {
					JOptionPane.showMessageDialog(null, "Erro Semantico: FUNCAO OU VARIAVEL NÃO DECLARADAS");
					Erro.tratarError1(i, "semantico");
				}
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
			JOptionPane.showMessageDialog(null, "Erro Semantico: PROCEDIMENTO COM NOME INVALIDO");
			Erro.tratarError1(i - 3, "semantico");
		} else {
			rotuloAux = GeradorCodigo.returnRotulo(tokenAS.get(i - 3).toString());
			if(rotuloAux.equals("-1")) {
				JOptionPane.showMessageDialog(null, "Erro Semantico: PROCEDIMENTO NÃO DECLARADO");
				Erro.tratarError1(i, "semantico");
			}
			GeradorCodigo.exibir_codigo_objeto("", "CALL", rotuloAux, "");
		}

		return i;
	}

	private static int analisaAtribuicao(int i) {
		String tipoAux = AnalisadorSemantico.retorna_tipo(tokenAS.get(i - 3).toString());
		if (tipoAux.equals("sinteiro")) {
			flgExpressaoBooleana = false;
		}

		i = pegarToken(i);
		i = analiseExpressao(i);
		if (flgExpressaoBooleana && tipoAux.equals("sinteiro")) {
			JOptionPane.showMessageDialog(null, "Erro Semantico: EXRPESSÃO NÃO BOOLEANA");

			Erro.tratarError1(i, "semantico");
		}

		return i;
	}

	private static int analisaSe(int i) {
		int auxrot = rotulo;
		int auxrot2 = rotulo;
		i = pegarToken(i);
		flgExpressaoBooleana = false;
		i = analiseExpressao(i);
		if (!flgExpressaoBooleana) {
			JOptionPane.showMessageDialog(null, "Erro Semantico: EXRPESSÃO NÃO BOOLEANA");

			Erro.tratarError1(i, "semantico");
		}

		if (PalavraReservada.sentao.equals(tokenAS.get(i))) {
			GeradorCodigo.exibir_codigo_objeto("", "JMPF", "L" + Integer.toString(auxrot), "");
			rotulo++;
			i = pegarToken(i);
			i = analisaComandoSimples(i);
			auxrot2 = rotulo;
			GeradorCodigo.exibir_codigo_objeto("", "JMP", "L" + Integer.toString(auxrot2), "");
			rotulo++;

			if (PalavraReservada.ssenao.equals(tokenAS.get(i))) {
				GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(auxrot), "NULL", "", "");
				i = pegarToken(i);
				i = analisaComandoSimples(i);

			} else
				GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(auxrot), "NULL", "", "");

		} else
			Erro.tratarError1(i, "sintatico");
		GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(auxrot2), "NULL", "", "");
		return i;
	}

	private static int analisaEnquanto(int i) {
		int auxrot1, auxrot2;
		auxrot1 = rotulo;
		GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(rotulo), "NULL", "", "");
		rotulo++;

		i = pegarToken(i);
		flgExpressaoBooleana = false;
		i = analiseExpressao(i);
		if (!flgExpressaoBooleana) {
			JOptionPane.showMessageDialog(null, "Erro Semantico: EXPRESSÃO NÃO BOOLEANA");
			Erro.tratarError1(i, "semantico");
		}

		if (PalavraReservada.sfaca.equals(tokenAS.get(i))) {
			auxrot2 = rotulo;
			GeradorCodigo.exibir_codigo_objeto("", "JMPF", "L" + Integer.toString(rotulo), "");
			rotulo++;
			i = pegarToken(i);
			i = analisaComandoSimples(i);
			GeradorCodigo.exibir_codigo_objeto("", "JMP", "L" + Integer.toString(auxrot1), "");
			GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(auxrot2), "NULL", "", "");

		} else
			Erro.tratarError1(i, "sintatico");
		return i;
	}

	private static int analiseExpressao(int i) {
		i = analiseExpressaoSimples(i);
		String aux = "";
		if (PalavraReservada.smaior.equals(tokenAS.get(i)) || PalavraReservada.smaiorig.equals(tokenAS.get(i))
				|| PalavraReservada.smenor.equals(tokenAS.get(i)) || PalavraReservada.smenorig.equals(tokenAS.get(i))
				|| PalavraReservada.sdif.equals(tokenAS.get(i)) || PalavraReservada.sig.equals(tokenAS.get(i))) {

			if (PalavraReservada.smaior.equals(tokenAS.get(i))) {
				aux = "CMA";
				flgExpressaoBooleana = true;
			}
			if (PalavraReservada.smaiorig.equals(tokenAS.get(i))) {
				aux = "CMAQ";
				flgExpressaoBooleana = true;
			}
			if (PalavraReservada.smenor.equals(tokenAS.get(i))) {
				aux = "CME";
				flgExpressaoBooleana = true;
			}
			if (PalavraReservada.smenorig.equals(tokenAS.get(i))) {
				aux = "CMEQ";
				flgExpressaoBooleana = true;
			}
			if (PalavraReservada.sdif.equals(tokenAS.get(i))) {
				aux = "CDIF";
				flgExpressaoBooleana = true;
			}
			if (PalavraReservada.sig.equals(tokenAS.get(i))) {
				aux = "CEQ";
				flgExpressaoBooleana = true;
			}

			AnalisadorSemantico.validar_tipo(tokenAS.get(i).toString(), "");
			
			i = pegarToken(i);
			i = analiseExpressaoSimples(i);

			GeradorCodigo.exibir_codigo_objeto("", aux, "", "");
		}

		return i;
	}

	private static int analiseExpressaoSimples(int i) {
		String aux = "";

		if (PalavraReservada.smais.equals(tokenAS.get(i)) || PalavraReservada.smenos.equals(tokenAS.get(i))) {
			if (PalavraReservada.smais.equals(tokenAS.get(i)))
				aux = "ADD";
			if (PalavraReservada.smenos.equals(tokenAS.get(i))) {
				aux = "INV";
			}

			i = pegarToken(i);

		}
		i = analiseTermo(i);
		if (aux != "")
			GeradorCodigo.exibir_codigo_objeto("", aux, "", "");

		while (PalavraReservada.smais.equals(tokenAS.get(i)) || PalavraReservada.smenos.equals(tokenAS.get(i))
				|| PalavraReservada.sou.equals(tokenAS.get(i))) {
			if (PalavraReservada.smais.equals(tokenAS.get(i)))
				aux = "ADD";
			if (PalavraReservada.smenos.equals(tokenAS.get(i)))
				aux = "SUB";
			if (PalavraReservada.sou.equals(tokenAS.get(i))) {
				aux = "OR";
				AnalisadorSemantico.expressão.clear();
			}

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
		while (PalavraReservada.smult.equals(tokenAS.get(i)) || PalavraReservada.sdiv.equals(tokenAS.get(i))
				|| PalavraReservada.se.equals(tokenAS.get(i))) {
			if (PalavraReservada.smult.equals(tokenAS.get(i)))
				aux = "MULT";
			if (PalavraReservada.sdiv.equals(tokenAS.get(i)))
				aux = "DIVI";
			if (PalavraReservada.se.equals(tokenAS.get(i))) {
				aux = "AND";
				AnalisadorSemantico.expressão.clear();
			}

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
		if (PalavraReservada.sidentificador.equals(tokenAS.get(i))) {
			tipo = AnalisadorSemantico.retorna_tipo(tokenAS.get(i - 1).toString());
			if (tipo.equals("sbooleano"))
				flgExpressaoBooleana = true;

			AnalisadorSemantico.validar_tipo(tokenAS.get(i - 1).toString(), tipo);
			if (AnalisadorSemantico.pesquisa_declvarfunc_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
				aux = GeradorCodigo.returnIndex(tokenAS.get(i - 1).toString(), nivel);
				if (aux != -1)
					GeradorCodigo.exibir_codigo_objeto("", "LDV", Integer.toString(aux), "");
			} else {
				JOptionPane.showMessageDialog(null, "Erro Semantico:VARIAVEL NÃO DECLARADA");
				Erro.tratarError1(i, "semantico");

			}
			i = analisaChamadaFuncao(i);
		} else {
			if (PalavraReservada.snumero.equals(tokenAS.get(i))) {
				GeradorCodigo.exibir_codigo_objeto("", "LDC", tokenAS.get(i - 1).toString(), "");
				i = pegarToken(i);
			} else {
				if (PalavraReservada.snao.equals(tokenAS.get(i))) {
					i = pegarToken(i);
					i = analiseFator(i);
					//i = analiseExpressao(i);
					GeradorCodigo.exibir_codigo_objeto("", "NEG", "", "");


				} else {
					if (PalavraReservada.sabre_parenteses.equals(tokenAS.get(i))) {
						i = pegarToken(i);
						i = analiseExpressao(i);
						String tip = AnalisadorSemantico.retorna_tipo(tokenAS.get(i - 3).toString());
						if (tip.equals("sbooleano"))
							flgExpressaoBooleana = true;
						if (PalavraReservada.sfecha_parenteses.equals(tokenAS.get(i))) {
							i = pegarToken(i);
						} else
							Erro.tratarError1(i, "sintatico");

					} else {
						if (PalavraReservada.sverdadeiro.equals(tokenAS.get(i)) || PalavraReservada.sfalso.equals(tokenAS.get(i))) {
							flgExpressaoBooleana = true;
							AnalisadorSemantico.validar_tipoAUX(i);
							if (PalavraReservada.sverdadeiro.equals(tokenAS.get(i)))
								GeradorCodigo.exibir_codigo_objeto("", "LDC", "1", "");
							else
								GeradorCodigo.exibir_codigo_objeto("", "LDC", "0", "");
							i = pegarToken(i);
						} else {
							if (PalavraReservada.smult.equals(tokenAS.get(i)) || PalavraReservada.sdiv.equals(tokenAS.get(i))
									|| PalavraReservada.sse.equals(tokenAS.get(i))) {
								return i;
							} else
								Erro.tratarError1(i, "sintatico");
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
		if (PalavraReservada.sabre_parenteses.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			if (PalavraReservada.sidentificador.equals(tokenAS.get(i))) {
				if (AnalisadorSemantico.pesquisa_declvar_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
					GeradorCodigo.exibir_codigo_objeto("", "RD", "", "");
					aux = GeradorCodigo.returnIndex(tokenAS.get(i - 1).toString(), nivel);
					GeradorCodigo.exibir_codigo_objeto("", "STR", Integer.toString(aux), "");

				} else {
					JOptionPane.showMessageDialog(null, "Erro Semantico: VARIAVEL NÃO DECLARADA");
					Erro.tratarError1(i, "semantico");

				}
				i = pegarToken(i);
				if (PalavraReservada.sfecha_parenteses.equals(tokenAS.get(i))) {
					i = pegarToken(i);
				}

			} else
				Erro.tratarError1(i, "sintatico");

		} else
			Erro.tratarError1(i, "sintatico");

		return i;
	}

	private static int analisaEscreva(int i) {
		i = pegarToken(i);
		int auxIndex = 0;
		String rotuloAux = "";
		if (PalavraReservada.sabre_parenteses.equals(tokenAS.get(i))) {
			i = pegarToken(i);
			if (PalavraReservada.sidentificador.equals(tokenAS.get(i))) {
				if (AnalisadorSemantico.pesquisa_declvar_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
					auxIndex = GeradorCodigo.returnIndex(tokenAS.get(i - 1).toString(), nivel);
					GeradorCodigo.exibir_codigo_objeto("", "LDV", Integer.toString(auxIndex), "");
					GeradorCodigo.exibir_codigo_objeto("", "PRN", "", "");
				} else {
					if (AnalisadorSemantico.pesquisa_declfunc_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
						rotuloAux = GeradorCodigo.returnRotulo(tokenAS.get(i - 1).toString());
						if(rotuloAux.equals("-1")) {
							JOptionPane.showMessageDialog(null, "Erro Semantico: FUNCAO OU VARIAVEL NÃO DECLARADAS");
							Erro.tratarError1(i, "semantico");
						}
						GeradorCodigo.exibir_codigo_objeto("", "CALL", rotuloAux, "");
						GeradorCodigo.exibir_codigo_objeto("", "PRN", "", "");
					} else {
						JOptionPane.showMessageDialog(null, "Erro Semantico: FUNCAO OU VARIAVEL NÃO DECLARADAS");
						Erro.tratarError1(i, "semantico");

					}

				}
				i = pegarToken(i);
				if (PalavraReservada.sfecha_parenteses.equals(tokenAS.get(i))) {
					i = pegarToken(i);
				} else
					Erro.tratarError1(i, "sintatico");
			} else
				Erro.tratarError1(i, "sintatico");
		} else
			Erro.tratarError1(i, "sintatico");
		return i;
	}

	private static int analisaSubrotinas(int i) {
		int flag = 0;
		int auxrot = rotulo;
		;
		if (PalavraReservada.sprocedimento.equals(tokenAS.get(i)) || PalavraReservada.sfuncao.equals(tokenAS.get(i))) {
			GeradorCodigo.exibir_codigo_objeto("", "JMP", "L" + Integer.toString(auxrot), "");
			rotulo++;
			flag = 1;

		}

		while (PalavraReservada.sprocedimento.equals(tokenAS.get(i)) || PalavraReservada.sfuncao.equals(tokenAS.get(i))) {
			if (PalavraReservada.sprocedimento.equals(tokenAS.get(i))) {
				i = analisaDeclaracaoProcedimento(i);
			} else {
				i = analisaDeclaracaoFuncao(i);
			}
			if (PalavraReservada.sponto_virgula.equals(tokenAS.get(i))) {
				i = pegarToken(i);
			} else
				Erro.tratarError1(i, "sintatico");
		}
		if (flag == 1) {
			GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(auxrot), "NULL", "", "");

		}

		return i;
	}

	private static int analisaDeclaracaoProcedimento(int i) {
		String aux1 = "";
		String aux2 = "";
		i = pegarToken(i); // ler proximo token
		if (PalavraReservada.sidentificador.equals(tokenAS.get(i))) {
			if (AnalisadorSemantico.pesquisa_declproc_tabela(tokenAS.get(i - 1).toString(), "procedimento", nivel,
					"")) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i - 1).toString(), "procedimento", nivel,
						"L" + Integer.toString(rotulo));
				GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(rotulo), "NULL", "", "");
				nivel++;
				rotulo++;
				i = pegarToken(i); // ler proximo token
				if (PalavraReservada.sponto_virgula.equals(tokenAS.get(i))) {
					i = analisarBloco(i);
				} else
					Erro.tratarError1(i, "sintatico");
			} else {
				JOptionPane.showMessageDialog(null, "Erro Semantico: PROCEDIMENTO COM NOME INVALIDO");
				Erro.tratarError1(i, "semantico");

			}

		} else
			Erro.tratarError1(i, "sintatico");
		AnalisadorSemantico.remover_nivel_tabelaSimbolo(nivel);

		for (int a = 0; a < allocAux.size(); a++) {
			if (allocAux.get(a).getNivel() == nivel) {
				aux1 = allocAux.get(a).getParam1();
				aux2 = allocAux.get(a).getParam2();
				int aux3 = Integer.parseInt(aux1) - Integer.parseInt(aux2);
				allocAux.get(a).setParam1(Integer.toString(aux3));
			}
		}

		if (!aux1.equals("")) {
			GeradorCodigo.exibir_codigo_objeto("", "DALLOC", aux1, aux2);
			countDalloc = countDalloc + Integer.parseInt(aux2);
		}
		nivel--;

		GeradorCodigo.exibir_codigo_objeto("", "RETURN", "", "");

		return i;
	}

	private static int analisaDeclaracaoFuncao(int i) {
		String aux1 = "";
		String aux2 = "";
		i = pegarToken(i); // ler proximo token
		if (PalavraReservada.sidentificador.equals(tokenAS.get(i))) {
			if (AnalisadorSemantico.pesquisa_declfunc_tabela(tokenAS.get(i - 1).toString(), "funcao", nivel, "")) {
				AnalisadorSemantico.inserirTabela(tokenAS.get(i - 1).toString(), "funcao", nivel,
						"L" + Integer.toString(rotulo));
				GeradorCodigo.exibir_codigo_objeto("L" + Integer.toString(rotulo), "NULL", "", "");
				nivel++;
				rotulo++;
				i = pegarToken(i); // ler proximo token
				if (PalavraReservada.sdoispontos.equals(tokenAS.get(i))) {
					i = pegarToken(i); // ler proximo token
					if (PalavraReservada.sinteiro.equals(tokenAS.get(i)) || PalavraReservada.sbooleano.equals(tokenAS.get(i))) {
						AnalisadorSemantico.coloca_tipo_tabela(null, "funcao", i, null, tokenAS.get(i).toString());
						i = pegarToken(i); // ler proximo token
						if (PalavraReservada.sponto_virgula.equals(tokenAS.get(i))) {
							i = analisarBloco(i);
						}
					} else
						Erro.tratarError1(i, "sintatico");
				} else
					Erro.tratarError1(i, "sintatico");
			} else {
				Erro.tratarError1(i, "semantico");
				JOptionPane.showMessageDialog(null, "Erro Semantico: FUNCAO NÃO DECLARADA");
			}

		} else
			Erro.tratarError1(i, "sintatico");

		AnalisadorSemantico.remover_nivel_tabelaSimbolo(nivel);

		for (int a = 0; a < allocAux.size(); a++) {
			if (allocAux.get(a).getNivel() == nivel) {
				aux1 = allocAux.get(a).getParam1();
				aux2 = allocAux.get(a).getParam2();
				int aux3 = Integer.parseInt(aux1) - Integer.parseInt(aux2);
				allocAux.get(a).setParam1(Integer.toString(aux3));
			}
		}

		if (!aux1.equals("")) {
			GeradorCodigo.exibir_codigo_objeto("", "RETURNF", aux1, aux2);
			countDalloc = countDalloc + Integer.parseInt(aux2);
		} else {
			GeradorCodigo.exibir_codigo_objeto("", "RETURNF", "", "");

		}

		nivel--;

		return i;
	}

	public static int analisaEtVariaveis(int i) {
		if (PalavraReservada.svar.equals(tokenAS.get(i))) {
			i = pegarToken(i); // Ler Proximo Token
			if (PalavraReservada.sidentificador.equals(tokenAS.get(i))) {
				while (PalavraReservada.sidentificador.equals(tokenAS.get(i))) {
					i = analisaVariaveis(i);
					if (PalavraReservada.sponto_virgula.equals(tokenAS.get(i))) {
						i = pegarToken(i); // Ler Proximo Token
					} else
						Erro.tratarError1(i, "sintatico");
				}
			} else
				Erro.tratarError1(i, "sintatico");
		}

		return i;
	}

	public static int analisaVariaveis(int i) {
		int allocAux2 = 0;

		do {
			if (PalavraReservada.sidentificador.equals(tokenAS.get(i))) {
				GeradorCodigo.pilhaVar.add(tokenAS.get(i - 1));

				if (AnalisadorSemantico.pesquisar_duplicvar_tabela(tokenAS.get(i - 1).toString(), "var", nivel, "")) {
					AnalisadorSemantico.inserirTabela(tokenAS.get(i - 1).toString(), "var", nivel, "");
					allocAux2++;
				} else {
					JOptionPane.showMessageDialog(null, "Erro Semantico: VARIAVEL COM NOME INVALIDO");
					Erro.tratarError1(i, "semantico");

				}
				i = pegarToken(i);
				if (PalavraReservada.svirgula.equals(tokenAS.get(i)) || PalavraReservada.sdoispontos.equals(tokenAS.get(i))) {
					if (PalavraReservada.svirgula.equals(tokenAS.get(i))) {
						i = pegarToken(i);
						if (PalavraReservada.sdoispontos.equals(tokenAS.get(i))) {
							Erro.tratarError1(i, "sintatico");
						}
					}
				} else
					Erro.tratarError1(i, "sintatico");
			} else
				Erro.tratarError1(i, "sintatico");
		} while (!PalavraReservada.sdoispontos.equals(tokenAS.get(i)));
		countAlloc++;
		AllocDTO alloc = new AllocDTO();
		alloc.setParam1(Integer.toString(allocAux1 - countDalloc));
		alloc.setParam2(Integer.toString(allocAux2));
		alloc.setNivel(nivel);
		allocAux.add(alloc);

		// allocAux
		GeradorCodigo.exibir_codigo_objeto("", "ALLOC", Integer.toString(allocAux1 - countDalloc),
				Integer.toString(allocAux2));
		allocAux1 = allocAux1 + allocAux2;

		i = pegarToken(i); // Ler Proximo Token

		i = analisaTipo(i);
		return i;
	}

	public static int analisaTipo(int i) {

		if (PalavraReservada.sinteiro.equals(tokenAS.get(i)) || PalavraReservada.sbooleano.equals(tokenAS.get(i))) {
			AnalisadorSemantico.coloca_tipo_tabela(null, "var", i, null, tokenAS.get(i).toString());
			i = pegarToken(i); // Ler Proximo Token

		} else {
			Erro.tratarError1(i, "sintatico");

		}
		return i;
	}

	public static int pegarToken(int i) {
		try {
		i += 2;
		contadorPegaToken++;
		AnalisadorLexico.chamarToken(Fonte.token);
		tokenAS.push(AnalisadorLexico.token1.get(contadorPegaToken).getLexema());
		tokenAS.push(AnalisadorLexico.token1.get(contadorPegaToken).getSimbolo());
		tokenASerror.push(AnalisadorLexico.token1.get(contadorPegaToken).getNumLinha());
		tokenASerror.push(AnalisadorLexico.token1.get(contadorPegaToken).getNumLinha());
		}catch(IndexOutOfBoundsException exception) {
		}
		
		return i;
	}


}