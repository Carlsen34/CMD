import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;

public class AnalisadorLexico {
	
   public static int count = -1;
	static List<Token> token1 = new ArrayList<Token>();


   public static void  AnaliseLexica(List<Token> token) {

		   count++;
		   tratarComentarioEspaco(token,count);
		   pegaToken(token,count);
		 
	    AnalisadorSintatico.analisadorSintatico1(token1);

	   
	   
   }
	
   public static void chamarToken(List<Token> token) {
	   
	   count++;
	   tratarComentarioEspaco(token,count);
	   pegaToken(token,count);

   
}
	




	private static void pegaToken(List<Token> token, int i) {
		if(isDigit(token.get(i).getLexema())) {
			trataDigit(token,i);
		}else {
			if(isLetra(token.get(i).getLexema())) {
				trataIdentificador(token,i);
			}else {
				if(token.get(i).getLexema().equals(":")) {
					tratarAtribuicao(token,i);
				}else {
					if(token.get(i).getLexema().equals("+")||token.get(i).getLexema().equals("-")||token.get(i).getLexema().equals("*")) {
				      tratarAritimetico(token,i);

					}else {
						if(token.get(i).getLexema().equals("<")||token.get(i).getLexema().equals(">")||token.get(i).getLexema().equals("=") ||token.get(i).getLexema().equals("!") ) {
							tratarRelacional(token,i);
						}else {
							if(token.get(i).getLexema().equals(";")||token.get(i).getLexema().equals(",")||token.get(i).getLexema().equals(".")
									||token.get(i).getLexema().equals("(")||token.get(i).getLexema().equals(")")) {
								tratarPontuacao(token,i);
							}else {
								JOptionPane.showMessageDialog(null,"Error Lexico :" +" "+ token.get(i).getLexema());
								Erro.tratarError1(token.get(i).getNumLinha(), "lexico");
							}
						}
					}
				}
			}
		}
	
}
	
	private static void tratarPontuacao(List<Token> token, int i) {
		if(token.get(i).getLexema().equals(";")) {
			Token t = new Token();
			t.setLexema(";");
			t.setSimbolo("sponto_virgula");
			t.setNumLinha(token.get(i).getNumLinha());
			count = i;

			token1.add(t);
		}
		if(token.get(i).getLexema().equals(",")) {
			Token t = new Token();
			t.setLexema(",");
			t.setSimbolo("svirgula");
			t.setNumLinha(token.get(i).getNumLinha());
			count = i;
			token1.add(t);
		}
		if(token.get(i).getLexema().equals(".")) {
			Token t = new Token();
			t.setLexema(".");
			t.setSimbolo("sponto");
			t.setNumLinha(token.get(i).getNumLinha());
			count = i;

			token1.add(t);
			
		}
		if(token.get(i).getLexema().equals("(")) {
			Token t = new Token();
			t.setLexema("(");
			t.setSimbolo("sabre_parenteses");
			t.setNumLinha(token.get(i).getNumLinha());
			count = i;

			token1.add(t);
		}
		if(token.get(i).getLexema().equals(")")) {
			Token t = new Token();
			t.setLexema(")");
			t.setSimbolo("sfecha_parenteses");
			t.setNumLinha(token.get(i).getNumLinha());
			count = i;

			token1.add(t);
		}
		i++;		
	}


	private static void tratarAritimetico(List<Token> token, int i) {

		if(token.get(i).getLexema().equals("+")) {
			Token t = new Token();
			t.setLexema("+");
			t.setSimbolo("smais");
			t.setNumLinha(token.get(i).getNumLinha());

			token1.add(t);
		}
		if(token.get(i).getLexema().equals("-")) {
			Token t = new Token();
			t.setLexema("-");
			t.setSimbolo("smenos");
			t.setNumLinha(token.get(i).getNumLinha());

			token1.add(t);
		}
		if(token.get(i).getLexema().equals("*")) {
			Token t = new Token();
			t.setLexema("+");
			t.setSimbolo("smult");
			t.setNumLinha(token.get(i).getNumLinha());

			token1.add(t);
		}
		i++;
		
	}


	private static void tratarAtribuicao(List<Token> token, int i) {
		String aux = token.get(i).getLexema();
		i++;
	    while(token.get(i).getLexema().equals("=")) {
    		aux = aux + token.get(i).getLexema();
        	i++;
        }
	    i--;
		
		Token t = new Token();
		t.setLexema(aux);
		t.setNumLinha(token.get(i).getNumLinha());
		
		
		if(aux.equals(":"))t.setSimbolo("sdoispontos");
		if(aux.equals(":=")) {
			t.setSimbolo("satribuicao");
			count++;
		}
		count = i;
		token1.add(t);		
	}


	private static void tratarRelacional(List<Token> token, int i) {
		String aux = token.get(i).getLexema();
        i++;
        while(token.get(i).getLexema().equals("=")) {
    		aux = aux + token.get(i).getLexema();
        	i++;
        }
        i--;
        
        Token t = new Token();
		t.setLexema(aux);
		t.setNumLinha(token.get(i).getNumLinha());
		
		if(aux.equals("="))	t.setSimbolo("sig");
		if(aux.equals("!=")) {
			t.setSimbolo("sdif");
			count++;
		}
		if(aux.equals(">=")) {
			t.setSimbolo("smaiorig");
			count++;
		}
		if(aux.equals("<=")) {
			t.setSimbolo("smenorig");
			count++;

		}
		if(aux.equals(">"))  t.setSimbolo("smaior");
		if(aux.equals("<"))	t.setSimbolo("smenor");
		count = i;
		token1.add(t);
        
	}


