import java.util.Stack;

public class GeradorCodigo {
	static Stack pilhaVar = new Stack();
	
	
	public static void exibir_codigo_objeto(String param1, String param2,String param3,String param4){
		System.out.println(param1 + " " + param2 + " " + param3 + " " + param4 + " " );
		
	}
	
	public static int returnIndex(String lexema) {
			
		return pilhaVar.indexOf(lexema);
	}
	
	public static String returnRotulo(String lexema) {
		for(int i = 0; i<AnalisadorSemantico.simbolos.size();i++) {
			if(lexema.equals(AnalisadorSemantico.simbolos.get(i).getLexema())) {
				return AnalisadorSemantico.simbolos.get(i).getRotulo();
			}
		}
		
		
		return "O";
		
	}
	
}
