import java.util.Stack;

public class AnalisadorLexico {
	

	public static void eliminarComentario(Stack token) {
		Stack aux = new Stack();
		int abre = 0;
		int fecha = token.size();

		for(int i = 0; i<token.size();i++) {
			if(token.get(i).equals('{') && abre == 0) {
				abre = i;
			}
			if(token.get(i).equals('}') && fecha > abre ) {
				fecha = i;
			}
		}
		
		for(int i = 0; i<token.size();i++) {
			if(i < abre) {
				aux.add(token.get(i));
			}
			if(i > fecha) {
				aux.add(token.get(i));
			}
		}
	
		
		
		System.out.println(abre);
		System.out.println(fecha);
		System.out.println(aux);
		
	}
	
	
}
