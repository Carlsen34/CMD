import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Limpar {

	public static void limpar() {
		
		AnalisadorSintatico.tokenAS = new Stack();
		AnalisadorSintatico.tokenASerror = new Stack();
		AnalisadorSintatico.nivel = 0;
		AnalisadorSintatico.tipo = "";
		AnalisadorSintatico.rotulo = 1;
		AnalisadorSintatico.allocAux1 = 0;
		AnalisadorSintatico.countAlloc = 0;
		AnalisadorSintatico.nivelAux = 0;
		AnalisadorSintatico.allocAux.clear();
		AnalisadorSintatico.countDalloc = 0;
		AnalisadorSintatico.flgExpressaoBooleana = true;
		AnalisadorSintatico.contadorPegaToken = 0;
		
		
		
		AnalisadorSemantico.tabelaSimbolo.clear();
		AnalisadorSemantico.expressão = new Stack();
		AnalisadorSemantico.errorToken = new Stack();
		
		
		AnalisadorLexico.count = -1;
		AnalisadorLexico.token1.clear();
		
		
		GeradorCodigo.pilhaVar.clear();
		GeradorCodigo.auxDalloc.clear();
		GeradorCodigo.flgAux = false;
		GeradorCodigo.auxParam3 = 0;
		GeradorCodigo.auxParam4 = 0;
		GeradorCodigo.auxRetornoFuncao = 0;
		GeradorCodigo.auxRetornoFuncaoTipo = false;
		GeradorCodigo.programaObjeto = "";
		

		
		Fonte.token.clear();
		
	}

}