	private static void trataDigit(List<Token> token, int i) {
		String aux = token.get(i).getLexema();
        i++;
		while(isDigit(token.get(i).getLexema())) {
		aux = aux + token.get(i).getLexema();
		 i++;
		}
		i--;
		
		Token t = new Token();
		t.setLexema(aux);
		t.setSimbolo("snumero");
		t.setNumLinha(token.get(i).getNumLinha());
		count = i;
		token1.add(t);
	}
	


	private static void trataIdentificador(List<Token> token, int i) {
		Boolean flgIdentificador = true;
		String aux = token.get(i).getLexema();
		i++;
		while(isLetra(token.get(i).getLexema()) || isDigit(token.get(i).getLexema()) || token.get(i).getLexema().equals("_") ) {
			aux = aux + token.get(i).getLexema();
			i++;

		}
		i--;

		Token t = new Token();
		t.setLexema(aux);
		t.setNumLinha(token.get(i).getNumLinha());
		
		if(aux.equals("programa")) {
			flgIdentificador = false;
			t.setSimbolo("sprograma");
		}
		if(aux.equals("se")) {
			flgIdentificador = false;
			t.setSimbolo("sse");
		}
		if(aux.equals("entao") || aux.equals("então")) {
			flgIdentificador = false;
			t.setSimbolo("sentao");
		}
		if(aux.equals("enquanto")) {
			flgIdentificador = false;
			t.setSimbolo("senquanto");
		}
		if(aux.equals("faca")) {
			flgIdentificador = false;
			t.setSimbolo("sfaca");
		}
		if(aux.equals("inicio") || aux.equals("início")) {
			flgIdentificador = false;
			t.setSimbolo("sinicio");
		}
		if(aux.equals("fim")) {
			flgIdentificador = false;
			t.setSimbolo("sfim");
		}
		if(aux.equals("escreva")) {
			flgIdentificador = false;
			t.setSimbolo("sescreva");
		}
		if(aux.equals("leia")) {
			flgIdentificador = false;
			t.setSimbolo("sleia");
		}
		if(aux.equals("var")) {
			flgIdentificador = false;
			t.setSimbolo("svar");
		}
		if(aux.equals("inteiro")) {
			flgIdentificador = false;
			t.setSimbolo("sinteiro");
		}
		if(aux.equals("booleano")) {
			flgIdentificador = false;
			t.setSimbolo("sbooleano");
		}
		if(aux.equals("verdadeiro")) {
			flgIdentificador = false;
			t.setSimbolo("sverdadeiro");
		}
		if(aux.equals("falso")) {
			flgIdentificador = false;
			t.setSimbolo("sfalso");
		}
		if(aux.equals("procedimento")) {
			flgIdentificador = false;
			t.setSimbolo("sprocedimento");
		}
		if(aux.equals("senao") || aux.equals("senão")) {
			flgIdentificador = false;
			t.setSimbolo("ssenao");
		}
		if(aux.equals("funcao") || aux.equals("funcão")) {
			flgIdentificador = false;
			t.setSimbolo("sfuncao");
		}
		if(aux.equals("div")) {
			flgIdentificador = false;
			t.setSimbolo("sdiv");
		}
		if(aux.equals("e")) {
			flgIdentificador = false;
			t.setSimbolo("se");
		}
		if(aux.equals("ou")) {
			flgIdentificador = false;
			t.setSimbolo("sou");
		}
		if(aux.equals("nao") || aux.equals("não")) {
			flgIdentificador = false;
			t.setSimbolo("snao");
		}
		
		if(flgIdentificador) {
			t.setSimbolo("sidentificador");

		}
		
		count = i;

        token1.add(t);


		
		
		
		count = i;
	}


	private static void print(List<Token> token1) {

		for(int i =0;i<token1.size();i++) {
			//	System.out.println(token1.get(i).getLexema());
			//System.out.println(token1.get(i).getSimbolo());
			//System.out.println(token1.get(i).getNumLinha());

		}
		
	}



	private static void tratarComentarioEspaco(List<Token> token, int i) {
		
		while(token.get(i).getLexema().equals(" ") || token.get(i).getLexema().equals("{")|| token.get(i).getLexema().equals("\t") || token.get(i).getLexema().equals("\n")) {
	        if(token.get(i).getLexema().equals("{")) {
	        	while(!token.get(i).getLexema().equals("}")) {
	        		token.remove(i);
	        	}
        		token.remove(i);
	        }
	        while(token.get(i).getLexema().equals(" ") || token.get(i).getLexema().equals("\t") || token.get(i).getLexema().equals("\n") ) {
        		token.remove(i);
	        }

		}
		
	
}





	public static boolean isDigit(String aux) {
		if (aux.equals("0") || aux.equals("1") || aux.equals("2") || aux.equals("3") || aux.equals("4")
				|| aux.equals("5") || aux.equals("6") || aux.equals("7") || aux.equals("8") || aux.equals("9")) {
			return true;
		}
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
